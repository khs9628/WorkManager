package android;

import java.sql.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class PlaceInfo_Board{
   private static PlaceInfo_Board placeinfo_board = new PlaceInfo_Board();

   public static PlaceInfo_Board getPlaceInfo_Board() {
      return placeinfo_board;
   }

   private String returns;
   private Connection con = null;
   private PreparedStatement pstmt = null;
   private ResultSet rs = null;
   DataSource ds;
   public String select(String id) {
	   String sResult="";
      try {
        returns ="";
        Context initctx = new InitialContext();

		//톰켓 서버에 정보를 담아놓은 곳으로 이동
		Context envctx = (Context) initctx.lookup("java:comp/env");

		//데이터 소스 객체를 선언
		ds = (DataSource) envctx.lookup("jdbc/WorkManager");

		con = ds.getConnection();
		
		String query = "SELECT e.emp_name, h.workplace, h.saltype, h.std_salary, h.work_day,to_char(h.start_time,'hh24:mi')start_time, to_char(h.end_time,'hh24:mi')end_time, h.rest_day, h.holiday, to_char(h.joindate,'YY-MM-DD')joindate, h.saldate ,h.rest_time, h.ismorefive FROM employee e inner join hireinfo h on e.emp_no=h.EMP_NO where e.emp_id='"+id+"'";
		pstmt = con.prepareStatement(query);
        rs = pstmt.executeQuery();
        while(rs.next()) {
           sResult += rs.getString("emp_name");
           sResult += "/";
           sResult += rs.getString("workplace");
           sResult += "/";
           sResult += rs.getString("saltype");
           sResult += "/";
           sResult += rs.getString("std_salary");
           sResult += "/";
           sResult += rs.getString("work_day");
           sResult += "/";
           sResult += rs.getString("start_time");
           sResult += "/";
           sResult += rs.getString("end_time");
           sResult += "/";
           sResult += rs.getString("rest_day");
           sResult += "/";
           sResult += rs.getString("holiday");
           sResult += "/";
           sResult += rs.getString("joindate");
           sResult += "/";
           sResult += rs.getString("saldate");
           sResult += "/";
           sResult += rs.getString("rest_time");
           sResult += "/";
           sResult += rs.getString("ismorefive");
         } 
        System.out.println("success");
        return sResult;
         
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