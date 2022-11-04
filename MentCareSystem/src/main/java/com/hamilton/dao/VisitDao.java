package com.hamilton.dao;

import com.hamilton.bean.Prescription;
import com.hamilton.bean.Visit;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VisitDao {
    private Connection connection;
    private DaoUtils daoUtils;
    private static final String INSERT_VISIT_SQL = "INSERT INTO visits" + "(doctor_id, patient_id, date, time) VALUES "
            + " ( ?, ?, ?, ?);";
    private static final String SELECT_UNRESOLVED_VISIT_SQL ="SELECT * from visits where doctor_id = ? and visit_fulfilled = 0";
    private static final String UPDATE_SUMMARY_SYMPTOMS_SQL =
            "UPDATE visits SET summary = ?, symptoms = ?, visit_fulfilled = ? WHERE visits.visit_id = ?";

    private static final String SELECT_PATIENT_ID_BY_VISIT_ID = "SELECT patient_id from visits where visit_id = ?";
    private static final String INSERT_PRESCRIPTON_SQL = "INSERT INTO prescription" + "(patient_id, doctor_id, prescription_name, dosage, visit_id) VALUES "
            + " (?, ?, ?, ?, ?);";
    private static final String SELECT_ALL_VISITS_BY_PATIENT = "SELECT * from visits where patient_id = ? and visit_fulfilled = 1";
    private static final String INSERT_LAB_TEST_SQL = "INSERT INTO lab_tests" + "(patient_id, doctor_id, visit_id, lab_test_name) VALUES"
            + "(?, ?, ?, ?)";
    private static final String SELECT_ALL_PRESCRIPTION_BY_VISIT = "SELECT * from prescription where visit_id = ?";
    private static final String SELECT_ALL_DOSAGE_BY_VISIT = "SELECT * from prescription where visit_id = ?";
    private static final String SELECT_ALL_PRESCRIPTION_BY_Patient = "SELECT * from prescription where patient_id = ?";
    private static final String SELECT_ALL_LAB_TEST_BY_VISIT = "SELECT * from lab_tests where visit_id = ?";
    private static final String SELECT_ALL_LAB_TEST_BY_PATIENT = "SELECT lab_test_name from lab_tests where patient_id = ?";



    public VisitDao(Connection connection, DaoUtils daoUtils){
        this.connection = connection;
        this.daoUtils = daoUtils;
    }

    public void insertVisit(Visit visit){
        System.out.println(INSERT_VISIT_SQL);
        try(
                PreparedStatement preparedStatement = connection.prepareStatement(INSERT_VISIT_SQL)){

            preparedStatement.setInt(1, visit.getDoctor_id());
            preparedStatement.setInt(2, visit.getPatient_id());
            preparedStatement.setDate(3, visit.getVisitDate());
            preparedStatement.setTime(4, visit.getVisitTime());

            System.out.println(preparedStatement);
            preparedStatement.executeUpdate();
        }catch(SQLException e){
            daoUtils.printSQLException(e);
        }
    }

    public List<Visit> showAppointments(int doctorID) {
        System.out.println(SELECT_UNRESOLVED_VISIT_SQL);
        List<Visit> visits = new ArrayList<>();

        try(
                PreparedStatement preparedStatement = connection.prepareStatement(SELECT_UNRESOLVED_VISIT_SQL)){

            preparedStatement.setInt(1, doctorID);

            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){

                int visitID = rs.getInt("visit_id");

                //Will be the same id as the parameter
                int doctorDatabaseID = rs.getInt("doctor_id");

                int patientID = rs.getInt("patient_id");
                Date date = rs.getDate("date");
                Time time = rs.getTime("time");
                String symptoms = rs.getString("symptoms");
                String summary = rs.getString("summary");

                visits.add(new Visit(visitID, doctorDatabaseID, patientID, date, time, symptoms, summary));
            }
            System.out.println(preparedStatement);
        }catch(SQLException e){
            daoUtils.printSQLException(e);
        }
        return visits;
    }

    public void postNost(int visit_id, String summary, String symptoms){
        try(
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_SUMMARY_SYMPTOMS_SQL)){
            preparedStatement.setString(1, summary);
            preparedStatement.setString(2, symptoms);
            preparedStatement.setInt(3, 1);
            preparedStatement.setInt(4, visit_id);
            preparedStatement.executeUpdate();
        }catch(SQLException e){
            daoUtils.printSQLException(e);
            System.out.println("post nost");
        }
    }
    public void prescribe(String prescriptions, int doctor_id, int patient_id, int visit_id, String dosage){
        try(
                PreparedStatement preparedStatement = connection.prepareStatement(INSERT_PRESCRIPTON_SQL)){
            preparedStatement.setInt(1, patient_id);
            preparedStatement.setInt(2, doctor_id);
            preparedStatement.setString(3, prescriptions);
            preparedStatement.setString(4, dosage);
            preparedStatement.setInt(5, visit_id);
            preparedStatement.executeUpdate();
        }catch(SQLException e){
            daoUtils.printSQLException(e);
            System.out.println("prescribe");
        }
    }

    public int getPatientByVisitId(int visit_id){
        int patient_id = 0;
        try(
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_PATIENT_ID_BY_VISIT_ID)){

            preparedStatement.setInt(1, visit_id);
            ResultSet rs = preparedStatement.executeQuery();

            while(rs.next()){

                patient_id = rs.getInt("patient_id");
            }
        }catch(SQLException e){
            daoUtils.printSQLException(e);
        }
        return patient_id;
    }

    public List<Visit> showAppointmentHistory(int patientID) {

        System.out.println(SELECT_ALL_VISITS_BY_PATIENT);

        List<Visit> visits = new ArrayList<>();

        try(
                PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_VISITS_BY_PATIENT)){

            preparedStatement.setInt(1, patientID);

            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){

                int visitID = rs.getInt("visit_id");

                List<String> listPrescription = new ArrayList<>();

                listPrescription = showPrescriptionHistory(visitID);

                List<String> listDosage = new ArrayList<>();

                listDosage = showDosageHistory(visitID);
                String totalPrescriptionsAndDosages = "";

                for(int i = 0; i < listPrescription.size(); i++) {

                    totalPrescriptionsAndDosages += listPrescription.get(i) + "-" + listDosage.get(i) ;

                    if(i != listPrescription.size()-1) {
                        totalPrescriptionsAndDosages += ", ";
                    }

                }

                List<String> listLabTests = new ArrayList<>();

                listLabTests = showLabTestHistory(visitID);

                String totalLabTests = "";
                for(int i = 0; i < listLabTests.size(); i++) {

                    totalLabTests += listLabTests.get(i);

                    if(i != listLabTests.size()-1) {
                        totalLabTests += ", ";
                    }

                }

                //Will be the same id as the parameter
                int doctorID = rs.getInt("doctor_id");

                int patientDatabaseID = rs.getInt("patient_id");
                Date date = rs.getDate("date");
                Time time = rs.getTime("time");
                String symptoms = rs.getString("symptoms");
                String summary = rs.getString("summary");

                visits.add(new Visit(visitID, doctorID, patientDatabaseID, date, time, symptoms, summary, totalPrescriptionsAndDosages, totalLabTests));
            }
            System.out.println(preparedStatement);
        }catch(SQLException e){
            daoUtils.printSQLException(e);
        }
        return visits;
    }

    public void prescribeTests(String test, int doctor_id, int patient_id, int visit_id){
        try(
                PreparedStatement preparedStatement = connection.prepareStatement(INSERT_LAB_TEST_SQL)){
            preparedStatement.setInt(1, patient_id);
            preparedStatement.setInt(2, doctor_id);
            preparedStatement.setInt(3, visit_id);
            preparedStatement.setString(4, test);

            preparedStatement.executeUpdate();
        }catch(SQLException e){
            daoUtils.printSQLException(e);
            System.out.println("prescribeTest");
        }
    }

    public List<String> showPrescriptionHistory(int visitID) {

        System.out.println(SELECT_ALL_PRESCRIPTION_BY_VISIT);

        List<String> prescriptions = new ArrayList<>();

        try(
                PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_PRESCRIPTION_BY_VISIT)){

            preparedStatement.setInt(1, visitID);

            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){

                String prescription = rs.getString("prescription_name");

                prescriptions.add(prescription);
            }
            System.out.println(preparedStatement);
        }catch(SQLException e){
            daoUtils.printSQLException(e);
        }
        return prescriptions;
    }

    public List<String> showDosageHistory(int visitID) {

        System.out.println(SELECT_ALL_DOSAGE_BY_VISIT);

        List<String> dosages = new ArrayList<>();

        try(
                PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_DOSAGE_BY_VISIT)){

            preparedStatement.setInt(1, visitID);

            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){

                String dosage = rs.getString("dosage");

                dosages.add(dosage);
            }
            System.out.println(preparedStatement);
        }catch(SQLException e){
            daoUtils.printSQLException(e);
        }
        return dosages;
    }

    public List<Prescription> showPrescriptionHistoryPatient(int patientID) {

        System.out.println(SELECT_ALL_PRESCRIPTION_BY_Patient);

        List<Prescription> prescriptions = new ArrayList<>();

        try(
                PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_PRESCRIPTION_BY_Patient)){

            preparedStatement.setInt(1, patientID);

            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){

                String prescription = rs.getString("prescription_name");
                String dosage = rs.getString("dosage");

                prescriptions.add(new Prescription(dosage,prescription));
            }
            System.out.println(preparedStatement);
        }catch(SQLException e){
            daoUtils.printSQLException(e);
        }
        return prescriptions;
    }



    public List<String> showLabTestHistory(int visitID) {

        System.out.println(SELECT_ALL_LAB_TEST_BY_VISIT);

        List<String> labTests = new ArrayList<>();

        try(
                PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_LAB_TEST_BY_VISIT)){

            preparedStatement.setInt(1, visitID);

            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){

                String labTest = rs.getString("lab_test_name");

                labTests.add(labTest);
            }
            System.out.println(preparedStatement);
        }catch(SQLException e){
            daoUtils.printSQLException(e);
        }
        return labTests;
    }

    public List<String> getPatientLabTestHistory(int patientid) {

        System.out.println(SELECT_ALL_LAB_TEST_BY_PATIENT);

        List<String> labTests = new ArrayList<>();

        try(
                PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_LAB_TEST_BY_PATIENT)){

            preparedStatement.setInt(1, patientid);

            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){

                String labTest = rs.getString("lab_test_name");

                labTests.add(labTest);
            }
            System.out.println(preparedStatement);
        }catch(SQLException e){
            daoUtils.printSQLException(e);
        }
        return labTests;
    }

}
