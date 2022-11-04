package com.hamilton.daotests;

import com.hamilton.dao.DaoUtils;
import junit.framework.TestCase;
import org.dbunit.IDatabaseTester;
import org.dbunit.JdbcDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.junit.Assert;
import java.io.FileInputStream;



public class TestDaoUtils extends TestCase{
    private DaoUtils daoUtils;
    IDatabaseTester databaseTester;

    public void setUp() throws Exception {
        databaseTester = new JdbcDatabaseTester(TestHelper.javaDatabaseDriver, TestHelper.javaDatabaseURL,  TestHelper.javaDatabaseUsername,  TestHelper.javaDatabasePassword);
        IDataSet dataSet = new FlatXmlDataSetBuilder().build(new FileInputStream("src/test/resources/DaoUtilsTestData/daoUtils.xml"));
        databaseTester.setDataSet(dataSet);
        daoUtils = new DaoUtils(TestHelper.getConnection());
        databaseTester.setSetUpOperation(DatabaseOperation.CLEAN_INSERT);
        databaseTester.onSetup();
    }

    public void tearDown() throws Exception {
        databaseTester.setTearDownOperation(DatabaseOperation.DELETE_ALL);
        databaseTester.onTearDown();
    }

    public void testToHex() throws Exception {

        byte[] test = {63, -99, -88, -118, 77, -2, 75, 105, -7, 26, -111, 33, 1, 47, 116, -70};
        String toHexActual = daoUtils.toHex(test);
        String toHexExpected = "3f9da88a4dfe4b69f91a9121012f74ba";
        Assert.assertEquals(toHexActual, toHexExpected);

    }

    public void testFromHex() throws Exception {

        String test = "3f9da88a4dfe4b69f91a9121012f74ba";
        byte[] fromHexActual = daoUtils.fromHex(test);
        byte[] fromHexExpected = {63, -99, -88, -118, 77, -2, 75, 105, -7, 26, -111, 33, 1, 47, 116, -70};
        Assert.assertArrayEquals(fromHexActual, fromHexExpected);

    }

    public void testCheckPasswordMatching() throws Exception {
        String enteredPassword = "password";
        String dataBasePassword = "65536:3c7e224c719b40e905cb34a4c5516ad5:e0092472df9c310f542606a849d938a4";

        boolean actual = daoUtils.checkPassword(enteredPassword, dataBasePassword);

        Assert.assertTrue(actual);

    }

    public void testCheckPasswordNotMatching() throws Exception {
        String enteredPassword = "notpassword";
        String dataBasePassword = "65536:3c7e224c719b40e905cb34a4c5516ad5:e0092472df9c310f542606a849d938a4";

        boolean actual = daoUtils.checkPassword(enteredPassword, dataBasePassword);

        Assert.assertFalse(actual);

    }

    public void testHashPassword() throws Exception {

        String enteredPassword = "password";
        String hashedPassword = daoUtils.hashPassword(enteredPassword);

        boolean actual = daoUtils.checkPassword(enteredPassword, hashedPassword);

        Assert.assertTrue(actual);

    }

    public void testUniqueEmail() throws Exception {
        boolean actual = daoUtils.uniqueEmail("uniqueemail@gmail.com", "patient");
        Assert.assertTrue(actual);
    }
    public void testNotUniqueEmail() throws Exception {
        boolean actual = daoUtils.uniqueEmail("test1@gmail.com", "patient");
        Assert.assertFalse(actual);
    }


}
