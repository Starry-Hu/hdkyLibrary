package cn.edu.hdky.library.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.edu.hdky.library.bean.Admin;
import cn.edu.hdky.library.enums.ResultEnum;
import cn.edu.hdky.library.pojo.AdminExtend;
import cn.edu.hdky.library.service.AdminService;
import cn.edu.hdky.library.util.BaseResponse;
import cn.edu.hdky.library.util.PageBean;

@RestController
@RequestMapping("/admin")
public class AdminController extends BaseController {
	@Autowired
	private AdminService adminService;

	/**
	 * 添加管理员
	 * @param adminId 管理员账号
	 * @param adminName 管理员昵称
	 * @param password1 密码
	 * @param createUser 创建者
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/addAdmin")
	public BaseResponse addAdmin(String adminId, String adminName, String password1,String password2, HttpSession session)
			throws Exception {
		// 信息填写不完整的情况
		if (adminId == null || adminName == null || password1 == null || password2 == null || "".equals(adminId.trim())
				|| "".equals(adminName.trim()) || "".equals(password1.trim()) || "".equals(password2.trim())) {
			return ajaxFail(ResultEnum.ADMIN_INFO_NULL);
		}

		// 检测相同账号的是否已存在
		Admin test = adminService.findAdmin(adminId);
		if (test != null) {
			return ajaxFail(ResultEnum.ADMIN_EXSIT);
		}
		// 两次输入密码不一样
		if (!password1.equals(password2)) {
			return ajaxFail(ResultEnum.ADMIN_NOT_SAME_PSWTWO);
		}
		
		String createUser = (String) session.getAttribute("aid");
		int n = adminService.addAdmin(adminId, adminName, password1, createUser);
		if (n > 0) {
			return ajaxSucc(null, ResultEnum.ADMIN_ADD_SUCCESS);
		}
		return ajaxFail(ResultEnum.ADMIN_ADD_FAIL);
	}
	
	/**
	 * 逻辑删除管理员
	 * @param id 管理员uuid  / 非账号
	 * @param updateUser 更新者
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/deleteAdminLogic")
	public BaseResponse deleteAdminLogic(String id,HttpSession session) throws Exception {
		// 检测id是否填写
		if (id == null || "".equals(id.trim())) {
			return ajaxFail(ResultEnum.ADMIN_ID_NULL);
		}
		
		String updateUser = (String) session.getAttribute("aid");
		int n = adminService.deleteAdminLogic(id, updateUser);
		if (n > 0) {
			return ajaxSucc(null, ResultEnum.ADMIN_DELETE_SUCCESS);
		}
		return ajaxFail(ResultEnum.ADMIN_DELETE_FAIL);
	}
	
	/**
	 * 更新管理员信息
	 * @param id 管理员id / 非账号
	 * @param adminId 管理员账号
	 * @param adminName 管理员名称
	 * @param password1 管理员密码
	 * @param updateUser 更新者
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/updateAdmin")
	public BaseResponse updateAdmin(String id,String adminId, String adminName,String password1,String password2,HttpSession session) throws Exception {
		// 信息填写不完整的情况
		if (id == null || adminName == null || password1 == null || password2 == null || "".equals(id.trim())
				|| "".equals(adminName.trim()) || "".equals(password1.trim()) || "".equals(password2.trim())) {
			return ajaxFail(ResultEnum.ADMIN_INFO_NULL);
		}
		
		// 检测相同账号的是否已存在   使用账号
		// 不存在则自动抛异常
		adminService.searchAdmin(adminId);
		
		String updateUser = (String) session.getAttribute("aid");
		
		// 两次输入密码不一样
		if (!password1.equals(password2)) {
			return ajaxFail(ResultEnum.ADMIN_NOT_SAME_PSWTWO);
		}
		
		// 此时传uuid而非账号
		int n = adminService.updateAdmin(id, adminName, password1, updateUser);
		if (n > 0) {
			return ajaxSucc(null, ResultEnum.ADMIN_UPDATE_SUCCESS);
		}
		return ajaxFail(ResultEnum.ADMIN_UPDATE_FAIL);
	}
	

	
	/**
	 * 管理员登陆
	 * @param adminId 管理员账号
	 * @param password 管理员密码
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/adminLogin")
	public BaseResponse adminLogin(String adminId,String password,HttpSession session) throws Exception {
		// 检测信息是否完整
		if (adminId == null || password == null || "".equals(adminId.trim()) || "".equals(password.trim())) {
			return ajaxFail(ResultEnum.ADMIN_INFO_NULL);
		}
		
		// 设置session过期时间为永久
		session.setMaxInactiveInterval(-1);
				
		Admin admin = adminService.adminLogin(adminId, password);
		if (admin != null) {
			session.setAttribute("aid", admin.getId());
			session.setAttribute("adminId", admin.getAdminid());
			session.setAttribute("adminName",admin.getAdminname());
			return ajaxSucc(null, ResultEnum.LOGIN_SUCCESS);
		} 
		return ajaxFail(ResultEnum.LOGIN_FAIL); 
	}
	
	/**
	 * 获取当前登录的管理员信息
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/getLoginedAdmin")
	public BaseResponse getLoginedAdmin(HttpSession session) throws Exception{
		Map<String, String> resultMap = new HashMap<>();
		
		String id = (String) session.getAttribute("aid");
		String adminName = (String) session.getAttribute("adminName");
		String adminId = (String) session.getAttribute("adminId");
		
		if (adminName != null) {
			resultMap.put("aid", id);
			resultMap.put("adminId", adminId);
			resultMap.put("adminName", adminName);
			return ajaxSucc(resultMap, ResultEnum.SUCCESS);
		}
		return ajaxFail(ResultEnum.FAIL);
	}
	
	
	/**
	 * 退出当前登陆  清除session
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/adminLogout")
	public BaseResponse adminLogout(HttpSession session) throws Exception{
		// 清除session
		//session.invalidate();
		session.removeAttribute("aid");
		session.removeAttribute("adminName");
		session.removeAttribute("adminId");
		return ajaxSucc(null, ResultEnum.SUCCESS);
	}
	
	/**
	 * 修改个人密码 
	 * @param id 管理员id  uuid
	 * @param adminId 管理员账号
	 * @param adminName 管理员名称
	 * @param oldPassword 输入的旧密码
	 * @param newPassword1 第一次输入的新密码
	 * @param newPassword2 第二次输入的新密码
	 * @param updateUser 更新者
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/editMyPassword")
	public BaseResponse editMyPassword(String id,String adminId,String adminName,String oldPassword,
			String newPassword1,String newPassword2,HttpSession session) throws Exception {
		// 检测信息是否完整
		if (adminId == null || newPassword1 == null || newPassword2 == null || "".equals(id.trim()) ||
				"".equals(adminId.trim()) || "".equals(newPassword1.trim())) {
			return ajaxFail(ResultEnum.ADMIN_INFO_NULL);
		}
		
		// 两次输入密码不一样
		if (!newPassword1.equals(newPassword2)) {
			return ajaxFail(ResultEnum.ADMIN_NOT_SAME_PSWTWO);
		}
		
		Admin admin = adminService.searchAdmin(adminId);
		// 原密码错误
		if (!oldPassword.equals( admin.getPassword() )) {
			return ajaxFail(ResultEnum.ADMIN_OLDPSW_ERROR);
		}
		// 新旧密码一致  无变化
		if (oldPassword.equals(newPassword1)) {
			return ajaxFail(ResultEnum.ADMIN_SAME_WITH_OLD);
		}
		
		String updateUser = (String) session.getAttribute("aid");
		int n = adminService.updateAdmin(id, adminName, newPassword1, updateUser);
		if (n > 0) {
			return ajaxSucc(null, ResultEnum.ADMIN_EDITPSW_SUCCESS);
		}
		return ajaxFail(ResultEnum.ADMIN_EDITPSW_FAIL);
	}
	
	
	/**
	 * 根据指定id获取管理员信息
	 * @param id 管理员id
	 * @return 
	 * @throws Exception
	 */
	@GetMapping("/getAdminById")
	public BaseResponse getAdminById(String id) throws Exception {
		// 检测id是否填写
		if (id == null || "".equals(id.trim())) {
			return ajaxFail(ResultEnum.ADMIN_ID_NULL);
		}
		
		AdminExtend adminExtend = adminService.getAdminById(id);
		return ajaxSucc(adminExtend, ResultEnum.ADMIN_SEARCH_SUCCESS);
	}
	
