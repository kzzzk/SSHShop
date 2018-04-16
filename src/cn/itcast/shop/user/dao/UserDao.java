package cn.itcast.shop.user.dao;

import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import cn.itcast.shop.user.entry.User;

/**
 * 用户模块持久层
 * @author zhaokun
 *
 */
public class UserDao extends HibernateDaoSupport {
   //按名字查询是否有该用户
	public User findByUsername(String username){
		String hql = "from User where username=?";
		//System.out.println("+++++"+username);
		List<User> list = this.getHibernateTemplate().find(hql, username);
		if(list!=null && list.size()>0){
			return list.get(0);
		}
		return null;
	}

	public void save(User user) {
		// TODO Auto-generated method stub
		this.getHibernateTemplate().save(user);
	}

	public User login(User user) {
      String hql = "from User where username =? and password =? and state =?";
      List<User> list = this.getHibernateTemplate().find(hql,user.getUsername(),user.getPassword(),1);
		if(list!=null && list.size()>0){
			return list.get(0);
		  }
      return null;
	}
}
