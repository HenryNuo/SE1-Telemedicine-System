package com.hamilton.web;

import com.hamilton.bean.Doctor;
import com.hamilton.bean.Patient;
import com.hamilton.bean.Test;
import com.hamilton.bean.Test_data;
import com.hamilton.dao.*;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@WebServlet(name = "TestDataServlet",  urlPatterns = {"/insertTestData", "/insertTest", "/listTests", "/viewTestHistoryPatient","/viewTestHistoryDoctor", "/viewTestHistoryDoctorClinic"})
public class TestDataServlet extends HttpServlet {

    private TestDataDao testDataDao;
    private DaoUtils daoUtils;
    Connection connection;

    public void init() throws ServletException {
        daoUtils = new DaoUtils();
        connection = daoUtils.getConnection();
        testDataDao = new TestDataDao(connection, daoUtils);

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getServletPath();
        switch (action) {
            case "/insertTestData":
                insertTestData(request,response);
                break;
            case "/insertTest":
                insertTest(request, response);
                break;
            case "/listTests":
                listTests(request,response);
                break;
            case "/viewTestHistoryPatient":
                viewTestHistoryPatient(request,response);
                break;
            case "/viewTestHistoryDoctor":
                viewTestHistoryDoctor(request,response);
                break;
            case "/viewTestHistoryDoctorClinic":
                viewTestHistoryDoctorClinic(request, response);
                break;
            default:
                showDataIn(request,response);
                break;
        }
    }

    private void showDataIn(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("patientPage.jsp");
        dispatcher.forward(request, response);
    }

    private void insertTestData(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Patient patient = (Patient)session.getAttribute("user");
        int patientID = patient.getPatientID();
        float stats = Float.parseFloat(request.getParameter("inputData"));
        String type = request.getParameter("testName");
        String unitOfMeasure = request.getParameter("unitOfMeasure");
        String time = Test_data.getTimeOne();

        
        Test_data newTest_data = new Test_data(patientID, stats, type, time, unitOfMeasure, null, 0);
        RequestDispatcher dispatcher;
        try {
            testDataDao.insertTestData(newTest_data);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        response.sendRedirect("patientPage.jsp");
    }

    private void insertTest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String testName = request.getParameter("testName");
        Double upperLimit = Double.parseDouble(request.getParameter("upperLimit"));
        Double lowerLimit = Double.parseDouble(request.getParameter("lowerLimit"));
        String unitOfMeasure = request.getParameter("unitOfMeasure");

        Test test = new Test(testName, upperLimit, lowerLimit, unitOfMeasure);

        try {
            testDataDao.insertTest(test);
        } catch(Exception e) {
            e.printStackTrace();
        }
        response.sendRedirect("adminPage.jsp");
    }

    private void listTests(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {

            List<Test> listTest = testDataDao.selectAllTests();

            RequestDispatcher dispatcher = request.getRequestDispatcher("patientPage.jsp");
            request.setAttribute("listTest", listTest);

            dispatcher.forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void viewTestHistoryPatient(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {

            List<Test_data> test_data = testDataDao.selectAllTestData();
            System.out.println(test_data.size());
            for(Test_data test: test_data){
                //System.out.println(test.time);
            }
            //test_data.removeIf(i -> i.getPatient_ID() != patientId);

            RequestDispatcher dispatcher = request.getRequestDispatcher("patientPage.jsp");
            request.setAttribute("testData", test_data);

            dispatcher.forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void viewTestHistoryDoctor(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            HttpSession session = request.getSession();
            Doctor doctor = (Doctor) session.getAttribute("user");
            List<Test_data> test_data = testDataDao.selectAllTestDataForCommunity(doctor.getCommunityID());
            System.out.println(test_data.size());
            for(Test_data test: test_data){
                //System.out.println(test.time);
            }
            //test_data.removeIf(i -> i.getPatient_ID() != patientId);
            Set<Integer> patientIds = new HashSet<>();
            for(Test_data test: test_data){
                patientIds.add(test.getPatient_ID());
            }
            RequestDispatcher dispatcher = request.getRequestDispatcher("doctorPage.jsp");
            request.setAttribute("testData", test_data);
            request.setAttribute("patientData", patientIds);

            dispatcher.forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void viewTestHistoryDoctorClinic(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            HttpSession session = request.getSession();
            Doctor doctor = (Doctor) session.getAttribute("user");
            List<Test_data> test_data = testDataDao.selectAllTestDataForClinic(doctor.getClinic_ID());
            System.out.println(test_data.size());
            for(Test_data test: test_data){
                //System.out.println(test.time);
            }
            //test_data.removeIf(i -> i.getPatient_ID() != patientId);
            Set<Integer> patientIds = new HashSet<>();
            for(Test_data test: test_data){
                patientIds.add(test.getPatient_ID());
            }
            RequestDispatcher dispatcher = request.getRequestDispatcher("doctorPage.jsp");
            request.setAttribute("testData", test_data);
            request.setAttribute("patientData", patientIds);

            dispatcher.forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

