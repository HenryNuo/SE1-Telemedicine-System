package com.hamilton.daotests;

import com.hamilton.bean.Community;
import com.hamilton.dao.CommunityDao;
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

public class TestCommunityDao extends TestCase{
    private DaoUtils daoUtils = new DaoUtils();
    IDatabaseTester databaseTester;
    private CommunityDao coDao;

    public void setUp() throws Exception {
        databaseTester = new JdbcDatabaseTester(TestHelper.javaDatabaseDriver, TestHelper.javaDatabaseURL,  TestHelper.javaDatabaseUsername,  TestHelper.javaDatabasePassword);
        IDataSet dataSet = new FlatXmlDataSetBuilder().build(new FileInputStream("src/test/resources/CommunityTestData/community-data.xml"));
        databaseTester.setDataSet(dataSet);
        coDao = new CommunityDao(TestHelper.getConnection(), daoUtils);
        databaseTester.setSetUpOperation(DatabaseOperation.CLEAN_INSERT);
        databaseTester.onSetup();
    }

    public void tearDown() throws Exception {
        databaseTester.setTearDownOperation(DatabaseOperation.DELETE_ALL);
        databaseTester.onTearDown();
    }

    //Community we are checking is in database
    public void testCommunityExistsPos() throws Exception {
        boolean communityExists = coDao.communityExists(1);
        Assert.assertTrue(communityExists);
    }

    //Community we are checking is not in the database
    public void testCommunityExistsNeg() throws Exception {
        boolean communityExists = coDao.communityExists(1000);
        Assert.assertFalse(communityExists);
    }


    public void testCreateCommunity() throws Exception {
        IDataSet expds = new FlatXmlDataSetBuilder().build(new FileInputStream("src/test/resources/CommunityTestData/community-data-insert.xml"));
        ITable expectedTable = expds.getTable("community");
        IDatabaseConnection connection = databaseTester.getConnection();
        Community community = new Community("test",1,"test","test");
        coDao.insertCommunity(community);
        IDataSet databaseDataSet = connection.createDataSet();
        ITable actualTable = databaseDataSet.getTable("community");
        Assertion.assertEquals(expectedTable, actualTable);
    }

}
