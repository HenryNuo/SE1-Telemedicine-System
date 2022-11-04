package com.hamilton.daotests;

import com.hamilton.bean.MedicalOwner;
import com.hamilton.bean.User;
import com.hamilton.dao.MedicalOwnerDao;
import com.hamilton.dao.DaoUtils;
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


public class TestMedicalOwnerDao extends TestCase {
    private DaoUtils daoUtils = new DaoUtils();
    IDatabaseTester databaseTester;
    private MedicalOwnerDao moDao;


    public TestMedicalOwnerDao(String name) {
        super(name);
    }

    public void setUp() throws Exception {
        databaseTester = new JdbcDatabaseTester(TestHelper.javaDatabaseDriver, TestHelper.javaDatabaseURL,  TestHelper.javaDatabaseUsername,  TestHelper.javaDatabasePassword);
        IDataSet dataSet = new FlatXmlDataSetBuilder().build(new FileInputStream("src/test/resources/MOTestData/mo-data.xml"));
        databaseTester.setDataSet(dataSet);
        moDao = new MedicalOwnerDao(TestHelper.getConnection(), daoUtils);
        databaseTester.setSetUpOperation(DatabaseOperation.CLEAN_INSERT);
        databaseTester.onSetup();
    }


    public void tearDown() throws Exception {
        databaseTester.setTearDownOperation(DatabaseOperation.DELETE_ALL);
        databaseTester.onTearDown();
    }



    public void testGetMedicalOwner() throws Exception {
        MedicalOwner actualMo = moDao.getMedicalOwner("test1@gmail.com","password");
        User user = new User("test", "test1@gmail.com","65536:3c7e224c719b40e905cb34a4c5516ad5:e0092472df9c310f542606a849d938a4",Date.valueOf("2000-02-02"),"test","100-10-1000");
        MedicalOwner expectedCO = new MedicalOwner(user,1,1,"test");
        Assert.assertTrue(actualMo.equals(expectedCO));
    }

    public void testGetMedicalOwnerNull() throws Exception {
        MedicalOwner actualMedicalOwner = moDao.getMedicalOwner("nonexistent@gmail.com","password");
        Assert.assertNull(actualMedicalOwner);
    }


    public void testInsertMedicalOwner() throws Exception{
        IDataSet expds = new FlatXmlDataSetBuilder().build(new FileInputStream("src/test/resources/MOTestData/mo-insert.xml"));
        ITable expectedTable = expds.getTable("medical_owner");
        IDatabaseConnection connection = databaseTester.getConnection();
        User user = new User("test", "test4@gmail.com","65536:3c7e224c719b40e905cb34a4c5516ad5:e0092472df9c310f542606a849d938a4",Date.valueOf("2000-02-02"),"test","100-10-1000");
        MedicalOwner mo = new MedicalOwner(user,2,4,"test");
        moDao.insertMedicalOwner(mo);
        IDataSet databaseDataSet = connection.createDataSet();
        ITable actualTable = databaseDataSet.getTable("medical_owner");
        Assertion.assertEquals(expectedTable, actualTable);
    }




}
