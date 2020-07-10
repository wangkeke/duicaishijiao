package com.duicaishijiao.crawler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import us.codecraft.webmagic.Spider;

@SpringBootApplication
public class Application implements CommandLineRunner{
	
	@Autowired
	private ElastrcsearchPipeline elastrcsearchPipeline;
	
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		
//		new Thread(new Runnable() {
//			
//			@Override
//			public void run() {
//				MiuiRepoPageProcessor miuiRepoPageProcessor = new MiuiRepoPageProcessor("综艺");
//				Spider miuispider = Spider.create(miuiRepoPageProcessor);
//				miuispider.setDownloader(new SeleniumChromeDownloader());
//				miuispider.addPipeline(elastrcsearchPipeline);
////				addPipeline(new JsonFilePipeline("E:\\tempdata\\"))
//				miuispider.addUrl(miuiRepoPageProcessor.getEntrance()).thread(1).run();
//			}
//		}).start();
		
//		new Thread(new Runnable() {
//			
//			@Override
//			public void run() {
//				String url = "https://hot.browser.miui.com/v7/#page=inline-video-detail&id=V_09720wzV&traceId=7D8C723061D10A1C4E3D1BC1097907A0&cp=cn-yidian-video&originCpId=blankOriginCpId&trackPath=rec&wemedia=aGlkZUFk&source=%E5%BC%AF%E5%BC%AF%E7%94%B5%E5%BD%B1%E8%AE%B2%E8%A7%A3&title=%E8%83%86%E5%B0%8F%E8%80%85%E7%9C%8B%E7%9A%84%E6%81%90%E6%80%96%E7%94%B5%E5%BD%B1%E8%A7%A3%E8%AF%B4%EF%BC%9A7%E5%88%86%E9%92%9F%E7%9C%8B%E6%97%A5%E6%9C%AC%E6%81%90%E6%80%96%E7%89%87%E3%80%8A%E6%9A%97%E9%BB%91%E9%83%BD%E5%B8%82%E4%BC%A0%E8%AF%B4%E3%80%8B&_miui_bottom_bar=comment";
//				MiuiRepoPageProcessor miuiRepoPageProcessor = new MiuiRepoPageProcessor("恐怖",url);
//				Spider miuispider = Spider.create(miuiRepoPageProcessor);
//				miuispider.setDownloader(new SeleniumChromeDownloader());
//				miuispider.addPipeline(elastrcsearchPipeline);
////				addPipeline(new JsonFilePipeline("E:\\tempdata\\"))
//				miuispider.addUrl(miuiRepoPageProcessor.getEntrance()).thread(1).run();
//			}
//		}).start();
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				NanGuaRepoPageProcessor nanGuaRepoPageProcessor = new NanGuaRepoPageProcessor(10,20);
				Spider nanguaSpider = Spider.create(nanGuaRepoPageProcessor);
				nanguaSpider.setDownloader(new SeleniumChromeDownloader());
				nanguaSpider.addPipeline(elastrcsearchPipeline);
//				nanguaSpider.addPipeline(new JsonFilePipeline("E:\\tempdata\\"));
				nanguaSpider.addUrl(nanGuaRepoPageProcessor.getEntrance()).thread(1).run();
				nanguaSpider.close();
			}
		}).start();
	}

}
