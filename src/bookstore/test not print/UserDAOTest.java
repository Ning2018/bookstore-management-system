package bookstore.test;

import static org.junit.Assert.*;

import org.junit.Test;

import bookstore.dao.UserDAO;
import bookstore.domain.User;
import dao.impl.UserDAOImpl;

public class UserDAOTest {
	private UserDAO userDAO = new UserDAOImpl();
	@Test
	public void testGetUser() {
		User user = userDAO.getUser("Tom");
		System.out.println(user);
	}

}
