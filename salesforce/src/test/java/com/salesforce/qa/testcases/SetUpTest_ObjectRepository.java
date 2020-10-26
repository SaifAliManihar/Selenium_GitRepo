package com.salesforce.qa.testcases;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.salesforce.qa.base.TestBase;
import com.salesforce.qa.pages.Account;
import com.salesforce.qa.pages.LoginPage;
import com.salesforce.qa.pages.SetUP;
import com.salesforce.qa.util.TestUtil;

public class SetUpTest_ObjectRepository extends TestBase{
	
	LoginPage loginpage;
	Account account;
	TestUtil util;
	SetUP setup;
	
	public SetUpTest_ObjectRepository() {
		super();
	}
	
	@BeforeMethod
	@Parameters("browser")
	public void setUp(String browser) throws InterruptedException
	{
		test = extent.createTest("SetUp Test Object Repository Scenario");
		initialization(browser);
		loginpage = new LoginPage();
		util = new TestUtil();
		loginpage.login(prop.getProperty("username"), prop.getProperty("password"));
		Thread.sleep(20000);
		account = new Account();
		setup = new SetUP();
	}
	
	@Test(priority=1)
	public void verifySetUpScenaio_objectRepository() throws Exception
	{
		setup.clickSetUpIcon();
		Thread.sleep(4000);
		
		setup.clickSetUpLink();
		Thread.sleep(5000);
		
		util.switchToChildWindow();
		
		setup.verifySetUpHomePage();
		
		setup.clickObjectManagerLink();		
		Thread.sleep(12000);
		
		setup.verifyObjectRepository_Home();
		Thread.sleep(10000);
		
		setup.verifyTableData_objectRepository();
		Thread.sleep(2000);
		
	}

}
