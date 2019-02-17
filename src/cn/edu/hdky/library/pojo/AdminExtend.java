package cn.edu.hdky.library.pojo;

import cn.edu.hdky.library.bean.Admin;

public class AdminExtend extends Admin {
	// 将时间戳转为时间
	private String createTimeString;
	private String updateTimeString;

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
