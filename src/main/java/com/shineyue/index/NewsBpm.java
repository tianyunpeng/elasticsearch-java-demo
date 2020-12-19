package com.shineyue.index;

import org.springframework.data.elasticsearch.annotations.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Document(indexName = "pt-elasticsearch-newsbpm", type = "NewsBpm")
public class NewsBpm {
	private String id;
	private String zxbm;
	private String zxmc;
	private int filetype;
	private int channel;
	private int userid;
	private int deptid;
	private int sprid;
	private int spzt;
	private int lcbz;
	private int label;
	private String outUrl;
	private String title;
	private String txt;
	private String filepath;
	private String filename;
	private String username;
	private String deptname;
	private String sprname;
	private String bpmid;
	private String processKey;
	private String processInstanceId;
	private String endname;
	private String enddate;
	private String firstDept;
	private String pxurl;
	private String applyUserInfo;
	private int tmptype;
	private int imgType;

	public String getZxmc() {
		return zxmc;
	}

	public void setZxmc(String zxmc) {
		this.zxmc = zxmc;
	}

	public int getImgType() {
		return imgType;
	}

	public void setImgType(int imgType) {
		this.imgType = imgType;
	}

	public int getTmptype() {
		return tmptype;
	}

	public void setTmptype(int tmptype) {
		this.tmptype = tmptype;
	}

	public String getApplyUserInfo() {
		return applyUserInfo;
	}

	public void setApplyUserInfo(String applyUserInfo) {
		this.applyUserInfo = applyUserInfo;
	}

	public String getPxurl() {
		return pxurl;
	}

	public void setPxurl(String pxurl) {
		this.pxurl = pxurl;
	}

	public String getFirstDept() {
		return firstDept;
	}

	public void setFirstDept(String firstDept) {
		this.firstDept = firstDept;
	}

	private int ret;
	private String msg;
	private String newsId;

	public String getNewsId() {
		return newsId;
	}

	public void setNewsId(String newsId) {
		this.newsId = newsId;
	}

	public String getZxbm() {
		return zxbm;
	}

	public void setZxbm(String zxbm) {
		this.zxbm = zxbm;
	}

	public int getFiletype() {
		return filetype;
	}

	public void setFiletype(int filetype) {
		this.filetype = filetype;
	}

	public int getChannel() {
		return channel;
	}

	public void setChannel(int channel) {
		this.channel = channel;
	}

	public int getUserid() {
		return userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
	}

	public int getDeptid() {
		return deptid;
	}

	public void setDeptid(int deptid) {
		this.deptid = deptid;
	}

	public int getSprid() {
		return sprid;
	}

	public void setSprid(int sprid) {
		this.sprid = sprid;
	}

	public int getSpzt() {
		return spzt;
	}

	public void setSpzt(int spzt) {
		this.spzt = spzt;
	}

	public int getLcbz() {
		return lcbz;
	}

	public void setLcbz(int lcbz) {
		this.lcbz = lcbz;
	}

	public int getLabel() {
		return label;
	}

	public void setLabel(int label) {
		this.label = label;
	}

	public String getOutUrl() {
		return outUrl;
	}

	public void setOutUrl(String outUrl) {
		this.outUrl = outUrl;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTxt() {
		return txt;
	}

	public void setTxt(String txt) {
		this.txt = txt;
	}

	public String getFilepath() {
		return filepath;
	}

	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getDeptname() {
		return deptname;
	}

	public void setDeptname(String deptname) {
		this.deptname = deptname;
	}

	public String getSprname() {
		return sprname;
	}

	public void setSprname(String sprname) {
		this.sprname = sprname;
	}

	public String getBpmid() {
		return bpmid;
	}

	public void setBpmid(String bpmid) {
		this.bpmid = bpmid;
	}

	public String getProcessKey() {
		return processKey;
	}

	public void setProcessKey(String processKey) {
		this.processKey = processKey;
	}

	public String getProcessInstanceId() {
		return processInstanceId;
	}

	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}

	public String getEndname() {
		return endname;
	}

	public void setEndname(String endname) {
		this.endname = endname;
	}

	public String getEnddate() {
		return enddate;
	}

	public void setEnddate(String enddate) {
		this.enddate = enddate;
	}

	public int getRet() {
		return ret;
	}

	public void setRet(int ret) {
		this.ret = ret;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@JsonIgnore
	public NewsBpm getIndex() {
		return new NewsBpm();
	}

}
