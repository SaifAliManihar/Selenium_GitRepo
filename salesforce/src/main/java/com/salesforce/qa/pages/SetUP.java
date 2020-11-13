package com.salesforce.qa.pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.salesforce.qa.base.TestBase;
import com.salesforce.qa.util.TestUtil;
import com.salesforce.qa.util.WriteToExcel;

public class SetUP extends TestBase{
	
	TestUtil util = new TestUtil();
	WriteToExcel excel = new WriteToExcel();
	
	By setUpIcon = By.xpath("//div[contains(@class,'Tooltip')]//lightning-icon[contains(@class,'setup')]");
	
	By setUp_lnk = By.xpath("//div[contains(@class,'popupTargetContainer') and contains(@class,'visible positioned')]//li[@id='related_setup_app_home']");
	
	By setUp_label = By.xpath("//div[contains(@class,'SetupHeader')]//span[contains(.,'Setup')]");

	By setUp_homeLabel = By.xpath("//div[contains(@class,'SetupHeader')]//span[contains(.,'Home')]");
	
	By objectManager_lnk = By.xpath("//a[contains(.,'Home')]//..//following-sibling::li//a[contains(@href,'ObjectManager')]");
	
	By objectManager_label = By.xpath("//h1//span[contains(.,'Object Manager')]");
	
	By tableObject_header = By.xpath("//table[contains(@class,'VirtualData')]//thead//th");	// All the object Manager table header xpath
	
	By tableObject_data = By.xpath("//table[contains(@class,'VirtualData')]//tbody//tr");
	
	By imageLink_table = By.xpath("//tr//a[contains(@href,'Image')]");
	
	By availableListData = By.xpath("//div[contains(@class,'navigation-list')]//li");
	
	By tableLastElement = By.xpath("(//table[contains(@class,'uiVirtualData')]//tbody//tr)[last()]");
	
	By objectTableDataCount = By.xpath("//span[contains(.,' Items')]");
	
	
	public void clickSetUpIcon()
	{
		if(util.isElementPresent(setUpIcon, 5))
		{
			System.out.println("SetUp Icon present");
			test.log(Status.PASS, MarkupHelper.createLabel("Set Up Icon is present", ExtentColor.GREEN));
			util.clickElement(setUpIcon, "SetUP Icon");
		}else{
			System.out.println("SetUp Icon isn't present");
			test.log(Status.FAIL, MarkupHelper.createLabel("Set Up Icon is not present", ExtentColor.RED));
		}
	}
	
	public void clickSetUpLink()
	{
		System.out.println("SetUp Link present");
		test.log(Status.PASS, MarkupHelper.createLabel("Set Up Link is present", ExtentColor.GREEN));
		driver.findElement(setUp_lnk).click();
	}
	
	public void verifySetUpHomePage()
	{
		if(util.isElementPresent(setUp_label, 5))
		{
			System.out.println("SetUp Label is present");
			test.log(Status.PASS, MarkupHelper.createLabel("Set Up Label is present", ExtentColor.GREEN));
		}else{
			System.out.println("SetUp Label is not present");
			test.log(Status.FAIL, MarkupHelper.createLabel("Set Up Label isn't present", ExtentColor.RED));
		}
		
		if(util.isElementPresent(setUp_homeLabel, 5))
		{
			System.out.println("SetUp Home Label is present");
			test.log(Status.PASS, MarkupHelper.createLabel("Set Up Home Label is present", ExtentColor.GREEN));
		}else{
			System.out.println("SetUp Home Label is not present");
			test.log(Status.FAIL, MarkupHelper.createLabel("Set Up Home Label isn't present", ExtentColor.RED));
		}
		
	}
	
	public void clickObjectManagerLink()
	{
		if(util.isElementPresent(objectManager_lnk, 5))
		{
			System.out.println("Object Manager Link is present");
			test.log(Status.PASS, MarkupHelper.createLabel("Object Mnager Link is present", ExtentColor.GREEN));
			util.clickElement(objectManager_lnk, "Object Manager Link");
		}else{
			System.out.println("SetUp Label is not present");
			test.log(Status.FAIL, MarkupHelper.createLabel("Set Up Label isn't present", ExtentColor.RED));
		}
	}
	
	public void verifyObjectRepository_Home()
	{
		if(util.isElementPresent(objectManager_label, 5))
		{
			System.out.println("Object Manager Home Page Label is present");
			test.log(Status.PASS, MarkupHelper.createLabel("Object Manager Home Page Label is present", ExtentColor.GREEN));
		}else{
			System.out.println("Object Manager Home Page Label is not present");
			test.log(Status.FAIL, MarkupHelper.createLabel("Object Manager Home Page Label is not present", ExtentColor.RED));
		}
	}
	
	
	
