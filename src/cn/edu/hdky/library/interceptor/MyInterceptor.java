package cn.edu.hdky.library.interceptor;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class MyInterceptor implements HandlerInterceptor {

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception exception) throws Exception {
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView arg3)
			throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// 获取请求的url
		// String url = request.getRequestURI();
		// 获取发送请求的html
		String url = request.getHeader("Referer");
		System.out.println(url);
		// 判断url是否是公开 地址（实际使用时将公开 地址配置配置文件中）
		// 这里公开地址是登陆提交的地址
		if (url.indexOf("login") >= 0 || url.indexOf("front") >= 0) {
			// 如果进行登陆提交，放行
			return true;
		}

		// 判断session
		HttpSession session = request.getSession();
		// 从session中取出用户身份信息
		String adminId = (String) session.getAttribute("adminId");
		if (adminId != null) {
			// 身份存在，放行
			return true;
		}
		System.out.println("跳转");
		redirect(request, response);
		return false;
		
		// 执行这里表示用户身份需要认证，跳转登陆页面
		// request.getRequestDispatcher("/Library/manage/login.html").forward(request,response);
		// response.sendRedirect("/Library/manage/login.html");
		// return false表示拦截，不向下执行
		// return true表示放行
	}

	/**
	 * 对于请求是ajax请求重定向问题的处理方法
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void redirect(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// 获取当前请求的路径
		String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
				+ request.getContextPath();
		// 如果request.getHeader("X-Requested-With") 返回的是"XMLHttpRequest"说明就是ajax请求，需要特殊处理
		// 否则直接重定向就可以了
		if ("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))) {
			// 告诉ajax我是重定向
			response.setHeader("REDIRECT", "REDIRECT");
			// 告诉ajax我重定向的路径
			response.setHeader("CONTENTPATH", basePath + "/manage/login.html");
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
		} else {
			response.sendRedirect(basePath + "/manage/login.html");
		}
	}

}
