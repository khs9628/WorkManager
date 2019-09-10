package android;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class Calendar_Write {
   private static Calendar_Write calendar = new Calendar_Write();

   public static Calendar_Write getWrite() {
      return calendar;
   }
   private String returns = "";
   private Connection conn = null;
   private PreparedStatement pstmt = null;
   private ResultSet rs = null;
   DataSource ds;
   
   public String write(String content, String memberId, String date) {
       try {
          Context initctx = new InitialContext();

         //톰켓 서버에 정보를 담아놓은 곳으로 이동
         Context envctx = (Context) initctx.lookup("java:comp/env");

         //데이터 소스 객체를 선언
         ds = (DataSource) envctx.lookup("jdbc/WorkManager");

      conn = ds.getConnection();

      Statement stmt = conn.createStatement();
      String seq = "select max(req_id) from request";
      ResultSet rs = stmt.executeQuery(seq);

       int RR_id = -1;
         if (rs.next()) 
            RR_id = rs.getInt(1);
            RR_id++;
      
         String nowTime = getCurrentTime("YYYYMMdd");
            String RR_title = nowTime+memberId;
            String dateFormat = "yy-mm-dd";
            String timeFormat = "yy-mm-ddhh24:mi";
            String type = "s";
            String worktimeId="";
            
            String query = "SELECT WORKTIME_ID FROM WORKTIME WHERE TO_CHAR(A_TIME,'YYYYMMdd')='"+nowTime+"' AND EMP_NO='"+memberId+"'";
            pstmt = conn.prepareStatement(query);
              rs = pstmt.executeQuery();
              while(rs.next()) {
                 worktimeId = rs.getString("WORKTIME_ID");
              }
            
            
            String SQL = "INSERT INTO Request(Req_id,Req_type,Req_title,Req_content,Req_date,REQ_EMP_NO) VALUES('"+ RR_id +"','"+type+ "','" + RR_title + "','" + content + "',to_date('"+ date + "','"+dateFormat+"'),'" + memberId+ "')";
            pstmt = conn.prepareStatement(SQL);
            pstmt.executeUpdate();
            returns = "success";

   } catch (Exception e) {
      e.printStackTrace();
   } finally {
       try {
            if(rs != null) rs.close();
            if(pstmt != null) pstmt.close();
            if(conn != null) conn.close();
         } catch (Exception e) {
            e.printStackTrace();
         }
   }
   return returns;
       }
        public static String getCurrentTime(String timeFormat) {
          return new SimpleDateFormat(timeFormat).format(System.currentTimeMillis());
        }
}