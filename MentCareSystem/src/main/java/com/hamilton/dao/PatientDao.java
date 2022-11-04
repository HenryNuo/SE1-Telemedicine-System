package com.hamilton.dao;

import com.hamilton.bean.*;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PatientDao {

    private static final String INSERT_PATIENT_SQL = "INSERT INTO patient" + "(name, email, pass, birthdate, address, SSN, community_id, family_id, prev_fam_id, clinic_id) VALUES "
            + " ( ?, ?, ?,?, ?, ?, ?, ?,?, ?);";
    private static final String GET_PATIENT_SQL = "Select * from patient where email = ? and pass = ?;";
    private static final String SELECT_MAX_FAMILYID_SQL = "SELECT MAX(family_id) from family;";
    private static final String INSERT_FAMILY_SQL = "INSERT INTO family" + "(family_name) VALUES " + "(?);";
    private static final String GET_UNNAPROVED_PATIENTS = "select * from PATIENT where isApproved = 0 and community_id = ?";
    private static final String DELETE_PATIENT_BY_ID = "delete from patient where patient_id = ?;";
    private static final String APPROVE_PATIENT_SQL = "UPDATE patient SET isApproved=1 WHERE patient_id = ?;";
    private static final String GET_PATIENT_EMAIL = "Select * from patient where email = ?";
    private Connection connection;
    private DaoUtils daoUtils;

    public PatientDao(Connection connection, DaoUtils daoUtils){
        this.connection = connection;
        this.daoUtils = daoUtils;
    }

    public void insertPatient(Patient patient, Family family) throws SQLException {
        System.out.println(INSERT_PATIENT_SQL);
        try(

            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_PATIENT_SQL)){
            preparedStatement.setString(1, patient.getName());
            preparedStatement.setString(2, patient.getEmail());
            preparedStatement.setString(3, patient.getPassword());
            preparedStatement.setDate(4, patient.getBirthdate());
            preparedStatement.setString(5, patient.getAddress());
            preparedStatement.setString(6, patient.getSocialSecurityNumber());
            preparedStatement.setInt(7, patient.getCommunityID());
            preparedStatement.setInt(8, family.getFamilyID());
            preparedStatement.setInt(9, patient.getPreviousFamilyID());
            preparedStatement.setInt(10, patient.getClinicID());
            System.out.println(preparedStatement);
            preparedStatement.executeUpdate();
        }catch(SQLException e){
            daoUtils.printSQLException(e);
        }
    }

    public boolean deletePatientById(int id) throws SQLException{
        boolean rowDeleted;
        try (
                PreparedStatement statement = connection.prepareStatement(DELETE_PATIENT_BY_ID);) {
            statement.setInt(1, id);
            rowDeleted = statement.executeUpdate() > 0;
        }
        return rowDeleted;
    }

    public boolean approvePatient(int id) throws SQLException {
        boolean rowUpdated;
        try (
                PreparedStatement statement = connection.prepareStatement(APPROVE_PATIENT_SQL);) {
            statement.setInt(1, id);
            System.out.println(statement);
            rowUpdated = statement.executeUpdate() > 0;
        }
        return rowUpdated;
    }

