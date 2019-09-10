<%@page import="java.util.ArrayList"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>  
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page import="member.memberDTO"%>
<%@page import="member.memberDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="kor">
<% 
	String userID = null;
	if(session.getAttribute("userID") != null){
	userID = (String) session.getAttribute("userID");
	}
	if(userID == null){
	session.setAttribute("messageType", "오류 메시지");
	session.setAttribute("messageContent", "현재 로그인이 되어 있지 않습니다.");
	response.sendRedirect("index.jsp");
	return;
	}
	
	request.setCharacterEncoding("UTF-8");
	ArrayList<memberDTO> memberList = new memberDAO().getWorkOne();
	ArrayList<memberDTO> memberList1 = new memberDAO().getWorkTwo();
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

  
    <div id="page-wrapper">
            <!-- 출근중 -->
                <div class="row">
                  <div class="col-lg-12">
                      <h1 class="page-header">Working</h1>
              <c:forEach var ="member" items ="${memberList}">	  
            <fmt:parseDate value="${member.a_TIME}" var="a_TIME" pattern="YYYY-MM-dd HH:MM"/>
			<fmt:parseDate value="${member.START_TIME}" var="s_TIME" pattern="YYYY-MM-dd HH:MM"/>
			<fmt:formatDate var="At_TIME" value="${a_TIME}" pattern="HHMM" />
            <fmt:formatDate var="St_TIME" value="${s_TIME}" pattern="HHMM" />
            <fmt:parseNumber var="Atn_TIME" value="${At_TIME}" integerOnly="true"/>
            <fmt:parseNumber var="Stn_TIME" value="${St_TIME}" integerOnly="true"/>
          
          <c:choose>
          <c:when test ="${Atn_TIME lt Stn_TIME}">
          <div class="col-lg-3 col-md-6">
                      <div class="panel panel-yellow">
                          <div class="panel-heading">
                              <div class="row">
                                  <div class="col-xs-3">
                                    <button type="button" class="btn btn-default btn-circle btn-xl"><i class="fa fa-user fa-2x"></i></button>
								  </div>
                                  <div class="col-xs-9 text-right">
                                    <div class="huge">${member.EMP_NAME}</div>
                                    <div> 
                                          출근 <button type="button" class="btn btn-outline btn-success">${member.a_TIME}</button><br>
                                          퇴근 <button type="button" class="btn btn-outline btn-danger">${member.e_TIME}</button>
                   </div>
                                  </div>
                              </div>
                          </div>
                           <a href="memberInfo.jsp?EMP_NO=${member.EMP_NO}">
                               <div class="panel-footer">
                                  <span class="pull-left">View Details</span>
                                  <span class="pull-right"><i class="fa fa-arrow-circle-right"></i></span>
                                  <div class="clearfix"></div>
                              </div>
                          </a>
                      </div>
                  </div>
                  </c:when>
           <c:otherwise>          
           	<div class="col-lg-3 col-md-6">
                      <div class="panel panel-yellow">
                          <div class="panel-heading">
                              <div class="row">
                                  <div class="col-xs-3">
                                    <button type="button" class="btn btn-default btn-circle btn-xl"><i class="fa fa-user fa-2x"></i></button>
								  </div>
                                  <div class="col-xs-9 text-right">
                                    <div class="huge">${member.EMP_NAME}</div>
                                    <div> 
                                         출근 <button type="button" class="btn btn-outline btn-success">${member.a_TIME}</button><br>
                                          퇴근 <button type="button" class="btn btn-outline btn-danger">${member.e_TIME}</button> 
                   <button type="button" class="btn btn-danger btn-xs pull-left">지각</button>
                                        
                                        
                   </div>
                                  </div>
                              </div>
                          </div>
                           <a href="memberInfo.jsp?EMP_NO=${member.EMP_NO}">
                               <div class="panel-footer">
                                  <span class="pull-left">View Details</span>
                                  <span class="pull-right"><i class="fa fa-arrow-circle-right"></i></span>
                                  <div class="clearfix"></div>
                              </div>
                          </a>
                      </div>
                  </div>
                  </c:otherwise>

         </c:choose> 
       </c:forEach>
       <div class="row">
                  <div class="col-lg-12">
                    
        <h1 class="page-header">미출근</h1>
                  
          <c:forEach var ="member1" items ="${memberList1}" >	  
          <c:choose>
           <c:when test ="${not(member1.a_TIME ne '--:--' and member1.e_TIME eq '--:--')}">
                    <div class="col-lg-3 col-md-6">
                      <div class="panel panel-info">
                          <div class="panel-heading">
                              <div class="row">
                                  <div class="col-xs-3">
                                    <button type="button" class="btn btn-default btn-circle btn-xl"><i class="fa fa-user fa-2x"></i></button>
								  </div>
                                  <div class="col-xs-9 text-right">
                                  <div class="huge">${member1.EMP_NAME}</div>
                                  <div>
                                      출근 <button type="button" class="btn btn-outline btn-success">${member1.a_TIME}</button><br>                   
                                     퇴근 <button type="button" class="btn btn-outline btn-danger">${member1.e_TIME}</button>
                                   </div>
                                  </div>
                              </div>
                          </div>
                           <a href="memberInfo.jsp?EMP_NO=${member1.EMP_NO}">
                               <div class="panel-footer">
                                  <span class="pull-left">View Details</span>
                                  <span class="pull-right"><i class="fa fa-arrow-circle-right"></i></span>
                                  <div class="clearfix"></div>
                              </div>
                          </a>
                      </div>
                  </div>
          </c:when>
          </c:choose>
          </c:forEach>
          </div>
          </div>
          </div>
		  </div>
             </div>
             </div>
             
        <!-- /#page-wrapper -->
  
  
  
  


       
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