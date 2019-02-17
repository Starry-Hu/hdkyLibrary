package cn.edu.hdky.library.exception;

import cn.edu.hdky.library.enums.ExceptionEnum;
import cn.edu.hdky.library.enums.ResultEnum;

public class MyException extends Exception {

	
	private Integer code;
    private String msg;

    // 实际取出信息应该用getMessage()
    public MyException(ExceptionEnum exceptionEnum){
        super(exceptionEnum.getMsg());
        this.code = exceptionEnum.getCode();
    }

    public MyException(ResultEnum resultEnum){
        super(resultEnum.getMsg());
        this.code = resultEnum.getCode();
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

}
