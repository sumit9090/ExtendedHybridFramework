package com.hybrid.base;

import java.io.FileInputStream;
import java.util.Properties;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.hybird.driver.DriverScript;
import com.hybird.util.DataUtil;
import com.hybird.util.Xls_Reader;
import com.qtpselenium.hybrid.reports.ExtentManager;


public class BaseTest
{
	public Properties envProp;
	public Properties prop;// env.properties
	
	public String testName;
	
	public DriverScript ds;
	
	public ExtentReports rep;
	public ExtentTest test;
	public Xls_Reader xls;
     @BeforeTest
	public void init()

{
    	 System.out.println("*** "+ this.getClass().getSimpleName());
 		testName=this.getClass().getSimpleName();
 		String arr[] = this.getClass().getPackage().getName().split("\\.");
 		String suiteName= arr[arr.length-1];
	
    	 prop = new Properties();
 		envProp = new Properties();
 		
 		try {
			FileInputStream fs = new FileInputStream(System.getProperty("user.dir")+"//src//test//resources//env.properties");
			prop.load(fs);// init env.properties
			String env=prop.getProperty("env");
			System.out.println(env);
			fs = new FileInputStream(System.getProperty("user.dir")+"//src//test//resources//"+env+".properties");
			envProp.load(fs);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
 		
 		System.out.println(envProp.getProperty(suiteName+"_xls"));
		xls = new Xls_Reader(envProp.getProperty(suiteName+"_xls"));
		ds=new DriverScript();
		ds.setEnvProp(envProp);
		ds.setProp(prop);

}
     
     
     @DataProvider
		public Object[][] getData(){
			// i can use xls file object to read data
			System.out.println("Inside data Provider");
			
			
			return DataUtil.getTestData(testName, xls);
		}
     
     
     @BeforeMethod
 	public void initTest(){
 		rep = ExtentManager.getInstance(prop.getProperty("reportPath"));
 		test = rep.createTest(testName);
 		ds.setExtentTest(test);
 	}
	
     
 	@AfterMethod
 	public void quit(){
 		// quit the driver
 		if(ds!=null)
 		ds.quit();
 		
 		if(rep!=null)
			rep.flush();
		
 		
 		
 		
 		
 	}

}