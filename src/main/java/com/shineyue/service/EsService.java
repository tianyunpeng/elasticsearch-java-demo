package com.shineyue.service;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.admin.indices.get.GetIndexRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.ResultsExtractor;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.shineyue.bean.InfoBean;
import com.shineyue.utils.IdAutoUtils;
import com.shineyue.utils.ResponseData;
import com.shineyue.utils.StringTool;

/**
 * 提供ElasticSearch的公共服务示例
 * 
 * @author tianyunpeng
 *
 */
@Service
public class EsService {
	@Autowired
	private ElasticsearchTemplate elasticsearchTemplate;
	private static Logger log = LoggerFactory.getLogger(EsService.class);
	public static final int MAX_SEARCH_SIZE = 9999;
	public static final int SEARCH_LENGTH = 50;

	/**
	 * 获得所有存储的index
	 * 
	 * @return
	 */
	public ResponseData getAllIndexs() {
		ResponseData responseData = new ResponseData();

		String[] indexes = elasticsearchTemplate.getClient().admin().indices().getIndex(new GetIndexRequest())
				.actionGet().getIndices();
		responseData.setResults(Arrays.asList(indexes));
		responseData.setSuccess(true);
		responseData.setMsg("获得所有存储的index成功");
		return responseData;
	}

	/**
	 * 根据条件查询文档
	 * 
	 * @return
	 */
	public ResponseData queryDocument(JSONObject bean) {
		ResponseData data = new ResponseData();
		if (!StringUtils.isNotBlank(bean.getString("indexName"))
				|| !StringUtils.isNotBlank(bean.getString("indexType"))) {
			data.setSuccess(false);
			data.setMsg("indexName、indexType不可为空！");
			return data;
		}
		String indexName = bean.getString("indexName");
		log.info("根据条件查询文档，indexName==={},入参===={}", indexName, bean.toString());

		try {
			if (elasticsearchTemplate.indexExists(indexName)) {
				// indexType为小写
				String indexType = bean.getString("indexType").toLowerCase();
				int searchSize = bean.getInteger("size") != null ? bean.getInteger("size") : Integer.MAX_VALUE;
				searchSize = searchSize > MAX_SEARCH_SIZE ? MAX_SEARCH_SIZE : searchSize;
				Pageable pageable = PageRequest.of(0, searchSize, Direction.DESC, "id");

				// 遍历查询条件
				if (!prepareCheckSearch(data, searchSize, bean.getString("content"))) {
					return data;
				}
				if (getIndexCount(indexName, indexType) < 1) {
					data.setMsg("该索引无资讯记录");
					data.setSuccess(false);
					return data;
				}

				// 查询条件汇总
				BoolQueryBuilder searchBuilder = QueryBuilders.boolQuery();
				// 绝不允许传递无用的参数
				Iterator iter = bean.entrySet().iterator();
				while (iter.hasNext()) {
					Map.Entry entry = (Map.Entry) iter.next();
					String key = entry.getKey().toString();
					String value = entry.getValue().toString();
					System.out.println("条件查询===" + key);
					System.out.println("条件值===" + value);
					if (!"indexName".equals(key) && !"indexType".equals(key) && !"size".equals(key)
							&& !"".equals(value)) {
						BoolQueryBuilder bqb = new BoolQueryBuilder();
						bqb = bqb.should(QueryBuilders.matchPhraseQuery(key, value));
						searchBuilder.must(bqb);
					}
				}

				// 查询条件合并
				SearchQuery searchQuery = new NativeSearchQueryBuilder().withIndices(indexName).withTypes(indexType)
						.withQuery(searchBuilder).withPageable(pageable).build();
				data = searchLog(searchQuery);
				data.setMsg("根据条件查询文档成功");
				data.setSuccess(true);
				log.info("查询成功==>total: {} ,size:{}", data.getTotalcount(), searchSize);

			} else {
				data.setMsg(StringTool.concat("索引:", indexName, "在Es服务器不存在!"));
				data.setSuccess(false);
			}
		} catch (Exception e) {
			log.error("查询异常==>", e);
			data.setMsg(StringTool.concat("索引:", indexName, "查询异常,异常信息:", e.getMessage()));
			data.setSuccess(false);
		}
		return data;
	}

