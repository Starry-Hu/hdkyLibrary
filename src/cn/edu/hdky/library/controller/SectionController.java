package cn.edu.hdky.library.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.edu.hdky.library.bean.Section;
import cn.edu.hdky.library.enums.ResultEnum;
import cn.edu.hdky.library.pojo.SectionExtend;
import cn.edu.hdky.library.service.SectionService;
import cn.edu.hdky.library.util.BaseResponse;

@RestController
@RequestMapping("/section")
public class SectionController extends BaseController {
	@Autowired
	private SectionService sectionService;

	/**
	 * 添加版块
	 * 
	 * @param name 名称
	 * @param level 等级
	 * @param parentId 二级栏目的父版块id
	 * @param address 链接的地址
	 * @param createUser
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/addSection")
	public BaseResponse addSection(String name, Integer level, String parentId, String address, HttpSession session )
			throws Exception {
		// 判断信息是否填写完整
		if (null == name || null == level || null == address || "".equals(name.trim()) || "".equals(address.trim())) {
			return ajaxFail(ResultEnum.SECTION_INFO_NULL);
		}

		// 控制一二级栏目 level的取值默认为1
		if (level != 1 && level != 2) {
			level = 1;
		}

		// 若是二级栏目 不可缺少父栏目id
		if (level == 2) {
			if (null == parentId || "".equals(parentId.trim())) {
				return ajaxFail(ResultEnum.SECTION_INFO_NULL);
			}
			// 判断所选父版块不存在的情况
			sectionService.getSectionByPid(parentId);
			
		}
		
		String createUser = (String) session.getAttribute("aid");
		int n = sectionService.addSection(name, level, parentId, address, createUser);

		if (n > 0) {
			return ajaxSucc(null, ResultEnum.NEWS_ADD_SUCCESS);
		}
		return ajaxFail(ResultEnum.NEWS_ADD_FAIL);
	}

	/**
	 * 逻辑删除版块
	 * 
	 * @param id 版块id
	 * @param updateUser
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/deleteSectionLogic")
	public BaseResponse deleteSectionLogic(String id,HttpSession session) throws Exception {
		// id必填写
		if (null == id || "".equals(id.trim())) {
			return ajaxFail(ResultEnum.SECTION_ID_NULL);
		}
		
		sectionService.searchSection(id);

		String updateUser = (String) session.getAttribute("aid");
		int n = sectionService.deleteSectionLogic(id,updateUser);

		if (n > 0) {
			return ajaxSucc(null, ResultEnum.SECTION_DELETE_SUCCESS);
		}
		return ajaxFail(ResultEnum.SECTION_DELETE_FAIL);
	}

	/**
	 * 物理删除版块
	 * 
	 * @param id 版跨id
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/deleteSection")
	public BaseResponse deleteSection(String id) throws Exception {
		// id必填写
		if (null == id || "".equals(id.trim())) {
			return ajaxFail(ResultEnum.SECTION_ID_NULL);
		}
		sectionService.searchSection(id);

		
		int n = sectionService.deleteSection(id);

		if (n > 0) {
			return ajaxSucc(null, ResultEnum.SECTION_DELETE_SUCCESS);
		}
		return ajaxFail(ResultEnum.SECTION_DELETE_FAIL);
	}

	/**
	 * 更新版块
	 * 
	 * @param id 版块id
	 * @param name 版块名称
	 * @param level 等级
	 * @param parentId 二级板块的父版块id
	 * @param address 链接的地址
	 * @param updateUser
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/updateSection")
	public BaseResponse updateSection(String id, String name, Integer level, String parentId, String address,
			HttpSession session) throws Exception {
		// 判断信息是否填写完整
		if (null == id || null == name || null == level || null == address ||
				"".equals(id.trim()) || "".equals(name.trim()) || "".equals(address.trim())) {
			return ajaxFail(ResultEnum.SECTION_INFO_NULL);
		}

		// 控制一二级栏目 level的取值默认为1
		if (level != 1 && level != 2) {
			level = 1;
		}

		// 若是二级栏目 不可缺少父栏目id
		if (level == 2) {
			if (null == parentId || "".equals(parentId.trim())) {
				return ajaxFail(ResultEnum.SECTION_INFO_NULL);
			}
			// 所选父版块不存在
			if (null == sectionService.getSectionByPid(parentId)) {
				return ajaxFail(ResultEnum.SECTION_PID_ERROR);
			}
		}
		
		sectionService.searchSection(id);
		
		String updateUser = (String) session.getAttribute("aid");
		int n = sectionService.updateSection(id, name, level, parentId, address, updateUser);

		if (n > 0) {
			return ajaxSucc(null, ResultEnum.SECTION_UPDATE_SUCCESS);
		}
		return ajaxFail(ResultEnum.SECTION_UPDATE_FAIL);
	}

	

	/**
	 * 获取指定id的子版块信息
	 * 
	 * @param cid 子版块id
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/getSectionByCid")
	public BaseResponse getSectionByCid(String cid) throws Exception {
		// id必填写
		if (null == cid || "".equals(cid.trim())) {
			return ajaxFail(ResultEnum.SECTION_ID_NULL);
		}
		SectionExtend data = sectionService.getSectionByCid(cid);

		return ajaxSucc(data, ResultEnum.SECTION_SEARCH_SUCCESS);
	}

	/**
	 * 获取指定父id的全部子版块信息
	 * 
	 * @param pid 父版块id
	 * @return 子版块集合
	 * @throws Exception
	 */
	@GetMapping("/getAllChildrenByPid")
	public BaseResponse getAllChildrenByPid(String pid) throws Exception {
		// id必填写
		if (null == pid || "".equals(pid.trim())) {
			return ajaxFail(ResultEnum.SECTION_ID_NULL);
		}
		List<SectionExtend> datas = sectionService.getAllChildrenByPid(pid);

		return ajaxSucc(datas, ResultEnum.SECTION_SEARCH_SUCCESS);
	}
	
