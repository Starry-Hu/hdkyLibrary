package cn.edu.hdky.library.service;

import java.util.List;

import cn.edu.hdky.library.bean.Admin;
import cn.edu.hdky.library.pojo.AdminExtend;
import cn.edu.hdky.library.util.PageBean;

public interface AdminService {
	/**
	 * 创建管理员对象
	 * @param adminId 管理员账号
	 * @param adminName 管理员昵称
	 * @param password 密码
	 * @param createUser 创建者
	 * @return -1/1
	 * @throws Exception 
	 */
	int addAdmin(String adminId,String adminName,String password,String createUser) throws Exception;
	
	/**
	 * 删除管理员 逻辑删除
	 * @param id 管理员id  / 非账号
	 * @param updateUser 处理的管理员
	 * @return -1/1
	 * @throws Exception 
	 */
	int deleteAdminLogic(String id,String updateUser) throws Exception;
	
	/**
	 * 更改管理员信息
	 * @param id 管理员id  / 非账号
	 * @param adminName 管理员昵称
	 * @param password 密码
	 * @param updateUser 更新者
	 * @return -1/1
	 * @throws Exception 
	 */
	int updateAdmin(String id,String adminName,String password,String updateUser) throws Exception;
	
	
	/**
	 * 根据管理员账号查找管理员
	 * 一般用于内部方法   不做接口的展示
	 * @param adminId 管理员账号 (假想的主键)
	 * @return Admin对象
	 * @throws Exception
	 */
	Admin searchAdmin(String adminId) throws Exception;
	
	/**
	 * 管理员登录
	 * @param adminId 管理员账号
	 * @param password 密码
	 * @return 登陆成功则返回管理员对象
	 * @throws Exception 
	 */
	Admin adminLogin(String adminId,String password) throws Exception;
	
	/**
	 * 通过id获取管理员的信息
	 * @param id 管理员id
	 * @return AdminExtend对象
	 * @throws Exception
	 */
	AdminExtend getAdminById(String id) throws Exception;
	
	/**
	 * 获取全部的管理员信息
	 * @return 管理员AdminExtend集合
	 * @throws Exception
	 */
	List<AdminExtend> getAllAdmins() throws Exception;
	
	/**
	 * 获取指定页的指定数目的管理员对象集合
	 * @param pageNum 当前页
	 * @param pageSize 每页的大小数目
	 * @return 管理员AdminExtend集合
	 * @throws Exception 
	 */
	PageBean<AdminExtend> getAllAdminWithPage(int pageNum, int pageSize) throws Exception;
	
	
	
	
	
	
	
	
	/**
	 * 为内部使用的方法
	 * 根据管理元账号查找管理员
	 * 无自定义异常的抛出
	 * @param adminId
	 * @return null/admin
	 * @throws Exception 
	 */
	Admin findAdmin(String adminId) throws Exception;
}
