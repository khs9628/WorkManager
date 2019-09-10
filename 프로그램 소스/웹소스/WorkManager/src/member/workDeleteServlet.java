package member;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class workDeleteServlet
 */
@WebServlet("/workDeleteServlet")
public class workDeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		String WORKTIME_ID = request.getParameter("WORKTIME_ID");
		
		if(WORKTIME_ID == null || WORKTIME_ID.equals(""))
	     	{  
			 request.getSession().setAttribute("messageType", "오류 메세지");
			 request.getSession().setAttribute("messageContent", "모든 내용을 입력하시오");
			 response.sendRedirect("index.jsp");
			 return;
				}
		
	    memberDAO memberDAO = new memberDAO();
	    int result = memberDAO.deleteWork(WORKTIME_ID);		
		if(result==1) {
		request.getSession().setAttribute("messageType", "성공 메시지");
		request.getSession().setAttribute("messageContent", "성공적으로 근무가 삭제 되었습니다.");
		response.sendRedirect("workList.jsp");
		return;
		}else {
			request.getSession().setAttribute("messageType", "오류 메세지");
			request.getSession().setAttribute("messageContent", "데이터 베이스 오류입니다.");
			response.sendRedirect("index.jsp");
			return;
		}
	}

}