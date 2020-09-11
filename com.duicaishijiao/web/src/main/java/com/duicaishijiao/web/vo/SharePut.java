package com.duicaishijiao.web.vo;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class SharePut {
	
	@NotNull
	@Min(1)
	private Integer id;
	
	@NotBlank
	private String token;
	
	@NotBlank
	private String path;
	
	private String ip;
	
	private String device;
}
