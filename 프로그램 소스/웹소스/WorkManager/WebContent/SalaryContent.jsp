<%@page import="member.memberDAO"%>
<%@page import="member.memberDTO"%>
<%@page import="android.CalSalary_Board"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="kor">
<%
String EMP_ID = null;
if(request.getParameter("EMP_ID") != null){
	EMP_ID = (String) request.getParameter("EMP_ID");
}
if(EMP_ID==null || EMP_ID.equals("")){
	session.setAttribute("messageType", "오류 메시지");
	session.setAttribute("messageContent", "사원을 선택해주십시오.");
	response.sendRedirect("salay.jsp");
	return;
}


CalSalary_Board cal = CalSalary_Board.getCalSalary_Board();
String returns = "";
returns = cal.select(EMP_ID);
System.out.println(returns);
String element[] = returns.split("-");

    memberDAO memberDAO = new memberDAO();
    String total =  memberDAO.Comma(element[0].trim());
    String STD_day = element[1];
    String basic = memberDAO.Comma(element[2].trim());
    String extend = memberDAO.Comma(element[3].trim());
    String night = memberDAO.Comma(element[4].trim());
    String weekly = memberDAO.Comma(element[5].trim());
    String restDay = memberDAO.Comma(element[6].trim());
    String holiday = memberDAO.Comma(element[7].trim());
    String deduct = memberDAO.Comma(element[8].trim());

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

    
        <!-- Navigation -->
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



<%
memberDTO member = new memberDAO().getMember1(EMP_ID);

%>


