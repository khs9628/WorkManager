package android;

import java.sql.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class Login_Board{
   private static Login_Board login_board = new Login_Board();

   public static Login_Board getLogin_Board() {
      return login_board;
   }

   private String returns;
   private Connection con = null;
   private PreparedStatement pstmt = null;
   private ResultSet rs = null;
   DataSource ds;
  
   public String select(String id, String pw) {
      try {
        returns ="";
        Context initctx = new InitialContext();

		//톰켓 서버에 정보를 담아놓은 곳으로 이동
		Context envctx = (Context) initctx.lookup("java:comp/env");

		//데이터 소스 객체를 선언
		ds = (DataSource) envctx.lookup("jdbc/WorkManager");

		con = ds.getConnection();
		
		String query = "SELECT emp_stat FROM employee where emp_id='"+id+"' and emp_pw ='"+pw+"'";
	    pstmt = con.prepareStatement(query);
	    rs = pstmt.executeQuery();
	       
	    while(rs.next()) {
	       returns = rs.getString("emp_stat");
	    }
	    if(returns.equals("W")) {
	    	return "";
	    }
		
		query = "SELECT emp_name FROM employee where emp_id='"+id+"' and emp_pw ='"+pw+"'";
        pstmt = con.prepareStatement(query);
        rs = pstmt.executeQuery();
        
       while(rs.next()) {
        	 return rs.getString("emp_name");
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