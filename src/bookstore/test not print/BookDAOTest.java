package bookstore.test;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Collection;

import org.junit.Test;

import bookstore.dao.BookDAO;
import bookstore.db.JDBCUtils;
import bookstore.domain.Book;
import bookstore.domain.ShoppingCartItem;
import bookstore.web.ConnectionContext;
import bookstore.web.CriteriaBook;
import bookstore.web.Page;
import dao.impl.BookDAOImpl;

public class BookDAOTest {
	private BookDAO bookDAO = new BookDAOImpl();

	@Test
	public void testGetBook() {
		Connection connection = JDBCUtils.getConnection();
		ConnectionContext.getInstance().bind(connection);
		
		Book book = bookDAO.getBook(5);
		System.out.println(book);
	}

	@Test
	public void testGetPage() {
		CriteriaBook cb = new CriteriaBook(50,80,2);
		Page<Book>page = bookDAO.getPage(cb);
		System.out.println("pageNo: "+page.getPageNo());
		System.out.println("TotalPageNumber: "+page.getTotalPageNumber());
		System.out.println("list: "+page.getList());
		System.out.println("prevPage: "+page.getPrevPage());
		System.out.println("nextPage: "+page.getNextPage());
		
	}

	@Test
	public void testGetTotalBookNumber() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetPageList() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetStoreNumber() {
		int storeNumber = bookDAO.getStoreNumber(5);
		System.out.println(storeNumber);
	}

	@Test
	public void testBatchUpdateStoreNumberAndSalesAmount() {
		Collection<ShoppingCartItem> items = new ArrayList<>();
		
		Book book = bookDAO.getBook(1);
		ShoppingCartItem sci = new ShoppingCartItem(book);
		sci.setQuantity(10);
		items.add(sci);
		
	    book = bookDAO.getBook(2);
		sci = new ShoppingCartItem(book);
		sci.setQuantity(11);
		items.add(sci);
		
		book = bookDAO.getBook(3);
		sci = new ShoppingCartItem(book);
		sci.setQuantity(12);
		items.add(sci);
		
		book = bookDAO.getBook(4);
		sci = new ShoppingCartItem(book);
		sci.setQuantity(13);
		items.add(sci);
		
		bookDAO.batchUpdateStoreNumberAndSalesAmount(items);
	}

}
