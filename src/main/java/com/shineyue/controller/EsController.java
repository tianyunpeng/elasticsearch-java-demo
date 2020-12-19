package com.shineyue.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.websocket.EncodeException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.shineyue.service.EsService;
import com.shineyue.utils.ResponseData;

/**
 * @author tianyunpeng
 * @create 2020-08-03 15:23
 */
@Controller
public class EsController {
	private static Logger loger = LoggerFactory.getLogger(EsController.class);
	@Autowired
	private EsService service;

	@RequestMapping(value = "/pt/elasticsearch/getAllIndexs", method = RequestMethod.POST)
	@ResponseBody
	private ResponseData getAllIndexs(@RequestBody JSONObject bean) throws EncodeException {
		ResponseData responseData = new ResponseData();
		responseData = service.getAllIndexs();
		return responseData;
	}

	@RequestMapping(value = "/pt/elasticsearch/queryDocument", method = RequestMethod.POST)
	@ResponseBody
	public ResponseData queryDocument(@RequestBody JSONObject bean) {
		ResponseData responseData = new ResponseData();
		responseData = service.queryDocument(bean);
		return responseData;
	}

	@RequestMapping(value = "/pt/elasticsearch/createIndex", method = RequestMethod.POST)
	@ResponseBody
	public ResponseData createIndex(@RequestBody JSONObject bean) {
		ResponseData responseData = new ResponseData();
		responseData = service.createIndex(bean);
		return responseData;
	}

	@RequestMapping(value = "/pt/elasticsearch/deleteDocument", method = RequestMethod.POST)
	@ResponseBody
	public ResponseData deleteDocument(@RequestBody JSONObject bean) {
		ResponseData responseData = new ResponseData();
		responseData = service.deleteDocument(bean);
		return responseData;
	}

	@RequestMapping(value = "/pt/elasticsearch/createDocumentAdapt", method = RequestMethod.POST)
	@ResponseBody
	public ResponseData createDocumentAdapt(@RequestBody JSONObject bean) {
		ResponseData responseData = new ResponseData();
		responseData = service.createDocumentAdapt(bean);
		return responseData;
	}

	@RequestMapping(value = "/pt/elasticsearch/queryIndexTypeList", method = RequestMethod.POST)
	@ResponseBody
	public ResponseData queryIndexTypeList() {
		ResponseData responseData = new ResponseData();
		responseData = service.queryIndexTypeList();
		return responseData;
	}

	@RequestMapping(value = "/pt/elasticsearch/updateDocument", method = RequestMethod.POST)
	@ResponseBody
	public ResponseData updateDocument(@RequestBody JSONObject bean) {
		ResponseData responseData = new ResponseData();
		responseData = service.updateDocument(bean);
		return responseData;
	}

	@RequestMapping(value = "/pt/elasticsearch/createIndexGet", method = RequestMethod.GET)
	@ResponseBody
	public ResponseData createIndexGet(HttpServletRequest request) {
		ResponseData responseData = new ResponseData();
		JSONObject bean = new JSONObject();
		if (null == request.getParameter("indexName") || null == request.getParameter("indexType")) {
			responseData.setSuccess(false);
			responseData.setMsg("indexName、indexType不可为空！");
			return responseData;
		}
		bean.put("indexName", request.getParameter("indexName"));
		bean.put("indexType", request.getParameter("indexType"));
		responseData = service.createIndex(bean);
		return responseData;
	}

	@RequestMapping(value = "/pt/elasticsearch/queryIndexTypeListGet", method = RequestMethod.GET)
	@ResponseBody
	public ResponseData queryIndexTypeListGet() {
		ResponseData responseData = new ResponseData();
		responseData = service.queryIndexTypeList();
		return responseData;
	}

	@RequestMapping(value = "/pt/elasticsearch/createAllDefaultIndexGet", method = RequestMethod.GET)
	@ResponseBody
	public ResponseData createAllDefaultIndexGet(HttpServletRequest request) {
		ResponseData responseData = new ResponseData();
		if (null == request.getParameter("cpmc")) {
			responseData.setSuccess(false);
			responseData.setMsg("产品名称不能为空！");
			return responseData;
		}
		JSONObject bean = new JSONObject();
		ResponseData indexTypeListResponse = new ResponseData();
		indexTypeListResponse = service.queryIndexTypeList();
		List<String> indexTypeList = (List<String>) indexTypeListResponse.getResults();
		// 遍历创建index
		List<String> createIndexResult = new ArrayList<String>();
		for (String indexType : indexTypeList) {
			bean.put("indexName", request.getParameter("cpmc") + "-" + "elasticsearch" + "-" + indexType);
			bean.put("indexType", indexType);
			ResponseData createIndexResponse = new ResponseData();
			createIndexResponse = service.createIndex(bean);
			String successMsg = "indexType为" + indexType + "的索引创建成功";
			String falseMsg = "indexType为" + indexType + "的索引创建失败！" + createIndexResponse.getMsg();
			createIndexResult.add(createIndexResponse.isSuccess() ? successMsg : falseMsg);
		}
		responseData.setSuccess(true);
		responseData.setResults(createIndexResult);
		return responseData;
	}
}
