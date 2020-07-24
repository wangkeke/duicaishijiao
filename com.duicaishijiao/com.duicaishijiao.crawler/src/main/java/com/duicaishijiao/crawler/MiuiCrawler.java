package com.duicaishijiao.crawler;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.duicaishijiao.crawler.domain.VideoInfo;
import com.duicaishijiao.crawler.repositories.VideoInfoRepository;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.selector.Selectable;

@Component
public class MiuiCrawler extends AbstractCrawler{
	
	
	@Autowired
	private VideoInfoRepository videoInfoRepository;
	
	@Autowired
	private KeyGenerator keyGenerator;
	
	@Override
	public String getEntrance() {
		return "https://hot.browser.miui.com/v7/#page=inline-video-detail&id=V_09KjUl3x&traceId=E816530E1CBBE7191570E6081BC8BAC9&cp=cn-yidian-video&originCpId=blankOriginCpId&trackPath=rec&wemedia=aGlkZUFk&source=%E5%96%9C%E5%89%A7%E4%BA%AB%E4%B8%8D%E5%81%9C&title=%E4%B8%80%E9%83%A8%E8%AE%A9%E4%BA%BA%E7%AC%91%E5%88%B0%E8%82%9A%E5%AD%90%E7%96%BC%E7%9A%84%E5%96%9C%E5%89%A7%E7%89%87%EF%BC%8C%E5%85%A8%E7%A8%8B%E7%B2%BE%E5%BD%A9%E6%90%9E%E7%AC%91%EF%BC%8C%E5%A5%BD%E7%9C%8B%E5%88%B0%E5%81%9C%E4%B8%8D%E4%B8%8B%E6%9D%A5&_miui_bottom_bar=comment";
	}

	@Override
	public String getRootDomain(String url) {
		return url.substring(0, url.indexOf("#"));
	}
	
	@Override
	public List<String> parseStartPage(String domain , String url, Page page) {
		return parsePage(domain, url, page);
	}

	@Override
	public List<String> parsePage(String domain , String url, Page page) {
		Selectable selectable = page.getHtml().$("div.inline-video__player");
	 	List<String> img = selectable.$("div.inline-video__poster img","src").all();
	 	List<String> src = selectable.$("video","data-src").all();
	 	List<String> title = selectable.$("div.inline-video__title p.title","text").all();
	 	List<String> duration = selectable.$("div.inline-video__duration","text").all();
	 	if(!title.isEmpty()) {
	 		VideoInfo videoInfo = new VideoInfo();
	 		videoInfo.setCreateTime(new Date());
	 		videoInfo.setUpdateTime(new Date());
	 		videoInfo.setId(keyGenerator.getKey());
//	 		videoInfo.setId(url.substring(url.indexOf("&id=")+4,url.indexOf("&",url.indexOf("&id=")+5)));
	 		videoInfo.setImg(img.get(img.size()-1));
	 		videoInfo.setSrc(src.get(src.size()-1));
	 		videoInfo.setTitle(title.get(title.size()-1));
	 		videoInfo.setDuration(duration.get(duration.size()-1));
	 		videoInfoRepository.save(videoInfo);
	 	}
		return page.getHtml().links().regex("#page=inline-video-detail&id=V_.+").all().stream().distinct().map(l -> domain+l).collect(Collectors.toList());
	}
	
}