	/**
	 * 创建index
	 * 
	 * @return
	 */
	public ResponseData createIndex(JSONObject bean) {
		ResponseData responseData = new ResponseData();
		if (!StringUtils.isNotBlank(bean.getString("indexName"))) {
			responseData.setSuccess(false);
			responseData.setMsg("indexName不可为空！");
			return responseData;
		}
		log.info("创建index==={}", bean.getString("indexName"));
		Boolean createIndexFlag = elasticsearchTemplate.createIndex(bean.getString("indexName"));
		responseData.setSuccess(createIndexFlag);
		responseData.setMsg("创建index成功");
		return responseData;
	}

	/**
	 * 创建自适应文档(根据传进来的obj和type创建对象)
	 * 
	 * @return
	 */
	public ResponseData createDocumentAdapt(JSONObject bean) {
		log.info("创建自适应文档body==={}", bean.toString());
		ResponseData responseData = new ResponseData();
		if (!StringUtils.isNotBlank(bean.getString("indexType"))) {
			responseData.setSuccess(false);
			responseData.setMsg("indexType不可为空！");
			return responseData;
		}
		responseData.setSuccess(true);
		String className = StringTool.concat("com.shineyue.index.", StringTool.InitialCap(bean.getString("indexType")),
				"Index");
		Class cls = null;
		Method method = null;
		Object o = null;
		try {
			cls = Class.forName(className);
		} catch (ClassNotFoundException e) {
			responseData.setSuccess(false);
			e.printStackTrace();
			return responseData;
		}

		try {
			method = cls.getMethod("getIndex");
		} catch (NoSuchMethodException e) {
			responseData.setSuccess(false);
			e.printStackTrace();
			return responseData;
		} catch (SecurityException e) {
			responseData.setSuccess(false);
			e.printStackTrace();
			return responseData;
		}
		try {
			o = method.invoke(cls.newInstance());
		} catch (IllegalAccessException e) {
			responseData.setSuccess(false);
			e.printStackTrace();
			return responseData;
		} catch (IllegalArgumentException e) {
			responseData.setSuccess(false);
			e.printStackTrace();
			return responseData;
		} catch (InvocationTargetException e) {
			responseData.setSuccess(false);
			e.printStackTrace();
			return responseData;
		} catch (InstantiationException e) {
			responseData.setSuccess(false);
			e.printStackTrace();
			return responseData;
		}
		Long id = IdAutoUtils.getNextTimeId();
		// 不允许手动输入id
		bean.put("id", id);
		o = JSONObject.parseObject(bean.toJSONString(), o.getClass());
		log.info("将要存储的文档" + o.toString());
		IndexQuery indexQuery = new IndexQueryBuilder().withId(id.toString()).withObject(o).build();
		String result = elasticsearchTemplate.index(indexQuery);
		log.info("创建自适应文档结果result===" + result);
		responseData.setMsg("创建自适应文档成功");

		return responseData;
	}

	/**
	 * 根据id删除文档
	 * 
	 * @return
	 */
	public ResponseData deleteDocument(JSONObject bean) {
		ResponseData responseData = new ResponseData();
		if (!StringUtils.isNotBlank(bean.getString("indexName"))
				|| !StringUtils.isNotBlank(bean.getString("indexType"))) {
			responseData.setSuccess(false);
			responseData.setMsg("indexName或indexType不可为空！");
			return responseData;
		}
		String indexName = bean.getString("indexName");
		log.info("删除文档indexName==={},body==={}", indexName, bean.toString());

		String result = "";
		try {
			result = elasticsearchTemplate.delete(indexName, bean.getString("indexType"), bean.getString("id"));
			responseData.setMsg("删除文档成功");
			responseData.setSuccess(true);
		} catch (Exception e) {
			log.error("删除文档失败！");
			responseData.setMsg("删除文档失败！");
			responseData.setSuccess(false);
		}
		System.out.println("删除文档结果result===" + result);
		return responseData;
	}

	/**
	 * 获得索引数据总数
	 * 
	 * @param indexName
	 * @param indexType
	 * @return
	 */
	public Long getIndexCount(String indexName, String indexType) {
		ResponseData responseData = new ResponseData();
		log.info("查询该索引数据总数入参=={}", indexType);
		SearchQuery countSearchQuery = new NativeSearchQueryBuilder().withIndices(indexName).withTypes(indexType)
				.withSearchType(SearchType.DEFAULT).build();
		log.info("查询该索引数据总数为=={}", elasticsearchTemplate.count(countSearchQuery));

		return elasticsearchTemplate.count(countSearchQuery);
	}

