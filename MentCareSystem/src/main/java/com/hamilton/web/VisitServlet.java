package com.hamilton.web;

import com.hamilton.bean.*;
import com.hamilton.dao.*;

import javax.print.Doc;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@WebServlet(name = "VisitServlet",  urlPatterns = {"/show-community-doctors","/show-clinic-doctors","/schedule-visit", "/show-appointments", "/visit-notes", "/post-notes"})
public class VisitServlet extends HttpServlet {
    private VisitDao visitDao;
    private DaoUtils daoUtils;
    private DoctorDao doctorDao;
    Connection connection;

    public void init() throws ServletException {
        daoUtils = new DaoUtils();
        connection = daoUtils.getConnection();
        visitDao = new VisitDao(connection,daoUtils);
        doctorDao = new DoctorDao(connection,daoUtils);

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getServletPath();
        switch (action) {
            case "/show-community-doctors":
                listCommunityDoctors(request,response);
                break;
            case "/show-clinic-doctors":
                listClinicDoctors(request, response);
                break;
            case "/schedule-visit":
                scheduleVisit(request,response);
                break;
            case "/show-appointments":
                showAppointments(request,response);
                break;
            case "/visit-notes":
                visitNotes(request, response);
                break;
            case "/post-notes":
                postNost(request, response);
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

    private void listClinicDoctors(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {

            HttpSession session = request.getSession();
            Patient patient = (Patient)session.getAttribute("user");
            List<Doctor> listDoctor = doctorDao.selectApprovedDoctorsByClinic(patient.getClinicID());

            RequestDispatcher dispatcher = request.getRequestDispatcher("scheduleAppointment.jsp");
            request.setAttribute("listDoctor", listDoctor);
            dispatcher.forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void listCommunityDoctors(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {

            HttpSession session = request.getSession();
            Patient patient = (Patient)session.getAttribute("user");
            List<Doctor> listDoctor = doctorDao.selectApprovedDoctorsByCommunity(patient.getCommunityID());

            RequestDispatcher dispatcher = request.getRequestDispatcher("scheduleAppointment.jsp");
            request.setAttribute("listDoctor", listDoctor);
            dispatcher.forward(request, response);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void scheduleVisit(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        Patient patient = (Patient)session.getAttribute("user");
        int doctor_id = Integer.parseInt(request.getParameter("doctor_id"));
        Date date= Date.valueOf(request.getParameter("date"));
        Time time = Time.valueOf(request.getParameter("time"));
        Visit visit = new Visit(doctor_id, patient.getPatientID(),date,time);
        visitDao.insertVisit(visit);
        response.sendRedirect("scheduleAppointment.jsp");
    }

    private void showAppointments(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        try {
            HttpSession session = request.getSession();
            Doctor doctor = (Doctor) session.getAttribute("user");
            List<Visit> listVisit = visitDao.showAppointments(doctor.getDoctor_ID());

            RequestDispatcher dispatcher = request.getRequestDispatcher("viewAppointment.jsp");
            request.setAttribute("listVisit", listVisit);
            dispatcher.forward(request, response);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void visitNotes(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        try {
            int visit_id = Integer.parseInt(request.getParameter("visit_id"));
            HttpSession session = request.getSession();
            Doctor doctor = (Doctor) session.getAttribute("user");
            int doctor_id = doctor.getDoctor_ID();
            int patient_id = visitDao.getPatientByVisitId(visit_id);

            List<Visit> listVisit = visitDao.showAppointmentHistory(patient_id);
            request.setAttribute("listVisit", listVisit);

            request.setAttribute("visit_id", visit_id);
            request.setAttribute("doctor_id", doctor_id);
            request.setAttribute("patient_id", patient_id);
            RequestDispatcher dispatcher = request.getRequestDispatcher("appointmentNotes.jsp");
            dispatcher.forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void postNost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        int visitId = Integer.parseInt(request.getParameter("visit_id"));
        String summary = request.getParameter("summary");
        String symptoms = request.getParameter("symptoms");
        String test = request.getParameter("tests");
        String prescriptions = request.getParameter("prescriptions");
        String dosage = request.getParameter("dosage");
        int doctor_id = Integer.parseInt(request.getParameter("doctor_id"));
        int patient_id = Integer.parseInt(request.getParameter("patient_id"));

        visitDao.prescribeTests(test, doctor_id, patient_id, visitId);

        visitDao.prescribe(prescriptions, doctor_id, patient_id, visitId, dosage);

        int i = 1;
        while(request.getParameter("prescription" + i) != null) {

            String prescription = request.getParameter("prescription" + i);
            String extraDosage = request.getParameter("dosage" + i);
            visitDao.prescribe(prescription, doctor_id, patient_id, visitId, extraDosage);

            i++;

        }

        int z = 1;
        while(request.getParameter("test" + z) != null) {

            String tests = request.getParameter("test" + z);
            visitDao.prescribeTests(tests, doctor_id, patient_id, visitId);

            z++;

        }

        visitDao.postNost(visitId, summary, symptoms);
        response.sendRedirect("doctorPage.jsp");
    }
}