	public void verifyTableData_objectRepository() throws Exception
	{
		//util.scrollWebTableToBottom(imageLink_table);
		util.scrollWebTableToBottom(tableLastElement);
		Thread.sleep(3000);
		
		List<String> tableHeaderData = new ArrayList<String>();
		List<String> tableBodyDataList = new ArrayList<String>();
		
		List<WebElement> tableHeader = util.getListOfWebElement(tableObject_header);

		tableHeaderData.add("LABEL");
		tableHeaderData.add("API NAME");
		tableHeaderData.add("TYPE");
		tableHeaderData.add("DESCRIPTION");
		tableHeaderData.add("LAST MODIFIED");
		tableHeaderData.add("DEPLOYED");
		tableHeaderData.add("ACTIONS");	
		
		List<WebElement> table_body = util.getListOfWebElement(tableObject_data);
		
		String separator = "$";
		
		//for(int i=1;i<=table_body.size();i++)
		for(int i=1;i<=table_body.size();i++)
		{
			By tableBody_header =  By.xpath("(//table[contains(@class,'VirtualData')]//tbody//tr)["+i+"]//th");
			String tableBody_header_str = util.getText(tableBody_header);
			tableBodyDataList.add(tableBody_header_str);
			
			By tableBody_Data =  By.xpath("(//table[contains(@class,'VirtualData')]//tbody//tr)["+i+"]//td");
			List<WebElement> tableBodyData = util.getListOfWebElement(tableBody_Data);
			for(WebElement tdData: tableBodyData)
			{
				String tdData_str = tdData.getText();
				tableBodyDataList.add(tdData_str);
			}
			tableBodyDataList.add(separator);
		}
		
		String fileNameToCreate = prop.getProperty("objectManagerFileName");
		String sheetName = "Object_Manager_Details";
		
		String fullFilePath = excel.writeToExcel(tableHeaderData,tableBodyDataList,fileNameToCreate,sheetName);
		test.log(Status.PASS, MarkupHelper.createLabel("Object Manager Data is successfully written in the excel file with location:"+fullFilePath, ExtentColor.GREEN));
	}
	
	//To Verify and capture each label under Object manager
	public void verifyTableData_objectRepository_eachObject() throws Exception
	{
		List<String> tableHeader_strList = new ArrayList<String>();
		List<String> tableBodyDataList = new ArrayList<String>();
		
		util.scrollWebTableToBottom(tableLastElement);
		
		List<WebElement> tableBody_objectManager = util.getListOfWebElement(tableObject_data);
		Thread.sleep(5000);
		
		for(int i=1;i<=tableBody_objectManager.size();i++)
		{
			By tableBody_objectManager_header =  By.xpath("(//table[contains(@class,'VirtualData')]//tbody//tr)["+i+"]//th//a");
			String tableBody_objectManager_header_str = util.getText(tableBody_objectManager_header);
			System.out.println("Link name under Object Manager-->"+tableBody_objectManager_header_str);
			
			util.clickElement(tableBody_objectManager_header, tableBody_objectManager_header_str);
			Thread.sleep(10000);
			
			List<WebElement> listOfLinksAvailable = util.getListOfWebElement(availableListData);
			
			for(int j=2;j<=listOfLinksAvailable.size();j++)
			{
				listOfLinksAvailable = util.getListOfWebElement(availableListData);
				String linkName = listOfLinksAvailable.get(j-1).getText();
				System.out.println("Link Name under "+tableBody_objectManager_header_str+ "::"+linkName);
				util.clickElement(By.xpath("(//div[contains(@class,'navigation-list')]//li)["+j+"]"), linkName);
				Thread.sleep(10000);
				
				if(!linkName.equals("Hierarchy Columns"))
				{
					List<WebElement> tableHeader_data = util.getListOfWebElement(By.xpath("//table[contains(@class,'uiVirtualData')]//thead//th"));
					Thread.sleep(10000);
					
					for(int k=1;k<=tableHeader_data.size();k++)
					{
							String headerData = util.getText(By.xpath("(//table[contains(@class,'uiVirtualData')]//thead//th)["+k+"]"));
							tableHeader_strList.add(headerData);
					}
				
				
					//Checking empty table data
					String tabledataCount = util.getText(objectTableDataCount);
					System.out.println("Table Data count::"+tabledataCount);
					if(tabledataCount.contains("0")){
						for(int m=1;m<=tableHeader_data.size();m++)
						{
							tableBodyDataList.add("-");
						}
						tableBodyDataList.add("$");
					}
					else{
						List<WebElement> tableBody_data = util.getListOfWebElement(By.xpath("//table[contains(@class,'uiVirtualData')]//tbody//tr"));
						String separator = "$";
						
						if(tableBody_data.size()!=0){
							util.scrollWebTableToBottom(tableLastElement);					
							for (int tb = 1; tb <= tableBody_data.size(); tb++) {
								By tableBody_Data = By.xpath("(//table[contains(@class,'VirtualData')]//tbody//tr)[" + tb + "]//td");
								List<WebElement> tableBodyData = util.getListOfWebElement(tableBody_Data);
								for (WebElement tdData : tableBodyData) {
									String tdData_str = tdData.getText();
									tableBodyDataList.add(tdData_str);
								}
								tableBodyDataList.add(separator);
							}
							
						}
					}
					
					//String fileNameToCreate = prop.getProperty("objectManagerFileName")+"_"+tableBody_objectManager_header_str+"_"+linkName;
					String fileNameToCreate = prop.getProperty("objectManagerFileName")+"_"+tableBody_objectManager_header_str;
					String sheetName = linkName;
					
					String fullFilePath = excel.writeToExcel(tableHeader_strList,tableBodyDataList,fileNameToCreate,sheetName);
					test.log(Status.PASS, MarkupHelper.createLabel("Object Manager Data is successfully written in the excel file with location:"+fullFilePath, ExtentColor.GREEN));
					
					tableHeader_strList.clear();
					tableBodyDataList.clear();
					
				}
				else{
					continue;
				}
				
				
			}
			clickObjectManagerLink();
			Thread.sleep(7000);
		}
	}
	
		
	
	
}
