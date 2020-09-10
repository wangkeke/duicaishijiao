package com.duicaishijiao.web.vo;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class StarPut {
	
	@NotNull
	@Min(1)
	private Integer id;
	
	@NotBlank
	private String path;
}
