package cn.edu.hdky.library.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


import cn.edu.hdky.library.bean.News;
import cn.edu.hdky.library.enums.ResultEnum;
import cn.edu.hdky.library.pojo.NewsExtend;
import cn.edu.hdky.library.service.NewsService;
import cn.edu.hdky.library.util.BaseResponse;
import cn.edu.hdky.library.util.PageBean;
import cn.edu.hdky.library.util.UploadFile;

@RestController
@RequestMapping("/news")
public class NewsController extends BaseController {
	@Autowired
	private NewsService newsService;

	/**
	 * 添加新闻
	 * 
	 * @param session
	 * @param title 新闻标题
	 * @param content 新闻内容
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/addNews")
	public BaseResponse addNews(String title, String sectionId, String content, HttpSession session) throws Exception {
		// 信息填写不完全
		if (null == title || null == sectionId || null == content || "".equals(title.trim())
				|| "".equals(sectionId.trim()) || "".equals(content.trim())) {
			return ajaxFail(ResultEnum.NEWS_INFO_NULL);
		}

		String createUser = (String) session.getAttribute("aid");
		System.out.println("-------------" + createUser + "---------------");
		int n = newsService.addNews(sectionId, title, content, createUser);

		if (n > 0) {
			return ajaxSucc(null, ResultEnum.NEWS_ADD_SUCCESS);
		}
		return ajaxFail(ResultEnum.NEWS_ADD_FAIL);
	}

	/**
	 * 删除新闻 //逻辑删除
	 * 
	 * @param id 新闻id
	 * @param updateUser 更新者
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/deleteNewsLogic")
	public BaseResponse deleteNewsLogic(String id, HttpSession session) throws Exception {
		// id未填写
		if (null == id || "".equals(id.trim())) {
			return ajaxFail(ResultEnum.NEWS_ID_NULL);
		}

		newsService.searchNewsById(id);

		String updateUser = (String) session.getAttribute("aid");
		int n = newsService.deleteNewsLogic(id, updateUser);

		if (n > 0) {
			return ajaxSucc(null, ResultEnum.NEWS_DELETE_SUCCESS);
		}
		return ajaxFail(ResultEnum.NEWS_DELETE_FAIL);
	}

	/**
	 * 删除新闻 // 物理删除
	 * 
	 * @param id 新闻id
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/deleteNews")
	public BaseResponse deleteNews(String id) throws Exception {
		// id未填写
		if (null == id || "".equals(id.trim())) {
			return ajaxFail(ResultEnum.NEWS_ID_NULL);
		}

		newsService.searchNewsById(id);

		int n = newsService.deleteNews(id);

		if (n > 0) {
			return ajaxSucc(null, ResultEnum.NEWS_DELETE_SUCCESS);
		}
		return ajaxFail(ResultEnum.NEWS_DELETE_FAIL);
	}

	/**
	 * 更新新闻
	 * 
	 * @param id 新闻id
	 * @param title 新闻标题
	 * @param content 新闻内容
	 * @param session 
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/updateNews")
	public BaseResponse updateNews(String id, String sectionId, String title, String content, HttpSession session)
			throws Exception {
		// 信息填写不完全
		if (null == id || null == title || null == sectionId || null == content || "".equals(id.trim())
				|| "".equals(title.trim()) || "".equals(sectionId.trim()) || "".equals(content.trim())) {
			return ajaxFail(ResultEnum.NEWS_INFO_NULL);
		}
		newsService.searchNewsById(id);
		
		System.out.println(session);
		String updateUser = (String) session.getAttribute("aid");
		int n = newsService.updateNews(id, sectionId, title, content, updateUser);

		if (n > 0) {
			return ajaxSucc(null, ResultEnum.NEWS_UPDATE_SUCCESS);
		}
		return ajaxFail(ResultEnum.NEWS_UPDATE_FAIL);
	}



	/**
	 * 获取指定id的所需的新闻信息
	 * 
	 * @param id 新闻id
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/getNewsById")
	public BaseResponse getNewsById(String id) throws Exception {
		// id未填写
		if (null == id || "".equals(id.trim())) {
			return ajaxFail(ResultEnum.NEWS_ID_NULL);
		}
		News data = newsService.getNewsById(id);

		return ajaxSucc(data, ResultEnum.NEWS_SEARCH_SUCCESS);
	}

	/**
	 * 获取指定版块全部新闻信息
	 * 
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/getAllNewsBySection")
	public BaseResponse getAllNewsBySection(String sectionId) throws Exception {
		// id未填写
		if (null == sectionId || "".equals(sectionId.trim())) {
			return ajaxFail(ResultEnum.NEWS_ID_NULL);
		}
		List<NewsExtend> data = newsService.getAllNewsBySection(sectionId);

		return ajaxSucc(data, ResultEnum.NEWS_SEARCH_SUCCESS);
	}

	/**
	 * 获取指定页的全部新闻信息
	 * 
	 * @param pageNum 当前页数
	 * @param pageSize 每页条数
	 * @param sectionId 所属版块id
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/getNewsBySectionWithPage")
	public BaseResponse getNewsBySectionWithPage(Integer pageNum, Integer pageSize, String sectionId) throws Exception {
		// id未填写
		if (null == sectionId || "".equals(sectionId.trim())) {
			return ajaxFail(ResultEnum.NEWS_ID_NULL);
		}
		// 处理分页的默认情况
		if (pageNum == null || pageSize == null) {
			pageNum = 1;
			pageSize = 10;
		}
		PageBean<NewsExtend> pageBean = newsService.getNewsBySectionWithPage(pageNum, pageSize, sectionId);

		return ajaxSucc(pageBean, ResultEnum.NEWS_SEARCH_SUCCESS);
	}
	

	
	
	/**
	 * 上传图片的接口
	 * 使用WangEditor
	 * @param image 图片
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/uploadImg")
	public Map<String, Object> uploadPicture(@Param("image") MultipartFile image) throws Exception{
		Map<String, Object> result_map = new HashMap<String, Object>();
		Integer errno = 0;
		List<String> urls = new ArrayList<String>();
		if (image == null) {
			errno = 1;
		}
		// 图片存储的名称和地址
		String picName = UploadFile.uploadFile(image, "img");
		// 在服务器里另开辟一个文件夹存储图片
		urls.add("\\upload\\" + picName);
		// 0为成功
		result_map.put("errno", errno);
		// 返回图片存储的现地址
		result_map.put("data", urls);
		return result_map;
	}
	
	
	
	/**
	 * 上传附件的接口
	 * @param attachment 附件
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/uploadAttachment")
	public Map<String, Object> uploadAttachment(@Param("attachment") MultipartFile attachment) throws Exception{
		Map<String, Object> result_map = new HashMap<String, Object>();
		Integer errno = 0;
		List<String> urls = new ArrayList<String>();
		if (attachment == null) {
			errno = 1;
		}
		// 附件存储的名称和地址
		String attachmentName = UploadFile.uploadFile(attachment, "attachment");
		// 在服务器里另开辟一个文件夹存储附件
		urls.add("\\upload\\" + attachmentName);
		// 0为成功
		result_map.put("errno", errno);
		// 返回图片存储的现地址
		result_map.put("data", urls);
		return result_map;
	}

	
	
	
	
	
	
	/*
	 * 可能用不到的接口
	 */
	
	/**
	 * 查找指定id的新闻
	 * 
	 * @param id 新闻id
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/searchNews")
	public BaseResponse searchNewsById(String id) throws Exception {
		// id未填写
		if (null == id || "".equals(id.trim())) {
			return ajaxFail(ResultEnum.NEWS_ID_NULL);
		}
		News data = newsService.searchNewsById(id);

		return ajaxSucc(data, ResultEnum.NEWS_SEARCH_SUCCESS);
	}

}
