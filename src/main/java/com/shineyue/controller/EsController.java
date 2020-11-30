package com.shineyue.controller;

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

}