	/**
	 * 获取全部的管理员信息
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/getAllAdmins")
	public BaseResponse getAllAdmins() throws Exception {
		List<AdminExtend> datas = adminService.getAllAdmins();
		
		return ajaxSucc(datas, ResultEnum.ADMIN_SEARCH_SUCCESS);
	}
	

	/**
	 * 获取当前页的指定数目的管理员信息
	 * @param pageNum 当前页
	 * @param pageSize 当前数目
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/getAllAdminWithPage")
	public BaseResponse getAllAdminWithPage(Integer pageNum, Integer pageSize) throws Exception {
		// 处理分页的默认情况
		if (pageNum == null || pageSize == null) {
			pageNum = 1;
			pageSize = 10;
		}
		PageBean<AdminExtend> pageBean = adminService.getAllAdminWithPage(pageNum, pageSize);

		return ajaxSucc(pageBean, ResultEnum.NEWS_SEARCH_SUCCESS);
	}
	
	
	
	/*
	 * 可能用不到的接口
	 */
	
	/**
	 * 根据管理员账号查找对应的管理员
	 * @param adminId 管理员账号
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/searchAdmin")
	public BaseResponse searchAdmin(String adminId) throws Exception {
		// 检测aminId是否填写
		if (adminId == null || "".equals(adminId.trim())) {
			return ajaxFail(ResultEnum.ADMIN_ID_NULL);
		}
		Admin admin = adminService.searchAdmin(adminId);
		
		return ajaxSucc(admin, ResultEnum.ADMIN_SEARCH_SUCCESS);
	}
}
