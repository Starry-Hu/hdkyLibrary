package cn.edu.hdky.library.service;

import java.util.List;

import cn.edu.hdky.library.bean.News;
import cn.edu.hdky.library.pojo.NewsExtend;
import cn.edu.hdky.library.util.PageBean;

/**
 * 新闻的业务逻辑层
 * 
 * @author 葫芦胡
 *
 */
public interface NewsService {
	
	
	/**
	 * 添加新闻 
	 * @param sectionId 所属版块id
	 * @param title 标题
	 * @param content 内容
	 * @param createUser 创建者
	 * @return -1/1
	 * @throws Exception
	 */
	int addNews(String sectionId,String title,String content,String createUser) throws Exception;
	
	/**
	 * 删除新闻 / 逻辑删除
	 * @param id 新闻id
	 * @param updateUser 更新者
	 * @return -1/1
	 * @throws Exception
	 */
	int deleteNewsLogic(String id,String updateUser) throws Exception;
	
	/**
	 * 删除新闻/ 物理删除
	 * @param id 新闻id
	 * @param updateUser 更新者
	 * @return -1/1
	 * @throws Exception
	 */
	int deleteNews(String id) throws Exception;
	
	/**
	 * 修改新闻
	 * @param id 新闻id
	 * @param sectionId 所属版块id
	 * @param title 标题
	 * @param content 内容
	 * @param updateUser 更新者
	 * @return -1/1
	 * @throws Exception
	 */
	int updateNews(String id,String sectionId, String title, String content,String updateUser) throws Exception;
	
	/**
	 * 查找指定id的新闻
	 * 一般只做内部调用  不做外部接口的展示
	 * 
	 * @param id 新闻id
	 * @return 新闻对象
	 * @throws Exception
	 */
	News searchNewsById(String id) throws Exception;
	
	/**
	 * 返回指定id新闻的信息
	 * @param id 新闻id
	 * @return 新闻对象
	 * @throws Exception
	 */
	NewsExtend getNewsById(String id) throws Exception;

	
	/**
	 * 获取指定版块的全部新闻信息
	 * @param sectionId 版块id
	 * @return 新闻集合
	 * @throws Exception
	 */
	List<NewsExtend> getAllNewsBySection(String sectionId) throws Exception;
	
	/**
	 * 从指定版块里获取当前页的新闻信息
	 * @param pageNum 开始页数
	 * @param pageSize 每页的大小
	 * @param sectionId 版块id
	 * @return pageBean对象
	 * @throws Exception
	 */
	PageBean<NewsExtend> getNewsBySectionWithPage(int pageNum, int pageSize, String sectionId) throws Exception;
	
}
