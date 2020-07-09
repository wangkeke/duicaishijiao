package com.duicaishijiao.crawler;

import java.util.List;
import java.util.stream.Collectors;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Selectable;


public class NanGuaRepoPageProcessor implements PageProcessor , RepoEntrance{
	
	private String entrance = "https://www.ooe.la/vodtype/1/";
	
	@Override
	public String getEntrance() {
		return this.entrance;
	}
	
	@Override
	public void process(Page page) {
		String domain = getEntrance().substring(0, getEntrance().indexOf("/", 10));
		if(page.getRequest().getUrl()==getEntrance()) {   //第一次爬取页面			
			Selectable selectable = page.getHtml().$("div.page_info");
			String em = selectable.$("em").get();
			em = em.substring(em.indexOf("当前")+2);
			String[] ss = em.split("/",2);
			int pageNum = Integer.parseInt(ss[0]);
			int totalPage = Integer.parseInt(ss[1].substring(0, ss[1].lastIndexOf("<")-1));
			String firstPageLink = selectable.$("a.page_link","href").all().get(0);
			int maxCrawPages = 10;
			if(totalPage<maxCrawPages) {
				maxCrawPages = totalPage;
			}
			for (int i = 2; i <= maxCrawPages; i++) {
				page.addTargetRequest(firstPageLink.replace("-1", "-"+i));
			}
		}
		String url = page.getRequest().getUrl();
		String path = url.replace(domain, "");
		if(path.matches("/n/\\d+/?")) {
			Selectable mainSelectable = page.getHtml().$("#main");
			Selectable viewSelectable = mainSelectable.$("div.view");
			List<Selectable> navs = viewSelectable.$("div.wz a","text").nodes();
			String type = navs.get(1).get();
			String img = viewSelectable.$("div.pic img","src").get();
			String name = viewSelectable.$("div.info h1","text").get();
			List<Selectable> linodes = viewSelectable.$("div.info ul li").nodes();
			String status = linodes.get(0).$("strong","text").get();
			String _s = linodes.get(1).get();
			_s = _s.substring(_s.indexOf("</span>")+7);
			String time = _s.substring(0,_s.indexOf("<"));
			String aname = null;
			if(_s.contains("<span>")) {
				_s = _s.substring(_s.indexOf("</span>")+7);
				aname = _s.substring(0,_s.indexOf("<"));
			}
			String author = linodes.get(2).$("a","text").get();
			String actors = linodes.get(3).$("a","text").all().stream().collect(Collectors.joining(","));
			String area = linodes.get(4).$("div.fl","text").get();
			String score = linodes.get(6).$("span.span_block","text").get();
			String desctext = page.getHtml().$("div.endpage div.juqing div.endtext","text").get();
			if(desctext.indexOf("本片" +name +"在线观看由南瓜影院")>0) {
				desctext = desctext.substring(0, desctext.indexOf("本片" +name +"在线观看由南瓜影院"));
			}
			String href = page.getHtml().$("div.play-list a","href").all().get(0);
			String[] pss = path.split("/");
			String id = pss[pss.length-1];
			page.putField("id", id);
			page.putField("href", href);
			page.putField("name", name);
			page.putField("aname", aname);
			page.putField("img", img);
			page.putField("score", score);
			page.putField("type", type);
			page.putField("actors", actors);
			page.putField("time", time);
			page.putField("status", status);
			page.putField("area", area);
			page.putField("desc", desctext);
			page.putField("author", author);
			page.addTargetRequest(href+";"+id);
		}else if (path.matches("(/.+)*/play-\\d+-\\d+-\\d+/?;\\d+")) {
			String id = path.substring(path.indexOf(";")+1);
			String source = page.getHtml().$("#playleft iframe","src").get();
			page.putField("id", id);
			page.putField("source", source);
//			https://jingdian.qincai-zuida.com/20200707/9129_2475f454/1000k/hls/index.m3u8
//				#EXTM3U
//				#EXT-X-STREAM-INF:PROGRAM-ID=1,BANDWIDTH=800000,RESOLUTION=1080x608
//				1000k/hls/index.m3u8
//			https://jingdian.qincai-zuida.com/20200707/9129_2475f454/1000k/hls/index.m3u8
//				#EXTM3U
//				#EXT-X-VERSION:3
//				#EXT-X-TARGETDURATION:7
//				#EXT-X-MEDIA-SEQUENCE:0
//				#EXTINF:4.571233,
//				428109a904e000.ts
//				#EXTINF:3.670333,
//				428109a904e001.ts
//				#EXT-X-ENDLIST
//			https://jingdian.qincai-zuida.com/20200707/9129_2475f454/1000k/hls/428109a904e000.ts
//			https://jingdian.qincai-zuida.com/20200707/9129_2475f454/1000k/hls/428109a904e001.ts
		}else {
			page.setSkip(true);
		}
		
		page.addTargetRequests(page.getHtml().$("div.movielist ul.mlist").links().regex("/n/\\d+/?").all());
		
		
		
		
//		List<Map<String, Object>> dataList = new ArrayList<Map<String,Object>>(18);
//		page.putField("dataList", dataList);
//		page.getHtml().$("div.movielist li").nodes().stream().forEach(st -> {
//			String href = st.$("a.p","href").get();
//			String title = st.$("a.p","title").get();
//			String img = st.$("a.p img","src").get();
//			String score = st.$("a.p i","text").get();
//			String type = st.$("a.p em","text").get();
//			
//			List<Selectable> pns = st.$("div.info p","text").nodes();
//			String actors = pns.get(0).get();
//			actors = actors.substring(actors.indexOf("：")+1);
//			String time = pns.get(1).get();
//			time = time.substring(time.indexOf("：")+1);
//			String status = pns.get(2).get();
//			status = status.substring(status.indexOf("：")+1);
//			String area = pns.get(4).get();
//			area = area.substring(area.indexOf("：")+1);
//			
//			Map<String, Object> dataMap = new HashMap<String, Object>();
//			dataMap.put("href", href);
//			dataMap.put("title", title);
//			dataMap.put("img", img);
//			dataMap.put("score", score);
//			dataMap.put("type", type);
//			dataMap.put("actors", actors);
//			dataMap.put("time", time);
//			dataMap.put("status", status);
//			dataMap.put("area", area);
//			dataList.add(dataMap);
//		});
	}
	
	@Override
	public Site getSite() {
		return Site.me()
				.setRetrySleepTime(3).setSleepTime(100);
	}
	
}
