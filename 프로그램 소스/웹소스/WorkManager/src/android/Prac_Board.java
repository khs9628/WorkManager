package android;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class Prac_Board {
	private static Prac_Board prac_board = new Prac_Board();
	public static Prac_Board getPrac_Board() {
	      return prac_board;
	   }
	   private String returns="";
	   private Connection conn = null;
	   private PreparedStatement pstmt = null;
	   private ResultSet rs = null;
	   DataSource ds;
	   String sWorkedDay, sStd_salary, sHoliday, sWorkTime, sRestDay, sKindDay, sRestATime, sRestETime, sWorkTimeId, sHoliDay;
	  
	   public String select(String id) {
	      try {
	        returns ="";
	        Context initctx = new InitialContext();

			//톰켓 서버에 정보를 담아놓은 곳으로 이동
			Context envctx = (Context) initctx.lookup("java:comp/env");

			//데이터 소스 객체를 선언
			ds = (DataSource) envctx.lookup("jdbc/WorkManager");

			conn = ds.getConnection();

			
			String nowTime = getCurrentTime("YYYY/MM/DD hh24:mm"); //기준 시간
			
			int total, basic, extend, night, weekly, restDay, holiday, deduct, add, workTime, workedDay; //각 항목 변수 선언
			int[] absent = new int[6];
			int[] late = new int[6];
			
			String query = "SELECT COUNT(WORKTIME_ID)WORKED_DAY FROM WORKTIME W inner join employee e on W.EMP_NO = e.emp_no where e.emp_id ='"+id+"' AND W.A_TIME>=TO_DATE('"+nowTime.substring(0, 7)+"','YYYY/MM')";
			pstmt = conn.prepareStatement(query);
	        rs = pstmt.executeQuery();
	        
	        while(rs.next()) {
	       	  sWorkedDay=rs.getString("WORKED_DAY");
	      }
	        System.out.println("ok_2");
	        
	        
	        
	        return returns;      		       
	     } 
	         
	      catch (Exception e) {
	    	 System.out.println("error");
	        e.printStackTrace();
	        return returns;
	     } // end try~catch
	      
	      finally {
	    	  //System.out.println("final");
	          if (pstmt != null)
	        	  try {
						if(rs != null) rs.close();
						if(pstmt != null) pstmt.close();
						if(conn != null) conn.close();
					} catch (Exception e) {
						e.printStackTrace();
					}
	      }
	     
	   }// end select()
	   public static String getCurrentTime(String timeFormat) {
	       return new SimpleDateFormat(timeFormat).format(System.currentTimeMillis());
	    }
	   
	   public static Calendar getCalendar(String str) {
			Calendar cal = Calendar.getInstance();
			cal.set(Integer.parseInt(str.substring(0, 4)), Integer.parseInt(str.substring(4,6))-1,
					Integer.parseInt(str.substring(6,8)), Integer.parseInt(str.substring(8,10)),
							Integer.parseInt(str.substring(10,12))); 
			return cal;
		}

	}


