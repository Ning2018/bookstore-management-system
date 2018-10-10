package bookstore.test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import bookstore.dao.TradeItemDAO;
import bookstore.domain.TradeItem;
import dao.impl.TradeItemDAOImpl;

public class TradeItemDAOTest {

	private TradeItemDAO tradeItemDAO = new  TradeItemDAOImpl();
	@Test
	public void testBatchSave() {
		Collection<TradeItem> items = new ArrayList<>();
		items.add(new TradeItem(null,1,10,25));
		items.add(new TradeItem(null,2,20,25));
		items.add(new TradeItem(null,3,10,25));
		items.add(new TradeItem(null,4,20,25));
		items.add(new TradeItem(null,5,10,25));
		
		tradeItemDAO.batchSave(items);
	}

	@Test
	public void testGetTradeItemsWithTradeId() {
		Set<TradeItem> items = tradeItemDAO.getTradeItemsWithTradeId(25);
		//tradeItemDAO.getTradeItemsWithTradeId(tradeId);
		System.out.println(items);
	}

}
