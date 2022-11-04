package com.hamilton.dao;

import com.hamilton.bean.*;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DoctorDao {

    private static final String INSERT_DOCTOR_SQL = "INSERT INTO doctor" + "(name, email, pass, birthdate, address, SSN, community_id, clinic_id,isApproved) VALUES "
            + " ( ?, ?, ?, ?, ?, ?, ?, ?, ?);";
    private static final String CHECK_DOCTOR_EMAIL = "Select name from doctor where email = ?;";
    private static final String GET_DOCTOR_SQL = "Select * from doctor where email = ? and pass = ?;";
    private static final String APPROVE_DOCTOR_SQL = "UPDATE doctor SET isApproved=1 WHERE doctor_ID = ?;";
    private static final String SELECT_ALL_DOCTORS = "select * from doctor where isApproved = 0 and community_id = ?";
    private static final String SELECT_ALL_DOCTORS_MED = "select * from doctor where isApproved = 0 and clinic_id = ?";
    private static final String SELECT_APPROVED_DOCTORS_Clinic = "select * from doctor where isApproved = 1 and clinic_id = ?";
    private static final String SELECT_APPROVED_DOCTORS_COMMUNITY = "select * from doctor where isApproved = 1 and community_id = ?";
    private static final String DELETE_DOCTORS_SQL = "delete from doctor where doctor_ID = ?;";
    private static final String GET_DOCTOR_EMAIL = "Select * from doctor where email = ?";
    private static final String UPDATE_ALERT_MESSAGE_SQL = "UPDATE test_data SET alertMessage = ? WHERE test_data_id = ?";

    private Connection connection;
    private DaoUtils daoUtils;

    public DoctorDao(Connection connection, DaoUtils daoUtils){
        this.connection = connection;
        this.daoUtils = daoUtils;
    }

    public Doctor insertDoctor(Doctor doctor) throws SQLException {
        System.out.println(INSERT_DOCTOR_SQL);
        try(
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_DOCTOR_SQL)){

            preparedStatement.setString(1, doctor.getName());
            preparedStatement.setString(2, doctor.getEmail());
            preparedStatement.setString(3, doctor.getPassword());
            preparedStatement.setDate(4, doctor.getBirthdate());
            preparedStatement.setString(5, doctor.getAddress());
            preparedStatement.setString(6, doctor.getSocialSecurityNumber());
            preparedStatement.setInt(7, doctor.getCommunityID());
            preparedStatement.setInt(8, doctor.getClinic_ID());
            preparedStatement.setBoolean(9, doctor.isApproved());

            System.out.println(preparedStatement);
            preparedStatement.executeUpdate();
        }catch(SQLException e){
            daoUtils.printSQLException(e);
        }
        return  doctor;
    }
