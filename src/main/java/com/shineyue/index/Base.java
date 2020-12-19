package com.shineyue.index;

import org.springframework.data.elasticsearch.annotations.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

/**
 * 基础index
 * 
 * @author Administrator
 *
 */
@Data
@Document(indexName = "pt-elasticsearch-base", type = "Base")
public class Base {
	// 必要参数
	private String id;
	private String indexName;
	private String indexType;
	// 业务参数
	private String title;
	private String content;
	private int size;
	private String createTime;

	@JsonIgnore
	public Base getIndex() {
		return new Base();
	}
}
