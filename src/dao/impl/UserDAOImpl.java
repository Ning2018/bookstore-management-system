package dao.impl;

import bookstore.dao.UserDAO;
import bookstore.domain.User;

public class UserDAOImpl extends BaseDAO<User> implements UserDAO {

	@Override
	public User getUser(String username) {
		// TODO Auto-generated method stub
		String sql = "SELECT userId, username, accountId FROM userinfo WHERE username = ?";
		return query(sql, username);
	}

}
