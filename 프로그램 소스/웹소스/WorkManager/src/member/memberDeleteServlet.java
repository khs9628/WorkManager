package member;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/memberDeleteServlet")
public class memberDeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		 doPost(request,response);
		
		}
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		String EMP_NO = request.getParameter("EMP_NO");
		if(EMP_NO == null || EMP_NO.equals("")) {
			request.getSession().setAttribute("messageType", "오류 메세지");
			request.getSession().setAttribute("messageContent", "사원을 선택하시오.");
			response.sendRedirect("memberList.jsp");
			return;
		}
		
		memberDAO memberDAO = new memberDAO();
		int result = memberDAO.memberD(EMP_NO);
		if(result == 1) {
			request.getSession().setAttribute("messageType", "성공 메세지");
			request.getSession().setAttribute("messageContent", "성공적으로 삭제했습니다.");
			response.sendRedirect("memberList.jsp");
			return;
		}else {
			request.getSession().setAttribute("messageType", "오류 메세지");
			request.getSession().setAttribute("messageContent", "데이터 베이스 오류입니다.");
			response.sendRedirect("index.jsp");
			return;
		}
		
	}

}
