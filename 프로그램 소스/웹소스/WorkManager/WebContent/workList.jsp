<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>  
<%@page import="member.memberDAO"%>
<%@page import="member.memberDTO"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="kor">
<%
	String userID = null;
	if(session.getAttribute("userID") != null){
	userID = (String) session.getAttribute("userID");
	}
	if(userID == null || userID.equals("")){
		session.setAttribute("messageType", "오류 메시지");
		session.setAttribute("messageContent", "접근 권한이 없습니다.");
		response.sendRedirect("index.jsp");
		return;
	}
	request.setCharacterEncoding("UTF-8");
	ArrayList<memberDTO> memberList = new memberDAO().getMemberWork();
	request.setAttribute("memberList", memberList);
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



  <!-- wrapper  -->

       
                <div id="page-wrapper">
                <br>
                    <div class="row">
                        <div class="col-lg-12">
                            <h1 class="page-header">&nbsp;&nbsp;근무 목록 </h1>
                           <button class="btn btn-info pull-right" type="button" onclick="location.href='workUpdateRequest.jsp'">근무수정요청 목록</button>
                          </div>
                        <!-- /.col-lg-12 -->
                       
	
                    <!-- /.row -->
                    <div class="row">
                        <div class="col-lg-12">
                            <div class="panel panel-default">
                                <div class="panel-heading">
                                   			 근무 기록  <button class="btn btn-default fa fa-plus-circle" data-toggle="modal" data-target="#myModal1"></button>
                            <!-- Modal -->
                                                    
                                 <div class="modal fade" id="myModal1" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                                	<div class="modal-dialog">
                                    <div class="modal-content">
                                        <div class="modal-header">
                                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                                            <h4 class="modal-title" id="myModalLabel">근무기록 추가</h4>
                                        </div>
                                        <div class="modal-body">
  								
									<form method = "post" action ="./workPlus" style = "backgroundcolor: gray;">
          							<table class = "table table-striped" style = "text-align : center; border : 1px solid #dddddd">
          							<tbody>
          							<tr>
          						<td>사원번호 : <input type ="text" class ="form-control" placeholder = "사원번호" name ="EMP_NO" maxlength = "50"></td>
          							</tr>
          							<tr>
          						<td>id : <input type ="text" class ="form-control" placeholder = "id(핸드폰 번호)" name ="EMP_ID" maxlength = "50"></td>
          							</tr>
          							<tr>
          					<td>출근시간 : <input type ="datetime-local" class ="form-control" placeholder = "출근시간" name ="A_TIME"></td>
          							</tr>
          							<tr>
          					<td>퇴근시간 : <input type ="datetime-local" class ="form-control" placeholder = "퇴근시간" name ="E_TIME"></td>
          							</tr>
          							<tr>
          					<td><input type ="submit" class ="btn btn-primary pull-right" value ="추가"></td>
          							</tr>
          				</tbody>
          			</table>
          					</form>
							
						</div>
				                        <div class="modal-footer">
                                            <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                                        </div>
                                    </div>
                                    <!-- /.modal-content -->
                                </div>
                                <!-- /.modal-dialog -->
                                </div>
                                <!-- /.modal-fade -->
                            <!-- /모달창 -->
						</div>
                                <!-- /.panel-추가 -->
                                <div class="panel-body">
                                    <table width="100%" class="table table-striped table-bordered table-hover" id="dataTables-example">
                                        <thead>
                                            <tr>
                                                <th style = "text-align :center">직원명</th>
                                                <th style = "text-align :center">아이디(전화번호)</th>
                                                <th style = "text-align :center">출근시간</th>
                                                <th style = "text-align :center">퇴근시간</th>
                                                <th style = "text-align :center">옵션</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                       
										<c:forEach var ="member" items ="${memberList}" varStatus="m">	  
											    <tr class="odd gradeX">
                                                <td style = "text-align :center">${member.EMP_NO}.${member.EMP_NAME}</td>
                                                <td style = "text-align :center">${member.EMP_ID}</td>
                                                <td style = "text-align :center"><button class="btn btn-default" type="button">${member.a_TIME}</button></td>
                                                <td style = "text-align :center"><button class="btn btn-default" type="button">${member.e_TIME}</button></td>
                                                <td style = "text-align :center">
                                         <button class="btn btn-default fa fa-edit" data-toggle="modal" data-target="#myModal${m.index}" onclick="document.getElementById('modalId${m.index}').style.display='block'"></button>
                                         <button class="btn btn-default fa fa-times" onclick= "location.href='workDelete?WORKTIME_ID=${member.WORKTIME_ID}'"></button>
                                    <div class="modal fade" id="myModal${m.index}" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                                	<div class="modal-dialog">
                                    <div class="modal-content">
                                        <div class="modal-header">
                                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                                            <h4 class="modal-title" id="myModalLabel">근무기록 수정</h4>
                                        </div>
                                        <div class="modal-body">
  								
									<form method = "post" action ="./workUpdate" style = "backgroundcolor: gray;">
          							<table class = "table table-striped" style = "text-align : center; border : 1px solid #dddddd">
          							<tbody>
          							<tr>
          						<td>사원번호 : <input type ="text" class ="form-control" placeholder = "사원번호" name ="EMP_NO" maxlength = "50" value="${member.EMP_NO}"></td>
          							</tr>
          							<tr>
          						<td>id : <input type ="text" class ="form-control" placeholder = "id(핸드폰 번호)" name ="EMP_ID" maxlength = "50" value="${member.EMP_ID}"></td>
          							</tr>
          							<tr>
          					<td>출근시간 : <input type ="datetime-local" class ="form-control" placeholder = "출근시간" name ="A_TIME"></td>
          							</tr>
          							<tr>
          					<td>퇴근시간 : <input type ="datetime-local" class ="form-control" placeholder = "퇴근시간" name ="E_TIME"></td>
          							</tr>
          							<tr>
          					<td><input type ="hidden" class ="form-control" name ="WORKTIME_ID" value="${member.WORKTIME_ID}"></td>
          							</tr>
          							<tr>
          					<td><input type ="submit" class ="btn btn-primary pull-right" value ="수정"></td>
          							</tr>
          				</tbody>
          			</table>
          					</form>
							
						</div>
				                        <div class="modal-footer">
                                            <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                                        </div>
                                    </div>
                                    <!-- /.modal-content -->
                                </div>
                                <!-- /.modal-dialog -->
                                </div>
                                <!-- /.modal-fade -->
                            <!-- /모달창 -->
                                                </td>
                                                </tr>
                           
                                        </c:forEach>
                                          </tbody>
                                       </table>
                               
                                 	 
                                    <!-- /.table-responsive -->
                                    </div>
                                    </div>
                                    </div>
                      				</div>	
                      				</div>
                      				</div>
                                    <!-- 모달창 -->
                                   
                                  
                           
    <!-- /#wrapper -->

       
       
       
       
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

    <!-- DataTables JavaScript -->
    <script src="bootstrap/vendor/datatables/js/jquery.dataTables.min.js"></script>
    <script src="bootstrap/vendor/datatables-plugins/dataTables.bootstrap.min.js"></script>
    <script src="bootstrap/vendor/datatables-responsive/dataTables.responsive.js"></script>

    <!-- Custom Theme JavaScript -->
    <script src="bootstrap/dist/js/sb-admin-2.js"></script>

    <!-- Page-Level Demo Scripts - Tables - Use for reference -->
    <script>
    $(document).ready(function() {
        $('#dataTables-example').DataTable({
            responsive: true
        });
    });
    
    </script>
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