	/**
	 * 获取所有的二级版块
	 * @return 二级板块（子版块）集合
	 * @throws Exception
	 */
	@GetMapping("/getAllChildren")
	public BaseResponse getAllChildren() throws Exception{
		List<SectionExtend> datas = sectionService.getAllChildren();
		
		return ajaxSucc(datas, ResultEnum.SECTION_SEARCH_SUCCESS);
	}
	
	/**
	 * 获取指定id的父版块信息
	 * 
	 * @param pid 指定的父版块id
	 * @return 父版块对象
	 * @throws Exception
	 */
	@GetMapping("/getSectionByPid")
	public BaseResponse getSectionByPid(String pid) throws Exception {
		// id必填写
		if (null == pid || "".equals(pid.trim())) {
			return ajaxFail(ResultEnum.SECTION_ID_NULL);
		}
		SectionExtend data = sectionService.getSectionByPid(pid);

		return ajaxSucc(data, ResultEnum.SECTION_SEARCH_SUCCESS);
	}

	/**
	 * 获取全部的父版块信息
	 * 
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/getAllParents")
	public BaseResponse getAllParents() throws Exception {
		List<SectionExtend> datas = sectionService.getAllParents();

		return ajaxSucc(datas, ResultEnum.SECTION_SEARCH_SUCCESS);
	}
	
	
	
	
	
	
	/*
	 * 可能用不到的接口
	 */
	/**
	 * 根据id查找版块
	 * 
	 * @param id 版块id
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/searchSectionById")
	public BaseResponse searchSectionById(String id) throws Exception {
		// id必填写
		if (null == id || "".equals(id.trim())) {
			return ajaxFail(ResultEnum.SECTION_ID_NULL);
		}
		Section data = sectionService.searchSection(id);

		return ajaxSucc(data, ResultEnum.NEWS_SEARCH_SUCCESS);
	}

}
