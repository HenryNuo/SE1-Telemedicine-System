package com.hamilton.dao;

import com.hamilton.bean.*;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CommunityOwnerDao {

    private static final String INSERT_COMMUNITY_OWNER_SQL = "INSERT INTO community_owner" + "( owner_id, community_name, owner_name, birthdate, address, SSN,  email, community_id, pass) VALUES "
            + " ( ?, ?, ?, ?, ?, ?, ?, ?, ?);";
    private static final String CHECK_COMMUNITY_OWNER_EMAIL = "Select name from community_owner where email = ?;";
    private static final String GET_COMMUNITY_OWNER_SQL = "Select * from community_owner where email = ? and pass = ?;";

    private static final String SELECT_MAX_FAMILYID_SQL = "SELECT MAX(family_id) from family;";
    private static final String GET_COMMUNITY_OWNER_EMAIL = "Select * from community_owner where email = ?";

    private Connection connection;
    private DaoUtils daoUtils;

    public CommunityOwnerDao(Connection connection, DaoUtils daoUtils){
        this.connection = connection;
        this.daoUtils = daoUtils;
    }

    public void insertCommunityOwner(CommunityOwner communityOwner) throws SQLException {
        System.out.println(INSERT_COMMUNITY_OWNER_SQL);
        try(
            //"INSERT INTO community_owner" + "( owner_id, community_name, owner_name, birthdate, address, SSN,  email, community_id, pass)
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_COMMUNITY_OWNER_SQL)){
            preparedStatement.setInt(1, communityOwner.getCommunityOwnerID());
            preparedStatement.setString(2, communityOwner.getCommunityName());
            preparedStatement.setString(3, communityOwner.getName());
            preparedStatement.setDate(4, communityOwner.getBirthdate());
            preparedStatement.setString(5, communityOwner.getAddress());
            preparedStatement.setString(6, communityOwner.getSocialSecurityNumber());
            preparedStatement.setString(7, communityOwner.getEmail());
            preparedStatement.setInt(8, communityOwner.getCommunityID());
            preparedStatement.setString(9, communityOwner.getPassword());
            System.out.println(preparedStatement);
            preparedStatement.executeUpdate();
        }catch(SQLException e){
            daoUtils.printSQLException(e);
        }
    }
/*
    public CommunityOwner getCommunityOwner(String email, String password){
        System.out.println(GET_COMMUNITY_OWNER_SQL);
        CommunityOwner owner = null;
        try(
                PreparedStatement preparedStatement = connection.prepareStatement(GET_COMMUNITY_OWNER_SQL)){
            preparedStatement.setString(1,email);
            preparedStatement.setString(2,password);
            System.out.println(preparedStatement);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()) {
                owner = new CommunityOwner();
                owner.setName(rs.getString("owner_name"));
                owner.setCommunityID(rs.getInt("community_id"));
                owner.setEmail(rs.getString("email"));
                owner.setPassword(rs.getString("pass"));
            }
        }catch(SQLException e){
            daoUtils.printSQLException(e);
        }
        return owner;
    }

 */

    public CommunityOwner getCommunityOwner(String email, String password) throws InvalidKeySpecException, NoSuchAlgorithmException {
        System.out.println(GET_COMMUNITY_OWNER_EMAIL);
        CommunityOwner owner = null;
        try(
                PreparedStatement preparedStatement = connection.prepareStatement(GET_COMMUNITY_OWNER_EMAIL)){
            preparedStatement.setString(1,email);
            System.out.println(preparedStatement);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()) {
                owner = new CommunityOwner();
                owner.setName(rs.getString("owner_name"));
                owner.setCommunityID(rs.getInt("community_id"));
                owner.setEmail(rs.getString("email"));
                owner.setPassword(rs.getString("pass"));
                owner.setCommunityOwnerID(rs.getInt("owner_id"));
                owner.setCommunityName(rs.getString("community_name"));
                owner.setBirthdate(rs.getDate("birthdate"));
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

}