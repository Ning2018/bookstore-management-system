package bookstore.servlet;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import bookstore.domain.Account;
import bookstore.domain.Book;
import bookstore.domain.ShoppingCart;
import bookstore.domain.ShoppingCartItem;
import bookstore.domain.User;
import bookstore.service.AccountService;
import bookstore.service.BookService;
import bookstore.service.UserService;
import bookstore.web.BookStoreWebUtils;
import bookstore.web.CriteriaBook;
import bookstore.web.Page;

/**
 * Servlet implementation class BookServlet
 */
@WebServlet("/bookServlet")
public class BookServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);
	}

	private BookService bookService = new BookService();
	
	protected void forwardPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String page=request.getParameter("page");
		request.getRequestDispatcher("/WEB-INF/pages/"+ page+".jsp").forward(request, response);
	}
	
	protected void cash(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//1. basic format check: empty, int, email, no database connection
		String username = request.getParameter("username");
		String accountId = request.getParameter("accountId");
		
		StringBuffer errors = validateFormField(username, accountId);
		
		if(errors.toString().equals("")) {
			errors=validateUser(username,accountId);
			if(errors.toString().equals("")) {
			   errors = validateBookStoreNumber(request);
				if(errors.toString().equals("")) {
				errors=validateBalance(request, accountId);
			}
		  }
		}
		
        if(!errors.toString().equals("")) {
        	request.setAttribute("errors", errors);
        	request.getRequestDispatcher("/WEB-INF/pages/cash.jsp").forward(request, response);
        	return;
        }
        
        // start to check out
        bookService.cash(BookStoreWebUtils.getShoppingCart(request),username,accountId);
        response.sendRedirect(request.getContextPath()+"/success.jsp");
	}
	
	
	private UserService userService = new UserService();
	private AccountService accountService = new AccountService();
	
	public StringBuffer validateBalance(HttpServletRequest request, String accountId) {
		
		StringBuffer errors = new StringBuffer("");
		ShoppingCart cart = BookStoreWebUtils.getShoppingCart(request);
		
		Account account = accountService.getAccount(Integer.parseInt(accountId));
		
		if(cart.getTotalMoney()>account.getBalance()) {
			errors.append("Insufficient balance.");
		}
		
		return errors;
	}
	
	public StringBuffer validateBookStoreNumber(HttpServletRequest request) {
		
		StringBuffer errors = new StringBuffer("");
		ShoppingCart cart = BookStoreWebUtils.getShoppingCart(request);
		
		for(ShoppingCartItem sci:cart.getItems()) {
			int quantity = sci.getQuantity();
			int storeNumber = bookService.getBook(sci.getBook().getId()).getStoreNumber();
			if(quantity>storeNumber) {
				errors.append(sci.getBook().getTitle()+"   Insufficient stock. storeNumber is: "+storeNumber+"<br>");
			}
		}
		
		return errors;
	}
	
	public StringBuffer validateUser(String username, String accountId) {
		boolean flag = false;
        User user = userService.getUserByUserName(username);
        if(user!=null) {
        	int accountId2 = user.getAccountId();
        	if(accountId.trim().equals(""+accountId2)) {
        		flag = true;
        	}
        }
        StringBuffer errors2 = new StringBuffer("");
        	if(!flag) {
        		errors2.append("Username and Account Id do not match.");
        	}
     return errors2;
	}
	
	public StringBuffer validateFormField(String username, String accountId){
		StringBuffer errors = new StringBuffer("");
		if(username == null ||username.trim().equals("")) {
			errors.append("User Name can not be null.<br>");
		}
		if(accountId == null||accountId.trim().equals("")) {
			errors.append("Account Id can not be null.");
		}
		return errors;
	}
	
	protected void clear(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ShoppingCart sc = BookStoreWebUtils.getShoppingCart(request);
		bookService.clearShoppingCart(sc);
		request.getRequestDispatcher("/WEB-INF/pages/emptycart.jsp").forward(request, response);
	}
	protected void remove(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String idStr = request.getParameter("id");
		int id= -1;
		try {
			id=Integer.parseInt(idStr);
		}
		catch(Exception e) {}
		ShoppingCart sc = BookStoreWebUtils.getShoppingCart(request);
		bookService.removeItemFromShoppingCart(sc,id);
		if(sc.isEmpty()) {
			request.getRequestDispatcher("WEB-INF/pages/emptycart.jsp").forward(request, response);
			return;
		}
		 request.getRequestDispatcher("/WEB-INF/pages/cart.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String methodName = request.getParameter("method");
		
		try {
			Method method = getClass().getDeclaredMethod(methodName,  HttpServletRequest.class, HttpServletResponse.class);
			method.setAccessible(true);
			method.invoke(this, request, response);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	protected void updateItemQuantity(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String idStr = request.getParameter("id");
		String quantityStr = request.getParameter("quantity");
		
		ShoppingCart sc = BookStoreWebUtils.getShoppingCart(request);
		int id=-1; 
		int quantity = -1;
		try {
			id=Integer.parseInt(idStr);
			quantity=Integer.parseInt(quantityStr);
		} catch (NumberFormatException e) {}
		
		if(id>0 && quantity>0) {
			bookService.updateItemQuantity(sc,id,quantity);	
		}
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("bookNumber", sc.getBookNumber());
		result.put("totalMoney", sc.getTotalMoney());
		
		Gson gson = new Gson();
		String jsonStr = gson.toJson(result);
		response.setContentType("text/javascript");
		response.getWriter().print(jsonStr);
	}
	protected void addToCart(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//1. get book id
		String idStr = request.getParameter("id");
		int id = -1;
		boolean flag = false;
		try {
			id=Integer.parseInt(idStr);
		} catch (Exception e) {}
		
		if(id>0) {
			ShoppingCart sc = BookStoreWebUtils.getShoppingCart(request);
			flag = bookService.addToCart(id, sc);
		}
		
		if(flag) {
			getBooks(request,response);
			return;
		}
		response.sendRedirect(request.getContextPath()+"/error-1.jsp");
		
		//2. get shopping cart item
		ShoppingCart sc= BookStoreWebUtils.getShoppingCart(request);
		
		//3. BookService addToCart()
	    bookService.addToCart(id,sc);
		
		//4. redirect page to getBooks()
	    getBooks(request,response);
	}
	
	protected void getBook(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	  String idStr = request.getParameter("id");
	  int id = -1;
	  Book book = null;
	  
	  try {
		id=Integer.parseInt(idStr);
	} catch (NumberFormatException e) {}
	  
	  if(id>0)
	     book = bookService.getBook(id);
	  
	  if(book==null) {
		  response.sendRedirect(request.getContextPath()+"/error-1.jsp");
		  return;
	  }
	  
	  request.setAttribute("book", book);
	  request.getRequestDispatcher("/WEB-INF/pages/book.jsp").forward(request, response);
	}
	protected void getBooks(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
				//doGet(request, response);
				String pageNoStr = request.getParameter("pageNo");
				String minPriceStr = request.getParameter("minPrice");
				String maxPriceStr = request.getParameter("maxPrice");
				
				int pageNo =1;
				int minPrice =0;
				int maxPrice = Integer.MAX_VALUE;
				
				try {
					pageNo = Integer.parseInt(pageNoStr);
				} catch (NumberFormatException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					minPrice = Integer.parseInt(minPriceStr);
				} catch (NumberFormatException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					maxPrice = Integer.parseInt(maxPriceStr);
				} catch (NumberFormatException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				CriteriaBook criteriaBook = new CriteriaBook(minPrice, maxPrice, pageNo);
				Page<Book> page = bookService.getPage(criteriaBook);
				request.setAttribute("bookpage",page);
				request.getRequestDispatcher("/WEB-INF/pages/books.jsp").forward(request, response);
	}
}
