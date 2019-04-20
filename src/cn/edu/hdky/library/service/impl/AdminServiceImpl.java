package cn.edu.hdky.library.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.edu.hdky.library.bean.Admin;
import cn.edu.hdky.library.bean.AdminExample;
import cn.edu.hdky.library.bean.AdminExample.Criteria;
import cn.edu.hdky.library.dao.AdminMapper;
import cn.edu.hdky.library.daoExtend.AdminExtendMapper;
import cn.edu.hdky.library.enums.ExceptionEnum;
import cn.edu.hdky.library.exception.MyException;
import cn.edu.hdky.library.pojo.AdminExtend;
import cn.edu.hdky.library.service.AdminService;
import cn.edu.hdky.library.util.DateStamp;
import cn.edu.hdky.library.util.IDGenerator;
import cn.edu.hdky.library.util.MD5Util;
import cn.edu.hdky.library.util.PageBean;

@Service("adminService")
public class AdminServiceImpl implements AdminService {
	@Autowired
	private AdminMapper adminMapper;
	@Autowired
	private AdminExtendMapper adminExtendMapper;

	@Override
	public int addAdmin(String adminId, String adminName, String password, String createUser) throws Exception {
		Admin admin = new Admin();
		Date date = new Date();
		admin.setId(IDGenerator.generator());
		admin.setAdminid(adminId);
		admin.setAdminname(adminName);
		admin.setPassword(MD5Util.getMD5String(password));
		admin.setCreatetime(date);
		admin.setCreateuser(createUser);
		// 默认为0 未删除
		admin.setIsdel(0);

		try {
			int n = adminMapper.insert(admin);
			if (n > 0) {
				return n;
			}
			throw new MyException(ExceptionEnum.ADMIN_ADD_FAIL);
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public int deleteAdminLogic(String id, String updateUser) throws Exception {
		Admin admin = new Admin();
		Date date = new Date();
		admin.setId(id);
		admin.setUpdateuser(updateUser);
		admin.setUpdatetime(date);
		// 设置1 为已删除
		admin.setIsdel(1);

		try {
			int n = adminMapper.updateByPrimaryKeySelective(admin);
			if (n > 0) {
				return n;
			}
			throw new MyException(ExceptionEnum.ADMIN_DELETE_FAIL);
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public int updateAdmin(String id, String adminName, String password, String updateUser) throws Exception {
		Admin admin = new Admin();
		Date date = new Date();
		admin.setId(id);
		admin.setAdminname(adminName);
		admin.setPassword(MD5Util.getMD5String(password));
		admin.setUpdateuser(updateUser);
		admin.setUpdatetime(date);

		try {
			int n = adminMapper.updateByPrimaryKeySelective(admin);
			if (n > 0) {
				return n;
			}
			throw new MyException(ExceptionEnum.ADMIN_DELETE_FAIL);
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public Admin searchAdmin(String adminId) throws Exception {
		try {
			AdminExample example = new AdminExample();
			Criteria criteria = example.createCriteria();
			criteria.andAdminidEqualTo(adminId);
			criteria.andIsdelEqualTo(0);
			
			List<Admin> datas = adminMapper.selectByExample(example);
			// 查出来整个数据是空的  管理员不存在
			if (datas.isEmpty()) {
				throw new MyException(ExceptionEnum.ADMIN_NOT_EXSIT);
			}
			
			// 若不空  取第一条信息判断
			Admin admin = datas.get(0);
			if (admin != null) {
				return admin;
			}
			throw new MyException(ExceptionEnum.ADMIN_NOT_EXSIT);
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public Admin adminLogin(String adminId, String password) throws Exception {
		try {
			AdminExample example = new AdminExample();
			example.createCriteria().andAdminidEqualTo(adminId).andIsdelEqualTo(0);
			
			List<Admin> datas = adminMapper.selectByExample(example);
			// 查出来整个数据是空的  管理员不存在
			if (datas.isEmpty()) {
				throw new MyException(ExceptionEnum.ADMIN_NOT_EXSIT);
			}
			
			// 若不空  取第一条信息判断
			Admin admin = datas.get(0);
			// 管理员不存在
			if (admin == null) {
				throw new MyException(ExceptionEnum.ADMIN_NOT_EXSIT);
			}
			
			// 登录密码错误
			if (!admin.getPassword().equals(MD5Util.getMD5String(password))) {
				throw new MyException(ExceptionEnum.LOGIN_PSW_ERROR);
			}
			
			// 登录成功则返回管理员对象
			return admin;
		} catch (Exception e) {
			throw e;
		}
	}


	@Override
	public AdminExtend getAdminById(String id) throws Exception {
		try {
			AdminExtend adminExtend = adminExtendMapper.selectById(id);
			if (adminExtend == null) {
				throw new MyException(ExceptionEnum.ADMIN_NOT_EXSIT);
			}
			
			// 处理时间戳
			if (adminExtend.getCreatetime() != null) {
				String createTimeString = DateStamp.stampToDate(adminExtend.getCreatetime());
				adminExtend.setCreateTimeString(createTimeString);
			}
			if (adminExtend.getUpdatetime() != null) {
				String updateTimeString = DateStamp.stampToDate(adminExtend.getUpdatetime());
				adminExtend.setUpdateTimeString(updateTimeString);
			}
			
			return adminExtend;
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public List<AdminExtend> getAllAdmins() throws Exception {
		try {
			List<AdminExtend> datas = adminExtendMapper.selectAll();
			if (datas.isEmpty()) {
				throw new MyException(ExceptionEnum.ADMIN_NOT_EXSIT);
			}
			
			for (AdminExtend adminExtend : datas) {
				// 处理时间戳
				if (adminExtend.getCreatetime() != null) {
					String createTimeString = DateStamp.stampToDate(adminExtend.getCreatetime());
					adminExtend.setCreateTimeString(createTimeString);
				}
				if (adminExtend.getUpdatetime() != null) {
					String updateTimeString = DateStamp.stampToDate(adminExtend.getUpdatetime());
					adminExtend.setUpdateTimeString(updateTimeString);
				}
			}
			
			return datas;
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public PageBean<AdminExtend> getAllAdminWithPage(int pageNum, int pageSize) throws Exception {
		try {
			// 拿到数据库里的全部数据 有多少拿多少
			List<AdminExtend> allDatas = adminExtendMapper.selectAll();
			if (allDatas.isEmpty()) {
				throw new MyException(ExceptionEnum.NEWS_NOT_EXSIT);
			}

			// 获取全部数量 同时建立pagebean对象
			int totalRecord = allDatas.size();
			PageBean<AdminExtend> pBean = new PageBean<>(pageNum, pageSize, totalRecord);

			// 获取pagebean对象的startIndex
			int startIndex = pBean.getStartIndex();

			// 用startIndex和pagesize拿到每页的数据
			// pBean.setList(newsExtendMapper.selectNewsWithPage(startIndex, pageSize));
			pageSize = pBean.getCurPageSize();
			// 查找对应的数据
			List<AdminExtend> realDatas = adminExtendMapper.selectAdminWithPage(startIndex, pageSize);
			System.out.println(pageSize + "");
			// 处理时间戳
			for (AdminExtend adminExtend : realDatas) {
				if (adminExtend.getCreatetime() != null) {
					String createTimeString = DateStamp.stampToDate(adminExtend.getCreatetime());
					adminExtend.setCreateTimeString(createTimeString);
				}
				if (adminExtend.getUpdatetime() != null) {
					String updateTimeString = DateStamp.stampToDate(adminExtend.getUpdatetime());
					adminExtend.setUpdateTimeString(updateTimeString);
				}
			}

			// 设置list对应的数据对象
			pBean.setList(realDatas);
			return pBean;

		} catch (Exception e) {
			throw e;
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	////// 内部使用的方法    无自定义异常的抛出//////
	@Override
	public Admin findAdmin(String adminId) throws Exception{
		try {
			AdminExample example = new AdminExample();
			Criteria criteria = example.createCriteria();
			criteria.andAdminnameEqualTo(adminId);
			
			List<Admin> datas = adminMapper.selectByExample(example);
			// 查出来整个数据是空的  管理员不存在
			if (datas.isEmpty()) {
				return null;
			}
			
			// 若不空  取第一条信息判断
			Admin admin = datas.get(0);
			if (admin != null && admin.getIsdel() != 1) {
				return admin;
			}
			return null;
		
		} catch (Exception e) {
			throw e;
		}
	}


}
