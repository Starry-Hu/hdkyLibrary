package cn.edu.hdky.library.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.edu.hdky.library.bean.Section;
import cn.edu.hdky.library.dao.SectionMapper;
import cn.edu.hdky.library.daoExtend.SectionExtendMapper;
import cn.edu.hdky.library.enums.ExceptionEnum;
import cn.edu.hdky.library.exception.MyException;
import cn.edu.hdky.library.pojo.SectionExtend;
import cn.edu.hdky.library.service.SectionService;
import cn.edu.hdky.library.util.DateStamp;
import cn.edu.hdky.library.util.IDGenerator;

@Service("sectionService")
public class SectionServiceImpl implements SectionService {
	@Autowired
	private SectionMapper sectionMapper;
	@Autowired
	private SectionExtendMapper sectionExtendMapper;

	@Override
	public int addSection(String name, int level, String parentId, String address, String createUser) throws Exception {
		Section section = new Section();
		Date date = new Date();
		section.setId(IDGenerator.generator());
		section.setName(name);
		section.setLevel(level);
		section.setParentid(parentId);
		section.setAddress(address);
		section.setCreateuser(createUser);
		section.setCreatetime(date);
		// 默认设置为0 （未删除）
		section.setIsdel(0);

		try {
			int n = sectionMapper.insert(section);
			if (n > 0) {
				return n;
			} else {
				throw new MyException(ExceptionEnum.SECTION_ADD_FAIL);
			}
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public int deleteSectionLogic(String id, String updateUser) throws Exception {
		Date date = new Date();
		Section section = new Section();

		section.setId(id);
		// 1为删除
		section.setIsdel(1);
		section.setUpdatetime(date);
		section.setUpdateuser(updateUser);

		try {
			int n = sectionMapper.updateByPrimaryKeySelective(section);
			if (n > 0) {
				return n;
			} else {
				throw new MyException(ExceptionEnum.SECTION_DELETE_FAIL);
			}
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public int deleteSection(String id) throws Exception {
		try {
			int n = sectionMapper.deleteByPrimaryKey(id);
			if (n > 0) {
				return n;
			} else {
				throw new MyException(ExceptionEnum.SECTION_DELETE_FAIL);
			}
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public int updateSection(String id, String name, int level, String parentId, String address, String updateUser)
			throws MyException {
		Date date = new Date();
		Section section = new Section();
		section.setId(id);
		section.setName(name);
		section.setLevel(level);
		section.setParentid(parentId);
		section.setAddress(address);
		section.setUpdateuser(updateUser);
		section.setUpdatetime(date);

		try {
			int n = sectionMapper.updateByPrimaryKeySelective(section);
			if (n > 0) {
				return n;
			} else {
				throw new MyException(ExceptionEnum.SECTION_UPDATE_FAIL);
			}
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public Section searchSection(String id) throws MyException {
		try {
			Section section = sectionMapper.selectByPrimaryKey(id);
			if (section == null || section.getIsdel() == 1) {
				throw new MyException(ExceptionEnum.SECTION_NOT_EXSIT);
			}
			return section;

		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public SectionExtend getSectionByCid(String cid) throws Exception {
		try {
			SectionExtend sectionExtend = sectionExtendMapper.selectByCid(cid);

			if (sectionExtend == null) {
				throw new MyException(ExceptionEnum.SECTION_NOT_EXSIT);
			}

			// 处理时间戳
			// 需要判空
			if (null != sectionExtend.getCreatetime()) {
				String createTimeString = DateStamp.stampToDate(sectionExtend.getCreatetime());
				sectionExtend.setCreateTimeString(createTimeString);
			}
			if (null != sectionExtend.getUpdatetime()) {
				String updateTimeString = DateStamp.stampToDate(sectionExtend.getUpdatetime());
				sectionExtend.setUpdateTimeString(updateTimeString);
			}

			return sectionExtend;
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public List<SectionExtend> getAllChildrenByPid(String pid) throws Exception {
		try {
			List<SectionExtend> datas = sectionExtendMapper.selectAllChildrenByPid(pid);
			if (datas.isEmpty()) {
				throw new MyException(ExceptionEnum.SECTION_NOT_EXSIT);
			}

			// 处理时间戳
			for (SectionExtend sectionExtend : datas) {
				if (null != sectionExtend.getCreatetime()) {
					String createTimeString = DateStamp.stampToDate(sectionExtend.getCreatetime());
					sectionExtend.setCreateTimeString(createTimeString);
				}
				if (null != sectionExtend.getUpdatetime()) {
					String updateTimeString = DateStamp.stampToDate(sectionExtend.getUpdatetime());
					sectionExtend.setUpdateTimeString(updateTimeString);
				}
			}

			return datas;
		} catch (Exception e) {
			throw e;
		}
	}


	@Override
	public List<SectionExtend> getAllChildren() throws Exception {
		try {
			List<SectionExtend> datas = sectionExtendMapper.selectAllChildren();
			if (datas.isEmpty()) {
				throw new MyException(ExceptionEnum.SECTION_NOT_EXSIT);
			}

			// 处理时间戳
			for (SectionExtend sectionExtend : datas) {
				if (null != sectionExtend.getCreatetime()) {
					String createTimeString = DateStamp.stampToDate(sectionExtend.getCreatetime());
					sectionExtend.setCreateTimeString(createTimeString);
				}
				if (null != sectionExtend.getUpdatetime()) {
					String updateTimeString = DateStamp.stampToDate(sectionExtend.getUpdatetime());
					sectionExtend.setUpdateTimeString(updateTimeString);
				}
			}

			return datas;
		} catch (Exception e) {
			throw e;
		}
	}
	
	
	@Override
	public SectionExtend getSectionByPid(String pid) throws Exception {
		try {
			SectionExtend parent = sectionExtendMapper.selectByPid(pid);
			if (parent == null) {
				throw new MyException(ExceptionEnum.SECTION_NOT_EXSIT);
			}

			List<SectionExtend> children = sectionExtendMapper.selectAllChildrenByPid(pid);
			// 处理子版块时间戳
			if (!children.isEmpty()) {
				for (SectionExtend child : children) {
					// 判断时间是否为空
					if (null != child.getCreatetime()) {
						String createTimeString = DateStamp.stampToDate(child.getCreatetime());
						child.setCreateTimeString(createTimeString);
					}
					if (null != child.getUpdatetime()) {
						String updateTimeString = DateStamp.stampToDate(child.getUpdatetime());
						child.setUpdateTimeString(updateTimeString);
					}
				}
			}
			parent.setChildrenSection(children);

			// 处理父版块时间戳
			// 需要判断时间是否为空
			if (null != parent.getCreatetime()) {
				String createTimeString = DateStamp.stampToDate(parent.getCreatetime());
				parent.setCreateTimeString(createTimeString);
			}
			if (null != parent.getUpdatetime()) {
				String updateTimeString = DateStamp.stampToDate(parent.getUpdatetime());
				parent.setUpdateTimeString(updateTimeString);
			}

			return parent;
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public List<SectionExtend> getAllParents() throws Exception {
		try {
			List<SectionExtend> parents = sectionExtendMapper.selectAllParents();
			if (parents.isEmpty()) {
				throw new MyException(ExceptionEnum.SECTION_NOT_EXSIT);
			}

			// 对每个父版块获取其子版块 并处理相应的时间戳
			for (SectionExtend parent : parents) {
				List<SectionExtend> children = sectionExtendMapper.selectAllChildrenByPid(parent.getId());
				// 处理子版块时间戳
				if (!children.isEmpty()) {
					for (SectionExtend child : children) {
						// 判断时间是否为空
						if (null != child.getCreatetime()) {
							String createTimeString = DateStamp.stampToDate(child.getCreatetime());
							child.setCreateTimeString(createTimeString);
						}
						if (null != child.getUpdatetime()) {
							String updateTimeString = DateStamp.stampToDate(child.getUpdatetime());
							child.setUpdateTimeString(updateTimeString);
						}
					}
				}
				parent.setChildrenSection(children);

				// 处理父版块时间戳
				// 需要判断时间是否为空
				if (null != parent.getCreatetime()) {
					String createTimeString = DateStamp.stampToDate(parent.getCreatetime());
					parent.setCreateTimeString(createTimeString);
				}
				if (null != parent.getUpdatetime()) {
					String updateTimeString = DateStamp.stampToDate(parent.getUpdatetime());
					parent.setUpdateTimeString(updateTimeString);
				}

			}

			// 返回全部的父版块集合
			return parents;
		} catch (Exception e) {
			throw e;
		}
	}


}
