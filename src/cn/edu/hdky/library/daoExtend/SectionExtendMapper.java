package cn.edu.hdky.library.daoExtend;

import java.util.List;

import cn.edu.hdky.library.pojo.SectionExtend;

public interface SectionExtendMapper {
	/**
	 * 获取指定子版块id的信息
	 * @param cid 子版块id
	 * @return 子版块
	 */
	SectionExtend selectByCid(String cid);

	/**
	 * 获取指定父版块的全部子版块信息
	 * @param pid 父版块id
	 * @return 父版块下的子版块集合
	 */
	List<SectionExtend> selectAllChildrenByPid(String pid);

	/**
	 * 获取所有的子版块信息
	 * @return 子版块集合
	 */
	List<SectionExtend> selectAllChildren();
	/**
	 * 获取指定父版块id的全部信息
	 * @param pid 指定的父版块id
	 * @return 父版块
	 */
	SectionExtend selectByPid(String pid);

	/**
	 * 获取所有父版块信息
	 * @return 父版块集合
	 */
	List<SectionExtend> selectAllParents();

//	// 分页获取当前页数指定数目的当前版块新闻
//	List<NewsExtend> selectNewsBySectionWithPage(@Param("startIndex") int startIndex, @Param("pageSize") int pageSize,
//			@Param("sectionId") String sectionId);
//
//	// 获取全部新闻
//	List<NewsExtend> selectAll();
//
//	// 在全部新闻中 分页获取当前页数指定数目的新闻
//	List<NewsExtend> selectNewsWithPage(@Param("startIndex") int startIndex, @Param("pageSize") int pageSize);
}