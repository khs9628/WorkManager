package board;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;



public class boardDAO {
	
	DataSource ds;
	  
	  // 데이터 베이스의 커넥션 풀을 사용하도록 설정하는 메소드
	  public boardDAO() {
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
	  
	   public int write(String userID, String boardTitle, String boardContent, String boardFile ,String boardRealFile) {
		   	  Connection conn = null;
			  PreparedStatement pstmt = null;
			 
			  
			  String SQL = "INSERT INTO BOARD VALUES (?, NVL((SELECT MAX(boardID) + 1 FROM BOARD), 1),?, ?, sysdate, 0, ?, ?, NVL((SELECT MAX(boardGroup) + 1 FROM BOARD), 0), 0, 0)";
			  
				 try {
				  conn = ds.getConnection();
				  pstmt = conn.prepareStatement(SQL);
				  pstmt.setString(1, userID);
				  pstmt.setString(2, boardTitle);
				  pstmt.setString(3, boardContent);
				  pstmt.setString(4, boardFile);
				  pstmt.setString(5, boardRealFile);
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
		  
		  public boardDTO getBoard(String boardID) {
			  Connection conn = null;
			  PreparedStatement pstmt = null;
			  ResultSet rs = null;
			  boardDTO board = new boardDTO();
			  String SQL ="SELECT * FROM BOARD WHERE boardID = ?";
			  try {
				  conn = ds.getConnection();
				  pstmt = conn.prepareStatement(SQL);
				  pstmt.setString(1, boardID);
				  rs = pstmt.executeQuery();
				  if(rs.next()) {
					  board.setUserID(rs.getString("userID"));
					  board.setBoardID(rs.getInt("boardID"));
					  board.setBoardTitle(rs.getString("boardTitle").replaceAll(" ", "&nbsp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br>"));
					  board.setBoardContent(rs.getString("boardContent").replaceAll(" ", "&nbsp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br>"));
					  board.setBoardDate(rs.getString("boardDate").substring(0, 11));
					  board.setBoardHit(rs.getInt("boardHit"));
					  board.setBoardFile(rs.getString("boardFile"));
					  board.setBoardRealFile(rs.getString("boardRealFile"));
					  board.setBoardGroup(rs.getInt("boardGroup"));
					  board.setBoardSequence(rs.getInt("boardSequence"));
					  board.setBoardLevel(rs.getInt("boardLevel"));
					 
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
			  return board; 
		  
		  
		  }
		 
		  public ArrayList<boardDTO> getList(String pageNumber){
			  ArrayList<boardDTO> boardList = null;
			  Connection conn = null;
			  PreparedStatement pstmt = null;
			  ResultSet rs = null;
			  String SQL= "SELECT * FROM BOARD WHERE boardGroup > (SELECT MAX(boardGroup) FROM BOARD) - ? AND boardGroup <= (SELECT MAX(boardGroup) FROM BOARD) -? ORDER BY boardGroup DESC, boardSequence ASC";
			 
			  try {
				  conn = ds.getConnection();
				  pstmt = conn.prepareStatement(SQL);
				  pstmt.setInt(1, Integer.parseInt(pageNumber) * 10);
				  pstmt.setInt(2, (Integer.parseInt(pageNumber) - 1) * 10);
				  rs = pstmt.executeQuery();
				  boardList = new ArrayList<boardDTO>();
				  while(rs.next()) {
					  boardDTO board = new boardDTO();
					  board.setUserID(rs.getString("userID"));
					  board.setBoardID(rs.getInt("boardID"));
					  board.setBoardTitle(rs.getString("boardTitle").replaceAll(" ", "&nbsp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br>"));
					  board.setBoardContent(rs.getString("boardContent").replaceAll(" ", "&nbsp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br>"));
					  board.setBoardDate(rs.getString("boardDate").substring(0, 11));
					  board.setBoardHit(rs.getInt("boardHit"));
					  board.setBoardFile(rs.getString("boardFile"));
					  board.setBoardRealFile(rs.getString("boardRealFile"));
					  board.setBoardGroup(rs.getInt("boardGroup"));
					  board.setBoardSequence(rs.getInt("boardSequence"));
					  board.setBoardLevel(rs.getInt("boardLevel"));
					  boardList.add(board);
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
		  
		  public int hit(String boardID) {
		   	  Connection conn = null;
			  PreparedStatement pstmt = null;
			  
			  String SQL = "UPDATE BOARD SET boardHit = boardHit + 1 WHERE boardID = ?" ;
			  
				 try {
				  conn = ds.getConnection();
				  pstmt = conn.prepareStatement(SQL);
				  pstmt.setString(1, boardID);
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
		  
		 
		  public String getFile(String boardID) {
			  Connection conn = null;
			  PreparedStatement pstmt = null;
			  ResultSet rs = null;
			  String SQL ="SELECT boardFile FROM BOARD WHERE boardID = ?";
			  try {
				  conn = ds.getConnection();
				  pstmt = conn.prepareStatement(SQL);
				  pstmt.setString(1, boardID);
				  rs = pstmt.executeQuery();
				  if(rs.next()) {
					return rs.getString("boardFile"); 
				  }
				  return "";
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
			  return ""; 
		  
		  
		  }
		  
		  public String getRealFile(String boardID) {
			  Connection conn = null;
			  PreparedStatement pstmt = null;
			  ResultSet rs = null;
			  String SQL ="SELECT boardRealFile FROM BOARD WHERE boardID = ?";
			  try {
				  conn = ds.getConnection();
				  pstmt = conn.prepareStatement(SQL);
				  pstmt.setString(1, boardID);
				  rs = pstmt.executeQuery();
				  if(rs.next()) {
					return rs.getString("boardRealFile"); 
				  }
				  return "";
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
			  return ""; 
		  
		  
		  }
		  
		  public int update(String boardID, String boardTitle, String boardContent, String boardFile ,String boardRealFile) {
		   	  Connection conn = null;
			  PreparedStatement pstmt = null;		  
			  String SQL = "UPDATE BOARD SET boardTitle = ?, boardContent = ?, boardFile = ?, boardRealFile = ? WHERE boardID =?";
			  
				 try {
				  conn = ds.getConnection();
				  pstmt = conn.prepareStatement(SQL);
				  
				  pstmt.setString(1, boardTitle);
				  pstmt.setString(2, boardContent);
				  pstmt.setString(3, boardFile);
				  pstmt.setString(4, boardRealFile);
				  pstmt.setInt(5, Integer.parseInt(boardID));
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
		  
		  
		  public int delete(String boardID) {
		   	  Connection conn = null;
			  PreparedStatement pstmt = null;
			  
			  String SQL = "DELETE FROM BOARD WHERE boardID =?";
			  
				 try {
				  conn = ds.getConnection();
				  pstmt = conn.prepareStatement(SQL);
				  pstmt.setInt(1, Integer.parseInt(boardID));
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
		  
		  
		  public int reply(String userID, String boardTitle, String boardContent, String boardFile ,String boardRealFile, boardDTO parent) {
		   	  Connection conn = null;
			  PreparedStatement pstmt = null;
			  
			  String SQL = "INSERT INTO BOARD VALUES (?, NVL((SELECT MAX(boardID) + 1 FROM BOARD), 1),?, ?, sysdate, 0, ?, ?, ?, ?, ?)";
			  
				 try {
				  conn = ds.getConnection();
				  pstmt = conn.prepareStatement(SQL);
				  pstmt.setString(1, userID);
				  pstmt.setString(2, boardTitle);
				  pstmt.setString(3, boardContent);
				  pstmt.setString(4, boardFile);
				  pstmt.setString(5, boardRealFile);
				  pstmt.setInt(6, parent.getBoardGroup());
				  pstmt.setInt(7, parent.getBoardSequence() + 1);
				  pstmt.setInt(8, parent.getBoardLevel() + 1);
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
		  
		  public int replyUpdate(boardDTO parent) {
		   	  Connection conn = null;
			  PreparedStatement pstmt = null;		  
			  String SQL = "UPDATE BOARD SET boardSequence = boardSequence + 1 WHERE boardGroup = ? AND boardSequence > ?";
			  
				 try {
				  conn = ds.getConnection();
				  pstmt = conn.prepareStatement(SQL);
				  
				  pstmt.setInt(1, parent.getBoardGroup());
				  pstmt.setInt(2, parent.getBoardSequence());
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

		  public boolean nextPage(String pageNumber) {
			  Connection conn = null;
			  PreparedStatement pstmt = null;
			  ResultSet rs = null;
			  String SQL ="SELECT * FROM BOARD WHERE boardGroup >= ?";
			  try {
				  conn = ds.getConnection();
				  pstmt = conn.prepareStatement(SQL);
				  pstmt.setInt(1, Integer.parseInt(pageNumber) * 10);
				  rs = pstmt.executeQuery();
				  if(rs.next()) {
					return true; 
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
			  return false; 
		  
		  
		  }
		  
		  public int targetPage(String pageNumber) {
			  Connection conn = null;
			  PreparedStatement pstmt = null;
			  ResultSet rs = null;
			  String SQL ="SELECT COUNT(boardGroup) FROM BOARD WHERE boardGroup > ?";
			  try {
				  conn = ds.getConnection();
				  pstmt = conn.prepareStatement(SQL);
				  pstmt.setInt(1, (Integer.parseInt(pageNumber) -1 ) * 10);
				  rs = pstmt.executeQuery();
				  if(rs.next()) {
					return rs.getInt(1)/10;
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
			  return 0; 
		  
		  
		  }
		  
		  
}
	  
