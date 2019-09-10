package android;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class AddWorktime_Write {
	private static AddWorktime_Write addworktime = new AddWorktime_Write();

	public static AddWorktime_Write getWrite() {
		return addworktime;
	}
	private String returns = "";
	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;
	DataSource ds;
	String sEmpId="", worktime_no, worktime_no_end;
	
	public String write(String id, String status) {
	    try {
	    	Context initctx = new InitialContext();

			//톰켓 서버에 정보를 담아놓은 곳으로 이동
			Context envctx = (Context) initctx.lookup("java:comp/env");

			//데이터 소스 객체를 선언
			ds = (DataSource) envctx.lookup("jdbc/WorkManager");

		conn = ds.getConnection();
		
		/*****************사원 번호 출력*******************/
		String query = "SELECT EMP_NO FROM EMPLOYEE WHERE EMP_ID='"+id+"'";
		 pstmt = conn.prepareStatement(query);
	     rs = pstmt.executeQuery();
	     while(rs.next()) {
	     sEmpId = rs.getString("EMP_NO");
	     }
	     int emp_id = Integer.parseInt(sEmpId);

		Statement stmt = conn.createStatement();
		String seq = "select max(worktime_id)MAX from worktime";
		ResultSet rs = stmt.executeQuery(seq);

		while(rs.next()) {
        	worktime_no = rs.getString("MAX");
        }
		
		seq = "select max(worktime_id)END_MAX from worktime where emp_no='"+emp_id+"'";
		rs = stmt.executeQuery(seq);

		while(rs.next()) {
        	worktime_no_end = rs.getString("END_MAX");
        }

		String nowTime;
		String SQL;
		
		switch(status) {
		case "0":
			/*********출근용 sql*********/
			nowTime = getCurrentTime("YYYYMMddHHmm");
			Calendar nowDate = getCalendar(nowTime.substring(0,8));
			int kindDay = nowDate.get(Calendar.DAY_OF_WEEK);
			String sKindDay = intDayList(kindDay);
			worktime_no = Integer.toString(Integer.parseInt(worktime_no)+1);
			
			SQL ="INSERT INTO WORKTIME (A_TIME, EMP_NO,worktime_id, KIND_DAY, BEFORE_WEEKLY, BEFORE_EXTEND) VALUES (TO_DATE('"+nowTime+"','YYYYMMDDHH24mi'),'"+emp_id+"', '"+worktime_no+"', '"+sKindDay+"', 0, 0 )";
			pstmt = conn.prepareStatement(SQL);
			pstmt.executeUpdate();
			returns = "workOn";
			break;
		
		case "1":
			/*********퇴근용 sql*********/
			nowTime = getCurrentTime("YYYYMMddHHmm");
			SQL ="UPDATE WORKTIME SET E_TIME = TO_DATE('"+nowTime+"','YYYYMMDDHH24mi') WHERE WORKTIME_ID='"+worktime_no_end+"'";
			pstmt = conn.prepareStatement(SQL);
			pstmt.executeUpdate();
			returns = "workOff";
			break;
		}

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
        public static Calendar getCalendar(String str) {
			Calendar cal = Calendar.getInstance();
			cal.set(Integer.parseInt(str.substring(0, 4)), Integer.parseInt(str.substring(4,6))-1,
					Integer.parseInt(str.substring(6,8))); 
			return cal;
		}
        public static String intDayList(int day) {
		   	String list="";
		   	if(day==1)list="일";
	        else if(day==2)list="월";
	        else if(day==3)list="화";
	        else if(day==4)list="수";
	        else if(day==5)list="목";
	        else if(day==6)list="금";
	        else if(day==7)list="토";
		   	
		   	return list;
		   
	   }
}