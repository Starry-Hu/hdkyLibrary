package cn.edu.hdky.library.util;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.web.multipart.MultipartFile;

public class UploadFile {

	/**
	 * 建立图片/附件文件夹
	 * @param path
	 * @return
	 */
	public static String createFileDir(String path) {
		// linux
		// String dirName = File.separator+"usr"+File.separator+"develop" +
		// File.separator + "lcn" +File.separator + "uploads" + File.separator+ path +
		// File.separator;

		// windows
		String dirName = "C:" + File.separator + "hdkyUpload" + File.separator + path + File.separator;

		File file = new File(dirName);
		if (!file.exists()) {
			file.mkdirs();
		}
		return dirName;
	}
	
//	/**
//	 * 建立附件文件夹
//	 * @param path
//	 * @return
//	 */
//	public static String createAttachDir(String path) {
//		// linux
//		// String dirName = File.separator+"usr"+File.separator+"develop" +
//		// File.separator + "lcn" +File.separator + "uploads" + File.separator+ path +
//		// File.separator;
//
//		// windows
//		String dirName = "C:" + File.separator + "hdkyAttach" + File.separator + path + File.separator;
//
//		File file = new File(dirName);
//		if (!file.exists()) {
//			file.mkdirs();
//		}
//		return dirName;
//	}

	/**
	 * 上传文件
	 * @param file 文件
	 * @param path 原地址
	 * @return
	 */
	public static String uploadFile(MultipartFile file, String path) {
		if (null == file) {
			return "";
		}
		// 原始名称
		String originalFilename = file.getOriginalFilename();

		String picPath = "";
		String newFileName = "";
		// 上传图片
		if (file != null && originalFilename != null && originalFilename.length() > 0) {
			// 存储图片的路径
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			String dateStr = format.format(new Date());
			String[] dirs = dateStr.split("-");
			path = path + File.separator;
			for (int i = 0; i < dirs.length; i++) {
				path += dirs[i] + File.separator;
			}
			picPath = createFileDir(path);
			// 新的图片名称
			newFileName = IDGenerator.generator() + originalFilename.substring(originalFilename.lastIndexOf("."));
			// 新图片
			File newFile = new File(picPath + newFileName);
			// 将内存中的数据写入磁盘
			try {
				file.transferTo(newFile);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return path + newFileName;
	}
	
//	/**
//	 * 上传附件
//	 * @param file 文件
//	 * @param path 原地址
//	 * @return
//	 */
//	public static String uploadAttach(MultipartFile file, String path) {
//		if (null == file) {
//			return "";
//		}
//		// 原始名称
//		String originalFilename = file.getOriginalFilename();
//
//		String attachPath = "";
//		String newFileName = "";
//		// 上传附件
//		if (file != null && originalFilename != null && originalFilename.length() > 0) {
//			// 存储附件的路径
//			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//			String dateStr = format.format(new Date());
//			String[] dirs = dateStr.split("-");
//			path = path + File.separator;
//			for (int i = 0; i < dirs.length; i++) {
//				path += dirs[i] + File.separator;
//			}
//			attachPath = createAttachDir(path);
//			// 新的附件名称
//			newFileName = IDGenerator.generator() + originalFilename.substring(originalFilename.lastIndexOf("."));
//			// 新附件
//			File newFile = new File(attachPath + newFileName);
//			// 将内存中的数据写入磁盘
//			try {
//				file.transferTo(newFile);
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
//		return path + newFileName;
//	}
}
