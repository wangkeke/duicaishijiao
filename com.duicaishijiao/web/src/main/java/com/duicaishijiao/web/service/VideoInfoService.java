package com.duicaishijiao.web.service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.duicaishijiao.base.entity.VideoInfo;
import com.duicaishijiao.base.repository.VideoInfoRepository;
import com.duicaishijiao.web.vo.Condition;

@Service
@Transactional(readOnly = true)
public class VideoInfoService {
	
	@Autowired
	private VideoInfoRepository repository;
	
	public Page<VideoInfo> query(Condition condition){
		return repository.findAll((Root<VideoInfo> root, CriteriaQuery<?> query, CriteriaBuilder builder)-> {
			Predicate predicate = builder.conjunction();
			if(condition.getKeyword()!=null && condition.getKeyword().length>0) {
				Predicate[] predicates = new Predicate[condition.getKeyword().length];
				for (int i = 0; i < predicates.length; i++) {
					predicates[i] = builder.like(root.get("title"), "%"+condition.getKeyword()[i]+"%");
				}
				predicate.getExpressions().add(builder.or(predicates));
			}
			if(condition.getDuration()!=null) {
				predicate.getExpressions().add(builder.greaterThanOrEqualTo(root.get("duration"), condition.startDuration()));
				if(condition.endDuration()!=null) {
					predicate.getExpressions().add(builder.lessThanOrEqualTo(root.get("duration"), condition.endDuration()));
				}
			}
			if(condition.getOrder()!=null) {
				Join<String, JoinType> join = root.join("metric", JoinType.LEFT);
				if(1==condition.getOrder()) {
					query.orderBy(builder.desc(join.get("plays")));
				}else if(2==condition.getOrder()) {
					query.orderBy(builder.desc(root.get("createTime")));
				}else if(3==condition.getOrder()) {
					query.orderBy(builder.desc(root.get("danmakus")));
				}
			}else {
				query.orderBy(builder.desc(root.get("createTime")));
			}
			return predicate;
		}, PageRequest.of(condition.getPage()==null?0:condition.getPage()-1, condition.getSize()));
	}
	
}
