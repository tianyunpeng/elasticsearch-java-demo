package com.shineyue.index;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import lombok.Data;

/**
 * 基础index
 * 
 * @author Administrator
 *
 */
@Data
@Document(indexName = "pt-elasticsearch-base", type = "base")
public class BaseIndex {
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
}
