package bookstore.test;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.List;

import org.junit.Test;

import bookstore.domain.Book;
import dao.impl.BaseDAO;
import dao.impl.BookDAOImpl;

public class BaseDAOTest {

	private BookDAOImpl bookDAOImpl=new BookDAOImpl();

	@Test
	public void testInsert() {
		
		String sql="INSERT INTO trade(userid, tradetime) VALUES(?,?)";
		long id = bookDAOImpl.insert(sql, 1, new Date(new java.util.Date().getTime()));
		System.out.println(id); 
	} 

	@Test
	public void testUpdate() {
		String sql = "update mybooks set salesamount=? where id=?";
		bookDAOImpl.update(sql, 10, 4);
	}

	@Test
	public void testQuery() {
		String sql = "SELECT id, author, title, price,salesAmount from mybooks where id = ?";
		Book book = bookDAOImpl.query(sql, 4);
		System.out.println(book);
	}

	@Test
	public void testQueryForList() {
		String sql = "SELECT id, author, title, price,salesAmount from mybooks where id < ?";
		List<Book> books = bookDAOImpl.queryForList(sql, 4);
		System.out.println(books);
	}

	@Test
	public void testGetSingleVal() {
		String sql= "SELECT count(id) FROM mybooks";
		long count = bookDAOImpl.getSingleVal(sql);
		System.out.println(count);
	}

	@Test
	public void testBatch() {
		String sql= "UPDATE mybooks set price=?, salesAmount=? where id =? ";
		bookDAOImpl.batch(sql, new Object[] {12,12,1}, new Object[] {12,12,2}, new Object[] {13,13,3});
	}

}
