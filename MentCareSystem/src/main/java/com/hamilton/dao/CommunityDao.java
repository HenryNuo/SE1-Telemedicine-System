package com.hamilton.dao;

import com.hamilton.bean.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CommunityDao {

    private static final String INSERT_COMMUNITY_SQL = "INSERT INTO community" + "(community_name,owner_name,community_info) VALUES "
            + " ( ?, ?, ?);";

    private static final String CHECK_COMMUNITY_EXISTS ="Select community_id from community where community_id = ?;";
    private static final String SELECT_MAX_FAMILYID_SQL = "SELECT MAX(family_id) from family;";

    private Connection connection;
    private DaoUtils daoUtils;

    public CommunityDao(Connection connection, DaoUtils daoUtils){
        this.connection = connection;
        this.daoUtils = daoUtils;
    }

    public boolean communityExists(int communityID){
        boolean exists = false;
        System.out.println(CHECK_COMMUNITY_EXISTS);
        try(PreparedStatement preparedStatement = connection.prepareStatement(CHECK_COMMUNITY_EXISTS)){
            preparedStatement.setInt(1, communityID);
            System.out.println(preparedStatement);
            ResultSet rs = preparedStatement.executeQuery();
            exists = rs.next(); // it exists if something is returned
        }catch(SQLException e){
            daoUtils.printSQLException(e);
        }
        return exists;
    }

    public void insertCommunity(Community community) throws SQLException {
        System.out.println(INSERT_COMMUNITY_SQL);
        try(
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_COMMUNITY_SQL)){
            preparedStatement.setString(1, community.getCommunityName());
            preparedStatement.setString(2, community.getCommunityOwnerName());
            preparedStatement.setString(3, community.getCommunityInfo());
            System.out.println(preparedStatement);
            preparedStatement.executeUpdate();
        }catch(SQLException e) {
            daoUtils.printSQLException(e);
        }
    }
}
