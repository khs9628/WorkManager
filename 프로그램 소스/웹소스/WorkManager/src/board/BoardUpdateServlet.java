package board;

import java.io.File;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

@WebServlet("/BoardUpdateServlet")
public class BoardUpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		MultipartRequest multi = null;
		int fileMaxSize = 10 * 1024 * 1024;
		String savePath = request.getRealPath("/upload").replaceAll("\\\\", "/");
		try {
			multi = new MultipartRequest(request,savePath,fileMaxSize,"UTF-8", new DefaultFileRenamePolicy());
		} catch (Exception e) {
			request.getSession().setAttribute("messageType", "오류 메세지");
			request.getSession().setAttribute("messageContent", "파일크기는 10MB를  넘을수 없습니다.");
			response.sendRedirect("index.jsp");
			return;
		}
		String userID = multi.getParameter("userID");
		HttpSession session = request.getSession();
		
		if(!userID.equals((String) session.getAttribute("userID"))){
			request.getSession().setAttribute("messageType", "오류 메세지");
			request.getSession().setAttribute("messageContent", "접근 권한이 없습니다.");
			response.sendRedirect("index.jsp");
			return;
		}
		String boardID = multi.getParameter("boardID");
		if(boardID == null || boardID.equals("")) {
			request.getSession().setAttribute("messageType", "오류 메세지");
			request.getSession().setAttribute("messageContent", "접근 권한이 없습니다.");
			response.sendRedirect("index.jsp");
			return;
		}
		boardDAO boardDAO = new boardDAO();
		boardDTO board = boardDAO.getBoard(boardID);
		if(!userID.equals(board.getUserID())){
			request.getSession().setAttribute("messageType", "오류 메세지");
			request.getSession().setAttribute("messageContent", "접근 권한이 없습니다.");
			response.sendRedirect("index.jsp");
			return;
		}
		
		String boardTitle = multi.getParameter("boardTitle"); 
		String boardContent = multi.getParameter("boardContent");
		if(boardTitle.equals("") || boardTitle == null ||boardContent.equals("")|| boardContent == null){
			request.getSession().setAttribute("messageType", "오류 메세지");
			request.getSession().setAttribute("messageContent", "내용을 모두 채워주세요.");
			response.sendRedirect("placeWrite.jsp");
			return;
		}
		
		String boardFile ="";
		String boardRealFile ="";
		File file = multi.getFile("boardFile");
		 if(file != null) {
			boardFile = multi.getOriginalFileName("boardFile");
			boardRealFile = file.getName();
			String prev = boardDAO.getRealFile(boardID);
			File prevFile = new File(savePath + "/" + prev);
			if(prevFile.exists()) {
				prevFile.delete();
			}
		 }else {
			 boardFile = boardDAO.getFile(boardID);
			 boardRealFile = boardDAO.getRealFile(boardID);
		 }
		 boardDAO.update(boardID, boardTitle, boardContent, boardFile, boardRealFile);
		 request.getSession().setAttribute("messageType", "성공 메세지");
		 request.getSession().setAttribute("messageContent", "성공적으로 게시물이 수정되었습니다.");
		 response.sendRedirect("placeInfo.jsp");
		 return;
		 
		}
	
}