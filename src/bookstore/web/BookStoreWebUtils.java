package bookstore.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import bookstore.domain.ShoppingCart;

public class BookStoreWebUtils {
	
	/*
	 * get shopping cart item from session
	 */
	public static ShoppingCart getShoppingCart(HttpServletRequest request) {
		HttpSession session = request.getSession();
	
		ShoppingCart sc= (ShoppingCart)session.getAttribute("ShoppingCart");
		if(sc==null) {
			sc = new ShoppingCart();
			session.setAttribute("ShoppingCart", sc);
		}
		
		return sc;
	}
}
