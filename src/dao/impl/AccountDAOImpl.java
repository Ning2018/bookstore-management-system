package dao.impl;

import bookstore.dao.AccountDAO;
import bookstore.db.JDBCUtils;
import bookstore.domain.Account;

public class AccountDAOImpl extends BaseDAO<Account> implements AccountDAO {

	@Override
	public Account get(Integer accountId) {
		// TODO Auto-generated method stub
		String sql ="SELECT accountId, balance FROM account WHERE accountId = ?";
		return query(sql,accountId);
	}

	@Override
	public void updateBalance(Integer accountId, float amount) {
		// TODO Auto-generated method stub
       String sql = "UPDATE account SET balance = balance - ? WHERE accountId = ?";
       update(sql, amount, accountId);
	}
}
