package member;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
public class memberDAO {
		
		DataSource ds;
		  
		  // 데이터 베이스의 커넥션 풀을 사용하도록 설정하는 메소드
		  public memberDAO() {
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
		  
		  //사원정보를 입력하는 메소드(WORK_ID.NEXTVAL - 1씩증가하는 시퀸스)
		  public int memberRegister(String EMP_ID,  String EMP_PW, String EMP_NAME, String EMP_EMAIL) {
			  Connection conn = null;
			  PreparedStatement pstmt = null;
			
			  
			  String SQL = "INSERT INTO EMPLOYEE VALUES (?, ?, ?, ?, 'W',WORK_ID.NEXTVAL)";
			  
			  try {
				  conn = ds.getConnection();
				  pstmt = conn.prepareStatement(SQL);
				  pstmt.setString(1, EMP_ID);
				  pstmt.setString(2, EMP_PW);
				  pstmt.setString(3, EMP_NAME);
				  pstmt.setString(4, EMP_EMAIL);
				  pstmt.executeUpdate();
				  return 1;
			}catch(Exception e) {
			  	e.printStackTrace();
			  }finally {
				  try {
					if(pstmt != null) pstmt.close();
					if(conn != null) conn.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			  }
			  return -1; //데이터 베이스 오류
		  }
		  
		 //사원의 고용정보를 입력하는 메소드 - sysdate는 입사일
		  public int memberHirePlus(String workPlace, String salType, String STD_salary,String WORK_DAY, String REST_DAY, String START_TIME, String END_TIME, String EMP_NO,String SALDATE) {
			  Connection conn = null;
			  PreparedStatement pstmt = null;
			
			  
			  String SQL = "INSERT INTO HIREINFO VALUES (?, ?, ?, sysdate, ?, ?, 10, to_date(?, 'HH24:MI') , to_date(?, 'HH24:MI'), 1,60 , ?, ?)";
			  
			  try {
				  conn = ds.getConnection();
				  pstmt = conn.prepareStatement(SQL);
				  pstmt.setString(1, workPlace);
				  pstmt.setString(2, salType);
				  pstmt.setString(3, STD_salary);
				  pstmt.setString(4, WORK_DAY);
				  pstmt.setString(5, REST_DAY);
				  pstmt.setString(6, START_TIME);
				  pstmt.setString(7, END_TIME);
				  pstmt.setString(8, SALDATE);
				  pstmt.setString(9, EMP_NO);
				  pstmt.executeUpdate();
				  return 1;
			  }catch(Exception e) {
			  	e.printStackTrace();
			  }finally {
				  try {
					if(pstmt != null) pstmt.close();
					if(conn != null) conn.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			  }
			  return -1; //데이터 베이스 오류
		  }
	
		  //사원의 근무정보를 입력하는 메소드 -
		  public int memberWorkPlus(String attendanceTime, String exitTime,String EMP_NO) {
			  Connection conn = null;
			  PreparedStatement pstmt = null;
			
			  
			  String SQL = "INSERT INTO WORKTIME VALUES (to_date(?, 'yyyy-mm-ddHH24:MI'), to_date(?, 'yyyy-mm-ddHH24:MI'), '-', WORK_TIME.NEXTVAL, ?)";
			  
			  try {
				  conn = ds.getConnection();
				  pstmt = conn.prepareStatement(SQL);
				  pstmt.setString(1, attendanceTime);
				  pstmt.setString(2, exitTime);
				  pstmt.setString(3, EMP_NO);
				  pstmt.executeUpdate();
				  return 1;
			  }catch(Exception e) {
			  	e.printStackTrace();
			  }finally {
				  try {
					if(pstmt != null) pstmt.close();
					if(conn != null) conn.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			  }
			  return -1; //데이터 베이스 오류
		  }
		  
		  //기본정보를 수정하는 메소드
		  public int memberBasicUpdate(String EMP_ID, String EMP_EMAIL, String EMP_NO) {
			  Connection conn = null;
			  PreparedStatement pstmt = null;
			
			  
			  String SQL = "UPDATE EMPLOYEE SET EMP_ID =? ,EMP_EMAIL = ? WHERE EMP_NO =?";
			  
			  try {
				  conn = ds.getConnection();
				  pstmt = conn.prepareStatement(SQL);
				  pstmt.setString(1, EMP_ID);					 
				  pstmt.setString(2, EMP_EMAIL);
				  pstmt.setString(3, EMP_NO);
				  pstmt.executeUpdate();
				  return 1;
			}catch(Exception e) {
			  	e.printStackTrace();
			  }finally {
				  try {
					if(pstmt != null) pstmt.close();
					if(conn != null) conn.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			  }
			  return -1; //데이터 베이스 오류
		  }
		  
		 
		  //사원 고용정보를 수정하는 메소드
		  public int memberHireUpdate(String workPlace, String salType, String STD_salary, String WORK_DAY,String REST_DAY,String START_TIME,String END_TIME,String SALDATE,String EMP_NO) {
			  Connection conn = null;
			  PreparedStatement pstmt = null;
			
			  
			  String SQL = "UPDATE HIREINFO SET WORKPLACE =? ,SALTYPE = ?, STD_SALARY = ?, WORK_DAY= ?, REST_DAY= ?, START_TIME = to_date(?, 'HH24:MI'), END_TIME = to_date(?, 'HH24:MI'), SALDATE=? WHERE EMP_NO =?";
			  
			  try {
				  conn = ds.getConnection();
				  pstmt = conn.prepareStatement(SQL);
				  pstmt.setString(1, workPlace);
				  pstmt.setString(2, salType);
				  pstmt.setString(3, STD_salary);
				  pstmt.setString(4, WORK_DAY);
				  pstmt.setString(5, REST_DAY);
				  pstmt.setString(6, START_TIME);
				  pstmt.setString(7, END_TIME);
				  pstmt.setString(8, SALDATE);
				  pstmt.setString(9, EMP_NO);
				  pstmt.executeUpdate();
				  return 1;
				  
			}catch(Exception e) {
			  	e.printStackTrace();
			  }finally {
				  try {
					if(pstmt != null) pstmt.close();
					if(conn != null) conn.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			  }
			  return -1; //데이터 베이스 오류
		  }
		  
		  //근무정보를 수정하는 메소드
		  public int memberWorkUpdate(String attendanceTime, String exitTime, String WORKTIME_ID) {
			  Connection conn = null;
			  PreparedStatement pstmt = null;
			  String SQL = "UPDATE WORKTIME SET A_TIME = to_date(?, 'yyyy-mm-ddHH24:MI'), E_TIME = to_date(?, 'yyyy-mm-ddHH24:MI')  WHERE WORKTIME_ID = ?";
			  
			  try {
				  conn = ds.getConnection();
				  pstmt = conn.prepareStatement(SQL);
				  pstmt.setString(1, attendanceTime);					 
				  pstmt.setString(2, exitTime);
				  pstmt.setString(3, WORKTIME_ID);
				  pstmt.executeUpdate();
				  return 1;
			}catch(Exception e) {
			  	e.printStackTrace();
			  }finally {
				  try {
					if(pstmt != null) pstmt.close();
					if(conn != null) conn.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			  }
			  return -1; //데이터 베이스 오류
		  }
		  
		//사원정보를 삭제하는 메소드 -> 사원데이터는 유지시키지만 출력하지 않는다.
		  public int delete(String EMP_NO) {
		   	  Connection conn = null;
			  PreparedStatement pstmt = null;
			  
			  String SQL = "UPDATE EMPLOYEE SET EMP_STAT = D WHERE EMP_NO = ?";
			  
				 try {
				  conn = ds.getConnection();
				  pstmt = conn.prepareStatement(SQL);
				  pstmt.setString(1, EMP_NO);
				  pstmt.executeUpdate();
				  return 1;
			}catch(Exception e) {
			  	e.printStackTrace();
			  }finally {
				  try {
					if(pstmt != null) pstmt.close();
					if(conn != null) conn.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			  }
			  return -1; //데이터 베이스 오류
		  }  
		  
		  //사원고용정보 삭제하는 메소드
		  public int deleteHire(String EMP_NO) {
		   	  Connection conn = null;
			  PreparedStatement pstmt = null;
			  
			  String SQL = "DELETE FROM HIREINFO WHERE EMP_NO =?";
			  
				 try {
				  conn = ds.getConnection();
				  pstmt = conn.prepareStatement(SQL);
				  pstmt.setString(1, EMP_NO);
				  pstmt.executeUpdate();
				  return 1;
			}catch(Exception e) {
			  	e.printStackTrace();
			  }finally {
				  try {
					if(pstmt != null) pstmt.close();
					if(conn != null) conn.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			  }
			  return -1; //데이터 베이스 오류
		  }  
		 
		  //사원근무정보 삭제하는 메소드
		  public int deleteWork(String WORKTIME_ID) {
		   	  Connection conn = null;
			  PreparedStatement pstmt = null;
			  
			  String SQL = "DELETE FROM WORKTIME WHERE WORKTIME_ID =?";
			  
				 try {
				  conn = ds.getConnection();
				  pstmt = conn.prepareStatement(SQL);
				  pstmt.setString(1, WORKTIME_ID);
				  pstmt.executeUpdate();
				  return 1;
			}catch(Exception e) {
			  	e.printStackTrace();
			  }finally {
				  try {
					if(pstmt != null) pstmt.close();
					if(conn != null) conn.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			  }
			  return -1; //데이터 베이스 오류
		  }  
		  
		  public ArrayList<memberDTO> getMemberBasic(){
			  ArrayList<memberDTO> memberList = null;
			  Connection conn = null;
			  PreparedStatement pstmt = null;
			  ResultSet rs = null;
			  String SQL= "SELECT m.EMP_ID, m.EMP_PW, m.EMP_NAME, m.EMP_EMAIL, m.EMP_NO,h.workPlace, h.salType, h.STD_Salary, m.EMP_STAT FROM HIREINFO H RIGHT JOIN EMPLOYEE M ON H.EMP_NO= M.EMP_NO Where EMP_STAT <> 'N'";
			 
			  try {
				  conn = ds.getConnection();
				  pstmt = conn.prepareStatement(SQL);
				  rs = pstmt.executeQuery();
				  memberList = new ArrayList<memberDTO>();
				  while(rs.next()) {
					  memberDTO member = new memberDTO();
					  member.setEMP_ID(rs.getString("EMP_ID"));
					  member.setEMP_PW(rs.getString("EMP_PW"));
					  member.setEMP_NAME(rs.getString("EMP_NAME"));
					  member.setEMP_EMAIL(rs.getString("EMP_EMAIL"));
					  member.setEMP_NO(rs.getInt("EMP_NO"));
					  member.setWorkPlace(rs.getString("workPlace"));
					  member.setSalType(rs.getString("salType"));
					  member.setSTD_Salary(rs.getInt("STD_Salary"));
					  member.setEMP_STAT(rs.getString("EMP_STAT"));
					  memberList.add(member);
				  }
			} catch (Exception e) {
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
			  return memberList; 
		  }
		  
		  public ArrayList<memberDTO> getMemberW(){
			  ArrayList<memberDTO> memberList = null;
			  Connection conn = null;
			  PreparedStatement pstmt = null;
			  ResultSet rs = null;
			  String SQL= "SELECT EMP_ID, EMP_PW, EMP_NAME, EMP_EMAIL, EMP_NO FROM EMPLOYEE WHERE EMP_STAT = 'W'";
			 
			  try {
				  conn = ds.getConnection();
				  pstmt = conn.prepareStatement(SQL);
				  rs = pstmt.executeQuery();
				  memberList = new ArrayList<memberDTO>();
				  while(rs.next()) {
					  memberDTO member = new memberDTO();
					  member.setEMP_ID(rs.getString("EMP_ID"));
					  member.setEMP_PW(rs.getString("EMP_PW"));
					  member.setEMP_NAME(rs.getString("EMP_NAME"));
					  member.setEMP_EMAIL(rs.getString("EMP_EMAIL"));
					  member.setEMP_NO(rs.getInt("EMP_NO"));
					  memberList.add(member);
				  }
			} catch (Exception e) {
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
			  return memberList; 
		  }
		  
		   //모든 사원정보 (기본 +고용)를 가져오는 메소드 - 삭제된 데이터도 가져온다.
		  public ArrayList<memberDTO> getMemberHire(){
			  ArrayList<memberDTO> memberList = null;
			  Connection conn = null;
			  PreparedStatement pstmt = null;
			  ResultSet rs = null;
			  String SQL= "SELECT m.EMP_ID, m.EMP_PW, m.EMP_NAME, m.EMP_EMAIL, m.EMP_NO, h.workPlace, h.salType, h.STD_salary,TO_CHAR(h.joinDate,'YYYY-MM-DD HH24:MI') AS JOINDATE, m.EMP_STAT ,h.WORK_DAY, h.REST_DAY, h.HOLIDAY, TO_CHAR(h.START_TIME,'HH24:MI') AS START_TIME, TO_CHAR(h.END_TIME,'HH24:MI') as END_TIME, h.ISMOREFIVE,h.REST_TIME,h.SALDATE FROM HIREINFO H RIGHT JOIN EMPLOYEE M ON H.EMP_NO= M.EMP_NO Where EMP_STAT = 'N'";
			 
			  try {
				  conn = ds.getConnection();
				  pstmt = conn.prepareStatement(SQL);
				  rs = pstmt.executeQuery();
				  memberList = new ArrayList<memberDTO>();
				  while(rs.next()) {
					  memberDTO member = new memberDTO();
					  member.setEMP_ID(rs.getString("EMP_ID"));
					  member.setEMP_PW(rs.getString("EMP_PW"));
					  member.setEMP_NAME(rs.getString("EMP_NAME"));
					  member.setEMP_EMAIL(rs.getString("EMP_EMAIL"));
					  member.setEMP_NO(rs.getInt("EMP_NO"));
					  member.setWorkPlace(rs.getString("workPlace"));
					  member.setSalType(rs.getString("salType"));
					  member.setSTD_Salary(rs.getInt("STD_salary"));
					  member.setJoinDate(rs.getString("joinDate"));
					  member.setEMP_STAT(rs.getString("EMP_STAT"));
					  member.setWORK_DAY(rs.getString("WORK_DAY"));
					  member.setREST_DAY(rs.getString("REST_DAY"));
					  member.setHOLIDAY(rs.getString("HOLIDAY"));
					  member.setSTART_TIME(rs.getString("START_TIME"));
					  member.setEND_TIME(rs.getString("END_TIME"));
					  member.setISMOREFIVE(rs.getInt("ISMOREFIVE"));
					  member.setREST_TIME(rs.getInt("REST_TIME"));
					  member.setSALDATE(rs.getString("SALDATE"));
				      memberList.add(member);
				  }
			} catch (Exception e) {
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
			  return memberList; 
		  }
		  
		  //모든 근무정보(+기본사원정보)를 가져오는 메소드
		  public ArrayList<memberDTO> getMemberWork(){
			  ArrayList<memberDTO> memberList = null;
			  Connection conn = null;
			  PreparedStatement pstmt = null;
			  ResultSet rs = null;
			  String SQL= "SELECT m.EMP_ID, m.EMP_PW, m.EMP_NAME, m.EMP_EMAIL\r\n" + 
			  		",TO_CHAR(W.A_TIME,'YYYY-MM-DD HH24:MI') as A_TIME \r\n" + 
			  		",TO_CHAR(W.E_TIME,'YYYY-MM-DD HH24:MI') as E_TIME \r\n" + 
			  		",W.WORKTIME_ID,W.EMP_NO, H.STD_SALARY,H.REST_TIME\r\n" + 
			  		"FROM WORKTIME W \r\n" + 
			  		"INNER JOIN EMPLOYEE M \r\n" + 
			  		"ON W.EMP_NO= M.EMP_NO \r\n" + 
			  		"INNER JOIN EMPLOYEE M \r\n" + 
			  		"ON W.EMP_NO= M.EMP_NO \r\n" + 
			  		"INNER JOIN HIREINFO H\r\n" + 
			  		"ON W.EMP_NO= H.EMP_NO\r\n" + 
			  		"WHERE M.EMP_STAT='N'";
			  		try {
				  conn = ds.getConnection();
				  pstmt = conn.prepareStatement(SQL);
				  rs = pstmt.executeQuery();
				  memberList = new ArrayList<memberDTO>();
				  while(rs.next()) {
					  memberDTO member = new memberDTO();
					  member.setEMP_ID(rs.getString("EMP_ID"));
					  member.setEMP_PW(rs.getString("EMP_PW"));
					  member.setEMP_NAME(rs.getString("EMP_NAME"));
					  member.setEMP_EMAIL(rs.getString("EMP_EMAIL"));
					  member.setA_TIME(rs.getString("A_Time"));
					  member.setE_TIME(rs.getString("E_Time"));
					  member.setWORKTIME_ID(rs.getInt("WORKTIME_ID"));
					  member.setEMP_NO(rs.getInt("EMP_NO"));
					  member.setSTD_Salary(rs.getInt("STD_SALARY"));
					  member.setREST_TIME(rs.getInt("REST_TIME"));
					  memberList.add(member);
				  }
			} catch (Exception e) {
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
			  return memberList; 
		  }
		  
		   /*근무중 OR 근무 X 출력하는 메소드*/
		  public ArrayList<memberDTO> getWorkOne(){
			  ArrayList<memberDTO> memberList = null;
			  Connection conn = null;
			  PreparedStatement pstmt = null;
			  ResultSet rs = null;
			  String SQL= "SELECT m.EMP_ID, m.EMP_PW, m.EMP_NAME, m.EMP_EMAIL, M.EMP_NO,\r\n" + 
			  		"TO_CHAR(W.A_TIME,'YYYY-MM-DD HH24:MI') as A_TIME , TO_CHAR(W.E_TIME,'YYYY-MM-DD HH24:MI') as E_TIME, W.WORKTIME_ID,\r\n" + 
			  		"TO_CHAR(H.START_TIME,'YYYY-MM-DD HH24:MI') AS START_TIME \r\n" + 
			  		"FROM WORKTIME W \r\n" + 
			  		"INNER JOIN EMPLOYEE M \r\n" + 
			  		"ON W.EMP_NO= M.EMP_NO \r\n" + 
			  		"AND TO_CHAR(A_TIME, 'YYYYMMDD') = TO_CHAR(SYSDATE, 'YYYYMMDD')AND E_TIME IS NULL\r\n" + 
			  		"INNER JOIN HIREINFO H  \r\n" + 
			  		"ON W.EMP_NO= H.EMP_NO  \r\n" + 
			  		"WHERE EMP_STAT='N'";
			  try {
				  conn = ds.getConnection();
				  pstmt = conn.prepareStatement(SQL);
				  rs = pstmt.executeQuery();
				  memberList = new ArrayList<memberDTO>();
				  while(rs.next()) {
					  memberDTO member = new memberDTO();
					  member.setEMP_ID(rs.getString("EMP_ID"));
					  member.setEMP_PW(rs.getString("EMP_PW"));
					  member.setEMP_NAME(rs.getString("EMP_NAME"));
					  member.setEMP_EMAIL(rs.getString("EMP_EMAIL"));
					  member.setA_TIME(rs.getString("A_Time"));
					  member.setE_TIME(rs.getString("E_Time"));
					  member.setWORKTIME_ID(rs.getInt("WORKTIME_ID"));
					  member.setEMP_NO(rs.getInt("EMP_NO"));
					  member.setSTART_TIME(rs.getString("START_TIME"));
					  memberList.add(member);
				  }
			} catch (Exception e) {
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
			  return memberList; 
		  }
		  
		  public ArrayList<memberDTO> getWorkTwo(){
			  ArrayList<memberDTO> memberList = null;
			  Connection conn = null;
			  PreparedStatement pstmt = null;
			  ResultSet rs = null;
			  String SQL= " SELECT m.EMP_ID, m.EMP_PW, m.EMP_NAME, m.EMP_EMAIL,\r\n" + 
			  		"        M.EMP_NO, TO_CHAR(W.A_TIME,'YYYY-MM-DD HH24:MI') as A_TIME , TO_CHAR(W.E_TIME,'YYYY-MM-DD HH24:MI') as E_TIME \r\n" + 
			  		"       ,W.WORKTIME_ID \r\n" + 
			  		"        FROM WORKTIME W \r\n" + 
			  		"        RIGHT JOIN EMPLOYEE M \r\n" + 
			  		"        ON W.EMP_NO= M.EMP_NO \r\n" + 
			  		"         AND TO_CHAR(A_TIME, 'YYYYMMDD') = TO_CHAR(SYSDATE, 'YYYYMMDD')\r\n" + 
			  		"        OR A_TIME IS NULL\r\n" + 
			  		"        WHERE EMP_Stat='N'";
			  try {
				  conn = ds.getConnection();
				  pstmt = conn.prepareStatement(SQL);
				  rs = pstmt.executeQuery();
				  memberList = new ArrayList<memberDTO>();
				  while(rs.next()) {
					  memberDTO member = new memberDTO();
					  member.setEMP_ID(rs.getString("EMP_ID"));
					  member.setEMP_PW(rs.getString("EMP_PW"));
					  member.setEMP_NAME(rs.getString("EMP_NAME"));
					  member.setEMP_EMAIL(rs.getString("EMP_EMAIL"));
					  member.setA_TIME(rs.getString("A_Time"));
					  member.setE_TIME(rs.getString("E_Time"));
					  member.setWORKTIME_ID(rs.getInt("WORKTIME_ID"));
					  member.setEMP_NO(rs.getInt("EMP_NO"));
					  memberList.add(member);
				  }
			} catch (Exception e) {
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
			  return memberList; 
		  }
		  
		  
		  //하나의 사원 정보를 가져오는 메소드
		  public memberDTO getMember1(String EMP_ID) {
			  Connection conn = null;
			  PreparedStatement pstmt = null;
			  ResultSet rs = null;
			  memberDTO member = new memberDTO();
			  String SQL ="SELECT * FROM EMPLOYEE WHERE EMP_ID = ?";
			  try {
				  conn = ds.getConnection();
				  pstmt = conn.prepareStatement(SQL);
				  pstmt.setString(1, EMP_ID);
				  rs = pstmt.executeQuery();
				
				if(rs.next()) {
					  member.setEMP_ID(rs.getString("EMP_ID"));
					  member.setEMP_PW(rs.getString("EMP_PW"));
					  member.setEMP_NAME(rs.getString("EMP_NAME"));
					  member.setEMP_EMAIL(rs.getString("EMP_EMAIL"));
					  member.setEMP_NO(rs.getInt("EMP_NO"));
				  }
			} catch (Exception e) {
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
			  return member; 
		  }
		  
		  public memberDTO getMember(String EMP_NO) {
			  Connection conn = null;
			  PreparedStatement pstmt = null;
			  ResultSet rs = null;
			  memberDTO member = new memberDTO();
			  String SQL ="SELECT * FROM EMPLOYEE WHERE EMP_NO = ?";
			  try {
				  conn = ds.getConnection();
				  pstmt = conn.prepareStatement(SQL);
				  pstmt.setString(1, EMP_NO);
				  rs = pstmt.executeQuery();
				
				if(rs.next()) {
					  member.setEMP_ID(rs.getString("EMP_ID"));
					  member.setEMP_PW(rs.getString("EMP_PW"));
					  member.setEMP_NAME(rs.getString("EMP_NAME"));
					  member.setEMP_EMAIL(rs.getString("EMP_EMAIL"));
					  member.setEMP_NO(rs.getInt("EMP_NO"));
				  }
			} catch (Exception e) {
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
			  return member; 
		  
		  
		  }
		  
		  //하나의 사원의 고용정보를 가져오는 메소드
		  public memberDTO getMemberHire(String EMP_NO) {
			  Connection conn = null;
			  PreparedStatement pstmt = null;
			  ResultSet rs = null;
			  memberDTO member = new memberDTO();
			  String SQL ="SELECT m.EMP_ID, m.EMP_PW, m.EMP_NAME, m.EMP_EMAIL, m.EMP_NO, h.workplace, h.saltype, h.std_salary,h.joindate, m.EMP_STAT ,h.WORK_DAY, h.REST_DAY, h.HOLIDAY, TO_CHAR(h.START_TIME,'HH24:MI') AS START_TIME, TO_CHAR(h.END_TIME,'HH24:MI') AS END_TIME, h.ISMOREFIVE,h.REST_TIME,h.SALDATE FROM HIREINFO H RIGHT JOIN EMPLOYEE M ON H.EMP_NO= M.EMP_NO WHERE M.EMP_NO=?";
			  try {
				  conn = ds.getConnection();
				  pstmt = conn.prepareStatement(SQL);
				  pstmt.setString(1, EMP_NO);
				  rs = pstmt.executeQuery();
				
				if(rs.next()) {
					  member.setEMP_ID(rs.getString("EMP_ID"));
					  member.setEMP_PW(rs.getString("EMP_PW"));
					  member.setEMP_NAME(rs.getString("EMP_NAME"));
					  member.setEMP_EMAIL(rs.getString("EMP_EMAIL"));  
					  member.setWorkPlace(rs.getString("workPlace"));
					  member.setSalType(rs.getString("saltype"));
					  member.setSTD_Salary(rs.getInt("STD_salary"));
					  member.setJoinDate(rs.getString("joindate"));
					  member.setEMP_STAT(rs.getString("EMP_STAT"));
					  member.setEMP_NO(rs.getInt("EMP_NO"));
					  member.setWORK_DAY(rs.getString("WORK_DAY"));
					  member.setREST_DAY(rs.getString("REST_DAY"));
					  member.setHOLIDAY(rs.getString("HOLIDAY"));
					  member.setSTART_TIME(rs.getString("START_TIME"));
					  member.setEND_TIME(rs.getString("END_TIME"));
					  member.setISMOREFIVE(rs.getInt("ISMOREFIVE"));
					  member.setREST_TIME(rs.getInt("REST_TIME"));
					  member.setSALDATE(rs.getString("SALDATE"));
				  }
			} catch (Exception e) {
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
			  return member; 
		  
		  
		  }
		  //이메일을 보내기위해 사용자의 이메일을 가져오는 메소드
		  public String getEMP_EMAIL(String EMP_NO) {
			  String EMP_EMAIL = null;
			  Connection conn = null;
			  PreparedStatement pstmt = null;
			  ResultSet rs = null;
			  String SQL ="SELECT EMP_EMAIL FROM EMPLOYEE WHERE EMP_NO = ?";
			  try {
				  EMP_EMAIL ="";
				  conn = ds.getConnection();
				  pstmt = conn.prepareStatement(SQL);
				  pstmt.setString(1, EMP_NO);
				  rs = pstmt.executeQuery();
				
				  while(rs.next()) {
					  EMP_EMAIL +=rs.getString("EMP_EMAIL");
			         }
			} catch (Exception e) {
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
			  return EMP_EMAIL; 
		   }
		  
		  //하나의 사원의 근무정보를 가져오는 메소드
		  public memberDTO getMemberWork(String EMP_NO) {
			  Connection conn = null;
			  PreparedStatement pstmt = null;
			  ResultSet rs = null;
			  memberDTO member = new memberDTO();
			  String SQL ="SELECT * FROM WORKTIME WHERE EMP_NO = ?";
			  try {
				  conn = ds.getConnection();
				  pstmt = conn.prepareStatement(SQL);
				  pstmt.setString(1, EMP_NO);
				  rs = pstmt.executeQuery();
				
				if(rs.next()) {
					  member.setA_TIME(rs.getString("attendanceTime"));
					  member.setE_TIME(rs.getString("exitTime"));
					  member.setWORKTIME_ID(rs.getInt("workTime_ID"));
					  member.setEMP_NO(rs.getInt("EMP_NO"));
				  }
			} catch (Exception e) {
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
			  return member; 
		  
		  
		  }
		  /*   권한을 바꿔주는함수    */
		  //정상 사원 상태로 바꿔주는 함수 - 안드로이드에서 회원가입 신청 후 사용 가능하게 만드는 것 
		  public int memberN(String EMP_NO) {
			  Connection conn = null;
			  PreparedStatement pstmt = null;
			
			  
			  String SQL = "UPDATE EMPLOYEE SET EMP_STAT = 'N' WHERE EMP_NO = ?";
			  
			  try {
				  conn = ds.getConnection();
				  pstmt = conn.prepareStatement(SQL);
				  pstmt.setString(1, EMP_NO);
				  pstmt.executeUpdate();
				  return 1;
			}catch(Exception e) {
			  	e.printStackTrace();
			  }finally {
				  try {
					if(pstmt != null) pstmt.close();
					if(conn != null) conn.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			  }
			  return -1; //데이터 베이스 오류
		  }

		  //회원 정보를 삭제할 때  
		  public int memberD(String EMP_NO) {
			  Connection conn = null;
			  PreparedStatement pstmt = null;
			
			  
			  String SQL = "UPDATE EMPLOYEE SET EMP_STAT = 'D' WHERE EMP_NO = ?";
			  
			  try {
				  conn = ds.getConnection();
				  pstmt = conn.prepareStatement(SQL);
				  pstmt.setString(1, EMP_NO);
				  pstmt.executeUpdate();
				  return 1;
			}catch(Exception e) {
			  	e.printStackTrace();
			  }finally {
				  try {
					if(pstmt != null) pstmt.close();
					if(conn != null) conn.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			  }
			  return -1; //데이터 베이스 오류
		  }
		  
		  
		  
		  /*------------REQUST------------------ */
		   public memberDTO getRequest(String REQ_ID) {
				  Connection conn = null;
				  PreparedStatement pstmt = null;
				  ResultSet rs = null;
				  memberDTO member = new memberDTO();
				  String SQL ="SELECT * FROM REQUEST WHERE REQ_ID = ?";
				  try {
					  conn = ds.getConnection();
					  pstmt = conn.prepareStatement(SQL);
					  pstmt.setString(1, REQ_ID);
					  rs = pstmt.executeQuery();
					  if(rs.next()) {
						  member.setREQ_EMP_NO(rs.getInt("REQ_EMP_NO"));
						  member.setREQ_ID(rs.getInt("REQ_ID"));
						  member.setREQ_TYPE(rs.getString("REQ_TYPE"));
						  member.setREQ_TITLE(rs.getString("REQ_TITLE").replaceAll(" ", "&nbsp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br>"));
						  member.setREQ_CONTENT(rs.getString("REQ_CONTENT").replaceAll(" ", "&nbsp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br>"));
						  member.setREQ_DATE(rs.getString("REQ_DATE").substring(0, 11));
						  member.setREQ_A_TIME(rs.getString("REQ_A_TIME"));
						  member.setREQ_E_TIME(rs.getString("REQ_E_TIME"));
						  member.setWORKTIME_ID(rs.getInt("WORKTIME_ID"));
						}
				} catch (Exception e) {
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
				  return member; 
			  
			  
			  }
			 
		   
		   public String getEMP_NAME(String EMP_NO) {
				  String EMP_NAME = null;
				  Connection conn = null;
				  PreparedStatement pstmt = null;
				  ResultSet rs = null;
				  String SQL ="SELECT EMP_NAME FROM EMPLOYEE WHERE EMP_NO = ?";
				  try {
					  EMP_NAME ="";
					  conn = ds.getConnection();
					  pstmt = conn.prepareStatement(SQL);
					  pstmt.setString(1, EMP_NO);
					  rs = pstmt.executeQuery();
					
					  while(rs.next()) {
						  EMP_NAME +=rs.getString("EMP_NAME");
				         }
				} catch (Exception e) {
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
				  return EMP_NAME; 
			   }
		   
		   
			  public ArrayList<memberDTO> getRequestAll(){
				  ArrayList<memberDTO> boardList = null;
				  Connection conn = null;
				  PreparedStatement pstmt = null;
				  ResultSet rs = null;
				  String SQL= "SELECT * FROM REQUEST";
				 
				  try {
					  conn = ds.getConnection();
					  pstmt = conn.prepareStatement(SQL);
					  rs = pstmt.executeQuery();
					  boardList = new ArrayList<memberDTO>();
					  while(rs.next()) {
						  memberDTO member = new memberDTO();
						  member.setREQ_EMP_NO(rs.getInt("REQ_EMP_NO"));
						  member.setREQ_ID(rs.getInt("REQ_ID"));
						  member.setREQ_TYPE(rs.getString("REQ_TYPE"));
						  member.setREQ_TITLE(rs.getString("REQ_TITLE").replaceAll(" ", "&nbsp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br>"));
						  member.setREQ_CONTENT(rs.getString("REQ_CONTENT").replaceAll(" ", "&nbsp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br>"));
						  member.setREQ_DATE(rs.getString("REQ_DATE").substring(0, 11));
						  member.setREQ_A_TIME(rs.getString("REQ_A_TIME"));
						  member.setREQ_E_TIME(rs.getString("REQ_E_TIME"));
						  member.setWORKTIME_ID(rs.getInt("WORKTIME_ID"));
						  boardList.add(member);
					  }
				} catch (Exception e) {
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
				  return boardList; 
			  
			  }

			  public int deleteRequest(String REQ_ID) {
			   	  Connection conn = null;
				  PreparedStatement pstmt = null;
				  
				  String SQL = "DELETE FROM REQUSET WHERE REQ_ID =?";
				  
					 try {
					  conn = ds.getConnection();
					  pstmt = conn.prepareStatement(SQL);
					  pstmt.setInt(1, Integer.parseInt(REQ_ID));
					  return pstmt.executeUpdate();
					  
				}catch(Exception e) {
				  	e.printStackTrace();
				  }finally {
					  try {
						if(pstmt != null) pstmt.close();
						if(conn != null) conn.close();
					} catch (Exception e) {
						e.printStackTrace();
					}
				  }
				  return -1; //데이터 베이스 오류
			  }  
			  
			  
			  public String Comma(String num) {
				    int inValues = Integer.parseInt(num);
				    DecimalFormat Commas = new DecimalFormat("#,###");
				    String result_int = (String) Commas.format(inValues);
				    return result_int;
				}
		
	}
	


