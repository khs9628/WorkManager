<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="board.boardDAO"%>
<%@page import="java.io.*"%>
<%@page import="java.text.*"%>
<%@page import="java.lang.*"%>
<%@page import="java.util.*"%>
<%@page import="java.net.*"%>

<!DOCTYPE html>
<html lang="kor">

<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="WorkManagerProject">
    <meta name="author" content="Andsoo">

    <title>WM</title>
    </head>
<body>
	<%
		request.setCharacterEncoding("UTF-8");
		String boardID = request.getParameter("boardID");
		if(boardID == null || boardID.equals("")){
			session.setAttribute("messageType", "오류 메세지");
			session.setAttribute("messageContent", "접근할 수 없습니다.");
			response.sendRedirect("placeInfo.jsp");
			return;
		}
		
		String root = request.getSession().getServletContext().getRealPath("/");
		String savePath = root + "upload";
		// 서버에 실제 저장된 파일명
		String fileName ="";
		// 실제 내보낼 파일명
		String realFile ="";
		
		boardDAO boardDAO = new boardDAO();
		fileName = boardDAO.getFile(boardID);
		realFile = boardDAO.getRealFile(boardID);
		
		if(fileName.equals("") || realFile.equals("")){
			session.setAttribute("messageType", "오류 메세지");
			session.setAttribute("messageContent", "접근할 수 없습니다.");		
			response.sendRedirect("placeInfoContent.jsp");
			return;
		}
		
			InputStream in = null;
			OutputStream os = null;
			File file = null;
			boolean skip = false;
			String client = "";
			
			try {
				/*파일을 읽어 스트림에 저장  */
				try {
					  file = new File(savePath, realFile);
					  in = new FileInputStream(file);
				} catch (FileNotFoundException e) {
					skip = true;
				}
				 
				client = request.getHeader("User-Agent");
				 
				 /*파일 다운로드 헤더 지정  */
				 response.reset();
				 response.setContentType("application/octet-stream");
				 response.setHeader("Content-Description", "JSP Generated Data");
				
				 if(!skip){
					 
					 /* internetExploer or Chrome  */
					 if(client.indexOf("MSIE")	!= -1){
						 response.setHeader("Content-Disposition", "attachment; filename=" + new String(fileName.getBytes("KSC5601"), "ISO8859_1"));
					 }else{
						 /* 한글 파일명 처리 */
						 fileName = new String(fileName.getBytes("UTF-8"), "iso-8859-1");
						 
						 response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
						 response.setHeader("Content-Type", "application/octet-stream; charset=UTF-8");
					 }
					 response.setHeader("Content-Length", ""+ file.length());
				
					 os = response.getOutputStream();
					 byte b[] = new byte[(int)file.length()];
					 int leng = 0;
					 while( (leng = in.read(b)) > 0){
							os.write(b, 0, leng); 
					 	}
					  }else{
						 response.setContentType("text/html;charset=UTF-8");
						 out.println("<script>alert('파일을 찾을 수 없습니다.');history.back();</script>");
				 }
				 
				 in.close();
				 os.close();
				 
			} catch (Exception e) {
				e.printStackTrace();
			}
	%>
	
</body>
</html>