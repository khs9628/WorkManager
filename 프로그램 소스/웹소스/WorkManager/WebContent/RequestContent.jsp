<%@page import="member.memberDTO"%>
<%@page import="member.memberDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="kor">

<%
String REQ_ID = null;
if(request.getParameter("REQ_ID") != null){
	REQ_ID = (String) request.getParameter("REQ_ID");
}
if(REQ_ID==null || REQ_ID.equals("")){
session.setAttribute("messageType", "오류 메시지");
session.setAttribute("messageContent", "사원을 선택해주세요");
response.sendRedirect("index.jsp");
return;
}

memberDAO memberDAO = new memberDAO();
memberDTO member = memberDAO.getRequest(REQ_ID);
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

    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
        <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->

</head>

<body>

    	<%
		String userID = null;
		if(session.getAttribute("userID") != null){
			userID = (String) session.getAttribute("userID");
		}
			
	%>
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
                      <%
                    	if(userID == null) {
                    %>
        
                    <ul class="dropdown-menu dropdown-user">
                        <li><a href="loginForm.jsp"><i class="fa fa-user fa-fw"></i> 로그인</a>
                        </li>
                        <li><a href="joinForm.jsp"><i class="fa fa-gear fa-fw"></i> 회원가입</a>
                        </li>
                    </ul>
                    <%
                    	}else{
                    %>
                   <ul class="dropdown-menu dropdown-user">
                        <li><a href="logoutAction.jsp"><i class="fa fa-user fa-fw"></i>로그아웃</a>
                        </li>
                   </ul>
                    <%
                    	}
                    %>
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


	
	<div id="page-wrapper">
 	 <div class="container">
		<div class= "row">
			<table class = "table table-striped" style = "text-align : center; border : 1px solid #dddddd">
			<thead>
						<tr>
							<th colspan ="3" style = "background-color:#eeeeee; text-align : center;">일정수정 요청</th>
						</tr>
				</thead>
				
				<tbody>
					<tr>
						<td style = "width:20%;">글 제목</td>
						<td colspan = "2"><%= member.getREQ_TITLE() %></td>
					</tr>
					<tr>
						<td>작성자</td>
						<td colspan = "2"><%=member.getREQ_EMP_NO() %></td>
					</tr>
					
					<tr>
						<td>작성일자</td>
						<td colspan = "2">
						<%=member.getREQ_DATE() %>
						</td>
						</tr>
					<tr>
						<td>내용</td>
						<td colspan = "2" style = "min-height:200px;"><%= member.getREQ_CONTENT() %></td>
					</tr>
				</tbody>
			</table>
			<a href ="workUpdateRequest.jsp" class ="btn btn-primary">목록</a>
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

    <!-- Morris Charts JavaScript -->
    <script src="bootstrap/vendor/raphael/raphael.min.js"></script>
    <script src="bootstrap/vendor/morrisjs/morris.min.js"></script>
    <script src="bootstrap/data/morris-data.js"></script>

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