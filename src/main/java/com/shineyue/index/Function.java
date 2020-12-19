package com.shineyue.index;

import org.springframework.data.elasticsearch.annotations.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

/**
 * 功能模块
 * 
 * @author Administrator
 *
 */
@Data
@Document(indexName = "pt-elasticsearch-function", type = "Function")
public class Function {
	// 必要参数
	private String id;
	private String indexName;
	private String indexType;
	private String title;
	private String content;
	private int size;
	private String createTime;
	private String createUserId;
	private String infoType;

	@JsonIgnore
	public Function getIndex() {
		return new Function();
	}
}
