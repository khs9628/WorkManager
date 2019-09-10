package member;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/workPlusServlet")
public class workPlusServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
   
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		String EMP_NO = request.getParameter("EMP_NO");
		String A_TIME = request.getParameter("A_TIME");
		String E_TIME = request.getParameter("E_TIME");
		if(A_TIME == null || A_TIME.equals("") ||
		   E_TIME == null || E_TIME.equals("") ||
		   EMP_NO == null || EMP_NO.equals("")) 
	     	{  
			 request.getSession().setAttribute("messageType", "오류 메세지");
			 request.getSession().setAttribute("messageContent", "모든 내용을 입력하시오");
			 response.sendRedirect("index.jsp");
			 return;
				}
		String A_TIME1 = A_TIME.substring(0, 10) + A_TIME.substring(11);
		String E_TIME1 = E_TIME.substring(0, 10) + E_TIME.substring(11);
		System.out.println(A_TIME1 +"  " + E_TIME1);
		
	    memberDAO memberDAO = new memberDAO();
	    int result = memberDAO.memberWorkPlus(A_TIME1, E_TIME1, EMP_NO);		
		if(result==1) {
		request.getSession().setAttribute("messageType", "성공 메시지");
		request.getSession().setAttribute("messageContent", "성공적으로 근무가 추가 되었습니다.");
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
