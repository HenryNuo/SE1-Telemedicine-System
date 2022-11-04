package com.hamilton.daotests;

import com.hamilton.dao.ClinicDao;
import com.hamilton.dao.DaoUtils;
import junit.framework.TestCase;
import org.dbunit.IDatabaseTester;
import org.dbunit.JdbcDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.junit.Assert;

import java.io.FileInputStream;

public class TestClinicDao extends TestCase{
    private DaoUtils daoUtils = new DaoUtils();
    IDatabaseTester databaseTester;
    private ClinicDao clinicDao;

    public void setUp() throws Exception {
        databaseTester = new JdbcDatabaseTester(TestHelper.javaDatabaseDriver, TestHelper.javaDatabaseURL,  TestHelper.javaDatabaseUsername,  TestHelper.javaDatabasePassword);
        IDataSet dataSet = new FlatXmlDataSetBuilder().build(new FileInputStream("src/test/resources/ClinicTestData/clinicExists.xml"));
        databaseTester.setDataSet(dataSet);
        clinicDao = new ClinicDao(TestHelper.getConnection(), daoUtils);
        databaseTester.setSetUpOperation(DatabaseOperation.CLEAN_INSERT);
        databaseTester.onSetup();
    }

    public void tearDown() throws Exception {
        databaseTester.setTearDownOperation(DatabaseOperation.DELETE_ALL);
        databaseTester.onTearDown();
    }

    //Clinic we are checking is in database
    public void testClinicExistsPos() throws Exception {
        boolean clinicExistsActual = clinicDao.clinicExists(1);
        boolean clinicExistsExpected = true;
        Assert.assertTrue(clinicExistsActual && clinicExistsExpected);
    }

    //Clinic we are checking is not in the database
    public void testClinicExistsNeg() throws Exception {
        boolean clinicExistsActual = clinicDao.clinicExists(2);
        boolean clinicExistsExpected = true;
        Assert.assertFalse(clinicExistsActual && clinicExistsExpected);
    }

}
