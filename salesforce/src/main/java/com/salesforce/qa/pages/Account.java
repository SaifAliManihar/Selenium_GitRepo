/**
 * 
 */
package com.salesforce.qa.pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.salesforce.qa.base.TestBase;
import com.salesforce.qa.util.TestUtil;

public class Account extends TestBase{
	
	TestUtil util = new TestUtil();
	WebDriver driver;
	
	By accountLink =By.xpath("//div[@class='slds-grid slds-has-flexi-truncate navUL']//one-app-nav-bar-item-root[contains(.,'Accounts')]");
	
	By newButton = By.xpath("//a[contains(@title,'New')]");
	
	By newAccountPopupLabel = By.xpath("//h2[contains(.,'New Account')]");
	
	//By accountNameTextBox = By.xpath("//span[contains(.,'*')]//preceding-sibling::span[contains(.,'Account Name')]//..//following-sibling::div//input");
	//for sandbox
	By accountNameTextBox = By.xpath("//span[contains(.,'*')]//preceding-sibling::span[contains(.,'Account Name')]//..//following-sibling::input");
	
	By saveButton = By.xpath("//span[text()='Save & New']//..//following-sibling::button");
	
	By errorCompleteFieldLabel = By.xpath("//span[contains(.,'*')]//..//..//following-sibling::ul[contains(@class,'error')]//li");
	
	By errorMsgText = By.xpath("//span[contains(@class,'genericError')]");
	
	By accountNameLabel = By.xpath("//div[contains(@class,'outputNameWithHierarchyIcon')]//span[contains(@class,'uiOutputText')]");
	
	By editButton = By.xpath("//button[@name='Edit']");
	
	By editButtonPopUpLabel = By.xpath("//h2[contains(.,'Edit')]");
		
	By editAccountNameTextBox = By.xpath("//label[contains(.,'Account Name')]//following-sibling::input");
	
	By newAccountPopUpCloseButton = By.xpath("//button[@title='Close this window']");
	
	By accountNameList = By.xpath("//table[contains(@class,'uiVirtualDataTable')]//th[@scope='row']//a");
	
	By editedAcountNameText = By.xpath("//div[contains(@class,'label-container') and contains(.,'Account Name')]//following-sibling::div//lightning-formatted-text");
		
	By detailsButton = By.xpath("(//ul[@role='tablist'])[1]//li[contains(.,'Details')]");
	
	public String accountNameToCreate = "Testing account creation";
	
	public String accountNameAfterEdit="";
	
	public Account()
	{
		PageFactory.initElements(driver, this);
	}
	
	public void verifyAccountTab()
	{
		if(util.isElementPresent(accountLink, 10))
		{
			test.log(Status.PASS, MarkupHelper.createLabel("Account Name link is Visible", ExtentColor.GREEN));
			util.clickElement(accountLink, "Account Link");
		}else{
			System.out.println("Account Tab is not available");
			test.log(Status.FAIL, MarkupHelper.createLabel("Account Name link is not Visible", ExtentColor.RED));
		}
	}
	
	public void verifyNewButton()
	{
		if(util.isElementPresent(newButton, 10))
		{
			test.log(Status.PASS, MarkupHelper.createLabel("New Button is Visible", ExtentColor.GREEN));
			util.clickElement(newButton, "New Button");
		}else{
			System.out.println("New Button is not available");
			test.log(Status.FAIL, MarkupHelper.createLabel("New Button is not  Visible", ExtentColor.RED));
		}
	}
	
