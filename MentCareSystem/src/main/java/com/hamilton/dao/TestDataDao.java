package com.hamilton.dao;

import com.hamilton.bean.*;

import javax.servlet.http.HttpSession;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TestDataDao {

    private static final String INSERT_TEST_DATA_SQL = "INSERT INTO test_data" + "( patient_ID, data_level, issue, time_entered, unitOfMeasure) VALUES "
            + " ( ?, ?, ?, ?, ?);";
    private static final String INSERT_TEST = "INSERT INTO test" + "(test_name, upper_limit, lower_limit, unit_of_measure) VALUES"
            + "(?, ?, ?, ?)";
    private static final String SELECT_ALL_TESTS = "select * from test";
    private static final String SELECT_ALL_TEST_DATA = "select * from test_data";
    private static final String SELECT_ALL_TEST_DATA_FOR_COMMUNITY = "select * from test_data INNER JOIN Patient " +
            "ON test_data.patient_ID=patient.patient_id Where patient.community_id=?;";
    private static final String SELECT_ALL_TEST_DATA_FOR_CLINIC = "select * from test_data INNER JOIN Patient " +
            "ON test_data.patient_ID=patient.patient_id Where patient.clinic_id=?;";

    private Connection connection;
    private DaoUtils daoUtils;

    public TestDataDao(Connection connection, DaoUtils daoUtils){
        this.connection = connection;
        this.daoUtils = daoUtils;
    }

    public void insertTestData(Test_data test_data) throws SQLException {
        System.out.println(INSERT_TEST_DATA_SQL);
        try(
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_TEST_DATA_SQL)){
            preparedStatement.setInt(1, test_data.getPatient_ID());
            preparedStatement.setFloat(2, test_data.getStats());
            preparedStatement.setString(3, test_data.getType());
            preparedStatement.setString(4, test_data.getTimeOne());
            preparedStatement.setString(5, test_data.getUnitOfMeasure());

            System.out.println(preparedStatement);
            preparedStatement.executeUpdate();
        }catch(SQLException e){
            daoUtils.printSQLException(e);
        }
    }

    public void insertTest(Test test) {
        System.out.println(INSERT_TEST);
        try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_TEST)) {

            preparedStatement.setString(1, test.getTestName());
            preparedStatement.setDouble(2, test.getUpperLimit());
            preparedStatement.setDouble(3, test.getLowerLimit());
            preparedStatement.setString(4, test.getUnitOfMeasure());

            System.out.println(preparedStatement);
            preparedStatement.executeUpdate();

        } catch(SQLException e){
            daoUtils.printSQLException(e);
        }
    }

    public List<Test> selectAllTests() {
        String statement =  SELECT_ALL_TESTS;

        // using try-with-resources to avoid closing resources (boiler plate code)
        List<Test> tests = new ArrayList<>();
        // Step 1: Establishing a Connection
        try (

                // Step 2:Create a statement using connection object
                PreparedStatement preparedStatement = connection.prepareStatement(statement);) {
            System.out.println(preparedStatement);
            // Step 3: Execute the query or update query
            ResultSet rs = preparedStatement.executeQuery();

            // Step 4: Process the ResultSet object.
            while (rs.next()) {

                int testID = rs.getInt("test_id");
                String testName = rs.getString("test_name");
                double upperLimit = rs.getDouble("upper_limit");
                double lowerLimit = rs.getDouble("lower_limit");
                String unitOfMeasure = rs.getString("unit_of_measure");

                tests.add(new Test(testName, upperLimit, lowerLimit, unitOfMeasure));
            }
        } catch (SQLException e) {
            daoUtils.printSQLException(e);
        }
        return tests;
    }

    public List<Test_data> selectAllTestData() {
        String statement =  SELECT_ALL_TEST_DATA;

        // using try-with-resources to avoid closing resources (boiler plate code)
        List<Test_data> tests = new ArrayList<>();
        // Step 1: Establishing a Connection
        try (

            // Step 2:Create a statement using connection object
            PreparedStatement preparedStatement = connection.prepareStatement(statement);) {
            System.out.println(preparedStatement);
            // Step 3: Execute the query or update query
            ResultSet rs = preparedStatement.executeQuery();

            // Step 4: Process the ResultSet object.
            while (rs.next()) {

                int patientID = rs.getInt("patient_id");
                float dataLevel = rs.getFloat("data_level");
                String issue = rs.getString("issue");
                String time = rs.getString("time_entered");
                System.out.print(time);
                String unitOfMeasure = rs.getString("unitOfMeasure");
                String alertMessage = rs.getString("alertMessage");
                int test_data_ID = rs.getInt("test_data_id");

                tests.add(new Test_data(patientID, dataLevel, issue, time, unitOfMeasure, alertMessage, test_data_ID));
            }
        } catch (SQLException e) {
            daoUtils.printSQLException(e);
        }
        return tests;
    }

    public List<Test_data> selectAllTestDataForCommunity(int communityId) {
        String statement =  SELECT_ALL_TEST_DATA_FOR_COMMUNITY;

        // using try-with-resources to avoid closing resources (boiler plate code)
        List<Test_data> tests = new ArrayList<>();
        // Step 1: Establishing a Connection
        try (

                // Step 2:Create a statement using connection object
                PreparedStatement preparedStatement = connection.prepareStatement(statement);) {
            preparedStatement.setInt(1,communityId);
            System.out.println(preparedStatement);
            // Step 3: Execute the query or update query
            ResultSet rs = preparedStatement.executeQuery();

            // Step 4: Process the ResultSet object.
            while (rs.next()){

                int patientID = rs.getInt("patient_id");
                float dataLevel = rs.getFloat("data_level");
                String issue = rs.getString("issue");
                String time = rs.getString("time_entered");
                System.out.print(time);
                String unitOfMeasure = rs.getString("unitOfMeasure");
                String alertMessage = rs.getString("alertMessage");
                int test_data_ID = rs.getInt("test_data_id");

                tests.add(new Test_data(patientID, dataLevel, issue, time, unitOfMeasure, alertMessage, test_data_ID));
            }
        } catch (SQLException e) {
            daoUtils.printSQLException(e);
        }
        return tests;
    }

    public List<Test_data> selectAllTestDataForClinic(int clinicID) {
        String statement =  SELECT_ALL_TEST_DATA_FOR_CLINIC;

        // using try-with-resources to avoid closing resources (boiler plate code)
        List<Test_data> tests = new ArrayList<>();
        // Step 1: Establishing a Connection
        try (

                // Step 2:Create a statement using connection object
                PreparedStatement preparedStatement = connection.prepareStatement(statement);) {
            preparedStatement.setInt(1,clinicID);
            System.out.println(preparedStatement);
            // Step 3: Execute the query or update query
            ResultSet rs = preparedStatement.executeQuery();

            // Step 4: Process the ResultSet object.
            while (rs.next()){

                int patientID = rs.getInt("patient_id");
                float dataLevel = rs.getFloat("data_level");
                String issue = rs.getString("issue");
                String time = rs.getString("time_entered");
                System.out.print(time);
                String unitOfMeasure = rs.getString("unitOfMeasure");
                String alertMessage = rs.getString("alertMessage");
                int test_data_ID = rs.getInt("test_data_id");

                tests.add(new Test_data(patientID, dataLevel, issue, time, unitOfMeasure, alertMessage, test_data_ID));
            }
        } catch (SQLException e) {
            daoUtils.printSQLException(e);
        }
        return tests;
    }


}
