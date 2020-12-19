package com.shineyue.index;

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
@Document(indexName = "pt-elasticsearch-knowledgeaccess", type = "KnowledgeAccess")
public class KnowledgeAccess {
	// 必要参数
	private String id;

	private String indexName;
	private String indexType;
	// 业务参数
	// 中心编码
	private String zxbm;
	// 用户ID
	private Integer yhid;
	// 用户名称
	private String yhmc;
	// 关键字
	private String gjz;
	// 标题
	private String bt;
	// 知识类型
	private String zslx;
	// 知识格式
	private String zsgs;

	// 封面地址
	private String fmdz;
	// 封面名称
	private String fmmc;
	// 封面格式
	private String fmgs;
	// 图文内容(图文，pdf)
	private String twnr;
	// 附件地址
	private String fjdz;
	// 附件名称
	private String fjmc;
	// 审批状态
	private Integer spzt;
	// 流程标志
	private Integer lcbz;
	// 知识id
	private Integer zsid;
	// 流程id；
	private Integer bpmid;
	// 部门名称
	private String bmmc;
	// 审批人id；
	private Integer sprid;
	// 审批人名称
	private String sprmc;
	// 知识内容缩略
	private String zsnrInfo;
	// 办理渠道
	private String blqd;
	private String tjsj;

	@JsonIgnore
	public KnowledgeAccess getIndex() {
		return new KnowledgeAccess();
	}
}
