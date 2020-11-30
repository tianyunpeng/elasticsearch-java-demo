package com.shineyue.bean;

import lombok.Data;

@Data
public class InfoBean {
	private Long id;
	/**
	 * 时间自增ID
	 */
	private String title;
	private String content;
	private String createTime;
}
