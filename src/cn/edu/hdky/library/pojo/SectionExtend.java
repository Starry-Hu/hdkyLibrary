package cn.edu.hdky.library.pojo;

import java.util.List;

import cn.edu.hdky.library.bean.Section;

public class SectionExtend extends Section {
	// 父版块名称
	private String parentName;
	// 子版块们
	private List<SectionExtend> childrenSection;
	// 处理后的时间戳
	private String createTimeString;
	private String updateTimeString;

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public List<SectionExtend> getChildrenSection() {
		return childrenSection;
	}

	public void setChildrenSection(List<SectionExtend> childrenSection) {
		this.childrenSection = childrenSection;
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
