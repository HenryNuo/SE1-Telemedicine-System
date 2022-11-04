package com.hamilton.dao;

import com.hamilton.bean.*;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MedicalOwnerDao {
    private static final String INSERT_MEDICAL_OWNER_SQL = "INSERT INTO medical_owner" + "( med_id, med_name, birthdate, address, SSN, clinic_id,  email, pass) VALUES "
            + " ( ?, ?, ?, ?, ?, ?, ?, ?);";

    private static final String CHECK_MEDICAL_OWNER_EMAIL = "Select name from medical_owner where email = ?;";
    private static final String GET_MEDICAL_OWNER_SQL = "Select * from medical_owner where email = ? and pass = ?;";
    private static final String APPROVE_DOCTOR_SQL = "UPDATE doctor SET isApproved=1 WHERE doctor_ID = ?;";
    private static final String SELECT_MAX_FAMILYID_SQL = "SELECT MAX(family_id) from family;";
    private static final String GET_MEDICAL_OWNER_EMAIL = "Select * from medical_owner where email = ?";

    private Connection connection;
    private DaoUtils daoUtils;

    public MedicalOwnerDao(Connection connection, DaoUtils daoUtils){
        this.connection = connection;
        this.daoUtils = daoUtils;
    }
/*
    public MedicalOwner getMedicalOwner(String email, String password){
        System.out.println(GET_MEDICAL_OWNER_SQL);
        MedicalOwner owner = null;
        try(
            PreparedStatement preparedStatement = connection.prepareStatement(GET_MEDICAL_OWNER_SQL)){
            preparedStatement.setString(1,email);
            preparedStatement.setString(2,password);
            System.out.println(preparedStatement);

            ResultSet rs = preparedStatement.executeQuery();
            // TODO: finish this after database is created
            while(rs.next()) {
                owner = new MedicalOwner();
                owner.setName(rs.getString("med_name"));
                owner.setClinicID(rs.getInt("clinic_id"));
                owner.setEmail(rs.getString("email"));
                owner.setPassword(rs.getString("pass"));
            }
        }catch(SQLException e){
            daoUtils.printSQLException(e);
        }
        return owner;
    }
*/

    public MedicalOwner getMedicalOwner(String email, String password) throws NoSuchAlgorithmException, InvalidKeySpecException {
        System.out.println(GET_MEDICAL_OWNER_EMAIL);
        MedicalOwner owner = null;
        try(
                PreparedStatement preparedStatement = connection.prepareStatement(GET_MEDICAL_OWNER_EMAIL)){
            preparedStatement.setString(1,email);
            System.out.println(preparedStatement);

            ResultSet rs = preparedStatement.executeQuery();
            // TODO: finish this after database is created
            while(rs.next()) {
                owner = new MedicalOwner();
                owner.setName(rs.getString("med_name"));
                owner.setClinicID(rs.getInt("clinic_id"));
                owner.setEmail(rs.getString("email"));
                owner.setPassword(rs.getString("pass"));
                owner.setMedicalOwnerID(rs.getInt("med_id"));
                owner.setMedicalName(rs.getString("med_name"));
            }

            boolean passwordMatch = false;
            if(owner != null) {
                passwordMatch = daoUtils.checkPassword(password, owner.getPassword());
            }

            if(!passwordMatch) {
                owner = null;
            }

        }catch(SQLException e){
            daoUtils.printSQLException(e);
        }
        return owner;
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

    public void insertMedicalOwner(com.hamilton.bean.MedicalOwner medicalOwner) throws SQLException {
        System.out.println(INSERT_MEDICAL_OWNER_SQL);
        try(
            //"INSERT INTO medical_owner" + "( med_id, med_name, birthdate, address, SSN, clinic_id,  email, pass)
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_MEDICAL_OWNER_SQL)){
            preparedStatement.setInt(1, medicalOwner.getMedicalOwnerID());
            preparedStatement.setString(2,medicalOwner.getName());
            preparedStatement.setDate(3, medicalOwner.getBirthdate());
            preparedStatement.setString(4, medicalOwner.getAddress());
            preparedStatement.setString(5, medicalOwner.getSocialSecurityNumber());
            preparedStatement.setInt(6,  medicalOwner.getClinicID());
            preparedStatement.setString(7, medicalOwner.getEmail());
            preparedStatement.setString(8, medicalOwner.getPassword());
            System.out.println(preparedStatement);
            preparedStatement.executeUpdate();
        }catch(SQLException e){
            daoUtils.printSQLException(e);
        }
    }
}
