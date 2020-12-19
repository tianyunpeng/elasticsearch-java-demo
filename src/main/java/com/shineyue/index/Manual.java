package com.shineyue.index;

import org.springframework.data.elasticsearch.annotations.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

/**
 * 操作手册
 * 
 * @author Administrator
 *
 */
@Data
@Document(indexName = "pt-elasticsearch-manual", type = "Manual")
public class Manual {
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
	public Manual getIndex() {
		return new Manual();
	}
}
