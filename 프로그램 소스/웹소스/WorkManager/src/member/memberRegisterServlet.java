package member;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class memberRegisterServlet
 */
@WebServlet("/memberRegisterServlet")
public class memberRegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException { 
	request.setCharacterEncoding("UTF-8");
	response.setContentType("text/html;charset=UTF-8");
	String EMP_ID = request.getParameter("EMP_ID");
	String EMP_PW = request.getParameter("EMP_PW");
	String EMP_NAME = request.getParameter("EMP_NAME");
	String EMP_EMAIL = request.getParameter("EMP_EMAIL");
	
	//입력한것이 없을때 알림창을 띄워주는 코드
	if(EMP_ID == null || EMP_ID.equals("") ||
	   EMP_EMAIL == null || EMP_EMAIL.equals("") ||
	   EMP_PW == null || EMP_PW.equals("") ||
	   EMP_NAME == null || EMP_NAME.equals("")) {
	   request.getSession().setAttribute("messageType", "오류 메세지");
		request.getSession().setAttribute("messageContent", "모든 내용을 입력하시오");
		response.sendRedirect("index.jsp");
		return;
	}
	
	System.out.println(EMP_ID+EMP_EMAIL+EMP_PW+EMP_NAME);
	
	memberDAO memberDAO = new memberDAO();
	int result = memberDAO.memberRegister(EMP_ID, EMP_PW, EMP_NAME, EMP_EMAIL);	
	if(result==1) {
	request.getSession().setAttribute("messageType", "성공 메시지");
	request.getSession().setAttribute("messageContent", "사원 가입신청이 도착했습니다.");
	response.sendRedirect("index.jsp");
	return;
	}else {
		request.getSession().setAttribute("messageType", "오류 메세지");
		request.getSession().setAttribute("messageContent", "데이터 베이스 오류입니다.");
		response.sendRedirect("index.jsp");
		return;
	}
}
}
