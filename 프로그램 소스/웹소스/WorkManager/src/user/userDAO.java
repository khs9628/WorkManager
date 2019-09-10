 package user;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class userDAO {
	 
	  DataSource ds;
	  
	  // 데이터 베이스의 커넥션 풀을 사용하도록 설정하는 메소드
	  public userDAO() {
		  try {
			  Context initctx = new InitialContext();

			//톰켓 서버에 정보를 담아놓은 곳으로 이동
			Context envctx = (Context) initctx.lookup("java:comp/env");

			//데이터 소스 객체를 선언
			ds = (DataSource) envctx.lookup("jdbc/WorkManager");


		} catch (Exception e) {
			e.printStackTrace();
		}
	
}
	  
	  public int login(String userID, String userPassword) {
		  Connection conn = null;
		  PreparedStatement pstmt = null;
		  ResultSet rs = null;
		  
		  String SQL = "SELECT userPassword FROM MANAGER WHERE userID = ?";
		  
		  try {
			  conn = ds.getConnection();
			  pstmt = conn.prepareStatement(SQL);
			  pstmt.setString(1, userID);
			  rs= pstmt.executeQuery();
			  
			  if(rs.next()) {
				  if(rs.getString("userPassword").equals(userPassword)) {
					  return 1; //로그인 성공
				  }
				  	return 2; //비밀번호 틀림
			  }else {
						  return 0; //해당 사용자가 존재하지 않음
					  }
			 
		  
		  } catch(Exception e) {
		  	e.printStackTrace();
		  }finally {
			  try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		  }
		  return -1; //데이터 베이스 오류
	  }
	  
	  
	  public int registerCheck(String userID) {
		  Connection conn = null;
		  PreparedStatement pstmt = null;
		  ResultSet rs = null;
		  
		  String SQL = "SELECT * FROM MANAGER WHERE userID = ?";
		  
		  try {
			  conn = ds.getConnection();
			  pstmt = conn.prepareStatement(SQL);
			  pstmt.setString(1, userID);
			  rs= pstmt.executeQuery();
			  
			  if(rs.next()|| userID.equals("")) {
				 return 0;//이미있는 아이디
					  }
			  else {
				  return 1; //가입가능한 아이디
			  }
		  
		  }catch(Exception e) {
		  	e.printStackTrace();
		  }finally {
			  try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		  }
		  return -1; //데이터 베이스 오류
	  }
	  
	  
	  public int register(String userID, String userPassword,String userName,String userGender,String userEmail) {
		  Connection conn = null;
		  PreparedStatement pstmt = null;
		  ResultSet rs = null;
		  
		  String SQL = "INSERT INTO MANAGER VALUES (?, ?, ?, ?, ?)" ;
		  
		  try {
			  conn = ds.getConnection();
			  pstmt = conn.prepareStatement(SQL);
			  pstmt.setString(1, userID);
			  pstmt.setString(2, userPassword);
			  pstmt.setString(3, userName);
			  pstmt.setString(4, userGender);
			  pstmt.setString(5, userEmail);
			  
			  return pstmt.executeUpdate();
			  
		}catch(Exception e) {
		  	e.printStackTrace();
		  }finally {
			  try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		  }
		  return -1; //데이터 베이스 오류
	  }
	  
	  
	  //한명의 사용자 정보를 가져오는 메소드
	  public userDTO getUser(String userID) {
		  Connection conn = null;
		  PreparedStatement pstmt = null;
		  ResultSet rs = null;
		  ;
		  userDTO user = new userDTO();
		  String SQL ="SELECT * FROM MANAGER WHERE userID = ?";
		  try {
			  conn= ds.getConnection();
			  pstmt = conn.prepareStatement(SQL);
			  pstmt.setString(1, userID);
			  rs = pstmt.executeQuery();
			  if(rs.next()) {
				  user.setUserID(userID);
				  user.setUserPassword(rs.getString("userPassword"));
				  user.setUserName(rs.getString("userName"));
				  user.setUserGender(rs.getString("userGender"));
				  user.setUserEmail(rs.getString("userEmail"));
				   }
		} catch (Exception e) {
			// TODO: handle exception
		}finally {
			  try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		  }
		  return user; 
	  
	  
	  }
	  //정보를 수정하는 메소드
	  public int update(String userID, String userPassword,String userName,String userGender,String userEmail) {
		  Connection conn = null;
		  PreparedStatement pstmt = null;
		  ResultSet rs = null;
		  
		  String SQL = "UPDATE MANAGER SET userPassword = ? ,userName = ?, userGender = ?, userEmail = ? WHERE userID =?" ;
		  
		  try {
			  conn = ds.getConnection();
			  pstmt = conn.prepareStatement(SQL);
			  
			  pstmt.setString(1, userPassword);
			  pstmt.setString(2, userName);
			  pstmt.setString(3, userGender);
			  pstmt.setString(4, userEmail);
			  pstmt.setString(5, userID);
			  return pstmt.executeUpdate();
			  
		}catch(Exception e) {
		  	e.printStackTrace();
		  }finally {
			  try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		  }
		  return -1; //데이터 베이스 오류
	  }
	  
}
