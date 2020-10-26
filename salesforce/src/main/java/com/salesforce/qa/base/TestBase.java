package com.salesforce.qa.base;

import java.io.FileInputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.salesforce.qa.util.TestUtil;

public class TestBase {
	
	public static WebDriver driver;
	public static Properties prop;
	public static EventFiringWebDriver e_driver;
	public static ExtentHtmlReporter htmlReporter;
	public static ExtentReports extent;
	public static ExtentTest test;
	private static ITestContext testContext;
	private ITestResult result;
	
	public static String filePath = System.getProperty("user.dir");
	public TestBase()
	{
		try{
			prop = new Properties();			
			FileInputStream ip = new FileInputStream(filePath+"\\src\\main\\java\\com\\salesforce\\qa\\config\\config.properties");
			prop.load(ip);
			
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public static void initialization(String browser){
		String browserName = prop.getProperty("browser");
		
		if(browser.equals("chrome"))
		{
			System.setProperty("webdriver.chrome.driver", filePath+"\\drivers\\chromedriver.exe");
			driver = new ChromeDriver();			
		}
		else if(browser.equals("firefox"))
		{
			System.setProperty("webdriver.gecko.driver", filePath+"\\drivers\\geckodriver.exe");
			driver = new FirefoxDriver();
		}
		else if(browser.equals("edge"))
		{
			System.setProperty("webdriver.edge.driver", filePath+"\\drivers\\msedgedriver.exe");
			driver = new EdgeDriver();
		}
			
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.get(prop.getProperty("url"));
		
	}
	
	public String getCurrentTime () {
    	DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd_MM_yyyy_HH_mm_ss");  
	    LocalDateTime now = LocalDateTime.now();   
	    return dtf.format(now);
    }
	
	
	@BeforeSuite
	public void beforeSuiteSetUp() {
		//testContext.getName()+
//		String reportFileName = "Test_Automaton_Report_"+getCurrentTime();//testContext.getName()+
//		htmlReporter = new ExtentHtmlReporter(filePath + "\\test-output\\"+reportFileName+".html");
//		extent = new ExtentReports();
//		extent.attachReporter(htmlReporter);
//		
//		htmlReporter.config().setChartVisibilityOnOpen(true);
//		htmlReporter.config().setDocumentTitle("Automation Test Report for Salesforce App");
//		htmlReporter.config().setReportName("Test Report for Salesforce App");//testContext.getName()+
//		htmlReporter.config().setTestViewChartLocation(ChartLocation.TOP);
//		htmlReporter.config().setTheme(Theme.STANDARD);
		
		htmlReporter = new ExtentHtmlReporter(System.getProperty("user.dir")+"\\test-output\\Test_Automaton_Report_"+getCurrentTime()+".html");
		//htmlReporter = new ExtentHtmlReporter(prop.getProperty("outputpath")+"\\Test_Automaton_Report_"+getCurrentTime()+".html");
		htmlReporter.config().setChartVisibilityOnOpen(true);
		htmlReporter.config().setDocumentTitle("Automation Test Report for Salesforce App");
		htmlReporter.config().setReportName("Test Report for Salesforce App");//testContext.getName()+
		htmlReporter.config().setTestViewChartLocation(ChartLocation.TOP);
		htmlReporter.config().setTheme(Theme.STANDARD);

		extent = new ExtentReports();
		extent.attachReporter(htmlReporter);
		//extent.setSystemInfo("User name",System.getProperty("user.name"));
		extent.setSystemInfo("Time Zone", System.getProperty("user.timezone"));
		extent.setSystemInfo("User Location", System.getProperty("user.country"));
		extent.setSystemInfo("OS name", System.getProperty("os.name"));
		extent.setSystemInfo("OS version", System.getProperty("os.version"));
		extent.setSystemInfo("JDK version",System.getProperty("java.version"));
	}
	
	@AfterSuite
	public void tearDown() {
		extent.flush();
	}
	
//	@AfterTest
//	public void closeEnv(){
//		TestUtil.closeBrowser(driver);
//	}
	
	@AfterMethod
	public void getResult(ITestResult result) {
		//test=extent.createTest(result.getName());
		if (result.getStatus() == ITestResult.FAILURE) {
			test.log(Status.FAIL, MarkupHelper.createLabel(result.getName() + " Test case FAILED due to below issues:",	ExtentColor.RED));
			test.fail(result.getThrowable());
		} else if (result.getStatus() == ITestResult.SUCCESS) {
			test.log(Status.PASS, MarkupHelper.createLabel(result.getName() + " Test Case PASSED", ExtentColor.GREEN));
		} else {
			test.log(Status.SKIP,
			MarkupHelper.createLabel(result.getName() + " Test Case SKIPPED", ExtentColor.ORANGE));
			test.skip(result.getThrowable());
		}
		TestUtil.closeBrowser(driver);
	}
	
	
	
	
}
