package com.hamilton.web;

import com.hamilton.bean.Doctor;
import com.hamilton.bean.MedicalOwner;
import com.hamilton.bean.User;
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

@WebServlet(name = "MedicalOwnerServlet", urlPatterns = {"/insertMedicalOwner","/MOSignOut","/MOSignIn","/listDoctors","/delete","/approve"})
public class MedicalOwnerServlet extends HttpServlet {
    private ClinicDao clinicDao;
    private DoctorDao doctorDao;
    private MedicalOwnerDao medicalOwnerDao;
    private DaoUtils daoUtils;
    Connection connection;

    public void init() throws ServletException {
        daoUtils = new DaoUtils();
        connection = daoUtils.getConnection();
        clinicDao = new ClinicDao(connection, daoUtils);
        doctorDao = new DoctorDao(connection, daoUtils);
        medicalOwnerDao = new MedicalOwnerDao(connection, daoUtils);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getServletPath();

        switch (action) {
            case "/insertMedicalOwner":
                try {
                    insertMedicalOwner(request, response);
                } catch (InvalidKeySpecException e) {
                    e.printStackTrace();
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }
                break;
            case "/MOSignIn":
                try {
                    signMedicalOwnerIn(request,response);
                } catch (InvalidKeySpecException e) {
                    e.printStackTrace();
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }
                break;
            case "/MOSignOut":
                signMedicalOwnerOut(request,response);
                break;
            case "/listDoctors":
                listDoctors(request, response);
                break;
            case "/delete":
                deleteDoctor(request, response);
                break;
            case "/approve":
                approveDoctor(request, response);
                break;
            default:
                showSignIn(request,response);
                break;
        }
    }

    private void showSignIn(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("loginPage.jsp");
        dispatcher.forward(request,response);
    }

    private void signMedicalOwnerIn(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, InvalidKeySpecException, NoSuchAlgorithmException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        MedicalOwner medicalOwner = medicalOwnerDao.getMedicalOwner(email,password);
        RequestDispatcher dispatcher;
        if (medicalOwner == null){ // username and password didn't exist
            dispatcher = request.getRequestDispatcher("loginPage.jsp");
            dispatcher.forward(request,response);
        } else{
            dispatcher = request.getRequestDispatcher("medicalOwnerPage.jsp");
            HttpSession session = request.getSession();
            session.setAttribute("user",medicalOwner);
            dispatcher.forward(request,response);
        }
    }

    private void signMedicalOwnerOut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        session.removeAttribute("user");
        response.sendRedirect("loginPage.jsp");
    }

    private void insertMedicalOwner(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, InvalidKeySpecException, NoSuchAlgorithmException {
        User user = MentCareSystemServlet.getUserInfo(request,response);
        int numericClinicID = Integer.parseInt(request.getParameter("mclinic_ID"));
        MedicalOwner newMedicalOwner =  new MedicalOwner(user, numericClinicID);

        String enteredPassword = newMedicalOwner.getPassword();
        newMedicalOwner.setPassword(daoUtils.hashPassword(enteredPassword));

        boolean isUniqueEmail = daoUtils.uniqueEmail(newMedicalOwner.getEmail(),"MO");
        boolean isValidClinic = clinicDao.clinicExists(newMedicalOwner.getClinicID());
        RequestDispatcher dispatcher;
        if(!isUniqueEmail || !isValidClinic){
            dispatcher = request.getRequestDispatcher("RegPage.jsp");
            request.setAttribute("user", user);
            request.setAttribute("medical_owner",newMedicalOwner);
            if(!isUniqueEmail){
                request.setAttribute("notUniqueEmail",true);
            }
            if(!isValidClinic){
                request.setAttribute("notValidMOClinic",true);
            }
            dispatcher.forward(request,response);
        } else {
            try {
                medicalOwnerDao.insertMedicalOwner(newMedicalOwner);
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            response.sendRedirect("loginPage.jsp");
        }
    }

    private void listDoctors(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {

            HttpSession session = request.getSession();
            MedicalOwner medicalOwner = (MedicalOwner) session.getAttribute("user");
            List<Doctor> listDoctor = doctorDao.selectAllNotApprovedDoctorsByClinic(medicalOwner);

            RequestDispatcher dispatcher = request.getRequestDispatcher("medicalOwnerPage.jsp");
            request.setAttribute("user",medicalOwner);
            request.setAttribute("listDoctor", listDoctor);
            dispatcher.forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void deleteDoctor(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        MedicalOwner medicalOwner = (MedicalOwner) session.getAttribute("user");
        int doctorID = Integer.parseInt((request.getParameter("doctor_ID")));

        System.out.println(doctorID);
        try {
            doctorDao.deleteDoctor(doctorID);
        } catch (Exception e) {
            e.printStackTrace();
        }

        List<Doctor> listDoctor = doctorDao.selectAllNotApprovedDoctorsByClinic(medicalOwner);

        RequestDispatcher dispatcher = request.getRequestDispatcher("medicalOwnerPage.jsp");
        request.setAttribute("user",medicalOwner);
        request.setAttribute("listDoctor", listDoctor);
        dispatcher.forward(request, response);

    }

    private void approveDoctor(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        MedicalOwner medicalOwner = (MedicalOwner) session.getAttribute("user");
        int doctorID = Integer.parseInt((request.getParameter("doctor_ID")));

        System.out.println(doctorID);
        try {
            doctorDao.approveDoctor(doctorID);
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<Doctor> listDoctor = doctorDao.selectAllNotApprovedDoctorsByClinic(medicalOwner);

        RequestDispatcher dispatcher = request.getRequestDispatcher("medicalOwnerPage.jsp");
        request.setAttribute("user",medicalOwner);
        request.setAttribute("listDoctor", listDoctor);
        dispatcher.forward(request, response);

    }
}