	/**
	 * 校验查询
	 * 
	 * @param data
	 * @param searchSize
	 * @param queryLogInfo
	 * @return
	 */
	public boolean prepareCheckSearch(ResponseData data, int searchSize, String content) {
		if (searchSize < 1) {
			data.setMsg("查询条数不能小于0!");
			return false;
		}
		if (StringUtils.length(content) > SEARCH_LENGTH) {
			data.setMsg(StringTool.concat("查询内容长度不能超过", SEARCH_LENGTH, "字符!"));
			return false;
		}
		return true;
	}

	/**
	 * 
	 * @param searchQuery
	 *            es查询条件
	 * @param simple
	 *            是否处理查询结果
	 * @return
	 */
	public ResponseData searchLog(SearchQuery searchQuery) {
		log.info("查询入参==={}", searchQuery.getFields());
		ResponseData data = elasticsearchTemplate.query(searchQuery, new ResultsExtractor<ResponseData>() {
			@Override
			public ResponseData extract(SearchResponse response) {
				ResponseData searchData = new ResponseData();
				SearchHits searchResults = response.getHits();
				long total = searchResults.totalHits;
				if (total < 1) {
					return searchData;
				}
				searchData.setTotalcount(total);
				List<InfoBean> ibList = new ArrayList<InfoBean>();
				for (SearchHit hit : searchResults) {
					String hitResult = hit.getSourceAsString();
					System.out.println("hitResult===" + hitResult);
					InfoBean ib = new InfoBean();
					ib = JSONObject.parseObject(hitResult, InfoBean.class);
					ibList.add(ib);
				}
				// 降序排序
				Collections.reverse(ibList);
				searchData.setResults(ibList);
				return searchData;
			}
		});
		return data;
	}

	/**
	 * 查询indexType列表
	 * 
	 * @return
	 */
	public ResponseData queryIndexTypeList() {
		log.info("查询indexType列表");
		ResponseData responseData = new ResponseData();
		List<String> fileNames = new ArrayList<>();
		List<String> classNameList = new ArrayList<>();
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		String packageName = "com.shineyue.index";
		String packagePath = packageName.replace(".", "/");
		Boolean childPackage = false;
		Enumeration<URL> urls = null;
		try {
			urls = loader.getResources(packagePath);
		} catch (IOException e) {
			responseData.setSuccess(false);
			e.printStackTrace();
		}
		while (urls.hasMoreElements()) {
			URL url = urls.nextElement();
			if (url == null)
				continue;
			String type = url.getProtocol();
			if (type.equals("file")) {
				try {
					fileNames.addAll(getClassNameByFile(url.getPath(), childPackage));
				} catch (UnsupportedEncodingException e) {
					responseData.setSuccess(false);
					e.printStackTrace();
				}
			} else if (type.equals("jar")) {
				try {
					fileNames.addAll(getClassNameByJar(url.getPath(), childPackage));
				} catch (UnsupportedEncodingException e) {
					responseData.setSuccess(false);
					e.printStackTrace();
				}
			}
		}
		try {
			fileNames.addAll(getClassNameByJars(((URLClassLoader) loader).getURLs(), packagePath, childPackage));
		} catch (UnsupportedEncodingException e) {
			responseData.setSuccess(false);
			e.printStackTrace();
		}
		for (String className : fileNames) {
			// 去除前缀
			String replace = "ode\\pt-elasticsearch\\target\\classes\\com\\shineyue\\index\\";
			className = className.replace(replace, "");
			// 去除Index后缀
			className = className.replace("Index", "");
			classNameList.add(className.toLowerCase());
		}
		responseData.setSuccess(true);
		responseData.setResults(classNameList);
		responseData.setMsg("查询indexType列表");
		return responseData;
	}

