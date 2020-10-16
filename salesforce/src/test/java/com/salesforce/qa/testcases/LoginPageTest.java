package com.salesforce.qa.testcases;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.salesforce.qa.base.TestBase;
import com.salesforce.qa.pages.LoginPage;
import com.salesforce.qa.util.TestUtil;

public class LoginPageTest extends TestBase{

	LoginPage loginpage;
	TestUtil util;
	
	public LoginPageTest() {
		super();
	}
	
	@BeforeMethod
	public void setUp()
	{
		test = extent.createTest("Login Functionality Testing");
		initialization();
		loginpage = new LoginPage();
		util = new TestUtil();
	}
	
	@Test(priority=1)
	public void loginTest() throws Exception
	{
		loginpage.login(prop.getProperty("username"), prop.getProperty("password"));
		Thread.sleep(20000);
		util.refreshBrowser();
		Thread.sleep(4000);
		loginpage.logOffAccount();
	}
	
}
