package com.shineyue.index;

import org.springframework.data.elasticsearch.annotations.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

/**
 * 待办
 * 
 * @author Administrator
 *
 */
@Data
@Document(indexName = "pt-elasticsearch-todo", type = "ToDo")
public class ToDo {
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
	public ToDo getIndex() {
		return new ToDo();
	}
}
