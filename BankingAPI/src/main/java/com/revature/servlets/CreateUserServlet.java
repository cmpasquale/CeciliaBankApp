package com.revature.servlets;



import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class CreateUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Connection conn;
       
 
    public CreateUserServlet() {
        super();
       
    }

	public void init() throws ServletException {
		System.out.println(this.getServletName() + "INSTANTIATED");
		super.init();
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			System.out.println("driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	
		try {
			//Class.forName("oracle.jdbc,driver.Oracledriver");
			conn = DriverManager.getConnection(
					"jdbc:oracle:thin:@database-2.c0rzi76acgyn.us-east-1.rds.amazonaws.com:1521:FIRSTDB", "admin",
					"12345678");
			System.out.println("CONNECTED");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
		
		String username = req.getParameter("username");
		String firstname = req.getParameter("firstname");
		String lastname = req.getParameter("lastname");
		String password = req.getParameter("password");
		String email = req.getParameter("email");
		String balance = req.getParameter("balance");
		
		
		
		//String actno = conn.prepareStatement(sql, new String[] {"accountno"});

//		
		try {
			String sql = "insert into user_cmp (username, first_name, last_name, email, pass, balance) values(?,?,?,?,?,?)";
			PreparedStatement pstmt=conn.prepareStatement(sql, new String[] {"actno"});
			pstmt.setString(1,username);
			pstmt.setString(2,firstname);
			pstmt.setString(3,lastname);
			pstmt.setString(4, email);
			pstmt.setString(5,password);
			pstmt.setString(6, balance);

			pstmt.executeUpdate();
			
			RequestDispatcher rd = req.getRequestDispatcher("/login.html");
			rd.forward(req, res);
			
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	

	public void destroy() {
		System.out.println(this.getServletName() + " DESTROYED!");
		super.destroy();
		// we will also close our connection
		try {
			conn.close();
			System.out.println("Connection closed.");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
