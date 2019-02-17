package cn.edu.hdky.library.util;

/**
 * 基本的数据返回类型
 * 所有的controller都返回这个格式
 * 
 * @author 葫芦胡
 *
 */
public class BaseResponse {
	/** 返回的状态码 */
	private Integer code;

	/** 返回的信息 */
	private String msg;

	/** 返回的数据 */
	private Object data;

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
}
