package com.hamilton.daotests;

import com.hamilton.dao.DaoUtils;
import com.hamilton.dao.FamilyDao;
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

public class TestFamilyDao extends TestCase{
    private DaoUtils daoUtils = new DaoUtils();
    IDatabaseTester databaseTester;
    private FamilyDao familyDao;

    public void setUp() throws Exception {
        databaseTester = new JdbcDatabaseTester(TestHelper.javaDatabaseDriver, TestHelper.javaDatabaseURL,  TestHelper.javaDatabaseUsername,  TestHelper.javaDatabasePassword);
        IDataSet dataSet = new FlatXmlDataSetBuilder().build(new FileInputStream("src/test/resources/FamilyTestData/family-data.xml"));
        databaseTester.setDataSet(dataSet);
        familyDao = new FamilyDao(TestHelper.getConnection(), daoUtils);
        databaseTester.setSetUpOperation(DatabaseOperation.CLEAN_INSERT);
        databaseTester.onSetup();
    }

    public void tearDown() throws Exception {
        databaseTester.setTearDownOperation(DatabaseOperation.DELETE_ALL);
        databaseTester.onTearDown();
    }

    //Family we are checking is in database
    public void testFamilyExistsPos() throws Exception {
        boolean familyExists = familyDao.familyExists(1);
        Assert.assertTrue(familyExists);
    }

    //Family we are checking is not in the database
    public void testFamilyExistsNeg() throws Exception {
        boolean familyExists = familyDao.familyExists(1000);
        Assert.assertFalse(familyExists);
    }

    public void testGetMaxFamId() throws Exception {
        Assert.assertEquals(3,familyDao.getMaxFamID());
    }

    public void testCreateFamily() throws Exception {
        IDataSet expds = new FlatXmlDataSetBuilder().build(new FileInputStream("src/test/resources/FamilyTestData/family-data-insert.xml"));
        ITable expectedTable = expds.getTable("family");
        IDatabaseConnection connection = databaseTester.getConnection();
        familyDao.createFamily("test");
        IDataSet databaseDataSet = connection.createDataSet();
        ITable actualTable = databaseDataSet.getTable("family");
        Assertion.assertEquals(expectedTable, actualTable);
    }
}
