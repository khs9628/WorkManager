package android;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class SignUp_Write {
	private static SignUp_Write signup = new SignUp_Write();

	public static SignUp_Write getWrite() {
		return signup;
	}
	private String returns = "";
	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;
	DataSource ds;
	
	public String write(String content, String id, String email, String pw) {
	    try {
	    	Context initctx = new InitialContext();

			//톰켓 서버에 정보를 담아놓은 곳으로 이동
			Context envctx = (Context) initctx.lookup("java:comp/env");

			//데이터 소스 객체를 선언
			ds = (DataSource) envctx.lookup("jdbc/WorkManager");

		conn = ds.getConnection();

		Statement stmt = conn.createStatement();
		String seq = "select max(emp_no) from employee";
		ResultSet rs = stmt.executeQuery(seq);

		int emp_no = -1;
		if (rs.next()) 
			emp_no = rs.getInt(1);
			emp_no++;

		String SQL = "INSERT INTO employee(emp_no,emp_stat,emp_name,emp_id,emp_email,emp_pw) VALUES('"+ emp_no +"','"+'W'+"','" + content + "','" + id + "','" + email + "','" + pw + "')";
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