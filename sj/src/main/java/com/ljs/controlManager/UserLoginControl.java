package com.ljs.controlManager;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ljs.control.BaseControl;
import com.ljs.util.Utility;


/**
 * @Description: 登陆
 * @author 高煜
 * @date 2016-04-23
 */
@Controller
@RequestMapping("/manage/login")
public class UserLoginControl extends BaseControl{
	private Logger logger = Logger.getLogger(UserLoginControl.class);
	
	/**
	 * @Description: 用户登陆
	 * @author 高煜
	 * @date 2016-04-23
	 */
	@RequestMapping(value = "/userLogin", method = { RequestMethod.POST,
			RequestMethod.GET }, produces = "application/json;charset=UTF-8")
	public @ResponseBody String userLogin(String username,String password) {
		
		Subject currentUser = SecurityUtils.getSubject();
		UsernamePasswordToken token = new UsernamePasswordToken(username,password);
		token.setRememberMe(true);
		try
		{
			currentUser.login(token);
		}
		catch (AuthenticationException e)
		{
			return Utility.rtJson(Utility.RETURN_ERROR,e.getMessage(), null);	
		}
		return Utility.rtJson(Utility.RETURN_OK, "登陆成功！", null);	
	}
	
	/**
	 * @Description: 用户退出
	 * @author 高煜
	 * @date 2016-04-23
	 */
	@RequestMapping(value = "/userLogout")
	public ModelAndView userLogout(HttpServletRequest request) {
		Subject subject = SecurityUtils.getSubject();
		if (subject.isAuthenticated())
		{
			subject.logout(); // session会销毁，在SessionListener监听session销毁，清理权限缓存
		}
		return new ModelAndView("redirect:/login.jsp");
	}
}
