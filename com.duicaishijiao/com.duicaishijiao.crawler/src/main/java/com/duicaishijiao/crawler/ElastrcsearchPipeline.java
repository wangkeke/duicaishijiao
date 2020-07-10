package com.duicaishijiao.crawler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;
import org.springframework.stereotype.Component;

import com.duicaishijiao.crawler.domain.MovieInfo;
import com.duicaishijiao.crawler.domain.MovieSource;
import com.duicaishijiao.crawler.domain.VideoInfo;
import com.duicaishijiao.crawler.repositories.MovieInfoRepository;
import com.duicaishijiao.crawler.repositories.MovieSourceRepository;
import com.duicaishijiao.crawler.repositories.VideoInfoRepository;

import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

@Component
public class ElastrcsearchPipeline implements Pipeline {
	
	@Autowired
	private VideoInfoRepository videoInfoRepository;
	
	@Autowired
	private MovieInfoRepository movieInfoRepository;
	
	@Autowired
	private MovieSourceRepository movieSourceRepository;
	
	@Override
	public void process(ResultItems resultItems, Task task) {
		Object doc = resultItems.get("doc");
		if(doc!=null) {
			if(doc instanceof VideoInfo) {
				videoInfoRepository.save((VideoInfo)doc);
			}else if(doc instanceof MovieInfo) {
				movieInfoRepository.save((MovieInfo)doc);
			}else if(doc instanceof MovieSource) {
				movieSourceRepository.save((MovieSource)doc);
			}
		}
	}

}
