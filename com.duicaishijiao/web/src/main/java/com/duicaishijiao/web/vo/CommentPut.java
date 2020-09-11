package com.duicaishijiao.web.vo;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class CommentPut {
	
	@NotBlank
	private String content;
	
	@Min(1)
	private Integer parentId;
	
}
