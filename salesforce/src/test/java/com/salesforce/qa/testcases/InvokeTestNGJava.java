package com.salesforce.qa.testcases;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.testng.TestListenerAdapter;
import org.testng.TestNG;
import org.testng.collections.Lists;
import org.testng.xml.XmlSuite;

public class InvokeTestNGJava {

	public static void main(String[] args) throws Exception {
		System.out.println("Started!");
	    TestNG testng = new TestNG();
	    XmlSuite suite = new XmlSuite();

	    suite.setVerbose(10);

	    List<String> suites = Lists.newArrayList();
	    suites.add("testng.xml");
	    testng.setTestSuites(suites);
	    testng.run();
	}
}
