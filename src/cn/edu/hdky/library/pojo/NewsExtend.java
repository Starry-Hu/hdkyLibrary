package cn.edu.hdky.library.pojo;

import cn.edu.hdky.library.bean.News;

public class NewsExtend extends News {
	// 所属项目名称
	private String sectionName;
	// 转换后的时间戳
	private String createTimeString;
	private String updateTimeString;

	public String getSectionName() {
		return sectionName;
	}

	public void setSectionName(String sectionName) {
		this.sectionName = sectionName;
	}

	public String getCreateTimeString() {
		return createTimeString;
	}

	public void setCreateTimeString(String createTimeString) {
		this.createTimeString = createTimeString;
	}

	public String getUpdateTimeString() {
		return updateTimeString;
	}

	public void setUpdateTimeString(String updateTimeString) {
		this.updateTimeString = updateTimeString;
	}

}
