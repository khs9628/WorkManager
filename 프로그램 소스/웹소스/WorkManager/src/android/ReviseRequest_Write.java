package android;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class ReviseRequest_Write {
   private static ReviseRequest_Write reviseRequest = new ReviseRequest_Write();

   public static ReviseRequest_Write getWrite() {
      return reviseRequest;
   }
   private String returns = "";
   private Connection conn = null;
   private PreparedStatement pstmt = null;
   private ResultSet rs = null;
   DataSource ds;
   
   public String write(String date, String sTime, String eTime, String content, String memberId) {
       try {
          Context initctx = new InitialContext();

         //톰켓 서버에 정보를 담아놓은 곳으로 이동
         Context envctx = (Context) initctx.lookup("java:comp/env");

         //데이터 소스 객체를 선언
         ds = (DataSource) envctx.lookup("jdbc/WorkManager");

      conn = ds.getConnection();

      Statement stmt = conn.createStatement();
      String seq = "select max(Req_id) from Request";
      ResultSet rs = stmt.executeQuery(seq);
      
      int RR_id = -1;
      if (rs.next()) 
         RR_id = rs.getInt(1);
         RR_id++;
         
      String nowTime = getCurrentTime("YYYYMMdd");
      String RR_title = nowTime+memberId;
      String dateFormat = "yy-mm-dd";
      String timeFormat = "yy-mm-ddhh24:mi";
      String type = "r";
      String worktimeId="";
      
      String query = "SELECT WORKTIME_ID FROM WORKTIME WHERE TO_CHAR(A_TIME,'YYYYMMdd')='"+date.replace("/", "")+"' AND EMP_NO='"+memberId+"'";
      pstmt = conn.prepareStatement(query);
        rs = pstmt.executeQuery();
        while(rs.next()) {
           worktimeId = rs.getString("WORKTIME_ID");
        }
      
      
      String SQL = "INSERT INTO Request(Req_id,Req_type,Req_title,Req_content,Req_date,Req_EMP_NO,REQ_A_Time,REQ_e_Time, WORKTIME_ID) VALUES('"+ RR_id +"','"+type+ "','" + RR_title + "','" + content + "',to_date('"+ date + "','"+dateFormat+"'),'" + memberId+ "',to_date('"+ date + sTime + "','"+timeFormat+"'),to_date('" + date+ eTime + "','"+timeFormat+"'),'"+worktimeId+"')";
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