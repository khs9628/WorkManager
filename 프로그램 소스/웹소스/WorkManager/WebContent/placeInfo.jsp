<%@page import="board.boardDAO"%>
<%@page import="board.boardDTO"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="kor">
<%
		
		String pageNumber = "1";
		if(request.getParameter("pageNumber")!= null){
			pageNumber = request.getParameter("pageNumber");
		}
		try{
			Integer.parseInt(pageNumber);
		}catch(Exception e){
			session.setAttribute("messageType", "오류 메시지");
			session.setAttribute("messageContent", "페이지 번호가 잘못되었습니다.");
			response.sendRedirect("placeInfo.jsp");
			return;
		}
		ArrayList<boardDTO> boardList = new boardDAO().getList(pageNumber);
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

    <!-- Morris Charts CSS -->
    <link href="bootstrap/vendor/morrisjs/morris.css" rel="stylesheet">

    <!-- Custom Fonts -->
    <link href="bootstrap/vendor/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">



</head>

<body>

    <div id="wrapper">

        <!-- Navigation -->
        <nav class="navbar navbar-default navbar-static-top" role="navigation" style="margin-bottom: 0">
            <div class="navbar-header">
                <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <a class="navbar-brand" href="index.jsp">WorkManager</a>
            </div>
            <!-- /.navbar-header -->

            <ul class="nav navbar-top-links navbar-right">

				<li class="dropdown">
                    <a class="dropdown-toggle" data-toggle="dropdown" href="#">
                        <i class="fa fa-user fa-fw"></i> <i class="fa fa-caret-down"></i>
                    </a>
                  
                   <ul class="dropdown-menu dropdown-user">
                        <li><a href="logoutAction.jsp"><i class="fa fa-user fa-fw"></i>로그아웃</a>
                        </li>
                   </ul>
                    <!-- /.dropdown-user -->
                </li>
                <!-- /.dropdown -->
            </ul>
            <!-- /.navbar-top-links -->

            <div class="navbar-default sidebar" role="navigation">
                <div class="sidebar-nav navbar-collapse">
                    <ul class="nav" id="side-menu">
                        <li class="sidebar-search">
                            <div class="input-group custom-search-form">
                                <input type="text" class="form-control" placeholder="Searchbootstrap.">
                                <span class="input-group-btn">
                                <button class="btn btn-default" type="button">
                                    <i class="fa fa-search"></i>
                                </button>
                            </span>
                            </div>
                            <!-- /input-group -->
                        </li>
                        <li>
                            <a href="working.jsp"><i class="fa fa-dashboard fa-fw"></i> Working</a>
                        </li>

                        <li>
                            <a href="workList.jsp"><i class="fa fa-bar-chart-o fa-fw"></i> 근무지 관리</a>
                        </li>

                        <li>
                          <a href="#"><i class="fa fa-wrench fa-fw"></i> 사업장 관리<span class="fa arrow"></span></a>
                          <ul class="nav nav-second-level">
                              <li>
                                  <a href="placeInfo.jsp">공지사항</a>
                              </li>
                               <li>
                                  <a href="placeExpense.jsp">예상 지출</a>
                              </li>
                            </ul>
                        </li>

                        <li>
                            <a href="#"><i class="fa fa-user fa-fw"></i> 사원<span class="fa arrow"></span></a>
                            <ul class="nav nav-second-level">
                                <li>
                                    <a href="memberList.jsp">사원 목록</a>
                                </li>
								
								 <li>
                                    <a href="salary.jsp">급여</a>
                                </li>
                                   
                                  

                            </ul>
                            <!-- /.nav-second-level -->
                        </li>
                        <li>
                            <a href="#"><i class="fa fa-files-o fa-fw"></i> Sample Pages<span class="fa arrow"></span></a>
                            <ul class="nav nav-second-level">
                                <li>
                                    <a href="AndroidJoin.jsp">안드로이드 가입 예제</a>
                                </li>
                                <li>
                                    <a href="1n.jsp">404에러</a>
                                </li>
                            </ul>
                            <!-- /.nav-second-level -->
                        </li>
                    </ul>
                </div>
                <!-- /.sidebar-collapse -->
            </div>
            <!-- /.navbar-static-side -->
        </nav>

 	
 	<!--page wrapper  -->
  	  <div id="page-wrapper">
  	  <h1 class="page-header">&nbsp;&nbsp;공지 사항
  	  <button class="btn btn-info pull-right" type="button" onclick="location.href='Calendar/fullcalendar.html'">일정표 생성</button>
  	  </h1>
  	                    
          <div class="container">
            <br>
          		<div class= "row">
          			<table class = "table table-striped table-hover" style = "text-align : center; border : 1px solid #dddddd">

          				<thead>
          						<tr>
          							<th style = "background-color:#eeeeee; text-align : center;">번호</th>
          							<th style = "background-color:#eeeeee; text-align : center;">제목</th>
          							<th style = "background-color:#eeeeee; text-align : center;">작성자</th>
          							<th style = "background-color:#eeeeee; text-align : center;">작성일</th>
          							<th style = "background-color:#eeeeee; text-align : center;">조회수</th>
          						</tr>
          				</thead>

          				<tbody>
								<%
								for(int i =0; i < boardList.size(); i++){ 
									boardDTO board = boardList.get(i);	
								%>
          						<tr>
          							<td style ="text-align:center;"><%= board.getBoardID()%></td>
          							<td style ="text-align:left;"><a href ="placeInfoContent.jsp?boardID=<%= board.getBoardID()%>">
          						<!-- 답글 들여쓰기  -->	
          						<%
          						  for(int j =0; j< board.getBoardLevel();j++){
          						%>	
          							<span class ="glyphicon glyphicon-arrow-right" aria-hidden="true"></span>
          							
          						<%
          							}
          						%>	<%=board.getBoardTitle() %></a></td>
          							
          							<td style ="text-align:center;"><%= board.getUserID()%></td>
          							<td style ="text-align:center;"><%= board.getBoardDate()%></td>
          							<td style ="text-align:center;"><%= board.getBoardHit()%></td>
          						</tr>
								<%
									}
								%>
								<tr>
					<td colspan ="5">
          			<a href ="placeWrite.jsp" class ="btn btn-primary pull-right">글쓰기</a>&nbsp;&nbsp;
 						<ul class="pagination" style="margin: 0 auto;">
 						<%
 							 int startPage = (Integer.parseInt(pageNumber) / 10) * 10 + 1;
 							 if(Integer.parseInt(pageNumber) % 10 == 0) startPage -= 10;
 							 int targetPage = new boardDAO().targetPage(pageNumber);
 							 if(startPage != 1){
 						%>		 
 							<li><a href="placeInfo.jsp?pageNumber=<%=startPage - 1 %>"><span class="glyphicon glyphicon-chevron-left"></span></a></li>
 						<%  
 						} else{
 						%>	
 							<li><span class="glyphicon glyphicon-chevron-left" style="color: gray;"></span></li>
 	 					<%
 						}
 							 for(int i= startPage; i < Integer.parseInt(pageNumber); i++) {
 						%>
 							<li><a href="placeInfo.jsp?pageNumber=<%= i %>"><%= i %></a></li>
 						<%
 							 }
 						%>
 						<li class="active"><a href="placeInfo.jsp?pageNumber=<%= pageNumber %>"><%= pageNumber %></a></li>
 						<%
 						for(int i= Integer.parseInt(pageNumber) + 1; i <= targetPage + Integer.parseInt(pageNumber); i++) {
 	 						if(i< startPage + 10) {
 						%>
 						<li><a href="placeInfo.jsp?pageNumber=<%=i %>"><%=i %></a></li>
 						<%
 							}
 						}
 						if(targetPage + Integer.parseInt(pageNumber) > startPage + 9){
 						%>
 						<li><a href="placeInfo.jsp?pageNumber=<%=startPage + 10%>"><span class="glyphicon glyphicon-chevron-right"></span></a></li>
 						
 						<%
 						} else {
 						%>
 						<li><span class="glyphicon glyphicon-chevron-right" style="color: gray;"></span></li>
 	 					<%
 						}
 						%>
 							</ul>
 						</td>
 					</tr>
          		</tbody>
          	</table>
          </div>
         </div>
        </div>

       
    <!-- /#wrapper -->
       <footer style="background-color: #000000; color: #ffffff">
          <div class="row">
             <div class="col-sm-3" style="text-align: center;">
               <img src="bootstrap/img/logoB.png" style="width: 66px; height: 66px;">
             </div>
             <div class="col-sm-6" style="text-align: center;">
                <h5>Copyright &copy; 2O18</h5>
                <h5>WorkManager</h5>
             </div>
             <div class="col-sm-3" style="text-align: center;">
               <img src="bootstrap/img/logo2.PNG" style="width: 66px; height: 66px;">
             </div>
          </div>
       </footer>
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
				<div class="modal-content <% if(messageType.equals("오류 메세지")) out.println("panel-warning"); else out.println("panel-success"); %>">
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
	
	<script type="text/javascript">
	$('#messageModal').modal("show");
	</script>
	<% 
		session.removeAttribute("messageContent");
	    session.removeAttribute("messageType");
	}
%>

</body>

</html>