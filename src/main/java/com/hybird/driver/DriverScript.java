package com.hybird.driver;

import java.lang.reflect.Method;
import java.util.Hashtable;
import java.util.Properties;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.hybird.keywords.AppKeywords;
import com.hybird.util.Constants;
import com.hybird.util.Xls_Reader;




public class DriverScript
{
	
	public Properties envProp;
	public Properties prop;// env.properties
	
	public ExtentTest test;
	
	AppKeywords app;
	
	
	public void executeKeywords(String testName, Xls_Reader xls, Hashtable<String,String> testData) throws Exception
	{
		int rows = xls.getRowCount(Constants.KEYWORDS_SHEET);
		System.out.println("Rows "+ rows);
		app = new AppKeywords();
		app.setEnvProp(envProp);
		app.setProp(prop);
		app.setExtentTest(test);
		
		app.setData(testData);
		
		for(int rNum=2;rNum<=rows;rNum++){
			String tcid = xls.getCellData(Constants.KEYWORDS_SHEET, Constants.TCID_COL, rNum);
				if(tcid.equals(testName)){
					String keyword = xls.getCellData(Constants.KEYWORDS_SHEET, Constants.KEYWORD_COL, rNum);
					String objectKey = xls.getCellData(Constants.KEYWORDS_SHEET, Constants.OBJECT_COL, rNum);
					String dataKey= xls.getCellData(Constants.KEYWORDS_SHEET, Constants.DATA_COL, rNum);
					String proceedOnFail=xls.getCellData(Constants.KEYWORDS_SHEET, Constants.PROCEED_COL, rNum);
					String data = testData.get(dataKey);
					test.log(Status.INFO, tcid +" --- "+ keyword+" --- "+ prop.getProperty(objectKey)+" --- "+ data);
					//System.out.println(tcid +" --- "+ keyword+" --- "+ prop.getProperty(objectKey)+" --- "+ data);
					app.setDataKey(dataKey);
					app.setObjectKey(objectKey);
					
					app.setProceedOnFail(proceedOnFail);
					
					Method method;
					
						method = app.getClass().getMethod(keyword);
						method.invoke(app);
						
				
				
					/*
					 * if(keyword.equals("openBrowser")) app.openBrowser();
					 * if(keyword.equals("navigate")) app.navigate(); if(keyword.equals("click"))
					 * app.click(); if(keyword.equals("type")) app.type();
					 */
					
	}
}
		
		app.assertAll();
	}


	public Properties getEnvProp() {
		return envProp;
	}


	public void setEnvProp(Properties envProp) {
		this.envProp = envProp;
	}


	public Properties getProp() {
		return prop;
	}


	public void setProp(Properties prop) {
		this.prop = prop;
	}
	
	public void setExtentTest(ExtentTest test) {
		this.test = test;
	}
	
	
	
	public void quit(){
		if(app!=null)
		app.quit();
	}
	
	
	
}