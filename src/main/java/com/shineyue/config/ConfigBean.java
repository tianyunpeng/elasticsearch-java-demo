package com.shineyue.config;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.extern.slf4j.Slf4j;

/**
 * @author tianyunpeng 可配置 Document index /type
 */
@Configuration
@Slf4j
public class ConfigBean {
	/**
	 * 自定义索引名称 默认esinfo
	 */
	@Value("${spring.application.name:esinfo}")
	private String indexName;
	private String typeName = "_type";

	@Bean
	public String indexDoc() {
		// 索引必须小写
		String storeIndex = StringUtils.lowerCase(indexName);
		log.info("Es Create indexDoc===>{}", storeIndex);
		return storeIndex;
	}

	@Bean
	public String typeDoc() {
		String storeType = StringUtils.lowerCase(indexName) + typeName;
		log.info("Es Create typeDoc===>{}", storeType);
		return storeType;
	}
}
