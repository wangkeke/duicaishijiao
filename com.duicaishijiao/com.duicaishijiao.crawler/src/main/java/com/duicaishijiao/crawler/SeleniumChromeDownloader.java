package com.duicaishijiao.crawler;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import javax.annotation.PreDestroy;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.downloader.AbstractDownloader;
import us.codecraft.webmagic.selector.PlainText;

//@Component
public class SeleniumChromeDownloader extends AbstractDownloader{
	
	private static final Logger logger = LogManager.getLogger();
	
	private int threadNum;
	
	@Value("${remote.webdriver.url}")
	private String remoteWebDriverUrl = "http://localhost:9515";
	
	private RemoteWebDriver remoteWebDriver;
	
	
	
	public SeleniumChromeDownloader() {
		try {			
			remoteWebDriver = new RemoteWebDriver(new URL(remoteWebDriverUrl),  DesiredCapabilities.chrome());
			remoteWebDriver.manage().timeouts()
			.setScriptTimeout(4, TimeUnit.SECONDS)
			.pageLoadTimeout(300, TimeUnit.SECONDS);
		} catch (MalformedURLException e) {
			e.printStackTrace();
			logger.warn(e);
		}
	}
	
	
	@PreDestroy
	public void destroy() {
		if(remoteWebDriver!=null)
			remoteWebDriver.quit();
	}
	
	@Override
	public Page download(Request request, Task task) {
//		if(remoteWebDriver.getWindowHandles().size()>1) {			
//		}
		remoteWebDriver.get(request.getUrl());
		try {
			Thread.currentThread().sleep(4000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		WebElement webElement = remoteWebDriver.findElement(By.xpath("/html"));
        String content = webElement.getAttribute("outerHTML");
        if(remoteWebDriver.getWindowHandles().size()>1) {        	
        	remoteWebDriver.close();
        }
		Page page = new Page();
        page.setRawText(content);
        page.setUrl(new PlainText(request.getUrl()));
        page.setRequest(request);
        return page;
	}

	@Override
	public void setThread(int threadNum) {
		this.threadNum = threadNum;
	}

}
