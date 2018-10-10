package bookstore.service;

import java.util.Set;

import bookstore.dao.BookDAO;
import bookstore.dao.TradeDAO;
import bookstore.dao.TradeItemDAO;
import bookstore.dao.UserDAO;
import bookstore.domain.Trade;
import bookstore.domain.TradeItem;
import bookstore.domain.User;
import dao.impl.BookDAOImpl;
import dao.impl.TradeDAOImpl;
import dao.impl.TradeItemDAOImpl;
import dao.impl.UserDAOImpl;

public class UserService {
	private UserDAO userDAO = new UserDAOImpl();
	public User getUserByUserName(String username) {
		return userDAO.getUser(username);
	}
	private TradeDAO tradeDAO = new TradeDAOImpl();
	private TradeItemDAO tradeItemDAO = new TradeItemDAOImpl();
	private BookDAO bookDAO = new BookDAOImpl();
	
	public User getUserWithTrades(String username) {
		
		User user = userDAO.getUser(username);
		if(user==null) {
		return null;
	}
		int userId = user.getUserId();
		Set<Trade> trades  = tradeDAO.getTradesWithUserId(userId);
		
		if(trades!=null) {
			for(Trade trade: trades) {
				int tradeId = trade.getTradeId();
				Set<TradeItem> items = tradeItemDAO.getTradeItemsWithTradeId(tradeId);
				
				if(items!=null) {
					for(TradeItem item : items) {
						item.setBook(bookDAO.getBook(item.getBookId()));
					}
				}
				
				trade.setItems(items);
			}
		}
		
		user.setTrades(trades);
		return user;
	}
}
