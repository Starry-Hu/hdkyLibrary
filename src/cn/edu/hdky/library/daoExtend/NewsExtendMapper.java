package cn.edu.hdky.library.daoExtend;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.edu.hdky.library.pojo.NewsExtend;

public interface NewsExtendMapper {
	/**
	 * 获取指定id的新闻信息
	 * @param id 新闻id
	 * @return NewsExtend
	 */
	NewsExtend selectById(String id);

	/**
	 * 获取指定版块的全部新闻
	 * @param sectionId 所属版块id
	 * @return NewsExtend集合
	 */
	List<NewsExtend> selectAllBySection(String sectionId);

	/**
	 * 分页获取当前页数指定数目的当前版块新闻
	 * @param startIndex sql语句查询的开始下标
	 * @param pageSize 每页的数目大小
	 * @param sectionId 版块id
	 * @return
	 */
	List<NewsExtend> selectNewsBySectionWithPage(@Param("startIndex") int startIndex, @Param("pageSize") int pageSize,
			@Param("sectionId") String sectionId);

	
	
	// 获取全部新闻
	List<NewsExtend> selectAll();

	// 在全部新闻中 分页获取当前页数指定数目的新闻
	List<NewsExtend> selectNewsWithPage(@Param("startIndex") int startIndex, @Param("pageSize") int pageSize);
}