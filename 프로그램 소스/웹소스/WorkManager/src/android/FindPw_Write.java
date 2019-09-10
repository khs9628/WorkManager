package android;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class FindPw_Write {
	private static FindPw_Write findpw = new FindPw_Write();

public static FindPw_Write getWrite() {
	return findpw;
}
private String returns = "";
private Connection conn = null;
private PreparedStatement pstmt = null;
private ResultSet rs = null;
DataSource ds;

public String write(String pw, String id) {
    try {
    	Context initctx = new InitialContext();

		//톰켓 서버에 정보를 담아놓은 곳으로 이동
		Context envctx = (Context) initctx.lookup("java:comp/env");

		//데이터 소스 객체를 선언
		ds = (DataSource) envctx.lookup("jdbc/WorkManager");

	conn = ds.getConnection();
	

	String SQL = "update employee set emp_pw='"+pw+"' where emp_id='"+id+"'";
	pstmt = conn.prepareStatement(SQL);
	pstmt.executeUpdate();
	returns = "success";
	

} catch (Exception e) {
	System.out.println("error");
	e.printStackTrace();
} finally {
	 try {
		 System.out.println("final");
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
