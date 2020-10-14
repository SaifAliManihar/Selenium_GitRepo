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
		initialization();
		loginpage = new LoginPage();
		util = new TestUtil();
	}
	
	@Test(priority=1)
	public void loginTest() throws Exception
	{
		test = extent.createTest("Login Functionality Testing");
		loginpage.login(prop.getProperty("username"), prop.getProperty("password"));
		Thread.sleep(20000);
		driver.navigate().refresh();
		Thread.sleep(2000);
		loginpage.logOffAccount();
	}
	
}