	public void verifyNegativeScenario_blankAccountName() throws Exception
	{
		if(util.isElementPresent(newAccountPopupLabel, 10))
		{
			System.out.println("New Account popup displayed");
			test.log(Status.PASS, MarkupHelper.createLabel("New Account Pop Up is Visible", ExtentColor.GREEN));
			
			Thread.sleep(2000);
			
			util.clickElement(saveButton, "Save Button");
			
			Thread.sleep(3000);
			
			if(util.isElementPresent(errorCompleteFieldLabel, 10))
			{
				System.out.println("Complete this field errror message displayed");
				test.log(Status.PASS, MarkupHelper.createLabel("Complete this field error is Visible", ExtentColor.GREEN));
			}else{
				System.out.println("Complete this field errror message is not displayed");
				test.log(Status.FAIL, MarkupHelper.createLabel("Complete this field error is not Visible", ExtentColor.RED));
			}
			
			if(util.isElementPresent(errorMsgText, 10))
			{
				System.out.println("Review the errors on this page errror message displayed");
				test.log(Status.PASS, MarkupHelper.createLabel("Review the error is Visible", ExtentColor.GREEN));
			}else{
				System.out.println("Review the errors on this page. errror message is not displayed");
				test.log(Status.FAIL, MarkupHelper.createLabel("Review the error is not Visible", ExtentColor.RED));
			}
			
			util.clickElement(newAccountPopUpCloseButton, "New Account popup close button");
			
		}else{
			System.out.println("New Account popup is not displayed");
			test.log(Status.FAIL, MarkupHelper.createLabel("New Account Pop Up is not Visible", ExtentColor.RED));
		}
	}
	
	public void accountCreation() throws InterruptedException
	{
		util.sendText(accountNameTextBox,accountNameToCreate,"Account Name Text Box");	
		
		Thread.sleep(2000);
		
		util.clickElement(saveButton, "Save Button");	
	}
	
	public void verifyCreatedAccount() throws InterruptedException
	{
		ArrayList<String> accountNameList_UI = new ArrayList<String>();
		List<WebElement> list_accountName = util.getListOfWebElement(accountNameList);
		
		for(WebElement w: list_accountName)
		{
			accountNameList_UI.add(w.getText());
		}
		System.out.println("Account List Available::"+accountNameList_UI);
		
		if(accountNameList_UI.contains(accountNameToCreate))
		{
			System.out.println("Account name list contains "+accountNameToCreate);
			test.log(Status.PASS, MarkupHelper.createLabel("Account Name list contains newly created account with name::"+accountNameToCreate, ExtentColor.GREEN));
		}else{
			System.out.println("Account name list does not contains "+accountNameToCreate);
			test.log(Status.FAIL, MarkupHelper.createLabel("Account Name list does not contain newly created account with name::"+accountNameToCreate, ExtentColor.RED));
		}
		
		for(WebElement w: list_accountName)
		{
			if(w.getText().equalsIgnoreCase(accountNameToCreate)){
				w.click();
				test.log(Status.PASS, MarkupHelper.createLabel("Account Name::"+accountNameToCreate+" clicked", ExtentColor.GREEN));
				break;
			}
		}
		Thread.sleep(5000);
		
		String accountNameCreated = util.getText(accountNameLabel);
		
		if(accountNameCreated.equalsIgnoreCase(accountNameToCreate))
		{
			System.out.println("Account name in UI ::"+ accountNameCreated + " is matching with account name created ::"+accountNameToCreate);
			test.log(Status.PASS, MarkupHelper.createLabel("Account name in UI ::"+ accountNameCreated + " is matching with account name created ::"+accountNameToCreate, ExtentColor.GREEN));
		}else{
			System.out.println("Account name in UI ::"+ accountNameCreated + " is not  matching with account name created ::"+accountNameToCreate);
			test.log(Status.FAIL, MarkupHelper.createLabel("Account name in UI ::"+ accountNameCreated + " is not matching with account name created ::"+accountNameToCreate, ExtentColor.RED));
		}
	}
	
	public void verifyEditButton() throws InterruptedException
	{
		if(util.isElementPresent(editButton, 10))
		{
			test.log(Status.PASS, MarkupHelper.createLabel("Edit Button is visible", ExtentColor.GREEN));
			util.clickElement(editButton, "Edit Button");
		}else{
			System.out.println("Edit Button is not available");
			test.log(Status.FAIL, MarkupHelper.createLabel("Edit Button is not visible", ExtentColor.RED));
		}
	}
	
