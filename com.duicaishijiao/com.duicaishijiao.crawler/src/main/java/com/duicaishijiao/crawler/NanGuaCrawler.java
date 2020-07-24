package com.duicaishijiao.crawler;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.duicaishijiao.crawler.domain.MovieInfo;
import com.duicaishijiao.crawler.domain.MovieSource;
import com.duicaishijiao.crawler.repositories.MovieInfoRepository;
import com.duicaishijiao.crawler.repositories.MovieSourceRepository;

import lombok.Getter;
import lombok.Setter;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.selector.Selectable;

@Getter
@Setter
@Component
public class NanGuaCrawler extends AbstractCrawler {
	
	
	@Autowired
	private MovieInfoRepository movieInfoRepository;
	
	@Autowired
	private MovieSourceRepository movieSourceRepository;
	
	private int maxCrawPages = 50;

	private int startCrawPage = 170;
	
	private int maxLatestPages = 4;
	
	private boolean crawAll = true;
	
	
	@Override
	public List<String> parseStartPage(String domain, String url, Page page) {
		Selectable selectable = page.getHtml().$("div.page_info");
		String em = selectable.$("em").get();
		em = em.substring(em.indexOf("当前") + 2);
		String[] ss = em.split("/", 2);
		int pageNum = Integer.parseInt(ss[0]);
		int totalPage = Integer.parseInt(ss[1].substring(0, ss[1].lastIndexOf("<") - 1));
		System.out.println(pageNum + "=====================  " + totalPage);
		String firstPageLink = selectable.$("a.page_link", "href").all().get(0);
		int end = startCrawPage + maxCrawPages;
		if (totalPage < end) {
			end = totalPage;
		}else if(crawAll){
			end = totalPage;
		}
		List<String> pages = new ArrayList<String>();
		for (int i = 1; i <= maxLatestPages; i++) {
			pages.add(domain + firstPageLink.replace("-1", "-" + i));
		}
		for (int i = startCrawPage; i < end; i++) {
			pages.add(domain + firstPageLink.replace("-1", "-" + i));
		}
		System.out.println(pages.size());
		return pages;
	}
	
	@Override
	public List<String> parsePage(String domain, String url, Page page) {
		String path = url.replace(domain, "");
		if (path.matches("/n/\\d+/?")) {
			Selectable mainSelectable = page.getHtml().$("#main");
			Selectable viewSelectable = mainSelectable.$("div.view");
			List<Selectable> navs = viewSelectable.$("div.wz a", "text").nodes();
			String type = navs.get(1).get();
			String img = viewSelectable.$("div.pic img", "src").get();
			String name = viewSelectable.$("div.info h1", "text").get();
			List<Selectable> linodes = viewSelectable.$("div.info ul li").nodes();
			String status = linodes.get(0).$("strong", "text").get();
			String _s = linodes.get(1).get();
			_s = _s.substring(_s.indexOf("</span>") + 7);
			String time = _s.substring(0, _s.indexOf("<"));
			String alias = null;
			if (_s.contains("<span>")) {
				_s = _s.substring(_s.indexOf("</span>") + 7);
				alias = _s.substring(0, _s.indexOf("<"));
			}
			String author = linodes.get(2).$("a", "text").get();
			String actors = linodes.get(3).$("a", "text").all().stream().collect(Collectors.joining(","));
			String area = linodes.get(4).$("div.fl", "text").get();
			String score = linodes.get(6).$("span.span_block", "text").get();
			String desctext = page.getHtml().$("div.endpage div.juqing div.endtext", "text").get();
			if (desctext.indexOf("本片" + name + "在线观看由南瓜影院") > 0) {
				desctext = desctext.substring(0, desctext.indexOf("本片" + name + "在线观看由南瓜影院"));
			}
			List<String> hrefs = page.getHtml().$("div.play-list a", "href").all();
			String[] pss = path.split("/");
			String id = pss[pss.length - 1];

			MovieInfo movieInfo = new MovieInfo();
			movieInfo.setCreateTime(new Date());
			movieInfo.setUpdateTime(new Date());
			movieInfo.setId(Integer.valueOf(id));
			movieInfo.setName(name);
			movieInfo.setAlias(alias);
			movieInfo.setImg(img);
			try {
				movieInfo.setScore(Double.valueOf(score));
			} catch (Exception e) {
				movieInfo.setScore(0.0D);
			}
			movieInfo.setType(type);
			movieInfo.setActors(actors);
			movieInfo.setTime(time);
			movieInfo.setStatus(status);
			movieInfo.setArea(area);
			movieInfo.setDesc(desctext);
			movieInfo.setAuthor(author);
			movieInfoRepository.save(movieInfo);
			return hrefs.stream()
					.map(h -> domain + h + ";" + id)
					.collect(Collectors.toList());
		} else if (path.matches("(/.+)*/play-\\d+-\\d+-\\d+/?;\\d+")) {
			String id = path.substring(path.indexOf(";") + 1);
			String source = page.getHtml().$("#playleft iframe", "src").get();
			String name = page.getHtml().$("div.player div.playdz h1", "text").get();

			MovieSource movieSource = new MovieSource();
			movieSource.setId(Integer.valueOf(id));
			movieSource.setName(name);
			movieSource.setSource(source);
			movieSourceRepository.save(movieSource);
			return null;
		}else {			
			List<String> list = page.getHtml().$("div.movielist ul.mlist").links().regex("/n/\\d+/?").all();
			list = list.stream().distinct().map(h -> domain+h).collect(Collectors.toList());
			return list;
		}
	}

	@Override
	public String getEntrance() {
		return "https://www.ooe.la/vodtype/1/";
	}

	@Override
	public String getRootDomain(String url) {
		return url.substring(0, url.indexOf("/", 10));
	}
	
	
	
}
