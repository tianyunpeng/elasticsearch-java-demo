package com.shineyue.utils;

/**
 * 字符串工具类
 * 
 * @author Administrator
 *
 */
public class StringTool {
	/**
	 * 合并字符串
	 * 
	 * @param params
	 * @return
	 */
	public static String concat(String... params) {
		StringBuilder sb = new StringBuilder();
		for (String str : params) {
			sb.append(str);
		}
		return sb.toString();
	}

	public static String concat(Object... params) {
		StringBuilder sb = new StringBuilder();
		for (Object str : params) {
			sb.append(str.toString());
		}
		return sb.toString();
	}

	// 首字母大写
	public static String InitialCap(String name) {
		char[] cs = name.toCharArray();
		// 如果是小写字母，则转换为大写
		if (cs[0] >= 'a' && cs[0] <= 'z') {
			cs[0] -= 32;
			return String.valueOf(cs);
		}
		return name;

	}
}
