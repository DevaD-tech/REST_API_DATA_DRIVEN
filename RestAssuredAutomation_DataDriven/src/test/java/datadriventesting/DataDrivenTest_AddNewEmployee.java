package datadriventesting;

import java.io.IOException;

import org.json.simple.JSONObject;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import junit.framework.Assert;

public class DataDrivenTest_AddNewEmployee {
	
	
	@Test(dataProvider="empdataprovider")
	void POSTNewEmployee(String EMPNAME, String EMPSAL, String EMPAGE)
	
	
	{
		
		RestAssured.baseURI="http://dummy.restapiexample.com/api/v1";
		
		RequestSpecification httprequest= RestAssured.given();
		
		// JSON class created to send the body with the post request, using object requestparam we will define what are the parameter we are going to send as part of post action
		// Created data that we will post
		
		JSONObject requestparam= new JSONObject(); 
		
		requestparam.put("name", EMPNAME);
		requestparam.put("salary", EMPSAL);
		requestparam.put("age", EMPAGE);
		
		//Inorder to send the data with your request we need to add the data in the request
		//We will use the request ‘httprequest’ and will add a header stating the request body is json
		
		httprequest.header("Content-Type","application/json");
		
		//Now add the json to the body of request

		httprequest.body(requestparam.toJSONString());
		
		// POST request
		
		Response response=httprequest.request(Method.POST,"/create");
		
		// Capture the response body to perform verification

		String responsebody=response.getBody().asString();
		
		//Verfication
		
		Assert.assertEquals(responsebody.contains(EMPNAME), true);
		Assert.assertEquals(responsebody.contains(EMPSAL), true);
		Assert.assertEquals(responsebody.contains(EMPAGE), true);
		
		int statuscode=response.getStatusCode();
		Assert.assertEquals(statuscode, 200);
			
		
	}

	@DataProvider(name="empdataprovider")
	String [][] getEmpData() throws IOException
	
	// read data from excel
	
	{	String path= "/Users/temp/eclipse-workspace1/RestAssuredAutomation_DataDriven/data2.xlsx";
		int rownumber= xlutility.getRowCount(path, "Sheet1");
		int columnnumber= xlutility.getCellCount(path, "Sheet1",1);
		String empdata[][]=new String[rownumber][columnnumber];
		
		for(int i=1; i<=rownumber; i++)
		{
			for(int j=0;j<columnnumber; j++)
		{
				empdata[i-1][j]= xlutility.getCellData(path, "Sheet1", i, j);
		}
		}
		//String empdata [][]= {{"Mango992","103","43"},{"Mango993","104","32"},{"Mango994","22","30"}};
		
		return(empdata);
	}
	
	
}
