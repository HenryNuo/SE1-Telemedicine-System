package com.hamilton.web;

import com.hamilton.bean.Family;
import com.hamilton.bean.Patient;
import com.hamilton.bean.Prescription;
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

@WebServlet(name = "PatientServlet", urlPatterns = {"/patientSignIn","/insertPatient", "/patientSignOut", "/listLabTests","/listPrescriptions"})
public class PatientServlet extends HttpServlet {

    private ClinicDao clinicDao;
    private CommunityDao communityDao;
    private FamilyDao familyDao;
    private PatientDao patientDao;
    private VisitDao visitDao;
    private DaoUtils daoUtils;
    Connection connection;

    public void init() throws ServletException {
        daoUtils = new DaoUtils();
        connection = daoUtils.getConnection();

        clinicDao = new ClinicDao(connection, daoUtils);
        communityDao = new CommunityDao(connection, daoUtils);
        familyDao = new FamilyDao(connection, daoUtils);
        patientDao = new PatientDao(connection, daoUtils);
        visitDao = new VisitDao(connection,daoUtils);


    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getServletPath();

        switch (action) {
            case "/insertPatient":
                try {
                    insertPatient(request, response);
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                } catch (InvalidKeySpecException e) {
                    e.printStackTrace();
                }
                break;
            case "/patientSignIn":
                try {
                    signPatientIn(request,response);
                } catch (InvalidKeySpecException e) {
                    e.printStackTrace();
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }
                break;
            case "/patientSignOut":
                signPatientOut(request,response);
                break;
            case "/listLabTests":
                listLabTests(request,response);
                break;
            case "/listPrescriptions":
                listPrescriptions(request,response);
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

    private void signPatientIn(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, InvalidKeySpecException, NoSuchAlgorithmException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        Patient patient = patientDao.getPatient(email,password);

        RequestDispatcher dispatcher;
        if (patient == null){ // username and password didn't exist
            dispatcher = request.getRequestDispatcher("loginPage.jsp");
            request.setAttribute("notValid",true);
            dispatcher.forward(request,response);
        } else if(!patient.isApproved()){
            dispatcher = request.getRequestDispatcher("loginPage.jsp");
            request.setAttribute("notApproved",true);
            dispatcher.forward(request,response);
        } else {
            dispatcher = request.getRequestDispatcher("patientPage.jsp");
//            request.setAttribute("user",user);
            HttpSession session = request.getSession();
            session.setAttribute("user",patient);
            dispatcher.forward(request,response);
        }
    }

    private void signPatientOut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        session.removeAttribute("user");
        response.sendRedirect("loginPage.jsp");
    }

    private void listLabTests(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Patient patient = (Patient) session.getAttribute("user");
        List<String> labtests = visitDao.getPatientLabTestHistory(patient.getPatientID());
        RequestDispatcher dispatcher = request.getRequestDispatcher("patientLabTests.jsp");
        request.setAttribute("labtests",labtests);
        dispatcher.forward(request,response);
    }

    private void listPrescriptions(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Patient patient = (Patient) session.getAttribute("user");
        List<Prescription> prescriptions = visitDao.showPrescriptionHistoryPatient(patient.getPatientID());
        RequestDispatcher dispatcher = request.getRequestDispatcher("patientLabTests.jsp");
        request.setAttribute("prescriptions",prescriptions);
        dispatcher.forward(request,response);
    }


    //TODO: Password Hashing Version, Don't Delete

    private void insertPatient(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, NoSuchAlgorithmException, InvalidKeySpecException{
        int numericFamilyID = 0;
        User user = MentCareSystemServlet.getUserInfo(request,response);
        int numericCommunityID = Integer.parseInt(request.getParameter("pcomID"));
        boolean isValidFamily = false;
        boolean notNullFamId = request.getParameter("FamID")!=null;
        if (notNullFamId) {
            numericFamilyID = Integer.parseInt(request.getParameter("FamID"));
            isValidFamily = familyDao.familyExists(numericFamilyID);
        }
        int numericPreviousFamilyID = Integer.parseInt(request.getParameter("PrevFamID"));
        int numericClinicID = Integer.parseInt(request.getParameter("pclinic_ID"));
        Patient newPatient =  new Patient(user,numericCommunityID,numericFamilyID,numericPreviousFamilyID, numericClinicID);

        String enteredPassword = newPatient.getPassword();
        newPatient.setPassword(daoUtils.hashPassword(enteredPassword));

        String familyName = request.getParameter("FamName");
        System.out.println(familyName);
        Family newfamily = null;
        int newFamilyId;
        if (isValidFamily&&notNullFamId){
           newfamily = new Family(familyName,numericFamilyID);
       } else if(!notNullFamId){
            try {
                newFamilyId = familyDao.createFamily(familyName);
                newfamily = new Family(familyName,newFamilyId);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

        boolean isUniqueEmail = daoUtils.uniqueEmail(newPatient.getEmail(),"patient");
        boolean isValidCommunity = communityDao.communityExists(newPatient.getCommunityID());
        boolean isValidClinic = clinicDao.clinicExists(newPatient.getClinicID());

        RequestDispatcher dispatcher;
        if(!isUniqueEmail || !isValidCommunity || !isValidClinic || ((!isValidFamily)&&notNullFamId)){
            dispatcher = request.getRequestDispatcher("RegPage.jsp");
            request.setAttribute("user", user);
            request.setAttribute("patient",newPatient);
            if(!isUniqueEmail){
                request.setAttribute("notUniqueEmail",true);
            }
            if(!isValidCommunity){
                request.setAttribute("notValidPCommunity",true);
            }
            if(((!isValidFamily)&&notNullFamId)){
                request.setAttribute("notValidFamily",true);
            }
            if (!isValidClinic) {
                request.setAttribute("notValidDClinic", true);
            }
            dispatcher.forward(request,response);
        } else {
            try {
                patientDao.insertPatient(newPatient, newfamily);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            response.sendRedirect("loginPage.jsp");
        }
    }

}
