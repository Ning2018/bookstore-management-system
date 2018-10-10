package bookstore.domain;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class ShoppingCart {
	
	private Map<Integer, ShoppingCartItem> books = new HashMap<>();
	
	public void updateItemQuantity(Integer id, int quantity){
		ShoppingCartItem sci =books.get(id);
		if(sci != null){
			sci.setQuantity(quantity);
		}
	}

	public void removeItem(Integer id){
		books.remove(id);
	}

	public void clear(){
		books.clear();
	}
	
	public boolean isEmpty(){
		return books.isEmpty();
	}

	public float getTotalMoney(){
		float total = 0;
		
		for(ShoppingCartItem sci: getItems()){
			total += sci.getItemMoney();
		}
		
		return total;
	}
	
	public Collection<ShoppingCartItem> getItems(){
		return books.values();
	}
	
	public int getBookNumber(){
		int total = 0;
		
		for(ShoppingCartItem sci: books.values()){
			total += sci.getQuantity();
		}
		
		return total;
	}
	
	public Map<Integer, ShoppingCartItem> getBooks() {
		return books;
	}
	
	public boolean hasBook(Integer id){
		return books.containsKey(id);
	}		
			
	public void addBook(Book book){
		//1. check if the book is in the shopping cart, 

		ShoppingCartItem sci = books.get(book.getId());
		
		if(sci == null){
			sci = new ShoppingCartItem(book);
			books.put(book.getId(), sci);
		}else{
			sci.increment();
		}
	}
}
