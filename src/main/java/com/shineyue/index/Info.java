package com.shineyue.index;

import org.springframework.data.elasticsearch.annotations.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

/**
 * 政策资讯
 * 
 * @author Administrator
 *
 */
@Data
@Document(indexName = "pt-elasticsearch-info", type = "Info")
public class Info {
	// 必传参数
	private String id;
	private String indexName;
	private String indexType;
	// 业务参数
	private String title;
	private String content;
	private int size;
	private String createTime;
	private String createUserId;
	private String infoType;

	@JsonIgnore
	public Info getIndex() {
		return new Info();
	}
}
