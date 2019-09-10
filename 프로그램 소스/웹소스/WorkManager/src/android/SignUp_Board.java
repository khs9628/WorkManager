package android;

import java.sql.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class SignUp_Board{
   private static SignUp_Board signup_board = new SignUp_Board();

   public static SignUp_Board getSignUp_Board() {
      return signup_board;
   }

   private String returns;
   private Connection con = null;
   private PreparedStatement pstmt = null;
   private ResultSet rs = null;
   DataSource ds;
  
   public String select(String id) {
      try {
        returns ="";
        Context initctx = new InitialContext();

		//톰켓 서버에 정보를 담아놓은 곳으로 이동
		Context envctx = (Context) initctx.lookup("java:comp/env");

		//데이터 소스 객체를 선언
		ds = (DataSource) envctx.lookup("jdbc/WorkManager");

		con = ds.getConnection();
         String query = "SELECT emp_id, emp_name FROM EMPLOYEE";
         pstmt = con.prepareStatement(query);
         rs = pstmt.executeQuery();
         
         while(rs.next()) {
            if(rs.getString("emp_id").equals(id)) {
            	return rs.getString("emp_name");
            }
         } // end while
      } catch (Exception e) {
         e.printStackTrace();
      } // end try~catch

      finally {
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