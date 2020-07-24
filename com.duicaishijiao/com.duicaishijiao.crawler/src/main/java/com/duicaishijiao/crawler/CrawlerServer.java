package com.duicaishijiao.crawler;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CrawlerServer implements Runnable{
	
	private static final Logger log = LogManager.getLogger(); 
	
	private List<AbstractCrawler> crawlers = new ArrayList<AbstractCrawler>();
	
	private List<CrawlerTask> crawlerTasks = new ArrayList<CrawlerTask>();
	
	@Autowired
	private MiuiCrawler miuiCrawler;
	
	@Autowired
	private NanGuaCrawler nanGuaCrawler;
	
	/**
	 * 轮询周期
	 */
	private long period = 10*60*1000;
	
	private Thread serverTask;
	
	private final static String SERVER_NAME = "CrawlerServer";
	
	/**
	 * 是否终止线程
	 */
	private boolean terminate = false;
	
	public void setTerminate(boolean terminate) {
		this.terminate = terminate;
	}
	
	public boolean isTerminate() {
		return terminate;
	}
	
	@PostConstruct
	public void init() {
		registCrawler(miuiCrawler,nanGuaCrawler);
		server();
	}
	
	public void server() {
		serverTask = new Thread(this,SERVER_NAME);
		for (AbstractCrawler c : crawlers) {
			crawlerTasks.add(new CrawlerTask(serverTask, c));
		}
		serverTask.start();
	}
	
	@Override
	public void run() {
		for (CrawlerTask task : crawlerTasks) {
			task.start();
		}
		long sleepTime = period;
		List<CrawlerTask> cts = new ArrayList<CrawlerServer.CrawlerTask>(crawlerTasks.size());
		while(!terminate) {
			try {
				Thread.sleep(sleepTime);				
			} catch (InterruptedException e) {
				log.info("休眠被唤醒");
			}
			//查询任务是否已终止或完成！
			boolean isAllalive = true;
			for (Iterator<CrawlerTask> it = crawlerTasks.iterator(); it.hasNext();) {
				CrawlerTask task = it.next();
				if(!task.isAlive()) {
					isAllalive = false;
					long mm = System.currentTimeMillis()-task.getEndTime();
					if(mm>=period) {
						it.remove();
						task = new CrawlerTask(serverTask, task.getCrawler());
						cts.add(task);
					}else if(sleepTime>mm){						
						sleepTime = mm;
					}
				}
			}
			if(isAllalive) {
				sleepTime = period;
			}else if (cts.size()>0) {
				for (CrawlerTask task : cts) {
					crawlerTasks.add(task);
					log.info("重新开始任务");
					task.start();
				}
				cts.clear();
			}
		}
	}
	
	@PreDestroy
	public void destroy() {
		for (CrawlerTask crawlerTask : crawlerTasks) {
			crawlerTask.terminat();
		}
		terminate = true;
	}
	
	public void registCrawler(AbstractCrawler... crawler) {
		for (int i = 0; i < crawler.length; i++) {
			crawlers.add(crawler[i]);			
		}
	}
	
	private static class CrawlerTask extends Thread{
		private Thread serverTask;
		private AbstractCrawler crawler;
		private long endTime;
		
		
		public CrawlerTask(Thread serverTask , AbstractCrawler crawler){
			this.serverTask = serverTask;
			this.crawler = crawler;
		}
		
		
		public long getEndTime() {
			return endTime;
		}
		
		public AbstractCrawler getCrawler() {
			return crawler;
		}
		
		public void terminat() {
			crawler.setTerminate(true);
		}
		
		@Override
		public void run() {
			if(!crawler.isInited()) {
				crawler.init();
			}
			try {
				crawler.crawler();
				log.info("任务正常完成！");
			} catch (RuntimeException e) {
				log.warn("未知的运行时异常，终止爬取！",e);
			}finally {
				endTime = System.currentTimeMillis();
				serverTask.interrupt();
			}
		}
		
	}
	
}
