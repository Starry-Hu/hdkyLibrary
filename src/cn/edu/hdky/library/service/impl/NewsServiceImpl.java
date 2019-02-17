package cn.edu.hdky.library.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.edu.hdky.library.bean.News;
import cn.edu.hdky.library.dao.NewsMapper;
import cn.edu.hdky.library.daoExtend.NewsExtendMapper;
import cn.edu.hdky.library.enums.ExceptionEnum;
import cn.edu.hdky.library.exception.MyException;
import cn.edu.hdky.library.pojo.NewsExtend;
import cn.edu.hdky.library.service.NewsService;
import cn.edu.hdky.library.util.DateStamp;
import cn.edu.hdky.library.util.IDGenerator;
import cn.edu.hdky.library.util.PageBean;

@Service("newsService")
public class NewsServiceImpl implements NewsService {

	@Autowired
	private NewsMapper newsMapper;
	@Autowired
	private NewsExtendMapper newsExtendMapper;

	// 添加新闻
	@Override
	public int addNews(String sectionId, String title, String content, String createUser) throws Exception {
		Date date = new Date();

		News news = new News();
		news.setId(IDGenerator.generator());
		news.setSectionid(sectionId);
		news.setTitle(title);
		news.setContent(content);
		news.setCreateuser(createUser);
		news.setCreatetime(date);
		// 默认设置为0  未删除
		news.setIsdel(0);
		
		try {
			int n = newsMapper.insert(news);
			if (n > 0) {
				return n;
			} else {
				throw new MyException(ExceptionEnum.NEWS_ADD_FAIL);
			}
		} catch (Exception e) {
			// e.printStackTrace();
			throw e;
		}

	}

	// 删除新闻 //逻辑删除
	@Override
	public int deleteNewsLogic(String id, String updateUser) throws Exception {
		News News = new News();

		Date date = new Date();
		News.setId(id);
		// 1为删除
		News.setIsdel(1);
		News.setUpdatetime(date);
		News.setUpdateuser(updateUser);

		try {
			int n = newsMapper.updateByPrimaryKeySelective(News);
			if (n > 0) {
				return n;
			} else {
				throw new MyException(ExceptionEnum.NEWS_DELETE_FAIL);
			}
		} catch (Exception e) {
			throw e;
		}
	}

	// 删除新闻 //物理删除
	@Override
	public int deleteNews(String id) throws Exception {
		try {
			int n = newsMapper.deleteByPrimaryKey(id);
			if (n > 0) {
				return n;
			} else {
				throw new MyException(ExceptionEnum.NEWS_DELETE_FAIL);
			}
		} catch (Exception e) {
			throw e;
		}
	}

	// 更新新闻
	@Override
	public int updateNews(String id, String sectionId, String title, String content, String updateUser)
			throws Exception {
		News News = new News();

		Date date = new Date();
		News.setId(id);
		News.setSectionid(sectionId);
		News.setTitle(title);
		News.setContent(content);
		News.setUpdatetime(date);
		News.setUpdateuser(updateUser);

		try {
			int n = newsMapper.updateByPrimaryKeySelective(News);
			if (n > 0) {
				return n;
			}
			throw new MyException(ExceptionEnum.NEWS_UPDATE_FAIL);
		} catch (Exception e) {
			throw e;
		}
	}

	// 查找新闻  内部使用
	@Override
	public News searchNewsById(String id) throws Exception {
		try {
			News news = newsMapper.selectByPrimaryKey(id);
			// null为物理已删除   1为逻辑已删除
			if (news == null || news.getIsdel() == 1) {
				throw new MyException(ExceptionEnum.NEWS_NOT_EXSIT);
			}
			return news;
		} catch (Exception e) {
			throw e;
		}

	}

	// 选择指定id的新闻信息获取   外部接口的调用
	@Override
	public NewsExtend getNewsById(String id) throws Exception {
		try {
			NewsExtend newsExtend = newsExtendMapper.selectById(id);
			if (newsExtend == null) {
				throw new MyException(ExceptionEnum.NEWS_NOT_EXSIT);
			}
			
			// 处理时间戳
			if (newsExtend.getCreatetime() != null) {
				String createTimeString = DateStamp.stampToDate(newsExtend.getCreatetime());
				newsExtend.setCreateTimeString(createTimeString);
			}
			if (newsExtend.getUpdatetime() != null) {
				String updateTimeString = DateStamp.stampToDate(newsExtend.getUpdatetime());
				newsExtend.setUpdateTimeString(updateTimeString);
			}
			
			
			return newsExtend;
		} catch (Exception e) {
			throw e;
		}
	}

	// 当前版块全部新闻信息获取
	@Override
	public List<NewsExtend> getAllNewsBySection(String sectionId) throws Exception {
		try {
			List<NewsExtend> datas = newsExtendMapper.selectAllBySection(sectionId);
			if (datas.isEmpty()) {
				throw new MyException(ExceptionEnum.NEWS_NOT_EXSIT);
			}
			
			// 处理时间戳 
			// 需要判断是否为空
			for (NewsExtend newsExtend : datas) {
				if (newsExtend.getCreatetime() != null) {
					String createTimeString = DateStamp.stampToDate(newsExtend.getCreatetime());
					newsExtend.setCreateTimeString(createTimeString);
				}
				
				if (newsExtend.getUpdatetime() != null) {
					String updateTimeString = DateStamp.stampToDate(newsExtend.getUpdatetime());
					newsExtend.setUpdateTimeString(updateTimeString);
				}
				
			}
			
			
			return datas;
		} catch (Exception e) {
			throw e;
		}
	}

	// 获取指定页数的当前版块的新闻信息
	@Override
	public PageBean<NewsExtend> getNewsBySectionWithPage(int pageNum, int pageSize, String sectionId) throws Exception {
		try {
			// 拿到数据库里的全部数据 有多少拿多少
			List<NewsExtend> allDatas = newsExtendMapper.selectAllBySection(sectionId);
			if (allDatas.isEmpty()) {
				throw new MyException(ExceptionEnum.NEWS_NOT_EXSIT);
			}

			// 获取全部数量  同时建立pagebean对象
			int totalRecord = allDatas.size();
			PageBean<NewsExtend> pBean = new PageBean<>(pageNum, pageSize, totalRecord);

			// 获取pagebean对象的startIndex
			int startIndex = pBean.getStartIndex();

			// 用startIndex和pagesize拿到每页的数据
			// pBean.setList(newsExtendMapper.selectNewsWithPage(startIndex, pageSize));

			// 查找对应的数据
			List<NewsExtend> realDatas = newsExtendMapper.selectNewsBySectionWithPage(startIndex, pageSize, sectionId);
			// 处理时间戳
			for (NewsExtend newsExtend : realDatas) {
				if (newsExtend.getCreatetime() != null) {
					String createTimeString = DateStamp.stampToDate(newsExtend.getCreatetime());
					newsExtend.setCreateTimeString(createTimeString);
				}
				if (newsExtend.getUpdatetime() != null) {
					String updateTimeString = DateStamp.stampToDate(newsExtend.getUpdatetime());
					newsExtend.setUpdateTimeString(updateTimeString);
				}
			}
			
			// 设置list对应的数据对象
			pBean.setList(realDatas);
			return pBean;

		} catch (Exception e) {
			throw e;
		}
	}
}
