package bookstore.dao;

import bookstore.domain.User;

public interface UserDAO {

	/**
	 * �����û�����ȡ User ����
	 * @param username
	 * @return
	 */
	public abstract User getUser(String username);

}

