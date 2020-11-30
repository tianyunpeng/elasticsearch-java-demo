package com.shineyue.index;

import org.springframework.data.annotation.Id;
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
@Document(indexName = "pt-elasticsearch-todo", type = "todo")
public class ToDoIndex {
	@Id
	private String id;
	/**
	 * 时间自增ID
	 */
	private String indexType;
	private String title;
	private String content;
	private int size;
	private String createTime;
	private String createUserId;
	private String infoType;

	@JsonIgnore
	public ToDoIndex getIndex() {
		return new ToDoIndex();
	}
}