/*
    public Doctor getDoctor(String email, String password){
        System.out.println(GET_DOCTOR_SQL);
        Doctor doctor = null;
        try(
            PreparedStatement preparedStatement = connection.prepareStatement(GET_DOCTOR_SQL)){
            preparedStatement.setString(1,email);
            preparedStatement.setString(2,password);
            System.out.println(preparedStatement);

            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                doctor = new Doctor();
                doctor.setName(rs.getString("name"));
                doctor.setDoctor_ID(rs.getInt("doctor_id"));
                doctor.setEmail(rs.getString("email"));
                doctor.setPassword(rs.getString("pass"));
                doctor.setBirthdate(rs.getDate("birthdate"));
                doctor.setAddress(rs.getString("address"));
                doctor.setSocialSecurityNumber(rs.getString("SSN"));
                doctor.setCommunityID(rs.getInt("community_id"));
                doctor.setClinic_ID(rs.getInt("clinic_id"));
                doctor.setApproved(rs.getBoolean("isApproved"));
            }

        }catch(SQLException e){
            daoUtils.printSQLException(e);
        }
        return doctor;
    }
*/

    public Doctor getDoctor(String email, String password) throws NoSuchAlgorithmException, InvalidKeySpecException {
        System.out.println(GET_DOCTOR_EMAIL);
        Doctor doctor = null;
        try(
                PreparedStatement preparedStatement = connection.prepareStatement(GET_DOCTOR_EMAIL)){
            preparedStatement.setString(1,email);
            System.out.println(preparedStatement);

            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                doctor = new Doctor();
                doctor.setName(rs.getString("name"));
                doctor.setDoctor_ID(rs.getInt("doctor_id"));
                doctor.setEmail(rs.getString("email"));
                doctor.setPassword(rs.getString("pass"));
                doctor.setBirthdate(rs.getDate("birthdate"));
                doctor.setAddress(rs.getString("address"));
                doctor.setSocialSecurityNumber(rs.getString("SSN"));
                doctor.setCommunityID(rs.getInt("community_id"));
                doctor.setClinic_ID(rs.getInt("clinic_id"));
                doctor.setApproved(rs.getBoolean("isApproved"));
            }

            boolean passwordMatch = false;
            if(doctor != null) {
                passwordMatch = daoUtils.checkPassword(password, doctor.getPassword());
            }

            if(!passwordMatch) {
                doctor = null;
            }

        }catch(SQLException e){
            daoUtils.printSQLException(e);
        }
        return doctor;
    }

    public List<Doctor> selectAllNotApprovedDoctorsByClinic(MedicalOwner medicalOwner) {
        int id = 0;
        String statement =  SELECT_ALL_DOCTORS_MED;
        id = medicalOwner.getClinicID();

        // using try-with-resources to avoid closing resources (boiler plate code)
        List<Doctor> doctors = new ArrayList<>();
        // Step 1: Establishing a Connection
        try (

             // Step 2:Create a statement using connection object
             PreparedStatement preparedStatement = connection.prepareStatement(statement);) {
            preparedStatement.setInt(1, id);
            System.out.println(preparedStatement);
            // Step 3: Execute the query or update query
            ResultSet rs = preparedStatement.executeQuery();

            // Step 4: Process the ResultSet object.
            while (rs.next()) {

                int doctorID = rs.getInt("doctor_id");
                String name = rs.getString("name");
                String email = rs.getString("email");
                String pass = rs.getString("pass");
                Date birthdate = rs.getDate("birthdate");
                String address = rs.getString("address");
                String ssn = rs.getString("SSN");
                int communityID = rs.getInt("community_id");
                int clinicID = rs.getInt("clinic_id");
                boolean isApproved = rs.getBoolean("isApproved");

                doctors.add(new Doctor(doctorID, name, email, pass, birthdate, address, ssn, communityID, clinicID, isApproved));
            }
        } catch (SQLException e) {
            daoUtils.printSQLException(e);
        }
        return doctors;
    }
    public List<Doctor> selectApprovedDoctorsByClinic(int clinic_id) {
        int id = 0;
        String statement =  SELECT_APPROVED_DOCTORS_Clinic;
        id = clinic_id;

        // using try-with-resources to avoid closing resources (boiler plate code)
        List<Doctor> doctors = new ArrayList<>();
        // Step 1: Establishing a Connection
        try (

                // Step 2:Create a statement using connection object
                PreparedStatement preparedStatement = connection.prepareStatement(statement);) {
            preparedStatement.setInt(1, id);
            System.out.println(preparedStatement);
            // Step 3: Execute the query or update query
            ResultSet rs = preparedStatement.executeQuery();

            // Step 4: Process the ResultSet object.
            while (rs.next()) {

                int doctorID = rs.getInt("doctor_id");
                String name = rs.getString("name");
                String email = rs.getString("email");
                String pass = rs.getString("pass");
                Date birthdate = rs.getDate("birthdate");
                String address = rs.getString("address");
                String ssn = rs.getString("SSN");
                int communityID = rs.getInt("community_id");
                int clinicID = rs.getInt("clinic_id");
                boolean isApproved = rs.getBoolean("isApproved");

                doctors.add(new Doctor(doctorID, name, email, pass, birthdate, address, ssn, communityID, clinicID, isApproved));
            }
        } catch (SQLException e) {
            daoUtils.printSQLException(e);
        }
        return doctors;
    }
    public List<Doctor> selectApprovedDoctorsByCommunity(int community_id) {
        int id = 0;
        String statement =  SELECT_APPROVED_DOCTORS_COMMUNITY;
        id = community_id;

        // using try-with-resources to avoid closing resources (boiler plate code)
        List<Doctor> doctors = new ArrayList<>();
        // Step 1: Establishing a Connection
        try (

                // Step 2:Create a statement using connection object
                PreparedStatement preparedStatement = connection.prepareStatement(statement);) {
            preparedStatement.setInt(1, id);
            System.out.println(preparedStatement);
            // Step 3: Execute the query or update query
            ResultSet rs = preparedStatement.executeQuery();

            // Step 4: Process the ResultSet object.
            while (rs.next()) {

                int doctorID = rs.getInt("doctor_id");
                String name = rs.getString("name");
                String email = rs.getString("email");
                String pass = rs.getString("pass");
                Date birthdate = rs.getDate("birthdate");
                String address = rs.getString("address");
                String ssn = rs.getString("SSN");
                int communityID = rs.getInt("community_id");
                int clinicID = rs.getInt("clinic_id");
                boolean isApproved = rs.getBoolean("isApproved");

                doctors.add(new Doctor(doctorID, name, email, pass, birthdate, address, ssn, communityID, clinicID, isApproved));
            }
        } catch (SQLException e) {
            daoUtils.printSQLException(e);
        }
        return doctors;
    }

    public boolean deleteDoctor(int doctorID) throws SQLException {
        boolean rowDeleted;
        try (
             PreparedStatement statement = connection.prepareStatement(DELETE_DOCTORS_SQL);) {
            statement.setInt(1, doctorID);
            rowDeleted = statement.executeUpdate() > 0;
        }
        return rowDeleted;
    }

    public boolean approveDoctor(int doctorID) throws SQLException {
        boolean rowUpdated;
        try (
             PreparedStatement statement = connection.prepareStatement(APPROVE_DOCTOR_SQL);) {
            statement.setInt(1, doctorID);
            System.out.println(statement);
            rowUpdated = statement.executeUpdate() > 0;
        }
        return rowUpdated;
    }

    public boolean insertAlert(String alertMessage, int test_data_id) throws SQLException {
        boolean rowUpdated;
        try (
                PreparedStatement statement = connection.prepareStatement(UPDATE_ALERT_MESSAGE_SQL);) {
            statement.setString(1,alertMessage);
            statement.setInt(2,test_data_id);
            System.out.println(statement);
            rowUpdated = statement.executeUpdate() > 0;
        }
        return rowUpdated;
    }
}
