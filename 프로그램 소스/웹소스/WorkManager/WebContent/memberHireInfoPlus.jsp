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
	String EMP_NO = null;
	if(session.getAttribute("EMP_NO") != null){
		EMP_NO = (String) session.getAttribute("EMP_NO");
	}
	out.println(EMP_NO);
	if(EMP_NO==null || EMP_NO.equals("")){
	session.setAttribute("messageType", "오류 메시지");
	session.setAttribute("messageContent", "사원을 선택해주세요");
	response.sendRedirect("index.jsp");
	return;
	}
	memberDAO memberDAO = new memberDAO();
	memberDTO member = memberDAO.getMember(EMP_NO);
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
                             <br>
                          <div class="panel panel-primary">
                          <div class="panel-heading">
                          <div class="container">
                          <h4 class="pull-left"><span class="glyphicon glyphicon-tags"></span>&nbsp;&nbsp;마이 페이지</h4>
                          </div>
                          </div>
                          <div class="panel-body">
                              <div class="row">
                              <div class="container">
                                  <div class="col-sm-2">
                                    <button type="button" class="btn btn-default btn-circle btn-xl"><i class="fa fa-user fa-2x"></i>
									</button>
                              </div>    
                                    <div class="huge"><%=member.getEMP_NAME() %>
                                    <h2 class="pull-right"><%= member.getWorkPlace() %></h2>
                                	</div>
                              </div>
                              <br>
               			
                        <div class="container">
                        
                              <h3>기본정보</h3>
                              <div class="well well-lg">
							<p> 이름  : <%=member.getEMP_NAME() %>  <br></p>
							<p>	id(전화번호) : <%= member.getEMP_ID() %><br> </p>
							<p>	email :<%= member.getEMP_EMAIL() %><br> </p>
								
           					 </div>                              

                           	  </div>
                           <form method ="post" action ="./memberInfoPlus">
                             <div class="container">
                              <h3>고용정보</h3>
                              <div class="well well-lg">
                          <p>    사원번호 : <%=member.getEMP_NO() %><br> </p>
                          <p>    근무지명 : <input type = "text" class="form-control" placeholder ="근무지" id ="workPlace" name ="workPlace" maxlength ="20" value="WorkManager"><br> </p>
 						  <p> 급여종류 :  <input type = "text" class="form-control" placeholder ="시급 /월급" id ="salType" name ="salType" maxlength ="20" value="시급"><br> </p>
                          <p> 기준급여 :  <input type = "text" class="form-control" placeholder ="숫자만 입력하시오" id ="STD_salary" name ="STD_salary" maxlength ="20"><br> </p>
                          <p> 주 근로일 : <input type = "text" class="form-control" id ="WORK_DAY" name ="WORK_DAY">
                  		  			주휴일:	<input type = "text" class="form-control" id ="REST_DAY" name ="REST_DAY"><br>  </p>
                  		  <p> 기준 근로시간 : <input type = "time" class="form-control" id ="START_TIME" name ="START_TIME"> ~
                  		  				<input type = "time" class="form-control" id ="END_TIME" name ="END_TIME"><br>  </p>
                  		   <p> 월급일 :  <input type = "text" class="form-control" placeholder ="숫자만 입력하시오" id ="SALDATE" name ="SALDATE" maxlength ="20" value="매월 1일"><br> </p>
           					 <input type ="hidden" name ="EMP_NO"  id ="EMP_NO" value= "<%=member.getEMP_NO() %>">
           					  <button type="submit" class="btn btn-success pull-right">추가</button>
          					 </div>
                              </div>
                           </form>
                          </div>
                          </div>
                         
                        
                              <div class="panel-footer">
                                  <span class="pull-left">WorkManager</span>
                                  <span class="pull-right"><i class="fa fa-arrow-circle-right"></i></span>
                                  <div class="clearfix"></div>
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