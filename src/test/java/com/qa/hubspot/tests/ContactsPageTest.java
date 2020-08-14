package com.qa.hubspot.tests;

import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.hubspot.base.BasePage;
import com.qa.hubspot.util.AppConstants;
import com.qa.hubspot.util.Credentials;
import com.qa.hubspot.util.ExcelUtil;
import com.qa.hutspot.page.ContactsPage;
import com.qa.hutspot.page.HomePage;
import com.qa.hutspot.page.LoginPage;

public class ContactsPageTest {

	BasePage basePage;
	Properties prop;
	WebDriver driver;
	LoginPage loginPage;
	HomePage homePage;
	ContactsPage contactsPage;
	Credentials userCred;
	@BeforeMethod
	public void setUp() {
		basePage = new BasePage();
		prop = basePage.init_properties();
		String browserName = prop.getProperty("browser");
		driver = basePage.init_driver(browserName);
		driver.get(prop.getProperty("url"));
		loginPage = new LoginPage(driver);
		userCred = new Credentials(prop.getProperty("username"), prop.getProperty("password"));
		homePage = loginPage.doLogin(userCred);
		contactsPage = homePage.goToContactsPage();
	}
	@Test(priority=1)
	public void verifyContactsPageTitle() {
		String title = contactsPage.getContactsPageTitle();
		System.out.println(title);
		Assert.assertEquals(title, "Contacts");
	}
	@DataProvider
	public Object[][] getContactsTestData(){
		Object [][] data = ExcelUtil.getTestData("contacts");
		return data;
	}
	@Test(priority=2, dataProvider= "getContactsTestData")
	public void createContactsTest(String email, String firstname, String lastname, String jobtitle) {
		contactsPage.creatNameContact(email, firstname, lastname, jobtitle);
	}
	@AfterMethod
	public void tearDown() {
		driver.quit();
	

	
	
}

}
