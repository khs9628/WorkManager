<%@page import="board.boardDTO"%>
<%@page import="board.boardDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="kor">

<%
	
	String boardID = null;
	if(request.getParameter("boardID") != null){
		boardID = (String) request.getParameter("boardID");
	}
	if(boardID==null || boardID.equals("")){
		session.setAttribute("messageType", "오류 메시지");
		session.setAttribute("messageContent", "게시물을 선택해주세요.");
		response.sendRedirect("index.jsp");
		return;
	}
	boardDAO boardDAO = new boardDAO();
	boardDTO board = boardDAO.getBoard(boardID);
	boardDAO.hit(boardID);
	
	String root = request.getSession().getServletContext().getRealPath("/");
	String savePath = root + "upload" +"\\"+ board.getBoardRealFile();
	System.out.println(savePath);
	
%>
<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="WorkManagerProject">
    <meta name="author" content="Andsoo">

    <title>WM</title>

    <!-- Bootstrap Core CSS -->
    <link href="bootstrap/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">

    <!-- MetisMenu CSS -->
    <link href="bootstrap/vendor/metisMenu/metisMenu.min.css" rel="stylesheet">

    <!-- Custom CSS -->
    <link href="bootstrap/dist/css/sb-admin-2.css" rel="stylesheet">

    <!-- Custom Fonts -->
    <link href="bootstrap/vendor/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">

  
</head>

<body>
	 <div id="wrapper">

      
 	 <div class="container">
		<div class= "row">
			<table class = "table table-striped" style = "text-align : center; border : 1px solid #dddddd">
			<thead>
						<tr>
							<th colspan ="4" style = "background-color:#eeeeee; text-align : center;">공지사항</th>
						</tr>
						<tr>
						<td>작성일자</td>
						<td><%=board.getBoardDate()%></td>
						<td>조회수</td>
						<td><%=board.getBoardHit()%></td>
						</tr>
						<tr>
						<td style = "width:20%;">글 제목</td>
						<td colspan = "3"><%=board.getBoardTitle() %></td>
						</tr>
						<tr>
						<td>작성자</td>
						<td colspan = "3"><%=board.getUserID()%></td>
						</tr>
						
						<tr>
						<td style ="vertical-align:middle; min-height:150px;">내용</td>
						<td colspan = "3" style="text-align:center;"><%=board.getBoardContent() %> <br>
						<img src="<%=request.getContextPath()%>/upload/<%=board.getBoardRealFile() %>" width ="100%"/>
						</td>
						</tr>
						
				</thead>
				
				<tbody>
				<tr>
					<td colspan ="4" style ="text-align:right;">
					<a href ="MobliePlaceInfo.jsp" class ="btn btn-primary">목록</a>
					
					
					</td>
				</tr>
				</tbody>
			</table>
	
  	</div>
  </div>
  </div>

       
  
	
    <!-- jQuery -->
    <script src="bootstrap/vendor/jquery/jquery.min.js"></script>

    <!-- Bootstrap Core JavaScript -->
    <script src="bootstrap/vendor/bootstrap/js/bootstrap.min.js"></script>

    <!-- Metis Menu Plugin JavaScript -->
    <script src="bootstrap/vendor/metisMenu/metisMenu.min.js"></script>


    <!-- Custom Theme JavaScript -->
    <script src="bootstrap/dist/js/sb-admin-2.js"></script>


	  <% 
	
	  String messageContent = null;
		if(session.getAttribute("messageContent") != null){
		messageContent = (String) session.getAttribute("messageContent");
	}
	
	String messageType = null;
	if(session.getAttribute("messageType") != null){
		messageType = (String) session.getAttribute("messageType");
	}
	
	if(messageContent != null){
	
	%>
		<div class="modal fade" id ="messageModal" tabindex ="-1" role ="dialog" aria-hidden ="true">
		<div class="vertical-alignment-helper">
			<div class="modal-dialog vertical-align-center">
				<div class="modal-content <% if(messageType.equals("오류 메세지")) out.println("panel-waring"); else out.println("panel-success"); %>">
					<div class ="modal-header panel-heading">
						<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">&times;</span>
						<span class="sr-only">Close</span>
						</button>
						<h4 class ="modal-title">
						<%= messageType %>
						</h4>	
					</div>
					<div class="modal-body">
						<%= messageContent %>
					</div>
					<div class="modal-footer">
					 <button type ="button" class="btn btn-primary" data-dismiss="modal">확인</button>
					</div>
				</div>
			</div>
		</div>
	</div>
	<script>
	$('#messageModal').modal("show");
	</script>
		<% 
		session.removeAttribute("messageContent");
	    session.removeAttribute("messageType");
	
	}
%>
	
</body>

</html>