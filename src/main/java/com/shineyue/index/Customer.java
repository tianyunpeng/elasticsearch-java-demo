package com.shineyue.index;

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
@Document(indexName = "pt-elasticsearch-customer", type = "Customer")
public class Customer {
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
	public Customer getIndex() {
		return new Customer();
	}
}
