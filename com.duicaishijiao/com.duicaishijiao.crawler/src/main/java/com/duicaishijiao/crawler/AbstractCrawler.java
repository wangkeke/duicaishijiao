package com.duicaishijiao.crawler;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.annotation.PreDestroy;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.StringRedisTemplate;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.selector.PlainText;

public abstract class AbstractCrawler implements RepoEntrance{
	
	private static final Logger log = LogManager.getLogger();
	
	private RemoteWebDriver remoteWebDriver;
	
//	private String[] entrances = {"https://www.ooe.la/vodtype/1/",
//			"https://hot.browser.miui.com/v7/#page=inline-video-detail&id=V_09KjUl3x&traceId=E816530E1CBBE7191570E6081BC8BAC9&cp=cn-yidian-video&originCpId=blankOriginCpId&trackPath=rec&wemedia=aGlkZUFk&source=%E5%96%9C%E5%89%A7%E4%BA%AB%E4%B8%8D%E5%81%9C&title=%E4%B8%80%E9%83%A8%E8%AE%A9%E4%BA%BA%E7%AC%91%E5%88%B0%E8%82%9A%E5%AD%90%E7%96%BC%E7%9A%84%E5%96%9C%E5%89%A7%E7%89%87%EF%BC%8C%E5%85%A8%E7%A8%8B%E7%B2%BE%E5%BD%A9%E6%90%9E%E7%AC%91%EF%BC%8C%E5%A5%BD%E7%9C%8B%E5%88%B0%E5%81%9C%E4%B8%8D%E4%B8%8B%E6%9D%A5&_miui_bottom_bar=comment"};
	
	@Value("${remote.webdriver.url:http://localhost:9515}")
	private String remoteWebDriverUrl = "http://localhost:9515";
		
	@Autowired
	private StringRedisTemplate template;
	
	private final static String ROOT_URLS_CACHE_KEY = "crawler:site:";
	
	private String site;
	
	private String entrance;
	
	private String rootDomain;
	
	private String cacheKey;
	
	/**
	 * 是否初始化
	 */
	private boolean inited = false;
	
	/**
	 * 流程是否已经开始
	 */
	private boolean started = false;
	
	/**
	 * 是否终止爬取
	 */
	private boolean terminate = false;
	
	
	
	public boolean isInited() {
		return inited;
	}
	
	public boolean isStarted() {
		return started;
	}
	
	public void setTerminate(boolean terminate) {
		this.terminate = terminate;
	}
	
	public String getSite(){
		return site;
	}
	
	public void init() {
		if(inited) {
			if(remoteWebDriver!=null) {
				remoteWebDriver.quit();
			}
			initRemoteWebDriver();
		}else {				
			initRemoteWebDriver();
			entrance = getEntrance();
			site = _getSite(entrance);
			rootDomain = getRootDomain(entrance);
			cacheKey = getCacheKey();
			inited = true;
		}
	}
	
	private void initRemoteWebDriver() {
		try {			
			remoteWebDriver = new RemoteWebDriver(new URL(remoteWebDriverUrl),  DesiredCapabilities.chrome());
			remoteWebDriver.manage().timeouts()
			.setScriptTimeout(4, TimeUnit.SECONDS)
			.pageLoadTimeout(300, TimeUnit.SECONDS);
		} catch (MalformedURLException e) {
			log.warn("创建RemoteWebDriver失败，请检查remoteWebDriverUrl格式是否正确！",e);
			throw new RuntimeException(e);
		}
	}
	
	private String getCacheKey() {
		return ROOT_URLS_CACHE_KEY+ site;
	}
	
	private Page loadPage(String url) {
		try {
			remoteWebDriver.get(url);
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		WebElement webElement = remoteWebDriver.findElement(By.xpath("/html"));
        String page = webElement.getAttribute("outerHTML");
        Page p = new Page();
		p.setRawText(page);
		p.setUrl(new PlainText(url));
		Request request = new Request(url);
		p.setRequest(request);
		return p;
	}
	
	private String _getSite(String url) {
		String domain = getEntrance().substring(0, getEntrance().indexOf("/", 10));
		return domain.replaceFirst("https?://", "");
	}
	
	public abstract String getRootDomain(String url);
	
	
	/**
	 * launch method
	 */
	public void crawler() {
			ListOperations<String, String> ops = template.opsForList();
			if(ops.size(getCacheKey())==0) {
				if(!started) {					
					Page page = loadPage(entrance);
					List<String> sus = parseStartPage(rootDomain, entrance, page);
					if(sus!=null && sus.size()>0) {
						ops.rightPushAll(cacheKey, sus);
					}
				}
			}
			started = true;
			
			while (!terminate && ops.size(getCacheKey())>0) {
				String url = ops.leftPop(cacheKey);
				try {					
					Page page = loadPage(url);
					List<String> sus = parsePage(rootDomain, url, page);
					if(sus!=null && sus.size()>0) {
						ops.rightPushAll(cacheKey, sus);
					}
				} catch (RuntimeException e) {
					ops.rightPush(cacheKey, url);
					throw new RuntimeException(e);
				}
			}
			log.info(site + " 站点爬取任务结束！");
	}
	
	/**
	 * 解析入口开始页面内容并返回需要缓存的URL地址，该函数在一个流程调度内只会调用一次
	 * @param domain
	 * @param url
	 * @param page
	 * @return 解析后需要追加的url列表
	 */
	public abstract List<String> parseStartPage(String domain, String url , Page page);
	
	/**
	 * 解析页面内容并返回需要缓存的URL地址
	 * @param domain
	 * @param url
	 * @param page
	 * @return 解析后需要追加的url列表
	 */
	public abstract List<String> parsePage(String domain, String url , Page page);
	
	@PreDestroy
	public void destory() {
		if(remoteWebDriver!=null) {			
			remoteWebDriver.quit();
		}
	}
	
}
