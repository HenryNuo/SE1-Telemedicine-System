package com.hamilton.web;

import com.hamilton.bean.*;
import com.hamilton.dao.*;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "CommunityOwnerServlet",  urlPatterns = {"/deletePatient","/approvePatient","/insertCommunity","/insertCommunityOwner","/COSignIn","/COSignOut","/listPatients"})
public class CommunityOwnerServlet extends HttpServlet {
    private CommunityDao communityDao;
    private CommunityOwnerDao communityOwnerDao;
    private PatientDao patientDao;
    private DaoUtils daoUtils;
    Connection connection;

    public void init() throws ServletException {
        daoUtils = new DaoUtils();
        connection = daoUtils.getConnection();


        communityDao = new CommunityDao(connection, daoUtils);
        communityOwnerDao = new CommunityOwnerDao(connection, daoUtils);
        patientDao = new PatientDao(connection, daoUtils);

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getServletPath();

        switch (action) {
            case "/insertCommunity":
                insertCommunity(request, response);
                break;
            case "/insertCommunityOwner":
                try {
                    insertCommunityOwner(request, response);
                } catch (InvalidKeySpecException e) {
                    e.printStackTrace();
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }
                break;
            case "/COSignIn":
                try {
                    signCommunityOwnerIn(request, response);
                } catch (InvalidKeySpecException e) {
                    e.printStackTrace();
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }
                break;
            case "/COSignOut":
                signCommunityOwnerOut(request,response);
                break;
            case "/listPatients":
                listPatients(request,response);
                break;
            case "/approvePatient":
                approvePatient(request,response);
                break;
            case"/deletePatient":
               deletePatient(request,response);
               break;
            default:
                showSignIn(request, response);
                break;
        }
    }

    private void showSignIn(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("loginPage.jsp");
        dispatcher.forward(request, response);
    }

    private void signCommunityOwnerOut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        session.removeAttribute("communityOwner");
        response.sendRedirect("loginPage.jsp");
    }


    private void signCommunityOwnerIn(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, InvalidKeySpecException, NoSuchAlgorithmException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        CommunityOwner communityOwner = communityOwnerDao.getCommunityOwner(email, password);
        RequestDispatcher dispatcher;
        if (communityOwner == null) { // username and password didn't exist
            dispatcher = request.getRequestDispatcher("loginPage.jsp");
            dispatcher.forward(request, response);
        } else {
            HttpSession session = request.getSession();
            session.setAttribute("communityOwner", communityOwner);
            dispatcher = request.getRequestDispatcher("communityOwnerPage.jsp");
            dispatcher.forward(request, response);
        }
    }

    private void insertCommunityOwner(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, InvalidKeySpecException, NoSuchAlgorithmException {
        User user = MentCareSystemServlet.getUserInfo(request,response);
        int numericCommunityID = Integer.parseInt(request.getParameter("ccomID"));
        CommunityOwner communityOwner =  new CommunityOwner(user, numericCommunityID);

        String enteredPassword = communityOwner.getPassword();
        communityOwner.setPassword(daoUtils.hashPassword(enteredPassword));

        boolean isUniqueEmail = daoUtils.uniqueEmail(communityOwner.getEmail(),"CO");
        boolean isValidCommunity = communityDao.communityExists(communityOwner.getCommunityID());
        RequestDispatcher dispatcher;
        if(!isUniqueEmail || !isValidCommunity){
            dispatcher = request.getRequestDispatcher("RegPage.jsp");
            request.setAttribute("user", user);
            request.setAttribute("community_owner",communityOwner);
            if(!isUniqueEmail){
                request.setAttribute("notUniqueEmail",true);
            }
            if(!isValidCommunity){
                request.setAttribute("notValidCOCommunity",true);
            }
            dispatcher.forward(request,response);
        } else {
            try {
                communityOwnerDao.insertCommunityOwner(communityOwner);
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            response.sendRedirect("loginPage.jsp");
        }
    }


    private void insertCommunity(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            communityDao.insertCommunity(new Community());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void listPatients(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            HttpSession session = request.getSession();
            CommunityOwner communityOwner = (CommunityOwner) session.getAttribute("communityOwner");

            List<Patient> listPatient = patientDao.selectNotApprovedPatientsForCommunity(communityOwner);

            RequestDispatcher dispatcher = request.getRequestDispatcher("communityOwnerPage.jsp");
            request.setAttribute("listPatient", listPatient);
            dispatcher.forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void deletePatient(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        CommunityOwner communityOwner = (CommunityOwner) session.getAttribute("communityOwner");

        int patientID = Integer.parseInt((request.getParameter("patient_ID")));
        System.out.println(patientID);
        try {
            patientDao.deletePatientById(patientID);
        } catch (Exception e) {
            e.printStackTrace();
        }

        List<Patient> listPatient = patientDao.selectNotApprovedPatientsForCommunity(communityOwner);

        RequestDispatcher dispatcher = request.getRequestDispatcher("communityOwnerPage.jsp");
        request.setAttribute("listPatient", listPatient);
        dispatcher.forward(request, response);

    }

    private void approvePatient(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        CommunityOwner communityOwner = (CommunityOwner) session.getAttribute("communityOwner");
        int patientID = Integer.parseInt((request.getParameter("patient_ID")));
        System.out.println(patientID);
        try {
            patientDao.approvePatient(patientID);
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<Patient> listPatient = patientDao.selectNotApprovedPatientsForCommunity(communityOwner);
        RequestDispatcher dispatcher = request.getRequestDispatcher("communityOwnerPage.jsp");
        request.setAttribute("listPatient", listPatient);
        dispatcher.forward(request, response);


    }

}
