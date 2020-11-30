package com.shineyue.index;

import org.springframework.data.annotation.Id;
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
@Document(indexName = "pt-elasticsearch-info", type = "info")
public class InfoIndex {
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
	public InfoIndex getIndex() {
		return new InfoIndex();
	}
}
