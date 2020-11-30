package com.shineyue.utils;
import java.util.List;

import lombok.Data;
/**
 * 返回数据工具类
 * @author Administrator
 *
 */
@Data
public class ResponseData  {
	
	private boolean success;
	private String msg;
	private long totalcount;
	private List<? extends Object> results;
	private Object errors;
	private Object data;
}
