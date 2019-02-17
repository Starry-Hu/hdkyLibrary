package cn.edu.hdky.library.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;

import cn.edu.hdky.library.enums.ResultEnum;
import cn.edu.hdky.library.exception.MyException;
import cn.edu.hdky.library.util.BaseResponse;

/**
 * 基础Controller
 * 所有的Controller都需要继承该Controller
 * 
 * @author 葫芦胡
 *
 */
public class BaseController {
	
	private static final Logger logger = LoggerFactory.getLogger(BaseController.class);
	public BaseResponse ajaxSucc(Object data, ResultEnum resultEnum){
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setCode(resultEnum.getCode());
        baseResponse.setMsg(resultEnum.getMsg());
        baseResponse.setData(data);
        return  baseResponse;
    }

    public BaseResponse ajaxFail(ResultEnum resultEnum){
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setCode(resultEnum.getCode());
        baseResponse.setMsg(resultEnum.getMsg());
        return  baseResponse;
    }
    
    @ExceptionHandler
    public BaseResponse MyExcHandler(Exception e) {
		logger.error("拦截异常");
		if (e instanceof MyException) {
			BaseResponse baseResponse = new BaseResponse();
			baseResponse.setCode(((MyException) e).getCode());
			// 继承的是exception父类 所以是getMessage
			baseResponse.setMsg(((MyException) e).getMessage());
			// baseResponse.setMsg(((MyException) e).getMsg());
			return baseResponse;
		} else {
			BaseResponse baseResponse = new BaseResponse();
			baseResponse.setCode(ResultEnum.UNKNOW_ERROR.getCode());
			baseResponse.setMsg(e.getMessage());
			// 其他异常
			return baseResponse;
		}
	}
}
