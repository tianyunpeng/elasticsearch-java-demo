package com.shineyue.bean;

import lombok.Data;

@Data
public class ResponseBean {
	private Long id;
	/**
	 * 时间自增ID
	 */
	private String type;
	private String title;
	private String content;
}
