package android;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class ReviseInfo_Write {
	private static ReviseInfo_Write reviseinfo = new ReviseInfo_Write();

	public static ReviseInfo_Write getWrite() {
		return reviseinfo;
	}
	private String returns = "";
	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;
	DataSource ds;
	
	public String write(String value, String index, String emp_no) {
	    try {
	    	Context initctx = new InitialContext();

			//톰켓 서버에 정보를 담아놓은 곳으로 이동
			Context envctx = (Context) initctx.lookup("java:comp/env");

			//데이터 소스 객체를 선언
			ds = (DataSource) envctx.lookup("jdbc/WorkManager");

		conn = ds.getConnection();
		
		if(index.equals("0")) {
			String SQL = "Update employee set emp_name='"+value+"' where emp_no='"+emp_no+"'";
			pstmt = conn.prepareStatement(SQL);
			pstmt.executeUpdate();
			returns = "success";
		}else if(index.equals("1")) {
			String SQL = "Update employee set emp_id='"+value+"' where emp_no='"+emp_no+"'";
			pstmt = conn.prepareStatement(SQL);
			pstmt.executeUpdate();
			returns = "success";
		}else if(index.equals("2")) {
			String SQL = "Update employee set emp_email='"+value+"' where emp_no='"+emp_no+"'";
			pstmt = conn.prepareStatement(SQL);
			pstmt.executeUpdate();
			returns = "success";
		}else if(index.equals("3")) {
			String SQL = "Update employee set emp_pw='"+value+"' where emp_no='"+emp_no+"'";
			pstmt = conn.prepareStatement(SQL);
			pstmt.executeUpdate();
			returns = "success";
		}else {
			returns = "";
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
}