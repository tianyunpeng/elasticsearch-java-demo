package com.shineyue.index;

import org.springframework.data.elasticsearch.annotations.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

/**
 * 知识内容
 * 
 * @author Administrator
 *
 */
@Data
@Document(indexName = "pt-elasticsearch-knowledge", type = "Knowledge")
public class Knowledge {
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
	public Knowledge getIndex() {
		return new Knowledge();
	}
}
