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
	
	ArrayList<memberDTO> memberList = new memberDAO().getMemberHire();
	ArrayList<memberDTO> memberList1 = new memberDAO().getMemberBasic();
	request.setCharacterEncoding("UTF-8");
	request.setAttribute("memberList", memberList);
	request.setAttribute("memberList1", memberList1);
	
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


 <div id="page-wrapper">
  <!-- wrapper  -->
	<div class="row">
                <div class="col-lg-12">
                    <div class="panel panel-default">
                        
 							<h1 class="page-header">&nbsp;&nbsp;사원 목록</h1>
                           <button class="btn btn-info pull-right" type="button" onclick="location.href='memberPlus.jsp'">직원 추가</button>
                        
                        <!-- /.panel-heading -->
                        <div class="panel-body">
                            <!-- Nav tabs -->
                            <ul class="nav nav-tabs">
                                <li class="active"><a href="#home" data-toggle="tab">사원</a>
                                </li>
                                <li><a href="#profile" data-toggle="tab">전체 데이터</a>
                                </li>
                             </ul>

                            <!-- Tab panes -->
                            <div class="tab-content">
                                <div class="tab-pane fade in active" id="home">
                                    <table width="100%" class="table table-striped table-bordered table-hover" id="dataTables-example">
                                        <thead>
                                            <tr>
                                               	<th style = "text-align :center">아이디(전화번호)</th>
                                                <th style = "text-align :center">직원명</th>
                                                <th style = "text-align :center">EMAIL</th>
                                                <th style = "text-align :center">근무지</th>
                                                <th style = "text-align :center">salary</th>
                                                
                                            </tr>
                                        </thead>
                                       <tbody>
                             <c:forEach var ="member" items ="${memberList}">	  
										        <tr class="odd gradeX">
                                                <td style = "text-align :center"><a href="memberInfo.jsp?EMP_NO=${member.EMP_NO}">${member.EMP_ID}(${member.EMP_STAT})</a></td>
                                                <td style = "text-align :center">${member.EMP_ID}</td>
                                               	<td style = "text-align :center">${member.EMP_EMAIL}</td>
                                                <td style = "text-align :center">${member.workPlace}</td>
                                                <td style = "text-align :center">${member.salType}&nbsp;${member.STD_Salary}</td>
                                            </tr>
                              </c:forEach>	 
                                        </tbody>
                                    </table>
                                    </div>
                              
                          
                                <div class="tab-pane fade" id="profile">
                                    <table width="100%" class="table table-striped table-bordered table-hover" id="dataTables-example1">
                                        <thead>
                                            <tr>
                                               	<th style = "text-align :center">아이디(전화번호)</th>
                                                <th style = "text-align :center">직원명</th>
                                                <th style = "text-align :center">EMAIL</th>
                                                <th style = "text-align :center">근무지</th>
                                                <th style = "text-align :center">salary</th>
                                                
                                            </tr>
                                        </thead>
                                       <tbody>
                             
                                <c:forEach var ="member1" items ="${memberList1}">	  
										
                                          <tr class="odd gradeX">
                                                <td style = "text-align :center"><a href="memberInfo.jsp?EMP_NO=${member1.EMP_NO}">${member1.EMP_ID}(${member1.EMP_STAT})</a></td>
                                                <td style = "text-align :center">${member1.EMP_ID}</td>
                                               	<td style = "text-align :center">${member1.EMP_EMAIL}</td>
                                                <td style = "text-align :center">${member1.workPlace}</td>
                                                <td style = "text-align :center">${member1.salType}&nbsp;${member1.STD_Salary}</td>
                                            </tr>
                              </c:forEach>	 
                                       
         								 
                                        </tbody>
                                    </table>
                                    
                                </div>
                                
                            </div>
                        </div>
                        <!-- /.panel-body -->
                    </div>
                    <!-- /.panel -->
                	</div>
       			</div>	
       		</div>
       	</div>
               
          
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
    
    $(document).ready(function() {
        $('#dataTables-example1').DataTable({
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