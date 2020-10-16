package com.salesforce.qa.testcases;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.salesforce.qa.base.TestBase;
import com.salesforce.qa.pages.Account;
import com.salesforce.qa.pages.LoginPage;
import com.salesforce.qa.util.TestUtil;

public class AccountPageTest extends TestBase{
	
	LoginPage loginpage;
	Account account;
	TestUtil util;
	
	public AccountPageTest() {
		super();
	}
	
	@BeforeMethod
	public void setUp() throws Exception
	{
		test = extent.createTest("Create Account And Edit Acount");
		initialization();
		loginpage = new LoginPage();
		util = new TestUtil();
		loginpage.login(prop.getProperty("username"), prop.getProperty("password"));
		Thread.sleep(20000);
		account = new Account();
	}
	
	@Test(priority=1)
	public void verifyAccountCreationAndEdit() throws Exception
	{
		account.verifyAccountTab();
		Thread.sleep(10000);
		
		account.verifyNewButton();
		Thread.sleep(6000);
		
		account.verifyNegativeScenario_blankAccountName();
		Thread.sleep(5000);
		
		account.verifyNewButton();
		Thread.sleep(5000);
		
		account.accountCreation();
		Thread.sleep(7000);
		
		account.verifyAccountTab();
		Thread.sleep(7000);
		
		account.verifyCreatedAccount();
		Thread.sleep(5000);
		
		account.verifyEditButton();
		Thread.sleep(5000);
		
		account.verifyEditFunctionality();		
		Thread.sleep(7000);
		
		util.refreshBrowser();
		Thread.sleep(2000);
		loginpage.logOffAccount();
	}
	
}
