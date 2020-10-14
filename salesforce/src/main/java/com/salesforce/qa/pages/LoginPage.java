package com.salesforce.qa.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.salesforce.qa.base.TestBase;
import com.salesforce.qa.util.TestUtil;

/**
 * @author saif2
 *
 */
public class LoginPage extends TestBase {
	
	TestUtil util = new TestUtil();

	By userName = By.xpath("//input[@id='username']");
	
	By password = By.xpath("//input[@id='password']");
	
	By loginButton = By.xpath("//input[@id='Login']");
	
	By accountLogOff_Logo = By.xpath("//button[contains(@class,'userProfile')]");
	
	By accountLogOff_LogOutLink = By.xpath("//div[contains(@class,'userProfile')]//a[contains(@class,'logout')]");
	
	public LoginPage()
	{
		PageFactory.initElements(driver, this);
	}
	
	public void login(String uname,String pwd)
	{
		util.sendText(userName, uname,"User Name");
		util.sendText(password, pwd, "Password");
		util.clickElement(loginButton, "Login Button");
	}
	
	public void logOffAccount() throws Exception
	{
		util.clickElement(accountLogOff_Logo, "Log off Logo");
		Thread.sleep(3000);
		
		util.clickElement(accountLogOff_LogOutLink, "Log Out Link");
		util.waitForPageLoaded(30);
	}
}