	public void verifyEditFunctionality() throws InterruptedException
	{
		if(util.isElementPresent(editButtonPopUpLabel, 10))
		{
			System.out.println("Edit pop up is displayed");
			test.log(Status.PASS, MarkupHelper.createLabel("Edit Button Pop up is visible", ExtentColor.GREEN));
		}else{
			System.out.println("Edit pop up is not displayed");
			test.log(Status.FAIL, MarkupHelper.createLabel("Edit Button Pop up is not visible", ExtentColor.RED));
		}
		
//		String editPopup_accountName = util.getText(editAccountNameTextBox);
//		if(editPopup_accountName.equalsIgnoreCase(accountNameToCreate))
//		{
//			System.out.println("Account name in edit pop up ::"+editPopup_accountName + " is matching with account name initially created ::"+accountNameToCreate);
//			test.log(Status.PASS, MarkupHelper.createLabel("Account name in edit pop up ::"+editPopup_accountName + " is matching with account name initially created ::"+accountNameToCreate, ExtentColor.GREEN));
//		}else{
//			System.out.println("Account name in edit pop up ::"+editPopup_accountName + " is not matching with account name initially created ::"+accountNameToCreate);
//			test.log(Status.FAIL, MarkupHelper.createLabel("Account name in edit pop up ::"+editPopup_accountName + " is not matching with account name initially created ::"+accountNameToCreate, ExtentColor.RED));
//		}
		
		String accNameToEdit_new = accountNameToCreate + " after edit";
		accountNameAfterEdit= accNameToEdit_new;
		
		util.sendText(editAccountNameTextBox, accNameToEdit_new,"Account Name Text Box::");
		
		util.clickElement(saveButton, "Save Button");
		
		Thread.sleep(3000);
		
		String accountNameCreated_new = util.getText(accountNameLabel);
		if(accountNameCreated_new.equalsIgnoreCase(accNameToEdit_new))
		{
			System.out.println("Account name in UI ::"+ accountNameCreated_new + " is matching with account name created ::"+accNameToEdit_new);
			test.log(Status.PASS, MarkupHelper.createLabel("Account name in UI ::"+ accountNameCreated_new + " is matching with account name created ::"+accNameToEdit_new, ExtentColor.GREEN));
		}else{
			System.out.println("Account name in UI ::"+ accountNameCreated_new + " is not  matching with account name created ::"+accNameToEdit_new);
			test.log(Status.FAIL, MarkupHelper.createLabel("Account name in UI ::"+ accountNameCreated_new + " is not matching with account name created ::"+accNameToEdit_new, ExtentColor.RED));
		}
		
		//New for sandbox
		if(util.isElementPresent(detailsButton, 10))
		{	
			test.log(Status.PASS, MarkupHelper.createLabel("Details Tab is visible", ExtentColor.GREEN));
			util.clickElement(detailsButton, "Details Tab");
			Thread.sleep(5000);
		}
		//ends
		String accountNameCreated_afterEditInUI =util.getText(editedAcountNameText);
		if(accountNameCreated_afterEditInUI.equalsIgnoreCase(accNameToEdit_new))
		{
			System.out.println("Account name in UI ::"+ accountNameCreated_afterEditInUI + " is matching with account name created ::"+accNameToEdit_new);
			test.log(Status.PASS, MarkupHelper.createLabel("Account name in UI ::"+ accountNameCreated_afterEditInUI + " is matching with account name created ::"+accNameToEdit_new, ExtentColor.GREEN));
		}else{
			System.out.println("Account name in UI ::"+ accountNameCreated_afterEditInUI + " is not  matching with account name created ::"+accNameToEdit_new);
			test.log(Status.FAIL, MarkupHelper.createLabel("Account name in UI ::"+ accountNameCreated_afterEditInUI + " is not matching with account name created ::"+accNameToEdit_new, ExtentColor.RED));
		}
		
		verifyAccountTab();
		
		Thread.sleep(6000);
		
		ArrayList<String> accountNameList_UI = new ArrayList<String>();
		List<WebElement> list_accountName = util.getListOfWebElement(accountNameList);
		for(WebElement w: list_accountName)
		{
			accountNameList_UI.add(w.getText());
		}
		System.out.println("Account List Available::"+accountNameList_UI);
		
		if(accountNameList_UI.contains(accNameToEdit_new))
		{
			System.out.println("Account name list contains "+accNameToEdit_new);
			test.log(Status.PASS, MarkupHelper.createLabel("Account name list contains "+accNameToEdit_new, ExtentColor.GREEN));
		}else{
			System.out.println("Account name list does not contains "+accNameToEdit_new);
			test.log(Status.FAIL, MarkupHelper.createLabel("Account name list does not contain "+accNameToEdit_new, ExtentColor.RED));
		}
		
	}
	
	
}