//    public Patient getPatient(String email, String password){
//        System.out.println(GET_PATIENT_SQL);
//        Patient patient = null;
//        try(
//            PreparedStatement preparedStatement = connection.prepareStatement(GET_PATIENT_SQL)){
//            preparedStatement.setString(1,email);
//            preparedStatement.setString(2,password);
//            System.out.println(preparedStatement);
//
//            ResultSet rs = preparedStatement.executeQuery();
//            while(rs.next()){
//                patient = new Patient();
//                patient.setName(rs.getString("name"));
//                patient.setPatientID(rs.getInt("patient_id"));
//                patient.setEmail(rs.getString("email"));
//                patient.setPassword(rs.getString("pass"));
//                patient.setBirthdate(rs.getDate("birthdate"));
//                patient.setAddress(rs.getString("address"));
//                patient.setSocialSecurityNumber(rs.getString("SSN"));
//                patient.setCommunityID(rs.getInt("community_id"));
//                patient.setFamilyID(rs.getInt("family_id"));
//                patient.setPreviousFamilyID(rs.getInt("prev_fam_id"));
//                patient.setApproved(rs.getBoolean("isApproved"));
//            }
//
//        }catch(SQLException e){
//            daoUtils.printSQLException(e);
//        }
//        return patient;
//    }


    //TODO: Password Hashing Version, Don't Delete

    public Patient getPatient(String email, String password) throws NoSuchAlgorithmException, InvalidKeySpecException {

        System.out.println(GET_PATIENT_EMAIL);
        Patient patient = null;

        try(PreparedStatement preparedStatement = connection.prepareStatement(GET_PATIENT_EMAIL)) {

            preparedStatement.setString(1,email);
            ResultSet rs = preparedStatement.executeQuery();

            while(rs.next()) {
                patient = new Patient();
                patient.setName(rs.getString("name"));
                patient.setPatientID(rs.getInt("patient_id"));
                patient.setEmail(rs.getString("email"));
                patient.setPassword(rs.getString("pass"));
                patient.setBirthdate(rs.getDate("birthdate"));
                patient.setAddress(rs.getString("address"));
                patient.setSocialSecurityNumber(rs.getString("SSN"));
                patient.setCommunityID(rs.getInt("community_id"));
                patient.setFamilyID(rs.getInt("family_id"));
                patient.setPreviousFamilyID(rs.getInt("prev_fam_id"));
                patient.setApproved(rs.getBoolean("isApproved"));
                patient.setClinicID(rs.getInt("clinic_id"));

            }

            boolean passwordMatch = false;
            if(patient != null) {
                passwordMatch = daoUtils.checkPassword(password, patient.getPassword());
            }

            if(!passwordMatch) {
                patient = null;
            }

        }catch(SQLException e){
            daoUtils.printSQLException(e);
        }

        return patient;
    }

    public int getMaxFamID() throws SQLException {
        System.out.println(SELECT_MAX_FAMILYID_SQL);
        try(
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_MAX_FAMILYID_SQL)){
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()){
                return rs.getInt("MAX(family_id)");
            }

        } catch (SQLException e){
            e.printStackTrace();
        }
        return 0;
    }

    public List<Patient> selectNotApprovedPatientsForCommunity(CommunityOwner communityOwner) {

        // using try-with-resources to avoid closing resources (boiler plate code)
        List<Patient> patients = new ArrayList<>();
        // Step 1: Establishing a Connection
        try (

                // Step 2:Create a statement using connection object
                PreparedStatement preparedStatement = connection.prepareStatement(GET_UNNAPROVED_PATIENTS);) {
            preparedStatement.setInt(1, communityOwner.getCommunityID());
            System.out.println(preparedStatement);
            // Step 3: Execute the query or update query
            ResultSet rs = preparedStatement.executeQuery();

            // Step 4: Process the ResultSet object.
            while (rs.next()) {

                int patientID = rs.getInt("patient_id");
                String name = rs.getString("name");
                String email = rs.getString("email");
                String pass = rs.getString("pass");
                Date birthdate = rs.getDate("birthdate");
                String address = rs.getString("address");
                String ssn = rs.getString("SSN");
                int communityID = rs.getInt("community_id");
                int famID = rs.getInt("family_id");
                int prev_fam_id = rs.getInt("prev_fam_id");
                boolean isApproved = rs.getBoolean("isApproved");
                int clinicID = rs.getInt("clinic_id");

                patients.add(new Patient(patientID, name, email, pass, birthdate, address, ssn, communityID,famID,prev_fam_id, isApproved, clinicID));
            }
        } catch (SQLException e) {
            daoUtils.printSQLException(e);
        }
        return patients;
    }
}
