package com.hamilton.dao;

import com.hamilton.bean.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FamilyDao {

    private static final String INSERT_FAMILY_SQL = "INSERT INTO family" + "(family_name) VALUES " + "(?);";
    private static final String SELECT_MAX_FAMILYID_SQL = "SELECT MAX(family_id) from family;";
    private static final String CHECK_FAMILY_EXISTS = "Select family_id from family where family_id = ?;";

    private Connection connection;
    private DaoUtils daoUtils;

    public FamilyDao(Connection connection, DaoUtils daoUtils) {
        this.connection = connection;
        this.daoUtils = daoUtils;
    }

    public boolean familyExists(int familyID) {
        boolean exists = false;
        System.out.println(CHECK_FAMILY_EXISTS);
        try (
             PreparedStatement preparedStatement = connection.prepareStatement(CHECK_FAMILY_EXISTS)) {
            preparedStatement.setInt(1, familyID);
            System.out.println(preparedStatement);
            ResultSet rs = preparedStatement.executeQuery();
            exists = rs.next(); // it exists if something is returned
        } catch (SQLException e) {
            daoUtils.printSQLException(e);
        }
        return exists;
    }

    public int createFamily(String familyName) throws SQLException {
        System.out.println(INSERT_FAMILY_SQL);
        try (
                PreparedStatement preparedStatement = connection.prepareStatement(INSERT_FAMILY_SQL)) {
            preparedStatement.setString(1, familyName);
            System.out.println(preparedStatement);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            daoUtils.printSQLException(e);
        }
        return getMaxFamID();
    }

    public int getMaxFamID() throws SQLException {
        System.out.println(SELECT_MAX_FAMILYID_SQL);
        try (
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_MAX_FAMILYID_SQL)) {
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                return rs.getInt("MAX(family_id)");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}