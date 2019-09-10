package user;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/UserRegisterServlet")
	
public class UserRegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		String userID = request.getParameter("userID");
		String userPassword = request.getParameter("userPassword");
		String userPassword2 = request.getParameter("userPassword2");
		String userName = request.getParameter("userName");
		String userGender = request.getParameter("userGender");
		String userEmail = request.getParameter("userEmail");
		
		//입력한것이 없을때 알림창을 띄워주는 코드
		if(userID == null || userID.equals("") ||
		   userPassword == null || userPassword.equals("") ||
		   userPassword2 == null || userPassword2.equals("") ||
		   userName == null || userName.equals("") ||
		   userGender == null || userGender.equals("") ||
		   userEmail == null || userEmail.equals("")) {
			request.getSession().setAttribute("messageType", "오류 메세지");
			request.getSession().setAttribute("messageContent", "모든 내용을 입력하시오");
			response.sendRedirect("joinForm.jsp");
			return;
		}
		if(!userPassword.equals(userPassword2)) {
			request.getSession().setAttribute("messageType", "오류 메세지");
			request.getSession().setAttribute("messageContent", "비밀 번호가 서로 다릅니다.");
			response.sendRedirect("joinForm.jsp");
			return;
		}
		
		int result = new userDAO().register(userID, userPassword, userName, userGender, userEmail);
		
		if(result == 1) {
			request.getSession().setAttribute("messageType", "성공 메시지");
			request.getSession().setAttribute("messageContent", "회원 가입에 성공했습니다.");
			response.sendRedirect("index.jsp");
			
		}
		else {
			request.getSession().setAttribute("messageType", "오류 메세지");
			request.getSession().setAttribute("messageContent", "이미 존재하는 회원입니다.");
			response.sendRedirect("joinForm.jsp");
			return;
		}
	}

}
