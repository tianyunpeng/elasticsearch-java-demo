package com.shineyue.index;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

/**
 * 基础index
 * 
 * @author Administrator
 *
 */
@Data
@Document(indexName = "pt-elasticsearch-accesslog", type = "AccessLog")
public class AccessLog {
	// 必要参数
	@Id
	private String id;
	/**
	 * 时间自增ID
	 */
	// 业务参数
	private String accessUrl;
	// private int unavailable;
	private String accessDate;
	private String requestBody;
	private String responseBody;
	private String proxyaddr;
	private String sourceaddr;
	private String token;
	private int state;
	private String servicename;
	private String method;
	private long startTime;
	private long endTime;

	@JsonIgnore
	public AccessLog getIndex() {
		return new AccessLog();
	}
}
