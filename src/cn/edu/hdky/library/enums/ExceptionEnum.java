package cn.edu.hdky.library.enums;

/**
 * 异常信息的枚举接口
 * 
 * @author 葫芦胡
 *
 */
public enum ExceptionEnum {
	UNKNOW_ERROR(-1, "未知错误"),

	ADMIN_ADD_FAIL(120,"管理员添加失败"),
	ADMIN_DELETE_FAIL(121,"管理员删除失败"),
	ADMIN_UPDATE_FAIL(122,"管理员更新失败"),
	ADMIN_SEARCH_FAIL(123, "管理员查找失败"),
	ADMIN_NOT_EXSIT(124, "管理员不存在"),
	LOGIN_PSW_ERROR(125,"账户或密码错误"),
	
    
    NEWS_ADD_FAIL(220,"新闻添加失败"),
    NEWS_DELETE_FAIL(221,"新闻删除失败"),
    NEWS_UPDATE_FAIL(222,"新闻更新失败"),
    NEWS_SEARCH_FAIL(223, "新闻查找失败"),
    NEWS_NOT_EXSIT(224, "新闻不存在"),
    
    SECTION_ADD_FAIL(320,"项目添加失败"),
    SECTION_DELETE_FAIL(321,"项目删除失败"),
    SECTION_UPDATE_FAIL(322,"项目更新失败"),
    SECTION_SEARCH_FAIL(323, "项目查找失败"),
    SECTION_NOT_EXSIT(324, "项目不存在"),
    ;
	
	
	
	
    private Integer code;

    private String msg;

    ExceptionEnum(Integer code, String msg){
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
