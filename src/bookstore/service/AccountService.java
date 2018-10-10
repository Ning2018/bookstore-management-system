package bookstore.service;

import bookstore.dao.AccountDAO;
import bookstore.domain.Account;
import dao.impl.AccountDAOImpl;

public class AccountService {
	
	private AccountDAO accountDAO = new AccountDAOImpl();

	public Account getAccount(int accountId) {
		
		return accountDAO.get(accountId);
	}
}
