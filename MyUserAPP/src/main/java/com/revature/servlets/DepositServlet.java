package com.revature.servlets;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/depositServlet")
public class DepositServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Connection conn;  // Make sure that the driver software is included in lib.

	
	public void init() throws ServletException {

		System.out.println(this.getServletName() + " INSTANTIATED!");
		super.init();
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
		
		try {
			conn = DriverManager.getConnection("jdbc:oracle:thin:@database-2.c0rzi76acgyn.us-east-1.rds.amazonaws.com:1521:FIRSTDB", "admin", "12345678");
			System.out.println("Connected!");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	protected void doPost(HttpServletRequest req, HttpServletResponse res) 
			throws ServletException, IOException {
			
			String email = req.getParameter("email");
			String password = req.getParameter("password"); // this is the password you want to update
			String amount = req.getParameter("amount"); // this is what user enters to withdraw
			
			int amountToBeDeposited = Integer.parseInt(amount);
			// we have to subtract this amount from what's already in the DB associated with this user.
			
			
			// the we create an insert statement Statement statement = conn.createStatement()
			try {
				Statement statement = conn.createStatement();
				int result = statement.executeUpdate("UPDATE user_cmp SET balance= balance+'"+amountToBeDeposited+"' WHERE email= '"+email+"'");                   
				// an insert statement affects 1 row, but an update or delete statement can affect more...	
				PrintWriter out = res.getWriter();
				if (result > 0) {
					out.println("<h1>Congrats, you have deposit " + amountToBeDeposited + "!</h1>");
				} else {
					out.println("<h1>Error depositing...</h1>");
				}
			} catch (SQLException e) {
				System.out.println("Failure...");
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
 