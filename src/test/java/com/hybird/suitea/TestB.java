package com.hybird.suitea;

import java.util.Hashtable;

import org.testng.SkipException;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.hybird.util.Constants;
import com.hybird.util.DataUtil;
import com.hybrid.base.BaseTest;

public class TestB extends BaseTest{
	
	@Test(dataProvider="getData")
	public void loginTest(Hashtable<String,String>data) throws Exception
	{
		System.out.println("TestB");
		if(DataUtil.isSkip(testName, xls) ||data.get(Constants.RUNMODE_COL).equals(Constants.RUNMODE_NO)){
			System.out.println("Runmode is set to NO");
			throw new SkipException("Runmode is set to NO");
		}	
		ds.executeKeywords(testName, xls, data);
		
		
	}
	
	
	
	
	
	
	
}