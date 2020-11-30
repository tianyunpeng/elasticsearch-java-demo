package com.shineyue.index;

import org.springframework.data.annotation.Id;
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
@Document(indexName = "pt-elasticsearch-manual", type = "manual")
public class ManualIndex {
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
	public ManualIndex getIndex() {
		return new ManualIndex();
	}
}
