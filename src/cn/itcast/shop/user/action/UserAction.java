package cn.itcast.shop.user.action;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import cn.itcast.shop.user.entry.User;
import cn.itcast.shop.user.service.UserService;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
/**
 * 用户模块的注册
 * @author zhaokun
 *  模型驱动 请求封装 
 */
public class UserAction extends ActionSupport implements ModelDriven<User>{
   private User user = new User();
	//注入UserService；
   private UserService userService;
   public void setUserService(UserService userService) {
	this.userService = userService;
}
	public User getModel() {
		return user;
	}
	/**
	 * 验证码值获取
	 */
	private String checkCode;
	public void setCheckCode(String checkCode) {
		this.checkCode = checkCode;
	}
	
	/**
	 * 返回用户模块方法
	 * @throws IOException 
	 */
	public String registPage() {
		
		
		return "registPage";
	}
	/**
	 * ajax 进行异步校验用户名的执行方法
	 * 
	 * @return
	 * @throws IOException 
	 */
	public String findByName() throws IOException{
		//调用service对象进行查询
		User existUser = userService.findByUserName(user.getUsername());
		//获得response对象 向页面输出
		HttpServletResponse response = ServletActionContext.getResponse();
		//设计编码
		response.setContentType("text/html;charset=UTF-8");
		if(existUser!=null){
			//查询到用户：用户名已存在
			response.getWriter().println("<font color='red'>用户名已经存在</font>");
		}else {//用户名可以使用
			response.getWriter().println("<font color='green'>用户名可以使用</font>");

		}
		return NONE;
	}
	/**
	 * 用户注册的方法
	 *
	 */
	public String registe(){
    String strCheckCode = ServletActionContext.getRequest().getSession().getAttribute("checkcode").toString();
	if(!strCheckCode.equalsIgnoreCase(checkCode)){
		this.addActionError("验证码不正确");
		//跳转
		return "checkCodeFail";
	}	
	
    userService.save(user);
    
   		
		return NONE;
	}
	/**
	 * 跳转到登录页面 
	 * 
	 */
	public String loginPage(){
		
		return "loginPage";
	}
	
	/**
	 * 用户登录
	 */
	public String login(){
		
		User existUser = userService.login(user);
		if(existUser==null){
			//登录失败
			this.addActionError("用户名或密码错误或用户未激活");
			return "login";
		}else{
			//登录失败
			//将用户的信息存到session中 
			ServletActionContext.getRequest().getSession().setAttribute("existUser", existUser);
			return "loginSuccess";
		}
		
		
	}
	/**
	 * 用户退出的方法  销毁session
	 */
	
	public String quit(){
		//销毁session
		ServletActionContext.getRequest().getSession().invalidate();
		//跳转到主页
		return "quit";
	}
}
