package cn.edu.hdky.library.enums;

/**
 * 请求接口的枚举信息
 * 
 * @author 葫芦胡
 *
 */
public enum ResultEnum {
	UNKNOW_ERROR(-1, "未知错误"),
    SUCCESS(0, "成功"),
    FAIL(1,"失败"),
    
    // 全部用三位数表示,1开头表示管理员相关
    ADMIN_ADD_SUCCESS(100, "添加成功"),
    ADMIN_ADD_FAIL(101, "添加失败"),
    ADMIN_DELETE_SUCCESS(102, "删除成功"),
    ADMIN_DELETE_FAIL(103, "删除失败"),
    ADMIN_UPDATE_SUCCESS(104, "更新成功"),
    ADMIN_UPDATE_FAIL(105, "更新失败"),
    ADMIN_SEARCH_SUCCESS(106,"查找成功"),
    ADMIN_SEARCH_FAIL(107,"查找失败"),
    ADMIN_INFO_NULL(108, "数据填写不完整"),
    ADMIN_ID_NULL(109,"所需id未填写"),
    ADMIN_EXSIT(110,"管理员已存在"),
    ADMIN_NOT_EXSIT(111,"管理员不存在"),
    
    LOGIN_SUCCESS(115, "登录成功"),
    LOGIN_FAIL(116,"登录失败"),
    
    ADMIN_OLDPSW_ERROR(117,"原密码错误"),
    ADMIN_NOT_SAME_PSWTWO(118,"两次输入密码不一致"),
    ADMIN_SAME_WITH_OLD(119,"密码无变化"),
    ADMIN_EDITPSW_SUCCESS(120,"修改密码成功"),
    ADMIN_EDITPSW_FAIL(121,"修改密码失败"),
    
    // 2开头表示新闻信息相关
    NEWS_ADD_SUCCESS(200, "添加成功"),
    NEWS_ADD_FAIL(201, "添加失败"),
    NEWS_DELETE_SUCCESS(202,"删除成功"),
    NEWS_DELETE_FAIL(203,"删除失败"),
    NEWS_UPDATE_SUCCESS(204,"更新成功"),
    NEWS_UPDATE_FAIL(205,"更新失败"),
    NEWS_SEARCH_SUCCESS(206,"查找成功"),
    NEWS_SEARCH_FAIL(207,"查找失败"),
    NEWS_INFO_NULL(208, "数据填写不完整"),
    NEWS_ID_NULL(209,"所需id未填写"),
    
    // 3开头表示版块相关
    SECTION_ADD_SUCCESS(300, "添加成功"),
    SECTION_ADD_FAIL(301, "添加失败"),
    SECTION_DELETE_SUCCESS(302, "删除成功"),
    SECTION_DELETE_FAIL(303, "删除失败"),
    SECTION_UPDATE_SUCCESS(304, "更新成功"),
    SECTION_UPDATE_FAIL(305, "更新失败"),
    SECTION_SEARCH_SUCCESS(206,"查找成功"),
    SECTION_SEARCH_FAIL(207,"查找失败"),
    SECTION_INFO_NULL(208, "数据填写不完整"),
    SECTION_ID_NULL(209,"id未填写"),
    SECTION_PID_ERROR(210,"所选父版块不正确"),
    
    
    
    ;
    private Integer code;
    private String msg;

    ResultEnum(Integer code, String msg){
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
