package com.shineyue.index;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

/**
 * 客户信息
 * 
 * @author Administrator
 *
 */
@Data
@Document(indexName = "pt-elasticsearch-customer", type = "customer")
public class CustomerIndex {
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
	public CustomerIndex getIndex() {
		return new CustomerIndex();
	}
}
