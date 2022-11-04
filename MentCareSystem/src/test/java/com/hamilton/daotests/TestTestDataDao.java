package com.hamilton.daotests;

import com.hamilton.bean.*;
import com.hamilton.dao.DaoUtils;
import com.hamilton.dao.PatientDao;
import com.hamilton.dao.TestDataDao;
import junit.framework.TestCase;
import org.dbunit.Assertion;
import org.dbunit.IDatabaseTester;
import org.dbunit.JdbcDatabaseTester;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.junit.Assert;

import java.io.FileInputStream;
import java.sql.Date;
import java.util.Arrays;
import java.util.List;

public class TestTestDataDao extends TestCase{
    private DaoUtils daoUtils = new DaoUtils();
    IDatabaseTester databaseTester;
    private TestDataDao testDataDao;

    public TestTestDataDao(String name) {
        super(name);
    }

    public void setUp() throws Exception {
        databaseTester = new JdbcDatabaseTester(TestHelper.javaDatabaseDriver, TestHelper.javaDatabaseURL,  TestHelper.javaDatabaseUsername,  TestHelper.javaDatabasePassword);
        IDataSet dataSet = new FlatXmlDataSetBuilder().build(new FileInputStream("src/test/resources/TestDataDaoTestData/testData.xml"));
        databaseTester.setDataSet(dataSet);
        testDataDao = new TestDataDao(TestHelper.getConnection(), daoUtils);
        databaseTester.setSetUpOperation(DatabaseOperation.CLEAN_INSERT);
        databaseTester.onSetup();
    }


    public void tearDown() throws Exception {
        databaseTester.setTearDownOperation(DatabaseOperation.DELETE_ALL);
        databaseTester.onTearDown();
    }

    public void testInsertTest() throws Exception{

        IDataSet expds = new FlatXmlDataSetBuilder().build(new FileInputStream("src/test/resources/TestDataDaoTestData/testData-expected.xml"));
        ITable expectedTable = expds.getTable("test");
        IDatabaseConnection connection = databaseTester.getConnection();

        Test test = new Test("Temperature", 100, 90, "degrees fahrenheit");
        testDataDao.insertTest(test);

        IDataSet databaseDataSet = connection.createDataSet();
        ITable actualTable = databaseDataSet.getTable("test");
        Assertion.assertEquals(expectedTable, actualTable);

    }

}
