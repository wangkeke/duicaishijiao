package com.duicaishijiao.web.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.duicaishijiao.base.entity.Comment;
import com.duicaishijiao.base.entity.MovieInfo;
import com.duicaishijiao.base.entity.MovieScore;
import com.duicaishijiao.base.entity.MovieSource;
import com.duicaishijiao.base.entity.SourceRecord;
import com.duicaishijiao.base.entity.User;
import com.duicaishijiao.base.entity.UserStar;
import com.duicaishijiao.base.repository.CommentRepository;
import com.duicaishijiao.base.repository.MovieInfoRepository;
import com.duicaishijiao.base.repository.MovieScoreRepository;
import com.duicaishijiao.base.repository.MovieSourceRepository;
import com.duicaishijiao.base.repository.UserStarRepository;
import com.duicaishijiao.web.Exception.BusinessException;
import com.duicaishijiao.web.Exception.NotFoundException;
import com.duicaishijiao.web.common.KeyConstants;
import com.duicaishijiao.web.filter.CurrentUser;
import com.duicaishijiao.web.vo.Condition;
import com.duicaishijiao.web.vo.ScorePut;
import com.duicaishijiao.web.vo.StarPut;

@Service
@Transactional(readOnly = true)
public class MovieInfoService {
	
	private static final Logger log = LogManager.getLogger();
	
	@Autowired
	private MovieInfoRepository repository;

	@Autowired
	private MovieSourceRepository sourceRepository;
	
	@Autowired
	private CommentRepository commentRepository;
	
	@Autowired
	private UserStarRepository userStarRepository;
	
	@Autowired
	private MovieScoreRepository movieScoreRepository;
	
	@Autowired
	private StringRedisTemplate redisTemplate;
	
	
	public Page<MovieInfo> query(Condition condition) {
		
		return repository.findAll((Root<MovieInfo> root, CriteriaQuery<?> query, CriteriaBuilder builder)-> {
			Predicate predicate = builder.conjunction();
			List<Order> orders = new ArrayList<>();
			if(condition.getType()!=null) {					
				predicate.getExpressions().add(builder.like(root.get("type"), condition.getType()+"%"));
			}
			if(condition.getCountry()!=null) {					
				predicate.getExpressions().add(builder.equal(root.get("area") , condition.getCountry()));
			}
			if(condition.getYears()!=null) {					
				predicate.getExpressions().add(builder.between(root.get("time"), condition.startYear(), condition.endYear()));
			}
			if(condition.getOrder()!=null) {
				if(1==condition.getOrder()) {
					orders.add(builder.desc(root.join("metric", JoinType.LEFT).get("plays")));
					orders.add(builder.desc(root.get("score")));
					orders.add(builder.desc(root.get("createTime")));
				}else if(2==condition.getOrder()) {
					orders.add(builder.desc(root.get("createTime")));
					orders.add(builder.desc(root.get("score")));
					orders.add(builder.desc(root.join("metric", JoinType.LEFT).get("plays")));
				}else if(3==condition.getOrder()) {
					orders.add(builder.desc(root.get("score")));
					orders.add(builder.desc(root.join("metric", JoinType.LEFT).get("plays")));
					orders.add(builder.desc(root.get("createTime")));
				}
			}
			if(StringUtils.isNotBlank(condition.getKeyword())) {
				List<Predicate> predicates = new ArrayList<>();
				for (String word : condition.getKeyword().split("[\\s+|(\\s*,\\s*)|(\\s*，\\s*)]")) {
					if(StringUtils.isNotBlank(word)) {						
						predicates.add(builder.like(root.get("name"), word+"%"));
						predicates.add(builder.like(root.get("alias"), word+"%"));
						predicates.add(builder.like(root.get("actors"), "%"+word+"%"));
					}
				}
				predicate.getExpressions().add(builder.or(predicates.toArray(new Predicate[predicates.size()])));
				orders.add(builder.desc(root.get("name")));
				orders.add(builder.desc(root.get("alias")));
			}
			if(orders.isEmpty()) {
				orders.add(builder.desc(root.get("createTime")));
				orders.add(builder.desc(root.get("score")));
				orders.add(builder.desc(root.join("metric", JoinType.LEFT).get("plays")));
			}
			query.orderBy(orders);
			return predicate;
		}, PageRequest.of(condition.getPage()==null?0:condition.getPage()-1, condition.getSize()));
	}
	
	@Cacheable(value = KeyConstants.MOVIE_ID,key = "#p0",condition = "#p0!=null")
	private MovieInfo getById(Integer id) {
		if(id==null || id<0)
			return null;
		Optional<MovieInfo> optional = repository.findById(id);
		if(optional.isPresent()) {
			return optional.get();
		}
		return null;
	}
	
	public MovieInfo detail(Integer id) {
		MovieInfo info = getById(id);
		if(info==null) {
			throw new NotFoundException("资源不存在");
		}
		List<MovieSource> sources = sourceRepository.findByMovieIdOrMovieInfo(info.getMovieId(), info);
		if(sources==null) {
			sources = new ArrayList<MovieSource>(0);
		}
		sources.forEach(s -> {if(s.getSource()!=null)s.setSource(s.getSource().substring(s.getSource().lastIndexOf("http")));});
		info.setSources(sources);
		BoundHashOperations<String, String, Integer> operations = redisTemplate.boundHashOps(KeyConstants.MOVIE_WATCHS);
		Integer watchs = operations.get(String.valueOf(id));
		if(watchs!=null) {
			info.setWatchs(watchs);
		}else {
			info.setWatchs(0);
		}
		//是否已收藏
//		long starCount = userStarRepository.countByMovieInfo(info);
		info.setStar(false);
		return info;
	}
	
