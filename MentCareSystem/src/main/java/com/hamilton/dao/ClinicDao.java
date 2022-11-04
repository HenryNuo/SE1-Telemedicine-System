package com.hamilton.dao;

import com.hamilton.bean.*;

import java.sql.*;

public class ClinicDao {

    private static final String INSERT_CLINIC_SQL = "INSERT INTO users" + "(medicalOwnerName, medicalOwnerID, clinicDescription) VALUES "
            + " ( ?, ?, ?);";
    private static final String CHECK_CLINIC_EXISTS ="Select clinic_id from clinic where clinic_id = ?;";
    private static final String SELECT_MAX_FAMILYID_SQL = "SELECT MAX(family_id) from family;";

    private Connection connection;
    private DaoUtils daoUtils;

    public ClinicDao(Connection connection, DaoUtils daoUtils){
        this.connection = connection;
        this.daoUtils = daoUtils;
    }

    public boolean clinicExists(int clinicID){
        boolean exists = false;
        System.out.println(CHECK_CLINIC_EXISTS);
        try(
            PreparedStatement preparedStatement = connection.prepareStatement(CHECK_CLINIC_EXISTS)){
            preparedStatement.setInt(1, clinicID);
            System.out.println(preparedStatement);
            ResultSet rs = preparedStatement.executeQuery();
            exists = rs.next(); // it exists if something is returned
        }catch(SQLException e){
            daoUtils.printSQLException(e);
        }
        return exists;
    }
/*
    public void insertClinic(Clinic clinic) throws SQLException {
        System.out.println(INSERT_CLINIC_SQL);
        try(
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_CLINIC_SQL)){
            preparedStatement.setInt(1,   clinic.getClinicID());
            preparedStatement.setString(2,clinic.getMedicalOwnerName());
            preparedStatement.setString(3,clinic.getMedicalName());
            System.out.println(preparedStatement);
            preparedStatement.executeUpdate();
        }catch(SQLException e){
            daoUtils.printSQLException(e);
        }
    }

 */
}
