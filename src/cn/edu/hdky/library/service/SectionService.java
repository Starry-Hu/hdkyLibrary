package cn.edu.hdky.library.service;

import java.util.List;

import cn.edu.hdky.library.bean.Section;
import cn.edu.hdky.library.pojo.SectionExtend;

/**
 * 底部版块的业务逻辑层
 * 
 * @author 葫芦胡
 *
 */
public interface SectionService {
	
	/**
	 * 添加版块
	 * @param name 版块名称
	 * @param level 版块级别
	 * @param parentId 若非2级则存储父级id
	 * @param address 存储地址  如是外部地址则直接链接过去/内部则拼接
	 * @param createUser 创建者管理员
	 * @return -1/1
	 * @throws Exception 
	 */
	int addSection(String name,int level,String parentId,String address,String createUser) throws Exception;
	
	/**
	 * 删除版块 / 逻辑删除
	 * @param id 版块id
	 * @param updateUser 处理管理员
	 * @return -1/1
	 * @throws Exception 
	 */
	int deleteSectionLogic(String id,String updateUser) throws Exception;
	
	/**
	 * 删除版块 / 物理删除
	 * @param id 版块id
	 * @return -1/1
	 * @throws Exception 
	 */
	int deleteSection(String id) throws Exception;
	
	/**
	 * 更新版块
	 * @param id 版块id
	 * @param name 版块名称
	 * @param level 版块等级
	 * @param parentId 版块父级id
	 * @param address 地址
	 * @param updateUser 处理管理员
	 * @return -1/1
	 * @throws Exception 
	 */
	int updateSection(String id,String name,int level,String parentId,String address,String updateUser) throws Exception;
	
	/**
	 * 根据id查找版块
	 * 一般用于内部方法  不用于外部接口的展示
	 * 
	 * @param id 版块id
	 * @return 对应的section对象
	 * @throws Exception
	 */
	Section searchSection(String id) throws Exception;
	
	
	/**
	 * 获取指定子版块的信息
	 * 用于外部接口的展示
	 * @param cid 子版块id
	 * @return 对应的SectionExtend对象
	 * @throws Exception
	 */
	SectionExtend getSectionByCid(String cid)throws Exception;
	
	/**
	 * 获取指定父id下的所有子版块信息
	 * @param pid 父版块id
	 * @return 对象集合
	 * @throws Exception
	 */
	List<SectionExtend> getAllChildrenByPid(String pid) throws Exception;
	
	/**
	 * 获取全部子版块信息
	 * @return 子版块集合
	 * @throws Exception
	 */
	List<SectionExtend> getAllChildren() throws Exception;
	/**
	 * 获取指定父版块信息
	 * @param pid 父版块id
	 * @return 对应的SectionExtend集合
	 * @throws Exception
	 */
	SectionExtend getSectionByPid(String pid) throws Exception;
	
	/**
	 * 获取所有的父版块信息
	 * @return
	 * @throws Exception
	 */
	List<SectionExtend> getAllParents() throws Exception;
}
