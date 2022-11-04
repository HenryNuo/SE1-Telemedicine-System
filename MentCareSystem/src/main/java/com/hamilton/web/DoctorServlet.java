package com.hamilton.web;

import com.hamilton.bean.CommunityOwner;
import com.hamilton.bean.Doctor;
import com.hamilton.bean.Patient;
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

@WebServlet(name = "DoctorServlet",  urlPatterns = {"/insertDoctor","/doctorSignIn","/doctorSignOut","/sendAlert"})
public class DoctorServlet extends HttpServlet {
    private ClinicDao clinicDao;
    private CommunityDao communityDao;
    private DoctorDao doctorDao;
    private DaoUtils daoUtils;
    Connection connection;

    public void init() throws ServletException {
        daoUtils = new DaoUtils();
        connection = daoUtils.getConnection();

        clinicDao = new ClinicDao(connection, daoUtils);
        communityDao = new CommunityDao(connection, daoUtils);
        doctorDao = new DoctorDao(connection, daoUtils);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getServletPath();

        switch (action) {
            case "/insertDoctor":
                try {
                    insertDoctor(request, response);
                } catch (InvalidKeySpecException e) {
                    e.printStackTrace();
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }
                break;
            case "/doctorSignIn":
                try {
                    signDoctorIn(request, response);
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                } catch (InvalidKeySpecException e) {
                    e.printStackTrace();
                }
                break;
            case "/doctorSignOut":
                signDoctorOut(request, response);
                break;
            case "/sendAlert":
                sendAlert(request, response);
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

    private void signDoctorIn(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        Doctor doctor = doctorDao.getDoctor(email, password);
        RequestDispatcher dispatcher;
        if (doctor == null) { // username and password didn't exist
            dispatcher = request.getRequestDispatcher("loginPage.jsp");
            request.setAttribute("notValid", true);
            dispatcher.forward(request, response);
        } else if (!doctor.isApproved()) {
            dispatcher = request.getRequestDispatcher("loginPage.jsp");
            request.setAttribute("notApproved", true);
            dispatcher.forward(request, response);
        } else {
            dispatcher = request.getRequestDispatcher("doctorPage.jsp");
            request.setAttribute("user", doctor);
            HttpSession session = request.getSession();
            session.setAttribute("user", doctor);
            dispatcher.forward(request, response);
        }
    }

    private void signDoctorOut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        session.removeAttribute("user");
        response.sendRedirect("loginPage.jsp");
    }

    private void insertDoctor(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, InvalidKeySpecException, NoSuchAlgorithmException {
        User user = MentCareSystemServlet.getUserInfo(request, response);
        int numericCommunityID = Integer.parseInt(request.getParameter("dcomID"));
        int numericClinic_ID = Integer.parseInt(request.getParameter("dclinic_ID"));
        Doctor newDoctor = new Doctor(user, numericCommunityID, numericClinic_ID, false);

        String enteredPassword = newDoctor.getPassword();
        newDoctor.setPassword(daoUtils.hashPassword(enteredPassword));

        boolean isUniqueEmail = daoUtils.uniqueEmail(newDoctor.getEmail(), "doctor");
        boolean isValidCommunity = communityDao.communityExists(newDoctor.getCommunityID());
        boolean isValidClinic = clinicDao.clinicExists(newDoctor.getClinic_ID());
        RequestDispatcher dispatcher;
        if (!isUniqueEmail || !isValidCommunity || !isValidClinic) {
            dispatcher = request.getRequestDispatcher("RegPage.jsp");
            request.setAttribute("user", user);
            request.setAttribute("doctor", newDoctor);
            if (!isUniqueEmail) {
                request.setAttribute("notUniqueEmail", true);
            }
            if (!isValidCommunity) {
                request.setAttribute("notValidDCommunity", true);
            }
            if (!isValidClinic) {
                request.setAttribute("notValidDClinic", true);
            }
            dispatcher.forward(request, response);
        } else {
            try {
                doctorDao.insertDoctor(newDoctor);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            response.sendRedirect("loginPage.jsp");
        }
    }


    private void sendAlert(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {


        int test_data_ID = Integer.parseInt((request.getParameter("testDataID")));
        String alertMessage = request.getParameter("alertMessage");
        System.out.println(test_data_ID);
        try {
            doctorDao.insertAlert(alertMessage, test_data_ID);
        } catch (Exception e) {
            e.printStackTrace();
        }
        RequestDispatcher dispatcher = request.getRequestDispatcher("doctorPage.jsp");
        dispatcher.forward(request, response);
    }
}
