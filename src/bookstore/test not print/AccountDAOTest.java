package bookstore.test;

import static org.junit.Assert.*;

import org.junit.Test;

import bookstore.dao.AccountDAO;
import bookstore.domain.Account;
import dao.impl.AccountDAOImpl;

public class AccountDAOTest {

	AccountDAO accountDAO = new AccountDAOImpl();
	
	@Test
	public void testGet() {
		Account account = accountDAO.get(1);
		System.out.println(account.getBalance());
	}

	@Test
	public void testUpdateBalance() {
	 accountDAO.updateBalance(1, 50);	
	}
}
