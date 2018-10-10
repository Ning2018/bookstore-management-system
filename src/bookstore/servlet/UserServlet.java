package bookstore.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bookstore.domain.User;
import bookstore.service.UserService;

/**
 * Servlet implementation class UserServlet
 */
@WebServlet("/userServlet")
public class UserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserService userService = new UserService();

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// get parameters from username
	     String username = request.getParameter("username");
	     // get User object
	     User user = userService.getUserWithTrades(username);
	     // put User object into request
	     if(user == null) {
	    	 response.sendRedirect(request.getServletPath()+"/error-1.jsp");
	    	 return;
	     }
	     request.setAttribute("user", user);
	     request.getRequestDispatcher("/WEB-INF/pages/trades.jsp").forward(request, response);
	     // get User Object with UserDAO
	}

}
