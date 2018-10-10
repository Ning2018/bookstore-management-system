package dao.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import bookstore.dao.BookDAO;
import bookstore.domain.Book;
import bookstore.domain.ShoppingCartItem;
import bookstore.web.CriteriaBook;
import bookstore.web.Page;

public class BookDAOImpl extends BaseDAO<Book> implements BookDAO {

	@Override
	public Book getBook(int id) {
		// TODO Auto-generated method stub
		String sql = "SELECT id, author, title, price, salesAmount,storeNumber FROM mybooks where id = ?";
		return query(sql,id);
	}

	@Override
	public Page<Book> getPage(CriteriaBook cb) {
		// TODO Auto-generated method stub
		Page page = new Page<>(cb.getPageNo());
		page.setTotalItemNumber(getTotalBookNumber(cb));
		//check pageNo validity 
		cb.setPageNo(page.getPageNo());
		page.setList(getPageList(cb,3));
		return page;
	}

	@Override
	public long getTotalBookNumber(CriteriaBook cb) {
		// TODO Auto-generated method stub
		String sql = "SELECT count(id) FROM mybooks where price >=? and price<= ?";
		System.out.println("Total book number under criteria is: "+ 
		getSingleVal(sql,cb.getMinPrice(),cb.getMaxPrice()));
		return getSingleVal(sql,cb.getMinPrice(),cb.getMaxPrice());
	}

	/* pageNo=5
	 * pageSize=3
	 * (non-Javadoc)
	 * LIMIT is for MySQL paging, and fromIndex starts from 0
	 * @see bookstore.dao.BookDAO#getPageList(bookstore.web.CriteriaBook, int)
	 */
	@Override
	public List<Book> getPageList(CriteriaBook cb, int pageSize) {
		
		String sql = "SELECT id, author, title, price, salesAmount, storeNumber FROM mybooks where price >=? and price<= ?"+
		"LIMIT ?, ?";
		return queryForList(sql,cb.getMinPrice(),cb.getMaxPrice(),(cb.getPageNo()-1)*pageSize,pageSize);
	}

	@Override
	public int getStoreNumber(Integer id) {
		
		String sql = "SELECT storeNumber FROM mybooks WHERE id = ?";
		return getSingleVal(sql, id);
	}

	@Override
	public void batchUpdateStoreNumberAndSalesAmount(Collection<ShoppingCartItem> items) {
		
		String sql = "UPDATE mybooks SET salesAmount = salesAmount + ?, storeNumber = storeNumber -? where id=?";
		
		Object [][] params = null;
		params = new Object[items.size()][3];
		List<ShoppingCartItem> scis = new ArrayList<>(items);
		for(int i=0; i<items.size();i++) {
			params[i][0]=scis.get(i).getQuantity();
			params[i][1]=scis.get(i).getQuantity();
			params[i][2]=scis.get(i).getBook().getId();
			
		}
		batch(sql,params);
		
	}

}
