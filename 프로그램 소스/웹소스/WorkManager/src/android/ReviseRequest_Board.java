package android;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class ReviseRequest_Board {
	   private static ReviseRequest_Board reviseRequest_board = new ReviseRequest_Board();

	   public static ReviseRequest_Board getReviseRequest_Board() {
	      return reviseRequest_board;
	   }

	   private String returns;
	   private Connection con = null;
	   private PreparedStatement pstmt = null;
	   private ResultSet rs = null;
	   DataSource ds;
	  
	   public String select(String userId) {
	      try {
	        returns ="";
	        Context initctx = new InitialContext();

			//톰켓 서버에 정보를 담아놓은 곳으로 이동
			Context envctx = (Context) initctx.lookup("java:comp/env");

			//데이터 소스 객체를 선언
			ds = (DataSource) envctx.lookup("jdbc/WorkManager");

			con = ds.getConnection();
			String query = "SELECT emp_no FROM employee where emp_id='"+userId+"'";
	        pstmt = con.prepareStatement(query);
	        rs = pstmt.executeQuery();
	        
	       while(rs.next()) {
	        	 return rs.getString("emp_no");
	       }   
	     } catch (Exception e) {
	    	 System.out.println("error");
	        e.printStackTrace();
	     } // end try~catch
	      
	      finally {
	    	  System.out.println("final");
	          if (pstmt != null)
	        	  try {
						if(rs != null) rs.close();
						if(pstmt != null) pstmt.close();
						if(con != null) con.close();
					} catch (Exception e) {
						e.printStackTrace();
					}
	      }
	      return returns;
	   }// end select()

	}