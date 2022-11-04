package com.hamilton.daotests;

import com.hamilton.bean.Doctor;
import com.hamilton.bean.MedicalOwner;
import com.hamilton.dao.DaoUtils;
import com.hamilton.dao.DoctorDao;
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


public class TestDoctorDao extends TestCase {
    private DaoUtils daoUtils = new DaoUtils();
    IDatabaseTester databaseTester;
    private DoctorDao doctorDao;


    public TestDoctorDao(String name) {
        super(name);
    }

    public void setUp() throws Exception {
        databaseTester = new JdbcDatabaseTester(TestHelper.javaDatabaseDriver, TestHelper.javaDatabaseURL,  TestHelper.javaDatabaseUsername,  TestHelper.javaDatabasePassword);
        IDataSet dataSet = new FlatXmlDataSetBuilder().build(new FileInputStream("src/test/resources/DoctorTestData/doctorData.xml"));
        databaseTester.setDataSet(dataSet);
        doctorDao = new DoctorDao(TestHelper.getConnection(), daoUtils);
        databaseTester.setSetUpOperation(DatabaseOperation.CLEAN_INSERT);
        databaseTester.onSetup();
    }


    public void tearDown() throws Exception {
        databaseTester.setTearDownOperation(DatabaseOperation.DELETE_ALL);
        databaseTester.onTearDown();
    }

    public void testDeleteDoctorById() throws Exception {
        IDataSet expds = new FlatXmlDataSetBuilder().build(new FileInputStream("src/test/resources/DoctorTestData/doctor-deleted.xml"));
        ITable expectedTable = expds.getTable("doctor");
        IDatabaseConnection connection = databaseTester.getConnection();
        doctorDao.deleteDoctor(1);
        IDataSet databaseDataSet = connection.createDataSet();
        ITable actualTable = databaseDataSet.getTable("doctor");
        Assertion.assertEquals(expectedTable, actualTable);
    }

    public void testApproveDoctor() throws Exception {
        IDataSet expds = new FlatXmlDataSetBuilder().build(new FileInputStream("src/test/resources/DoctorTestData/doctor-approved.xml"));
        ITable expectedTable = expds.getTable("doctor");
        IDatabaseConnection connection = databaseTester.getConnection();
        doctorDao.approveDoctor(6);
        IDataSet databaseDataSet = connection.createDataSet();
        ITable actualTable = databaseDataSet.getTable("doctor");
        Assertion.assertEquals(expectedTable, actualTable);
    }


    public void testGetDoctor() throws Exception {
        Doctor actualDoctor = doctorDao.getDoctor("test1@gmail.com","password");
        Doctor expectedDoctor = new Doctor(1,"test", "test1@gmail.com","65536:3c7e224c719b40e905cb34a4c5516ad5:e0092472df9c310f542606a849d938a4",Date.valueOf("2000-02-02"),"test","100-10-1000",1,1, true );
        Assert.assertTrue(actualDoctor.equals(expectedDoctor));
    }

    public void testGetDoctorNull() throws Exception {
        Doctor actualDoctor = doctorDao.getDoctor("nonexistent@gmail.com","password");
        Assert.assertNull(actualDoctor);
    }


    public void testInsertDoctor() throws Exception{
        IDataSet expds = new FlatXmlDataSetBuilder().build(new FileInputStream("src/test/resources/DoctorTestData/doctor-insert.xml"));
        ITable expectedTable = expds.getTable("doctor");
        IDatabaseConnection connection = databaseTester.getConnection();
        Doctor doctor = new Doctor("test", "test8@gmail.com","65536:3c7e224c719b40e905cb34a4c5516ad5:e0092472df9c310f542606a849d938a4",Date.valueOf("2000-02-02"),"test","100-10-1000",2,2,false);
        doctorDao.insertDoctor(doctor);
        IDataSet databaseDataSet = connection.createDataSet();
        ITable actualTable = databaseDataSet.getTable("doctor");
        Assertion.assertEquals(expectedTable, actualTable);
    }

    public void testSelectNotApprovedDoctorsByClinic() {
        MedicalOwner mo = new MedicalOwner();
        mo.setClinicID(2);
        List<Doctor> actualDoctors= doctorDao.selectAllNotApprovedDoctorsByClinic(mo);
        Doctor doctor1 =new Doctor(6,"test", "test6@gmail.com",
                "65536:3c7e224c719b40e905cb34a4c5516ad5:e0092472df9c310f542606a849d938a4",Date.valueOf("2000-02-02"),
                "test","100-10-1000",2,2, false );
        Doctor doctor2 =new Doctor(7,"test", "test7@gmail.com",
                "" + "65536:3c7e224c719b40e905cb34a4c5516ad5:e0092472df9c310f542606a849d938a4",Date.valueOf("2000-02-02"),
                "test","100-10-1000",2,2, false );

        List<Doctor> expected = Arrays.asList(doctor1,doctor2);
        Assert.assertEquals(actualDoctors,expected);
    }


}
