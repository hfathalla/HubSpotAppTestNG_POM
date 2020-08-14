package com.qa.hubspot.tests;

import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
//import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
//import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.qa.hubspot.base.BasePage;
import com.qa.hubspot.util.AppConstants;
import com.qa.hubspot.util.Credentials;
import com.qa.hutspot.page.HomePage;
import com.qa.hutspot.page.LoginPage;

public class HomePageTest {
	
WebDriver driver;
BasePage basePage;
Properties prop;
LoginPage loginPage;
HomePage homePage;
Credentials userCred;

@BeforeTest
public void setUp() throws InterruptedException{
	basePage =new BasePage();
	prop =basePage.init_properties();
	String browserName =prop.getProperty("browser");
	driver =basePage.init_driver(browserName);
	driver.get(prop.getProperty("url"));
	loginPage=new LoginPage(driver);
userCred =new Credentials(prop.getProperty("username"), prop.getProperty("password"));
homePage =loginPage.doLogin(userCred);
	
	Thread.sleep(5000);
	
}
@Test(priority =1,description ="home page title _ Reports dashboard")
public void verifyHomePageTitleTest(){
	String title = homePage.getHomePageTitle();
	System.out.println("homepage title is :"+ title);
	Assert.assertEquals(title, AppConstants.HOME_PAGE_TITLE);
	
}
@Test(priority =2,description ="home page header - Marketing Dashboard")
public void verifyHomPageHeaderTest(){
	String header=homePage.getHomePageHeader();
	System.out.println("home page header is :"+header);
	Assert.assertEquals(header, AppConstants.HOME_PAGE_HEADER );
}
@Test(priority =3, description = "account name is SiliconeLabs",enabled=true)
public void verifyLoggedInUserTest(){
String accountName =homePage.getLoggedInUserAccountName();
System.out.println("logged account name is:"+accountName);
Assert.assertEquals(accountName, "hend fathalla");
}

@AfterTest
public void tearDown(){
	driver.quit();
}
}
