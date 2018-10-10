package bookstore.filter;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bookstore.db.JDBCUtils;
import bookstore.web.ConnectionContext;

/**
 * Servlet Filter implementation class TransactionFilter
 */
@WebFilter("/*")
public class TransactionFilter implements Filter {

    /**
     * Default constructor. 
     */
    public TransactionFilter() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		Connection connection = null;
		try {
			//get connection
			connection = JDBCUtils.getConnection();
			// start event
			connection.setAutoCommit(false);
			// bind connection and current thread by threadlocal
			ConnectionContext.getInstance().bind(connection);
			// redirect request to target servlet
			chain.doFilter(request, response);
			
			// hand in events
			connection.commit();
			
		} catch (Exception e) {
			e.printStackTrace();
			try {
				connection.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			HttpServletResponse resp = (HttpServletResponse)response;
			HttpServletRequest req = (HttpServletRequest)request;
			resp.sendRedirect(req.getContextPath()+"/error-1.jsp");
		}
	finally {
		ConnectionContext.getInstance().remove();
		JDBCUtils.release(connection);
		
	}
	}
	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
