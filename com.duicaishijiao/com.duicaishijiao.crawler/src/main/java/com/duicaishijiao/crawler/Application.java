package com.duicaishijiao.crawler;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.JsonFilePipeline;

@SpringBootApplication
public class Application implements CommandLineRunner{

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				MiuiRepoPageProcessor miuiRepoPageProcessor = new MiuiRepoPageProcessor();
				Spider miuispider = Spider.create(miuiRepoPageProcessor);
				miuispider.setDownloader(new SeleniumChromeDownloader());
				miuispider.addPipeline(new JsonFilePipeline("E:\\tempdata\\"));
				miuispider.addUrl(miuiRepoPageProcessor.getEntrance()).thread(1).run();
			}
		}).start();
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				NanGuaRepoPageProcessor nanGuaRepoPageProcessor = new NanGuaRepoPageProcessor();
				Spider nanguaSpider = Spider.create(nanGuaRepoPageProcessor);
				nanguaSpider.setDownloader(new SeleniumChromeDownloader());
				nanguaSpider.addPipeline(new JsonFilePipeline("E:\\tempdata\\"));
				nanguaSpider.addUrl(nanGuaRepoPageProcessor.getEntrance()).thread(1).run();
				nanguaSpider.close();
			}
		}).start();
	}

}
