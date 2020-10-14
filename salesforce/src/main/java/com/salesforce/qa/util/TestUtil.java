package com.salesforce.qa.util;


import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.salesforce.qa.base.TestBase;

public class TestUtil extends TestBase{

	public final int IMPLICITWAIT = Integer.parseInt(prop.getProperty("IMPLICITWAIT"));
	public final int LONGWAIT = Integer.parseInt(prop.getProperty("LONGWAIT"));
	public final int SHORTWAIT = Integer.parseInt(prop.getProperty("SHORTWAIT"));
	
	WebDriverWait wait;
	
	public TestUtil()
	{
		PageFactory.initElements(driver, this);
	}
	
	public void waitForPageLoaded(int timeOutInSeconds) 
	{
		//wait for page to be loaded
		boolean isPageLoadComplete = false;
		int waitTime =0;
		do{
			isPageLoadComplete = ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete");
			waitTime++;
			if(waitTime > timeOutInSeconds)
			{
				break;
			}
		}
		while(!isPageLoadComplete);
	}
	
	public void flash(WebDriver driver,By locator)
	{
		try{
			JavascriptExecutor jse = (JavascriptExecutor) driver; 
			WebElement element = driver.findElement(locator);
			//String bgcolor = element.getCssValue("backgroundColor");
			for(int i=0;i<3;i++)
			{
				changeColor("yellow", element, jse);
				changeColor("", element, jse);
			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void changeColor(String color, WebElement element, JavascriptExecutor jse)
	{
		//jse.executeScript("argument[0].style.backgroundColor='"+color+"'", element);
		jse.executeScript("arguments[0].setAttribute('style', 'background: "+color+";');", element);
		try{
			Thread.sleep(20);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void setImplicitWait(int waitTimeInSecond)
	{
		driver.manage().timeouts().implicitlyWait(waitTimeInSecond, TimeUnit.SECONDS);
	}
	
	public void nullifyImplicitWait()
	{
		driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
	}
	
	public boolean isElementPresent(By locator, int waitTime)
	{
		boolean bFlag = false;
		nullifyImplicitWait();
		WebDriverWait wait = new WebDriverWait(driver,waitTime);
		wait.until(ExpectedConditions.presenceOfElementLocated(locator));
		if(driver.findElement(locator).isDisplayed() || driver.findElement(locator).isEnabled())
		{
			flash(driver,locator);
			bFlag = true;
		}
		
		return bFlag;
	}
	
	public boolean isAlertPresent(int waitTime)
	{
		boolean bFlag = false;
		nullifyImplicitWait();
		
		WebDriverWait wait = new WebDriverWait(driver,waitTime);
		wait.until(ExpectedConditions.alertIsPresent());
		
		Alert alert = driver.switchTo().alert();
			bFlag = true;
		setImplicitWait(IMPLICITWAIT);
		return bFlag;
	}
	
	public boolean waitUntilClickable(By locator, int waitTime)
	{
		boolean bFlag = false;
		nullifyImplicitWait();
		
		WebDriverWait wait = new WebDriverWait(driver,waitTime);
		wait.until(ExpectedConditions.elementToBeClickable(locator));
		
		if(driver.findElement(locator).isDisplayed())
		{
			flash(driver,locator);
			bFlag = true;
		}
		
		return bFlag;
		
	}
	
	public boolean waitUntilElementDisappears(By locator, int waitTime)
	{
		boolean isNotVisible = false;
		nullifyImplicitWait();
		
		WebDriverWait wait = new WebDriverWait(driver,waitTime);
		wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
		
		isNotVisible = true;
		setImplicitWait(IMPLICITWAIT);
		
		return isNotVisible;
	}
	
	public boolean isElementSelected(By locator)
	{
		boolean isSelected = false;
		setImplicitWait(IMPLICITWAIT);
		if(isElementPresent(locator,SHORTWAIT))
		{
			flash(driver,locator);
			isSelected = driver.findElement(locator).isSelected();
		}
		return isSelected;
	}

	public boolean clickElement(By locator, String reportText)
	{
		if(isElementPresent(locator,SHORTWAIT))
		{
			flash(driver, locator);
			driver.findElement(locator).click();
			System.out.println(reportText+" clicked");
			test.log(Status.PASS, MarkupHelper.createLabel("Clicked on "+reportText, ExtentColor.GREEN));
			return true;
		}
		else{
			test.log(Status.FAIL, MarkupHelper.createLabel("Not able to click on "+reportText, ExtentColor.RED));
		}
		return false;
	}
	
	public boolean sendText(By locator, String text, String reportText)
	{
		if(isElementPresent(locator,SHORTWAIT))
		{
			flash(driver, locator);
			driver.findElement(locator).clear();
			driver.findElement(locator).sendKeys(text);
			if(reportText.contains("Password"))
			{
				System.out.println("Entering the text in the ::"+reportText+"::********");
				test.log(Status.PASS, MarkupHelper.createLabel("Entering the text in "+reportText +"::********", ExtentColor.GREEN));
			}else{
				System.out.println("Entering the text in the ::"+reportText+"::"+text);
				test.log(Status.PASS, MarkupHelper.createLabel("Entering the text in "+reportText+"::"+text,ExtentColor.GREEN));
				
			}
			return true;
		}
		return false;
	}
	
	public void pageLoadTime()
	{
		driver.manage().timeouts().pageLoadTimeout(LONGWAIT, TimeUnit.SECONDS);
	}
	
	public String getText(By locator)
	{
		String outputText = "";
		if(isElementPresent(locator,SHORTWAIT))
		{
			flash(driver, locator);
			outputText = driver.findElement(locator).getText();
			test.log(Status.PASS, MarkupHelper.createLabel("Able to fetch data ::"+outputText,ExtentColor.GREEN));
		}
		return outputText;
	}
	
	public List<WebElement> getListOfWebElement(By locator)
	{
		List<WebElement> webElementList = null ;
		if(isElementPresent(locator,SHORTWAIT))
		{
			webElementList = driver.findElements(locator);
		}
		return webElementList;
	}
	
	public static void closeBrowser(WebDriver driver)
	{
		driver.quit();
	}
	
	public String getCurrentTime () {
    	DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
	    LocalDateTime now = LocalDateTime.now();   
	    return dtf.format(now);
    }
	
	public void refreshBrowser()
	{
		driver.navigate().refresh();
	}
	
//	public static String getScreenshot(WebDriver driver)
//	{
//		TakesScreenshot ts=(TakesScreenshot) driver;
//		
//		File src=ts.getScreenshotAs(OutputType.FILE);
//		
//		String path=System.getProperty("user.dir")+"/Screenshot/"+System.currentTimeMillis()+".png";
//		
//		File destination=new File(path);
//		
//		try 
//		{
//			FileHandler.copyFile(src, destination);
//		} catch (IOException e) 
//		{
//			System.out.println("Capture Failed "+e.getMessage());
//		}
//		
//		return path;
//	}
	
}
