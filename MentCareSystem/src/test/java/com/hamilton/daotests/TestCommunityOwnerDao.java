package com.hamilton.daotests;

import com.hamilton.bean.CommunityOwner;
import com.hamilton.bean.User;
import com.hamilton.dao.CommunityOwnerDao;
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


public class TestCommunityOwnerDao extends TestCase {
    private DaoUtils daoUtils = new DaoUtils();
    IDatabaseTester databaseTester;
    private CommunityOwnerDao coDao;


    public TestCommunityOwnerDao(String name) {
        super(name);
    }

    public void setUp() throws Exception {
        databaseTester = new JdbcDatabaseTester(TestHelper.javaDatabaseDriver, TestHelper.javaDatabaseURL,  TestHelper.javaDatabaseUsername,  TestHelper.javaDatabasePassword);
        IDataSet dataSet = new FlatXmlDataSetBuilder().build(new FileInputStream("src/test/resources/COTestData/coData.xml"));
        databaseTester.setDataSet(dataSet);
        coDao = new CommunityOwnerDao(TestHelper.getConnection(), daoUtils);
        databaseTester.setSetUpOperation(DatabaseOperation.CLEAN_INSERT);
        databaseTester.onSetup();
    }


    public void tearDown() throws Exception {
        databaseTester.setTearDownOperation(DatabaseOperation.DELETE_ALL);
        databaseTester.onTearDown();
    }



    public void testGetCommunityOwner() throws Exception {
        CommunityOwner actualCO = coDao.getCommunityOwner("test1@gmail.com","password");
        CommunityOwner expectedCO = new CommunityOwner(1,"test", "test1@gmail.com","65536:3c7e224c719b40e905cb34a4c5516ad5:e0092472df9c310f542606a849d938a4",Date.valueOf("2000-02-02"),"test","100-10-1000",1,"test" );
        Assert.assertTrue(actualCO.equals(expectedCO));
    }

    public void testGetCommunityOwnerNull() throws Exception {
        CommunityOwner actualCommunityOwner = coDao.getCommunityOwner("nonexistent@gmail.com","password");
        Assert.assertNull(actualCommunityOwner);
    }


    public void testInsertCommunityOwner() throws Exception{
        IDataSet expds = new FlatXmlDataSetBuilder().build(new FileInputStream("src/test/resources/COTestData/co-insert.xml"));
        ITable expectedTable = expds.getTable("community_owner");
        IDatabaseConnection connection = databaseTester.getConnection();
        User user = new User("test", "test4@gmail.com","65536:3c7e224c719b40e905cb34a4c5516ad5:e0092472df9c310f542606a849d938a4",Date.valueOf("2000-02-02"),"test","100-10-1000");
        CommunityOwner co = new CommunityOwner(user,2,4,"test");
        coDao.insertCommunityOwner(co);
        IDataSet databaseDataSet = connection.createDataSet();
        ITable actualTable = databaseDataSet.getTable("community_owner");
        Assertion.assertEquals(expectedTable, actualTable);
    }




}
