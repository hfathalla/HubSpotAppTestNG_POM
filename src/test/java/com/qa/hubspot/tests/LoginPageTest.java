package com.qa.hubspot.tests;

import java.util.Properties;
import org.testng.annotations.Parameters;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;

import org.testng.annotations.BeforeMethod;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;


import com.qa.hubspot.base.BasePage;
import com.qa.hubspot.util.AppConstants;
import com.qa.hubspot.util.Credentials;
import com.qa.hutspot.page.HomePage;
import com.qa.hutspot.page.LoginPage;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;

import org.apache.log4j.Logger;

@Epic("Epic - 101 : create login features")
@Feature("US - 501 : Create test for login on Hubspot")
public class LoginPageTest {
	WebDriver driver;
	BasePage basePage;
	Properties prop;
	LoginPage loginPage;
	Credentials userCred;
	Logger log = Logger.getLogger(LoginPageTest.class);
	

	@BeforeMethod(alwaysRun= true)
	@Parameters(value= {"browser"})
	public void setUp(String browser) {
		String browserName = null;
		basePage = new BasePage();
		prop = basePage.init_properties();
		if(browser.equals(null)) {
			browserName = prop.getProperty("browser");
		}else {
			browserName = browser;
		}
		driver = basePage.init_driver(browserName);
		driver.get(prop.getProperty("url"));
		log.info("url is launched "+ prop.getProperty("url"));
		loginPage = new LoginPage(driver);

		userCred = new Credentials(prop.getProperty("username"), prop.getProperty("password"));

	}

	//groups
	
	@Test(priority = 1,groups="regression" ,description = "get page title as HubSpot Login", enabled = true)
	@Description("Verfiy login Page title")
	@Severity(SeverityLevel.NORMAL)
	
	public void verifyPageTitleTest() {
		log.info("starting------------>>>>verfiyLoginPageTest");
		// Thread.sleep(5000);
		String title = loginPage.getPageTitle();

		System.out.println("login page title is :" + title);
		Assert.assertEquals(title, AppConstants.LOGIN_PAGE_TITLE);
		log.info("endding------------>>>>verfiyLoginPageTest");
		log.warn("some warning");
		log.warn("some error");
		log.warn("fatal error");
	}

	@Test(priority = 2, description = "sign up link is displayed or not", enabled = true)
	@Description("Verfiy sign up link")
	@Severity(SeverityLevel.NORMAL)
	
	
	
	public void verifySignUpLinkTest() {
		Assert.assertTrue(loginPage.checkSignUpLink());
	}

	@Test(priority = 3, description = "invalid username and password for the Login Page", enabled = true)
	@Description("Login page credentials")
	@Severity(SeverityLevel.BLOCKER)
	
	
	public void loginTest() {
		HomePage homePage = loginPage.doLogin(userCred);
		String accountName = homePage.getLoggedInUserAccountName();
		System.out.println("logged in account name :" + accountName);
		Assert.assertEquals(accountName, prop.getProperty("accountName"));

	}

	@DataProvider
	public Object[][] getLoginInvalidData() {
		Object data[][] = { { "bill@gmail.com", "test12345" }, { "jimy@gmail.com", " " }, { " ", "test12345" },
				{ "yummy", "yummy" }, { " ", " " } };
		return data;

	}

	@Test(priority = 4, dataProvider = "getLoginInvalidData", enabled = false)
	public void logging_invalidTestCase(String username, String pwd) {
		userCred.setAppUsername(username);
		userCred.setAppPassword(pwd);
		loginPage.doLogin(userCred);
		Assert.assertTrue(loginPage.checkLoginErrorMessage());
		;

	}

	@AfterMethod(alwaysRun = true)
	public void tearDown() {
		driver.quit();
	}

}
