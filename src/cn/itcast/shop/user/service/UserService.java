package cn.itcast.shop.user.service;

import org.springframework.transaction.annotation.Transactional;

import cn.itcast.shop.user.dao.UserDao;
import cn.itcast.shop.user.entry.User;
import cn.itcast.shop.utils.UUIDUtils;

/**
 * 用户模块业务层 
 * @author zhaokun
 *
 */
@Transactional
public class UserService {
  //注入 
	private UserDao userDao;
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
	//按用户名查询用户的方法 
	public User findByUserName(String username){
		 return userDao.findByUsername(username);
	}
	//业务层用户注册代码
	public void save(User user) {
    // TODO Auto-generated method stub
		//将数据存入数据库
		user.setState(0);//未激活 0     1 已激活
		String code = UUIDUtils.getUUID()+UUIDUtils.getUUID();//64位
		user.setCode(code);
		userDao.save(user);
		
		
		
	}
	public User login(User user) {
       
		return userDao.login(user);
	}

}
