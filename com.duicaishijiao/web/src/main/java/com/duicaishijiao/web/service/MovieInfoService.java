package com.duicaishijiao.web.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.duicaishijiao.base.entity.MovieInfo;
import com.duicaishijiao.base.repository.MovieInfoRepository;
import com.duicaishijiao.web.vo.Condition;

@Service
@Transactional(readOnly = true)
public class MovieInfoService {
	
	@Autowired
	private MovieInfoRepository repository;

	
	public Page<MovieInfo> query(Condition condition) {
		return repository.findAll((Root<MovieInfo> root, CriteriaQuery<?> query, CriteriaBuilder builder)-> {
			Predicate predicate = builder.conjunction();
			if(condition.getType()!=null) {					
				predicate.getExpressions().add(builder.like(root.get("type"), condition.getType()+"%"));
			}
			if(condition.getCountry()!=null) {					
				predicate.getExpressions().add(builder.equal(root.get("area") , condition.getCountry()));
			}
			if(condition.getYears()!=null) {					
				predicate.getExpressions().add(builder.between(root.get("time"), condition.startYear(), condition.endYear()));
			}
			if(condition.getKeyword()!=null && condition.getKeyword().length>0) {
				List<Predicate> predicates = new ArrayList<>();
				for (String word : condition.getKeyword()) {
					predicates.add(builder.like(root.get("name"), word+"%"));
					predicates.add(builder.like(root.get("alias"), word+"%"));
					predicates.add(builder.like(root.get("actors"), "%"+word+"%"));
				}
				predicate.getExpressions().add(builder.or(predicates.toArray(new Predicate[predicates.size()])));
			}
			if(condition.getOrder()!=null) {
				if(1==condition.getOrder()) {
					query.orderBy(builder.desc(root.join("metric", JoinType.LEFT).get("plays")));
				}else if(2==condition.getOrder()) {
					query.orderBy(builder.desc(root.get("createTime")));
				}else if(3==condition.getOrder()) {
					query.orderBy(builder.desc(root.get("score")));
				}
			}else {
				query.orderBy(builder.desc(root.get("createTime")));
			}
			return predicate;
		}, PageRequest.of(condition.getPage()==null?0:condition.getPage()-1, condition.getSize()));
	}
	
}
