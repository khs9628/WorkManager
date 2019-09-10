<%@page import="java.util.ArrayList"%>
<%@page import="android.*"%>
<%@page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>

<%
   request.setCharacterEncoding("UTF-8");
   
   String returns = "";
   String type = request.getParameter("type");
   
   if(type == null)return;
   
   else if (type.equals("login_list")){
      
      String request_id = request.getParameter("login_list");
      String request_pw = request.getParameter("pw_list");
      System.out.println(request_id +"\t" + request_pw);
      System.out.println(type);
      
         System.out.println("값을 리턴합니다.");
         Login_Board vision_board = Login_Board.getLogin_Board();
         returns = vision_board.select(request_id, request_pw);
         out.println(returns);
        System.out.println(returns);
      }
   
   else if (type.equals("signUp_write")) {
      
      String name = request.getParameter("signUp_write");
      String id = request.getParameter("id_write");
      String email = request.getParameter("email_write");
      String pw = request.getParameter("pw_write");
      
       System.out.println("값을받았습니다." +name+ '\t' +id+ '\t' +email+ '\t' +pw);
       SignUp_Write vision_board = SignUp_Write.getWrite();
       returns = vision_board.write(name,id,email,pw);
       out.println(returns);
       System.out.println(returns);
    }
   
   else if (type.equals("signUp_list")) {
      
      String id_request = request.getParameter("signUp_list");
      
       System.out.println("값을 리턴합니다.");
       SignUp_Board vision_board = SignUp_Board.getSignUp_Board();
       returns = vision_board.select(id_request);
       out.println(returns);
      System.out.println(returns);
    }
   
   else if (type.equals("findPw_write")) {
      
      String pw = request.getParameter("findPw_write");
      String id = request.getParameter("id_write");
      
         System.out.println(type);  
         System.out.println("값을받았습니다."+pw+"\t"+id);
         FindPw_Write vision_board = FindPw_Write.getWrite();
         returns = vision_board.write(pw, id);
         out.println(returns);
         System.out.println(returns);
         
         response.sendRedirect("emailSendAction.jsp?EMP_ID="+id);
      }
   
   else if (type.equals("findPw_list")) {
      
      String request_id = request.getParameter("findPw_list");
      String request_name = request.getParameter("name_list");
      String request_email = request.getParameter("email_list");
      
         System.out.println(type);
         System.out.println("값을 리턴합니다.");
         FindPw_Board vision_board = FindPw_Board.getFindPw_Board();
         returns = vision_board.select(request_id, request_name, request_email);
         out.println(returns);
        System.out.println(returns);
      }
   
   else if (type.equals("calendar_write")) {
      
      String content = request.getParameter("calendar_write");
      String memberId = request.getParameter("memberId_write");
      String date = request.getParameter("date_write");
      
      System.out.println(type);
       System.out.println("값을받았습니다." +content+ '\t' +memberId);
       Calendar_Write vision_board = Calendar_Write.getWrite();
       returns = vision_board.write(content,memberId, date);
       out.println(returns);
       System.out.println(returns);
    }
   
   else if (type.equals("calendar_list")) {
      
      String userId_request = request.getParameter("calendar_list");
      
      System.out.println(type);
       System.out.println("값을 리턴합니다.");
       Calendar_Board vision_board = Calendar_Board.getCalendar_Board();
       returns = vision_board.select(userId_request);
       out.println(returns);
      System.out.println(returns);
    }
   
   
   else if (type.equals("reviseRequest_write")) {
      
      String date = request.getParameter("reviseRequest_write");
      String sTime = request.getParameter("sTime_write");
      String eTime = request.getParameter("eTime_write");
      String content = request.getParameter("content_write");
      String memberId = request.getParameter("memberId_write");
      
      System.out.println(type);
       System.out.println("값을받았습니다." +date+ '\t' +sTime+ '\t' +eTime+ '\t' +content+ '\t' +memberId);
       ReviseRequest_Write vision_board = ReviseRequest_Write.getWrite();
       returns = vision_board.write(date,sTime,eTime,content,memberId);
       out.println(returns);
       System.out.println(returns);
    }
   
   else if (type.equals("hireInfo_list")) {
      
      String id_request = request.getParameter("hireInfo_list");
      
      System.out.println(type);
      System.out.println(id_request);
       System.out.println("값을 리턴합니다.");
       PlaceInfo_Board vision_board = PlaceInfo_Board.getPlaceInfo_Board();
       returns = vision_board.select(id_request);
       out.println(returns);
      System.out.println(returns);
    }
   
   else if (type.equals("reviseInfo_write")) {
      
      String value = request.getParameter("reviseInfo_write");
      String index = request.getParameter("index_write");
      String emp_no = request.getParameter("emp_no_write");
      
      System.out.println(type);
       System.out.println("값을받았습니다." +value+ '\t' +index+ '\t' +emp_no);
       ReviseInfo_Write vision_board = ReviseInfo_Write.getWrite();
       returns = vision_board.write(value, index, emp_no);
       out.println(returns);
       System.out.println(returns);
    }
    
    
  else if (type.equals("reviseInfo_list")) {
     
     String id_request = request.getParameter("reviseInfo_list"); 
     
     System.out.println(type);
       System.out.println("값을 리턴합니다.");
       ReviseInfo_Board vision_board = ReviseInfo_Board.getReviseInfo_Board();
       returns = vision_board.select(id_request);
       out.println(returns);
      System.out.println(returns);
    }
   
  else if (type.equals("calSalary_list")){
      
      String request_id = request.getParameter("calSalary_list");
      System.out.println(type);
      
         System.out.println("값을 리턴합니다.");
         CalSalary_Board vision_board = CalSalary_Board.getCalSalary_Board();
         returns = vision_board.select(request_id);
         out.println(returns);
        System.out.println(returns);
      }
   
  else if (type.equals("byPeriod_list")){
      
      String request_id = request.getParameter("byPeriod_list");
      String startDate = request.getParameter("startDate_list");
      String endDate = request.getParameter("endDate_list");
      System.out.println(type);
      
         System.out.println("값을 리턴합니다.");
         System.out.println("id: "+request_id+ "\tstart: "+startDate+"\tend: "+ endDate);
         ByPeriod_Board vision_board = ByPeriod_Board.getByPeriod_Board();
         returns = vision_board.select(request_id, startDate, endDate);
         out.println(returns);
        System.out.println(returns);
      }
  else if (type.equals("addWorktime_write")){
      
      String request_id = request.getParameter("addWorktime_write");
      String status = request.getParameter("status");
      System.out.println(type);
      
         System.out.println("값을 리턴합니다.");
         System.out.println("id: "+request_id+ "\tstatus: "+status);
         AddWorktime_Write vision_write = AddWorktime_Write.getWrite();
         returns = vision_write.write(request_id, status);
         out.println(returns);
        System.out.println(returns);
      }
   
  else if (type.equals("NFCMain_list")){
      System.out.println(type);
      
      String request_id = request.getParameter("NFCMain_list");
      System.out.println("id: "+request_id);
         System.out.println("값을 리턴합니다.");
   
         NFCMain nfc = new NFCMain();
         returns = nfc.run();
         out.println(returns);
        System.out.println(returns);
        
      }
  
   
   %>
   