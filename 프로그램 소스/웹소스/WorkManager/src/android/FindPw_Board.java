package android;

import java.sql.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class FindPw_Board{
   private static FindPw_Board findpw_board = new FindPw_Board();

   public static FindPw_Board getFindPw_Board() {
      return findpw_board;
   }

   private String returns;
   private Connection con = null;
   private PreparedStatement pstmt = null;
   private ResultSet rs = null;
   DataSource ds;
  
   public String select(String id, String name, String email) {
      try {
        returns ="";
        Context initctx = new InitialContext();

      //톰켓 서버에 정보를 담아놓은 곳으로 이동
      Context envctx = (Context) initctx.lookup("java:comp/env");

      //데이터 소스 객체를 선언
      ds = (DataSource) envctx.lookup("jdbc/WorkManager");

      con = ds.getConnection();
      String query = "SELECT emp_email FROM EMPLOYEE where emp_id='"+id+"' and emp_name ='"+name+"' and emp_email='"+email+"'";
        pstmt = con.prepareStatement(query);
        rs = pstmt.executeQuery();
        
        while(rs.next()) {
              returns = rs.getString("emp_email");
       }
        return returns;
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