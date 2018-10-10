package bookstore.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import bookstore.exception.DBException;

/**
 * 
 * JDBC 的工具类
 *
 */
public class JDBCUtils {
	
	private static ComboPooledDataSource dataSource = null;
	
	static {
		dataSource = new ComboPooledDataSource("javawebapp");
	}
	public static Connection getConnection(){  
		try {
			return dataSource.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DBException("数据库连接错误!");
		}
	}

/*	private static final String USERNAME = "root";
	//database pass code
	private static final String PASSWORD = "root";
	//driver information
	private static final String DRIVER = "com.mysql.jdbc.Driver";
	//database address
	private static final String URL = "jdbc:mysql://localhost:3306/bookstore";
	private static Connection connection;
	
	public JDBCUtils() {
		// TODO Auto-generated constructor stub
		try{
			Class.forName(DRIVER);//add Driver dynamically
			System.out.println("database connection success!");

		}catch(Exception e){

		}
	}
	
	//获取数据库连接
	public static Connection getConnection(){
		try {
			connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return connection;
	}
 */
	//关闭数据库连接
	public static void release(Connection connection) {
		try {
			if(connection != null){
				connection.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DBException("wrong database connection!");
		}
	}
	
	//关闭数据库连接
	public static void release(ResultSet rs, Statement statement) {
		try {
			if(rs != null){
				rs.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DBException("wrong database connection!");
		}
		
		try {
			if(statement != null){
				statement.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DBException("wrong database connection!");
		}
	}
	
}
