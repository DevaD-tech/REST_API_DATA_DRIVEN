API / Web Services Testing using RestAssured: Data Driven Test

Project Location: https://github.com/Dabbbu/REST_API_DATA_DRIVEN.git

Prerequisite:

Test Data in Excel 
Apache POI
XL Utility file (java class file) that will read data from excel
Write TestNG test with data provider methods

Start:

1- Define the Base URI

RestAssured.baseURI="http://dummy.restapiexample.com/api/v1";

2- Request Object

RequestSpecification httprequest= RestAssured.given();

JSON class created to send the body with the post request, using object requestparam we will define what are the parameter we are going to send as part of post action.

JSONObject requestparam= new JSONObject(); 
		
		requestparam.put("name", "Mango Kumar");
		requestparam.put("salary", "10");
		requestparam.put("age", "14");
		
Inorder to send the data with your request we need to add the data in the request
We will use the request httprequest and will add a header.
We will add a header stating the request body is json

httprequest.header("Content-Type","Application/json");

Now add the json to the body of request

httprequest.body(requestparam.toJSONString());

Now we will send the POST request




3- Response
Capture the response body to perform verification

ValidationPart


		Assert.assertEquals(responsebody.contains("Mango Kumar"), true);
		Assert.assertEquals(responsebody.contains("10"), true);
		Assert.assertEquals(responsebody.contains("14"), true);

Now instead of hard-coding the data we will now provide the data from data provider method.

@DataProvider(name="empdataprovider")
String [][] getEmpData()
	
	{
		String empdata [][]= {{"Mango992","103","43"},{"Mango993","104","32"},{"Mango994","22","30"}};
		return(empdata);
	}

Now instead of hardcoding the data in the dataprovider method, we will now get the data from the xlxs file.
We will use the class xlutility to perform activity from the external xlxs file.

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
