package cn.edu.hdky.library.util;

import java.util.UUID;

import cn.edu.hdky.library.bean.News;

public class IDGenerator {

	public static String generator() {
		return UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();
	}

}
