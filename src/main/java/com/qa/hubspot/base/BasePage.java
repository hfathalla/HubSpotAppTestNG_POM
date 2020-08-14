package com.qa.hubspot.base;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.safari.SafariDriver;

import io.github.bonigarcia.wdm.WebDriverManager;



public class BasePage {

	//defined initalized driver - initialized properties
	//WebDriver driver;
	Properties prop;
	public static boolean highlightElement;
	public OptionsManager optionsManager;
	
	public static ThreadLocal<WebDriver> tlDriver = new ThreadLocal<WebDriver>();
	
	public static synchronized WebDriver getDriver() {
	return tlDriver.get();	
	}
	
	
	public WebDriver init_driver(String browserName){
		highlightElement = prop.getProperty("highlight").equals("yes")?true:false;
		
		
		System.out.println("Browser is :"+browserName);
		
		if (browserName.equalsIgnoreCase("chrome")) {
		WebDriverManager.chromedriver().setup();
		//driver = new ChromeDriver();
		//driver = new ChromeDriver(optionsManager.getChromeOptions());
		tlDriver.set(new ChromeDriver(optionsManager.getChromeOptions()));
		
//	if(prop.getProperty("headless").equals("yes")){
//			ChromeOptions co =new ChromeOptions();
//			co.addArguments("--headless");
//			driver = new ChromeDriver(co);
//			
//			
//	}else{
//			driver = new ChromeDriver();
//		}
//		
		
		
		
		}else if (browserName.equalsIgnoreCase("firefox")) {
			WebDriverManager.firefoxdriver().setup();
			//driver = new FirefoxDriver();
			
			tlDriver.set(new FirefoxDriver(optionsManager.getFirefoxOptions()));
			
				
			
			
				
		}else if (browserName.equalsIgnoreCase("safari")) {
			WebDriverManager.getInstance(SafariDriver.class).setup();
		//	driver = new SafariDriver();
			tlDriver.set(new SafariDriver());	
				
		}else{
			System.out.println("Browser name "+ browserName+"is not found");
		}
		
	getDriver().manage().deleteAllCookies();	
	getDriver().manage().window().maximize();
	
	return getDriver();
		
	}
	public Properties init_properties(){
		prop = new Properties();
		String path =null;
		String env = null;
		
		//String path = "/Users/mohannaelhetawy/Documents/workspace/HubSpotAppTestNG_POM/src/main/java/com/qa/hubspot/config/config properties";
		try {
			env=System.getProperty("env");
			if(env.equals("qa")){
				path="./src/main/java/com/qa/hubspot/config/config.qa.properties";
			}else if (env.equals("stg")) {
				path="./src/main/java/com/qa/hubspot/config/config.stg.properties";
			}
		} catch (Exception e) {
		path ="./src/main/java/com/qa/hubspot/config/config properties";	
		}
		
		
		try {
			FileInputStream ip =new FileInputStream(path);
			prop.load(ip);
		} catch (FileNotFoundException e) {
			System.out.println("some issue happend with config properties...Correct the file");
		}catch (IOException e) {
		e.printStackTrace();	
		}
		return prop;
	}
	
	
}