	public List<MovieInfo> recommend(Condition condition){
		Page<MovieInfo> page = repository.findAll((Root<MovieInfo> root, CriteriaQuery<?> query, CriteriaBuilder builder)-> {
			Predicate predicate = builder.conjunction();
			if(condition.getId()!=null) {
				predicate.getExpressions().add(builder.notEqual(root.get("id"), condition.getId()));
			}
			if(condition.getType()!=null) {					
				predicate.getExpressions().add(builder.equal(root.get("type"), condition.getType()));
			}
			if(condition.getCountry()!=null) {					
				predicate.getExpressions().add(builder.equal(root.get("area") , condition.getCountry()));
			}
			List<Order> orders = new ArrayList<>();
			if(condition.getYears()!=null) {					
				predicate.getExpressions().add(builder.lessThanOrEqualTo(root.get("time"), condition.getYears()));
				orders.add(builder.desc(root.get("time")));
			}
			if(condition.getOrder()!=null) {
				if(1==condition.getOrder()) {
					orders.add(builder.desc(root.join("metric", JoinType.LEFT).get("plays")));
					orders.add(builder.desc(root.get("score")));
					orders.add(builder.desc(root.get("createTime")));
				}else if(2==condition.getOrder()) {
					orders.add(builder.desc(root.get("createTime")));
					orders.add(builder.desc(root.get("score")));
					orders.add(builder.desc(root.join("metric", JoinType.LEFT).get("plays")));
				}else if(3==condition.getOrder()) {
					orders.add(builder.desc(root.get("score")));
					orders.add(builder.desc(root.join("metric", JoinType.LEFT).get("plays")));
					orders.add(builder.desc(root.get("createTime")));
				}
			}
			if(orders.isEmpty()) {
				orders.add(builder.desc(root.get("createTime")));
				orders.add(builder.desc(root.get("score")));
				orders.add(builder.desc(root.join("metric", JoinType.LEFT).get("plays")));
			}
			query.orderBy(orders);
			return predicate;
		}, PageRequest.of(0, condition.getTotal()));
		return page.getContent();
	}
	
	
	public Page<Comment> comment(Condition condition){
		if(condition.getId()==null) {
			throw new NotFoundException("不存在的资源");
		}
		MovieInfo movieInfo = new MovieInfo();
		movieInfo.setId(condition.getId());
		Sort sort = null;
		if(condition.getOrder()!=null && condition.getOrder()==1) {
			sort = Sort.by(org.springframework.data.domain.Sort.Order.desc("likes"),org.springframework.data.domain.Sort.Order.desc("createTime"));
		}else {
			sort = Sort.by(org.springframework.data.domain.Sort.Order.desc("createTime"),org.springframework.data.domain.Sort.Order.desc("likes"));
		}
		return commentRepository.findByMovieInfo(movieInfo, PageRequest.of(condition.getPage()==null?0:condition.getPage()-1, condition.getSize(), sort));
	}
	
	@Transactional
	public void addScore(ScorePut score) {
		MovieInfo movieInfo = getById(score.getId());
		if(movieInfo==null) {
			throw new NotFoundException("该影片资源已下架或不存在~");
		}
		String anonymity = CurrentUser.getAnonymity();
		User user = CurrentUser.getUser();
		int count = movieScoreRepository.countByMovieInfoAndUserAndAnonymity(movieInfo, CurrentUser.getUser(), anonymity);
		if(count>0) {
			throw new BusinessException("你已打过分了哦~");
		}
		Date time = new Date();
		MovieScore movieScore = new MovieScore();
		movieScore.setAnonymity(anonymity);
		movieScore.setCreateTime(time);
		movieScore.setMovieInfo(movieInfo);
		movieScore.setScore(score.getScore());
		movieScore.setUpdateTime(time);
		movieScore.setUser(user);
		movieScoreRepository.save(movieScore);
	}
	
	@Transactional
	public void addStar(StarPut starPut) {
		MovieInfo movieInfo = getById(starPut.getId());
		if(movieInfo==null) {
			throw new NotFoundException("该影片资源已下架或不存在~");
		}
		User user = CurrentUser.getUser();
		UserStar userStar = userStarRepository.findFirstByMovieInfoAndUserAndFlagNot(movieInfo, user, -1);
		Date time = new Date();
		if(userStar!=null) {
			userStar.setUpdateTime(time);
			userStarRepository.save(userStar);
		}else {
			userStar = new UserStar();
			userStar.setCreateTime(time);
			userStar.setMovieInfo(movieInfo);
			userStar.setUpdateTime(time);
			userStar.setUser(user);
			SourceRecord record = new SourceRecord();
			record.setImg(movieInfo.getImg());
			record.setTitle(movieInfo.getName());
			record.setUrl("movie/25787?order=3&page=2");
			userStarRepository.save(userStar);
		}
	}
	
}
