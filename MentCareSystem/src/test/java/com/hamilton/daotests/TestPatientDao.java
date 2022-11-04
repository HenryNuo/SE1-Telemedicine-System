
package com.hamilton.daotests;

import com.hamilton.bean.CommunityOwner;
import com.hamilton.bean.Family;
import com.hamilton.bean.Patient;
import com.hamilton.dao.DaoUtils;
import com.hamilton.dao.PatientDao;
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


public class TestPatientDao extends TestCase {
    private DaoUtils daoUtils = new DaoUtils();
    IDatabaseTester databaseTester;
    private PatientDao patientDao;


    public TestPatientDao(String name) {
        super(name);
    }

    public void setUp() throws Exception {
        databaseTester = new JdbcDatabaseTester(TestHelper.javaDatabaseDriver, TestHelper.javaDatabaseURL,  TestHelper.javaDatabaseUsername,  TestHelper.javaDatabasePassword);
        IDataSet dataSet = new FlatXmlDataSetBuilder().build(new FileInputStream("src/test/resources/patientTestData/patientData.xml"));
        databaseTester.setDataSet(dataSet);
        patientDao = new PatientDao(TestHelper.getConnection(), daoUtils);
        databaseTester.setSetUpOperation(DatabaseOperation.CLEAN_INSERT);
        databaseTester.onSetup();
    }


    public void tearDown() throws Exception {
        databaseTester.setTearDownOperation(DatabaseOperation.DELETE_ALL);
        databaseTester.onTearDown();
    }

    public void testDeletePatientById() throws Exception {
        IDataSet expds = new FlatXmlDataSetBuilder().build(new FileInputStream("src/test/resources/patientTestData/patientData-expected.xml"));
        ITable expectedTable = expds.getTable("patient");
        IDatabaseConnection connection = databaseTester.getConnection();
        patientDao.deletePatientById(1);
        IDataSet databaseDataSet = connection.createDataSet();
        ITable actualTable = databaseDataSet.getTable("patient");
        Assertion.assertEquals(expectedTable, actualTable);
    }

    public void testApprovePatient() throws Exception {
        IDataSet expds = new FlatXmlDataSetBuilder().build(new FileInputStream("src/test/resources/patientTestData/patientData-expected-approve.xml"));
        ITable expectedTable = expds.getTable("patient");
        IDatabaseConnection connection = databaseTester.getConnection();
        patientDao.approvePatient(4);
        IDataSet databaseDataSet = connection.createDataSet();
        ITable actualTable = databaseDataSet.getTable("patient");
        Assertion.assertEquals(expectedTable, actualTable);
    }

    public void testGetPatient() throws Exception {
        Patient actualPatient = patientDao.getPatient("test@gmail.com","password");
        Patient expectedPatient = new Patient(1,"test", "test@gmail.com","65536:3c7e224c719b40e905cb34a4c5516ad5:e0092472df9c310f542606a849d938a4",Date.valueOf("2000-02-02"),"test","100-10-1000",1,1,2, true, 1);
        Assert.assertTrue(actualPatient.equals(expectedPatient));
    }

    public void testGetPatientNull() throws Exception {
        Patient actualPatient = patientDao.getPatient("nonexistent@gmail.com","password");
        Assert.assertNull(actualPatient);
    }

    public void testInsertPatient() throws Exception{
        IDataSet expds = new FlatXmlDataSetBuilder().build(new FileInputStream("src/test/resources/patientTestData/patient-insert-expected.xml"));
        ITable expectedTable = expds.getTable("patient");
        IDatabaseConnection connection = databaseTester.getConnection();
        Patient patient = new Patient("test", "test7@gmail.com","65536:3c7e224c719b40e905cb34a4c5516ad5:e0092472df9c310f542606a849d938a4",Date.valueOf("2000-02-02"),"test","100-10-1000",2,3,2, 1);
        Family newfamily = new Family("test",3);
        patientDao.insertPatient(patient,newfamily);
        IDataSet databaseDataSet = connection.createDataSet();
        ITable actualTable = databaseDataSet.getTable("patient");
        Assertion.assertEquals(expectedTable, actualTable);
    }

    public void testSelectNotApprovedPatientsForCommunity() {
        CommunityOwner co = new CommunityOwner();
        co.setCommunityID(2);
        List<Patient> actualPatients= patientDao.selectNotApprovedPatientsForCommunity(co);
        Patient patient1 =new Patient(6,"test", "test5@gmail.com",
                "65536:3c7e224c719b40e905cb34a4c5516ad5:e0092472df9c310f542606a849d938a4",Date.valueOf("2000-02-02"),
                "test","100-10-1000",2,3,2, false, 1);
        Patient patient2 =new Patient(7,"test", "test6@gmail.com",
                "65536:3c7e224c719b40e905cb34a4c5516ad5:e0092472df9c310f542606a849d938a4",Date.valueOf("2000-02-02"),
                "test","100-10-1000",2,3,2, false, 1);

        List<Patient> expected = Arrays.asList(patient1,patient2);
        Assert.assertEquals(actualPatients,expected);
    }

    public void testGetMaxFamId() throws Exception {
        Assert.assertEquals(3,patientDao.getMaxFamID());
    }

}