	/**
	 * 从jar获取某包下所有类
	 *
	 * @param jarPath
	 *            jar文件路径
	 * @param childPackage
	 *            是否遍历子包
	 * @return 类的完整名称
	 * @throws UnsupportedEncodingException
	 */
	private static List<String> getClassNameByJar(String jarPath, boolean childPackage)
			throws UnsupportedEncodingException {
		List<String> myClassName = new ArrayList<String>();
		String[] jarInfo = jarPath.split("!");
		String jarFilePath = jarInfo[0].substring(jarInfo[0].indexOf("/"));
		String packagePath = jarInfo[1].substring(1);
		try {
			JarFile jarFile = new JarFile(jarFilePath);
			Enumeration<JarEntry> entrys = jarFile.entries();
			while (entrys.hasMoreElements()) {
				JarEntry jarEntry = entrys.nextElement();
				String entryName = jarEntry.getName();
				if (entryName.endsWith(".class")) {
					if (childPackage) {
						if (entryName.startsWith(packagePath)) {
							entryName = entryName.replace("/", ".").substring(0, entryName.lastIndexOf("."));
							myClassName.add(entryName);
						}
					} else {
						int index = entryName.lastIndexOf("/");
						String myPackagePath;
						if (index != -1) {
							myPackagePath = entryName.substring(0, index);
						} else {
							myPackagePath = entryName;
						}
						if (myPackagePath.equals(packagePath)) {
							entryName = entryName.replace("/", ".").substring(0, entryName.lastIndexOf("."));
							myClassName.add(entryName);
						}
					}
				}
			}
		} catch (Exception e) {
			// SystemLog.Log(LogType.systemInfo, e.getMessage(), e);
		}
		return myClassName;
	}

	/**
	 * 从所有jar中搜索该包，并获取该包下所有类
	 *
	 * @param urls
	 *            URL集合
	 * @param packagePath
	 *            包路径
	 * @param childPackage
	 *            是否遍历子包
	 * @return 类的完整名称
	 * @throws UnsupportedEncodingException
	 */
	private static List<String> getClassNameByJars(URL[] urls, String packagePath, boolean childPackage)
			throws UnsupportedEncodingException {
		List<String> myClassName = new ArrayList<String>();
		if (urls != null) {
			for (int i = 0; i < urls.length; i++) {
				URL url = urls[i];
				String urlPath = url.getPath();
				// 不必搜索classes文件夹
				if (urlPath.endsWith("classes/")) {
					continue;
				}
				String jarPath = urlPath + "!/" + packagePath;
				myClassName.addAll(getClassNameByJar(jarPath, childPackage));
			}
		}
		return myClassName;
	}

	/**
	 * 从项目文件获取某包下所有类
	 *
	 * @param filePath
	 *            文件路径 类名集合
	 * @param childPackage
	 *            是否遍历子包
	 * @return 类的完整名称
	 * @throws UnsupportedEncodingException
	 */
	private static List<String> getClassNameByFile(String filePath, boolean childPackage)
			throws UnsupportedEncodingException {
		List<String> myClassName = new ArrayList<>();
		File file = new File(filePath);
		File[] childFiles = file.listFiles();
		if (childFiles == null)
			return myClassName;
		for (File childFile : childFiles) {
			if (childFile.isDirectory()) {
				if (childPackage) {
					myClassName.addAll(getClassNameByFile(childFile.getPath(), childPackage));
				}
			} else {
				String childFilePath = childFile.getPath();
				if (childFilePath.endsWith(".class")) {
					childFilePath = childFilePath.substring(childFilePath.indexOf("/classes/") + 9,
							childFilePath.lastIndexOf("."));
					childFilePath = childFilePath.replace("/", ".");
					myClassName.add(childFilePath);
				}
			}
		}
		return myClassName;
	}

	/**
	 * 修改文档
	 * 
	 * @return
	 */
	public ResponseData updateDocument(JSONObject bean) {
		log.info("修改文档body==={}", bean.toString());
		// 先根据id删除文档
		ResponseData deleteResult = deleteDocument(bean);
		log.info("根据id删除文档结果" + deleteResult.isSuccess());
		// 然后新增
		if (deleteResult.isSuccess()) {
			ResponseData responseData = createDocumentAdapt(bean);
			log.info("新增文档结果" + deleteResult.isSuccess());
			if (deleteResult.isSuccess()) {
				responseData.setMsg("修改文档成功");
				responseData.setSuccess(true);
			} else {
				responseData.setSuccess(false);
			}
			return responseData;
		} else {
			deleteResult.setSuccess(false);
			return deleteResult;

		}
	}
}
