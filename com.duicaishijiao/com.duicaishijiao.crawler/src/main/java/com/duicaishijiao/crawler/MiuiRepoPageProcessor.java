package com.duicaishijiao.crawler;

import java.util.Date;
import java.util.List;

import com.duicaishijiao.crawler.domain.VideoInfo;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Selectable;


public class MiuiRepoPageProcessor implements PageProcessor , RepoEntrance{
	
	//类型：电影短片
//	private String entrance = "https://hot.browser.miui.com/v7/#page=inline-video-detail&id=V_09720wzV&traceId=7D8C723061D10A1C4E3D1BC1097907A0&cp=cn-yidian-video&originCpId=blankOriginCpId&trackPath=rec&wemedia=aGlkZUFk&source=%E5%BC%AF%E5%BC%AF%E7%94%B5%E5%BD%B1%E8%AE%B2%E8%A7%A3&title=%E8%83%86%E5%B0%8F%E8%80%85%E7%9C%8B%E7%9A%84%E6%81%90%E6%80%96%E7%94%B5%E5%BD%B1%E8%A7%A3%E8%AF%B4%EF%BC%9A7%E5%88%86%E9%92%9F%E7%9C%8B%E6%97%A5%E6%9C%AC%E6%81%90%E6%80%96%E7%89%87%E3%80%8A%E6%9A%97%E9%BB%91%E9%83%BD%E5%B8%82%E4%BC%A0%E8%AF%B4%E3%80%8B&_miui_bottom_bar=comment";
	
	//类型：综艺短片
	private String entrance = "https://hot.browser.miui.com/v7/#page=inline-video-detail&id=V_097EJzRf&traceId=2F6AF103F9659CDE036032D594BB465E&cp=cn-yidian-video&originCpId=blankOriginCpId&trackPath=rec&wemedia=aGlkZUFk&source=%E5%B0%A4%E4%B9%BE%E4%BB%81%E4%B8%8D%E5%AE%B9%E6%98%93&title=%E6%AC%A2%E4%B9%90%E6%B3%A2%E6%B2%88%E8%85%BE%E5%8C%96%E8%BA%AB%E5%8D%93%E5%88%AB%E6%9E%97%EF%BC%8C%E4%B8%80%E5%87%BA%E5%9C%BA%E5%AE%8B%E5%B0%8F%E5%AE%9D%E9%83%BD%E6%83%8A%E8%AE%B6%E4%BA%86%EF%BC%8C%E7%AE%80%E7%9B%B4%E6%9C%AC%E5%B0%8A%E9%A9%BE%E5%88%B0&_miui_bottom_bar=comment";
	
	private String categroy;
	
	public MiuiRepoPageProcessor(String categroy){
		this.categroy = categroy;
	}
	
	public MiuiRepoPageProcessor(String categroy , String entrance){
		this.categroy = categroy;
		this.entrance = entrance;
	}
	
	@Override
	public String getEntrance() {
		return this.entrance;
	}
	
	@Override
	public void process(Page page) {
		Selectable selectable = page.getHtml().$("div.inline-video__player");
	 	List<String> img = selectable.$("div.inline-video__poster img","src").all();
	 	List<String> src = selectable.$("video","data-src").all();
	 	List<String> title = selectable.$("div.inline-video__title p.title","text").all();
	 	List<String> duration = selectable.$("div.inline-video__duration","text").all();
	 	if(!title.isEmpty()) {
//	 		page.putField("title", title.get(title.size()-1));
//	 		page.putField("img", img.get(img.size()-1));
//	 		page.putField("src", src.get(src.size()-1));
	 		String url = page.getRequest().getUrl();
//	 		page.putField("id", url.substring(url.indexOf("&id=")+4,url.indexOf("&",url.indexOf("&id=")+5)));
	 		VideoInfo videoInfo = new VideoInfo();
	 		videoInfo.setCreateTime(new Date());
	 		videoInfo.setUpdateTime(new Date());
	 		videoInfo.setId(url.substring(url.indexOf("&id=")+4,url.indexOf("&",url.indexOf("&id=")+5)));
	 		videoInfo.setImg(img.get(img.size()-1));
	 		videoInfo.setSrc(src.get(src.size()-1));
	 		videoInfo.setTitle(title.get(title.size()-1));
	 		videoInfo.setDuration(duration.get(duration.size()-1));
	 		videoInfo.setType(this.categroy);
	 		page.putField("doc", videoInfo);
	 	}
		page.addTargetRequests(page.getHtml().links().regex("#page=inline-video-detail&id=V_.+").all());
	}

	@Override
	public Site getSite() {
		return Site.me()
				.setRetrySleepTime(3).setSleepTime(100);
	}
	
}