<div id="page-wrapper">
		
            <!-- /.row -->
     <br>
      	 

				<div class="panel panel-default">
                        <div class="panel-heading">
                            <i class="fa fa-clock-o fa-fw"></i> 월별 총 월급 상세사항
                        </div>
                        <!-- /.panel-heading -->
                        <div class="panel-body">
                        <div class="panel panel-warning">
         <div class="panel-heading">
            <div class="container">
                                  <div class="col-sm-2">
                                    <button type="button" class="btn btn-default btn-circle btn-xl"><i class="fa fa-user fa-2x"></i></button>
                              </div>    
                                    <div class="huge"><%=member.getEMP_NAME() %>
                                    <h2 class="pull-right">WorkManager</h2>
                                	</div>
                              </div>
                              </div>
                      
				</div>
 
                            <ul class="timeline">
                                <li>
                                    <div class="timeline-badge"><i class="fa fa-money"></i>
                                    </div>
                                    <div class="timeline-panel">
                                        <div class="timeline-heading">
                                            <h4 class="timeline-title">총 월급 합계</h4>
                                            <p><small class="text-muted"><i class="fa fa-clock-o"></i> ~<%= STD_day %>까지</small>
                                            </p>
                                        </div>
                                        <div class="timeline-body">
                                        <div class="row">
               								<div class="col-lg-12">
                                          <div class="panel panel-yellow">
                        				<div class="panel-heading">
                            <div class="row">
                                <div class="col-xs-3">
                                    <i class="fa fa-money fa-5x"></i>
                                </div>
                                <div class="col-xs-9 text-right">
                                    <div class="huge"><%= total %>원</div>
                                </div>
                            </div>
                        </div>
                            </div>
                        </div> </div>
                                           </div>
                                           </div>
                                    
                                </li>
                                <li class="timeline-inverted">
                                    <div class="timeline-badge danger"><i class="fa fa-money"></i>
                                    </div>
                                    <div class="timeline-panel">
                                        <div class="timeline-heading">
                                            <h4 class="timeline-title">기본 월급</h4>
                                        </div>
                                        <div class="timeline-body">
              			  <div class="panel panel-red">
                        <div class="panel-heading">
                            <div class="row">
                                <div class="col-xs-3">
                                    <i class="fa fa-krw fa-5x"></i>
                                </div>
                                <div class="col-xs-9 text-right">
                                    <div class="huge"><%= basic %>원</div>
                                    <div>시급 * 일한시간</div>
                                </div>
                            </div>
                        </div>
                        
                    </div>
                </div>
            </div>
                            </li>
                                <li>
                                    <div class="timeline-badge primary"><i class="fa fa-money"></i>
                                    </div>
                                    <div class="timeline-panel">
                                        <div class="timeline-heading">
                                            <h4 class="timeline-title">연장수당</h4>
                                        </div>
                                        <div class="timeline-body">
                                          <div class="panel panel-primary">
                        <div class="panel-heading">
                            <div class="row">
                                <div class="col-xs-3">
                                    <i class="fa fa-clock-o fa-5x"></i>
                                </div>
                                <div class="col-xs-9 text-right">
                                    <div class="huge"><%= extend  %>원</div>
                                    <div>초과 근무시간 *시급 * 1.5배</div>
                                </div>
                            </div>
                        </div>
                        </div>
                         </div>
                                    </div>
                                </li>
                                <li class="timeline-inverted">
                                <div class="timeline-badge warning"><i class="fa fa-money"></i>
                                    </div>
                                    <div class="timeline-panel">
                                        <div class="timeline-heading">
                                            <h4 class="timeline-title">야간 수당</h4>
                                        </div>
                                        <div class="timeline-body">
                                                <div class="panel panel-yellow">
                        <div class="panel-heading">
                            <div class="row">
                                <div class="col-xs-3">
                                    <i class="fa  fa-rocket fa-5x"></i>
                                </div>
                                <div class="col-xs-9 text-right">
                                    <div class="huge"><%= night   %>원</div>
                                    <div>야간근무시간(22:00 ~ 06:00) * 시급 * 1.5</div>
                                </div>
                            </div>
                        </div>
                        </div>
                                    </div></div>
                                </li>
                                <li>
                                    <div class="timeline-badge success"><i class="fa fa-money"></i>
                                    </div>
                                    <div class="timeline-panel">
                                        <div class="timeline-heading">
                                            <h4 class="timeline-title">주휴수당</h4>
                                        </div>
                                        <div class="timeline-body">
                            <div class="panel panel-green">
                      		<div class="panel-heading">
                            <div class="row">
                                <div class="col-xs-3">
                                    <i class="fa fa-key fa-5x"></i>
                                </div>
                                <div class="col-xs-9 text-right">
                                    <div class="huge"><%= weekly  %>원</div>
                                    <div>주 15시간 근무 / 결근 x 시급 * 8 *(주간 총근무시간/40)</div>
                                </div>
                            </div>
                        </div>
                        </div>
                                        </div>
                                    </div>
                                </li>
                               
                                <li class="timeline-inverted">
                                    <div class="timeline-badge info"><i class="fa fa-money"></i>
                                    </div>
                                    <div class="timeline-panel">
                                        <div class="timeline-heading">
                                            <h4 class="timeline-title">휴일수당</h4>
                                        </div>
                                        <div class="timeline-body">
                                             <div class="panel panel-info">
                      		<div class="panel-heading">
                            <div class="row">
                                <div class="col-xs-3">
                                    <i class="fa fa-power-off fa-5x"></i>
                                </div>
                                <div class="col-xs-9 text-right">
                                    <div class="huge"><%= restDay %>원</div>
                                    <div>휴일 근무 시간 * 시급  * 1.5</div>
                                </div>
                            </div>
                        </div>
                        </div>  </div>
                                    </div>
                                </li>
                                  <li>
                                    <div class="timeline-badge default"><i class="fa fa-money"></i>
                                    </div>
                                    <div class="timeline-panel">
                                        <div class="timeline-heading">
                                            <h4 class="timeline-title">연차수당</h4>
                                        </div>
                                        <div class="timeline-body">
                            <div class="panel panel-default">
                      		<div class="panel-heading">
                            <div class="row">
                                <div class="col-xs-3">
                                    <i class="fa fa-calendar-o fa-5x"></i>
                                </div>
                                <div class="col-xs-9 text-right">
                                    <div class="huge"><%= holiday %>원</div>
                                    <div>남은연차일 * 1일 통상 임금(2월 1일 지급)</div>
                                </div>
                            </div>
                        </div>
                       </div>
                                        </div>
                                    </div>
                                </li>
                               
                                 <li class="timeline-inverted">
                                    <div class="timeline-badge danger"><i class="fa fa-money"></i>
                                    </div>
                                    <div class="timeline-panel">
                                        <div class="timeline-heading">
                                            <h4 class="timeline-title">차감 내역</h4>
                                        </div>
                                        <div class="timeline-body">
              			  <div class="panel panel-red">
                        <div class="panel-heading">
                            <div class="row">
                                <div class="col-xs-3">
                                    <i class="fa fa-minus-circle fa-5x"></i>
                                </div>
                                <div class="col-xs-9 text-right">
                                    <div class="huge"><%= deduct %>원</div>
                                    <div>(4대보험, 원천세, 기타 차감내역)</div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
                            </ul>
                        </div>
                        <!-- /.panel-body -->
                    </div>
                    <!-- /.panel -->
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