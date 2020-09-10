package com.duicaishijiao.web.vo;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class ScorePut{
	
	@NotNull
	@Min(1)
	private Integer id;
	
	@NotNull
	@Min(0)
	private Double score;
	
	public Double getScore() {
		return this.score*2;
	}
	
}
