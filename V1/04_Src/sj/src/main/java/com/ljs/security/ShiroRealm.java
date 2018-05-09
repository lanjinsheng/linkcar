package com.ljs.security;

import java.util.HashMap;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

import com.ljs.dao.BackgroundUserMapper;


/**
 * 验证登陆方法
 * 高煜
 * 2016-10-21
 */
public class ShiroRealm extends AuthorizingRealm{
	@Autowired
	private BackgroundUserMapper backgroundUserMapper;

	 /** 
     * 为当前登录的Subject授予角色和权限 
     * @see  经测试:本例中该方法的调用时机为需授权资源被访问时 
     * @see  经测试:并且每次访问需授权资源时都会执行该方法中的逻辑,这表明本例中默认并未启用AuthorizationCache 
     * @see  个人感觉若使用了Spring3.1开始提供的ConcurrentMapCache支持,则可灵活决定是否启用AuthorizationCache 
     * @see  比如说这里从数据库获取权限信息时,先去访问Spring3.1提供的缓存,而不使用Shior提供的AuthorizationCache 
     */  
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		String currentUsername = (String)super.getAvailablePrincipal(principals);  
		SimpleAuthorizationInfo simpleAuthorInfo = new SimpleAuthorizationInfo(); 
//		if(null!=currentUsername && "admin".equals(currentUsername)){  
//            //添加一个角色,不是配置意义上的添加,而是证明该用户拥有admin角色  对应配置文件中roles
//            simpleAuthorInfo.addRole("admin");  
//            //添加权限  对应配置文件中perms
//            simpleAuthorInfo.addStringPermission("admin:manage");  
//            System.out.println("已为用户[admin]赋予了[admin]角色和[admin:manage]权限");  
//            return simpleAuthorInfo;  
//        }

		return null;
	}

	 /** 
     * 验证当前登录的Subject 
     * @see  经测试:本例中该方法的调用时机为LoginController.login()方法中执行Subject.login()时 
     */  
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken authcToken) throws AuthenticationException {
		    // 实际上这个authcToken是从LoginController里面currentUser.login(token)传过来的
			// 两个token的引用都是一样的
			UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
			String username = token.getUsername();
			String password = new String(token.getPassword());
			if((null != username && !"".equals(username)) && (null != password && !"".equals(password)))
			{
				Map<String,Object> map = new HashMap<String, Object>();
				map.put("username", username);
				map.put("password", password);
				Map<String,Object> userMap = backgroundUserMapper.findBackgroundUser(map);
				
				if(null != userMap)
				{
					this.setSession("LOGIN_USER", userMap);
					// 此处无需比对,比对的逻辑Shiro会做,我们只需返回一个和令牌相关的正确的验证信息
					// 说白了就是第一个参数填登录用户名,第二个参数填合法的登录密码
					// 这样一来,在随后的登录页面上就只有这里指定的用户和密码才能通过验证
					AuthenticationInfo authcInfo = new SimpleAuthenticationInfo(username, password, this.getName());
					return authcInfo;
				}
				else
				{
					throw new AuthenticationException("用户名或密码不正确！");
				}
			}
			else
			{
				throw new AuthenticationException("用户名或密码不可为空！");
			}
	}

	/**
	 * 将一些数据放到ShiroSession中,以便于其它地方使用
	 * @see 比如Controller,使用时直接用HttpSession.getAttribute(key)就可以取到
	 */
	private void setSession(Object key, Object value)
	{
		Subject currentUser = SecurityUtils.getSubject();
		if (null != currentUser)
		{
			Session session = currentUser.getSession();
			if (null != session)
			{
				session.setAttribute(key, value);
			}
		}
	}
}
