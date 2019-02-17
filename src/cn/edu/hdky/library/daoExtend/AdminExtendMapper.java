package cn.edu.hdky.library.daoExtend;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.edu.hdky.library.pojo.AdminExtend;

public interface AdminExtendMapper {
	/**
	 * 获取指定id的管理员信息
	 * @param id 管理员id
	 * @return AdminExtend
	 */
	AdminExtend selectById(String id);

	/**
	 * 获取全部管理员信息
	 * @return AdminExtend集合
	 */
	List<AdminExtend> selectAll();

	/**
	 * 分页获取全部管理员信息的一部分
	 * @param startIndex sql语句查询开始下标
	 * @param pageSize 每页数目的大小
	 * @return AdminExtend集合
	 */
	List<AdminExtend> selectAdminWithPage(@Param("startIndex") int startIndex, @Param("pageSize") int pageSize);
}