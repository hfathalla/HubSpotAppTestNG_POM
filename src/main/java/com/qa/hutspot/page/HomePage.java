package com.qa.hutspot.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.qa.hubspot.base.BasePage;
import com.qa.hubspot.util.ElementUtil;

public class HomePage extends BasePage {
	
	
WebDriver driver;
ElementUtil elementUtil;

//locators
By header =By.cssSelector("h1.dashboard-selector__title");
By accountName = By.cssSelector("div.user-info-name ");

By mainContactsLink = By.id("nav-primary-contacts-branch");
By childContactsLink = By.id("nav-secondary-contacts");

//Constructor
public HomePage(WebDriver driver){
	this.driver =driver;
	elementUtil =new ElementUtil(driver);
}

public String getHomePageTitle(){
	return elementUtil.doGetPageTitle();

}

public String getHomePageHeader(){
	return elementUtil.doGetText(header);
	
}
public String getLoggedInUserAccountName(){
	elementUtil.waitForElementPresent(accountName);
	 driver.findElement(By.id("account-menu")).click();
//return driver.findElement(accountName).getText();
	return elementUtil.doGetText(accountName);
	 
	 
}

public void clickOnContacts(){
elementUtil.waitForElementPresent(mainContactsLink);
elementUtil.waitForElementPresent(childContactsLink);
elementUtil.doClick(mainContactsLink);
elementUtil.doClick(childContactsLink);
}
public ContactsPage goToContactsPage(){
	clickOnContacts();
	return new ContactsPage(driver);
}

}


