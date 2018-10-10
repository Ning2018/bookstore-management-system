package bookstore.service;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import bookstore.dao.AccountDAO;
import bookstore.dao.BookDAO;
import bookstore.dao.TradeDAO;
import bookstore.dao.TradeItemDAO;
import bookstore.dao.UserDAO;
import bookstore.db.JDBCUtils;
import bookstore.domain.Book;
import bookstore.domain.ShoppingCart;
import bookstore.domain.ShoppingCartItem;
import bookstore.domain.Trade;
import bookstore.domain.TradeItem;
import bookstore.web.CriteriaBook;
import bookstore.web.Page;
import dao.impl.AccountDAOImpl;
import dao.impl.BookDAOImpl;
import dao.impl.TradeDAOImpl;
import dao.impl.TradeItemDAOImpl;
import dao.impl.UserDAOImpl;

public class BookService {

	private BookDAO bookDAO = new BookDAOImpl();
	public Page<Book> getPage(CriteriaBook criteriaBook){
		return bookDAO.getPage(criteriaBook);
	}
	public Book getBook(int id) {
		return bookDAO.getBook(id);
	}
	public boolean addToCart(int id, ShoppingCart sc) {
		Book book = bookDAO.getBook(id);
		if(book!=null) {
			sc.addBook(book);
			return true;
		}
		else {
			return false;
		}	
	}
	public void removeItemFromShoppingCart(ShoppingCart sc, int id) {
	   sc.removeItem(id);
	}
	public void clearShoppingCart(ShoppingCart sc) {
		sc.clear();
	}
	public void updateItemQuantity(ShoppingCart sc, int id, int quantity) {
		sc.updateItemQuantity(id,quantity);	 
	}
	
	private AccountDAO accountDAO = new AccountDAOImpl();
	private TradeDAO tradeDAO = new TradeDAOImpl();
	private UserDAO userDAO = new UserDAOImpl();
	private TradeItemDAO tradeItemDAO = new TradeItemDAOImpl();
	
	public void cash(ShoppingCart shoppingCart, String username, String accountId) {
		//1. update mybooks table
	/*	Connection connection= null;
		
		try {
			connection = JDBCUtils.getConnection();
			connection.setAutoCommit(false);
			
			//DAO operation, need connection object
			connection.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				connection.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}finally {
			JDBCUtils.release(connection);
		} 
	*/	
		bookDAO.batchUpdateStoreNumberAndSalesAmount(shoppingCart.getItems());
		
	//	int i = 10/0;
		//2. update account table
		accountDAO.updateBalance(Integer.parseInt(accountId), shoppingCart.getTotalMoney());
		
		//3. insert one record into trade table
		Trade trade = new Trade();
		trade.setTradeTime(new Date(new java.util.Date().getTime()));
		trade.setUserId(userDAO.getUser(username).getUserId());
		tradeDAO.insert(trade);
		
		//4. insert multiple records into tradeitem table
		Collection<TradeItem> items = new ArrayList<>();
		for(ShoppingCartItem sci: shoppingCart.getItems()) {
			TradeItem tradeItem = new TradeItem();
			tradeItem.setBookId(sci.getBook().getId());
			tradeItem.setQuantity(sci.getQuantity());
			tradeItem.setTradeId(trade.getTradeId());
			
			items.add(tradeItem);
		}
		tradeItemDAO.batchSave(items);
		
		//5. clear shopping cart
		shoppingCart.clear();
	}
	
}
