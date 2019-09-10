<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="member.ItemData"%>
<%@page import="java.util.ArrayList"%>
<%@page import="member.memberDAO"%>
<%@page import="member.memberDTO"%>
<%@page import="android.ByPeriod_Board"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="kor">
<%
String EMP_ID = null;
if(request.getParameter("EMP_ID") != null){
	EMP_ID = (String) request.getParameter("EMP_ID");
}
String A_TIME = null;
if(request.getParameter("A_TIME") != null){
	A_TIME = (String) request.getParameter("A_TIME");
}
String E_TIME = null;
if(request.getParameter("E_TIME") != null){
	E_TIME = (String) request.getParameter("E_TIME");
}

if(EMP_ID==null || EMP_ID.equals("")||
   A_TIME==null || A_TIME.equals("")|| 
   E_TIME==null || E_TIME.equals("")){
	session.setAttribute("messageType", "오류 메시지");
	session.setAttribute("messageContent", "사원을 선택해주십시오.");
	response.sendRedirect("salay.jsp");
	return;
}
String A_TIME1 = A_TIME.replace("-","/");
String E_TIME1 = E_TIME.replace("-","/");


ByPeriod_Board vision_board = ByPeriod_Board.getByPeriod_Board();
String returns = "";
returns = vision_board.select(EMP_ID, A_TIME1, E_TIME1);
System.out.println(returns);
String element[] = returns.split("-");

int size = Integer.parseInt(element[0]);
String place = element[1];
String salType = element[2];
String startDate = element[3];
String endDate = element[4];
String totalTime = element[5];
String late = element[6];
String absent = element[7];
String early = element[8];
String[] date =new String [size];
String[] day = new String [size];
String[] dateDay = new String[size];
String[] startTime = new String [size];
String[] endTime = new String [size];
String[] dailyTime = new String [size];

int i, n=9;
for(i=0;i<size;i++){
    date[i] = element[n].substring(0,2)+"/"+element[n++].substring(2,4)+" ";
}
for(i=0;i<size;i++){
    day[i] = element[n++];
}
for(i=0;i<size;i++){
    dateDay[i] =date[i]+"\t"+day[i];
}
for(i=0;i<size;i++){
    startTime[i]  = element[n].substring(0,2)+":"+element[n++].substring(2,4);
}
for(i=0;i<size;i++){
    endTime[i] = element[n].substring(0,2)+":"+element[n++].substring(2,4);
}

int intDailyTime, intDailyMin;
for(i=0;i<size;i++){
    dailyTime[i]= element[n++];
    if(Double.parseDouble(dailyTime[i])%1 != 0){
        intDailyTime = (int)(Double.parseDouble(dailyTime[i]))/1;
        intDailyMin = (int) ((Double.parseDouble(dailyTime[i]))%1*60);
        dailyTime[i]= Integer.toString(intDailyTime) + ":" + Integer.toString(intDailyMin);
    }
    else{
        intDailyTime = (int)(Double.parseDouble(dailyTime[i]))/1;
        dailyTime[i]= Integer.toString(intDailyTime) + ":00";
    }
}

double douTotalTime = Double.parseDouble(totalTime);
int intTotalTIme = (int)(douTotalTime/1);
int intTotalMin = (int)(douTotalTime%1*60);

if(douTotalTime%1 != 0){
    totalTime=Integer.toString(intTotalTIme)+"시간 "+ Integer.toString(intTotalMin)+"분";
}
else totalTime = Integer.toString(intTotalTIme)+"시간";


ArrayList<ItemData> oData = new ArrayList<>();
for (i=0; i<size; ++i)
{
    ItemData oItem = new ItemData();
    oItem.setDateDay(dateDay[i]);
    oItem.setStartTime(startTime[i]);
    oItem.setEndTime(endTime[i]);
    oItem.setDailyTime(dailyTime[i]);
    oData.add(oItem);
    //if (nDatCnt >= strDate.length) nDatCnt = 0;
}

request.setAttribute("oData", oData);

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
   

<%
	memberDTO member = new memberDAO().getMember1(EMP_ID);

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
		    <div class="row">
                <div class="col-lg-12">
                    <h1 class="page-header">기간별 근무정보</h1>
                </div>
                <!-- /.col-lg-12 -->
    		
           <div class= "panel panel-default">
         <div class= "panel-body">
                		
           	
           	    <div class="col-lg-3 col-md-6">      		
           	<div class= "panel panel-info">
                    	  <div class="panel-heading">
                   			<div class="row">
                                <div class="col-xs-3">
                                    <i class="fa fa-user fa-5x"></i>
                                </div>
                              	   <div class="col-xs-9 text-right">
                                    	<div class="huge"> 이름 </div>
                                   	 <div><%= member.getEMP_NAME()  %></div>
                         	       </div>
                           	 </div>
                     	   </div>
                 	   </div>
               	    </div>
               	    
           	<div class="col-lg-3 col-md-6"> 
           	<div class= "panel panel-green">
                    	  <div class="panel-heading">
                   			<div class="row">
                                <div class="col-xs-3">
                                    <i class="fa fa-building-o fa-5x"></i>
                                </div>
                              	   <div class="col-xs-9 text-right">
                                    	<div class="huge"> 근무지 </div> 
                                   	 <div><%= place  %></div>
                         	       	 
                         	       </div>
                           	 </div>
                     	   </div>
                 	   </div>
               	    </div>
                
         
               	    
               	    <div class="col-lg-3 col-md-6">      		
           	<div class= "panel panel-danger">
                    	  <div class="panel-heading">
                   			<div class="row">
                                <div class="col-xs-3">
                                    <i class="fa fa-book fa-5x"></i>
                                </div>
                              	   <div class="col-xs-9 text-right">
                                    	<div class="huge"> 기간 </div>
                                   	 <div><%= startDate  %> - <%= endDate  %></div>
                         	       	 
                         	       </div>
                           	 </div>
                     	   </div>
                 	   </div>
               	    </div>
               	    
               	    <div class="col-lg-3 col-md-6">      		
          		 	<div class= "panel panel-success">
                    	  <div class="panel-heading">
                   			<div class="row">
                                <div class="col-xs-3">
                                    <i class="fa fa-clock-o fa-5x"></i>
                                </div>
                              	   <div class="col-xs-9 text-right">
                                    	<div class="huge"> 총시간 </div>
                                   	 <div><%= totalTime  %></div>
                         	       	 
                         	       </div>
                           	 </div>
                     	   </div>
                 	   </div>
               	    </div>
                    </div>
                    </div>
                    </div>
                     <div class="row">
                        <div class= "panel panel-warning">
                            <div class="panel-heading">
                            <div class="text-center">
                                <div class="row">
                                    <div class="col-md-3 col-md-offset-1">
                                        <h5>지각<br/></h5><h4><%= late %></h4>
                                    </div>
                                    <div class="col-md-4">
                                        <h5>결근<br/></h5><h4><%= absent %></h4>
                                    </div>
                                    <div class="col-md-3">
                                        <h5>조퇴<br/></h5><h4><%= early  %></h4>
                                    </div>
                                </div>
                            </div>
                            </div>
                            <div class="panel-body">
                		 <c:forEach var ="oItem" items ="${oData}">	  
                          
                           
                		<div class="col-lg-6 col-sm-6">
                		<div class= "panel panel-yellow">
                    	  <div class="panel-body">
                   			<div class="row">
                                <div class="col-xs-3">
                                    <i class="fa fa-clock-o fa-5x"></i>
                                </div>
                              	   <div class="col-xs-9 text-right">
                                    	<div class="huge"> ${oItem.dateDay}</div>
                                   	 <div>출근: ${oItem.startTime} - 퇴근: ${oItem.endTime}</div>
                         	       	  <div>총근무시간: ${oItem.dailyTime}</div>
                         	       </div>
                           	 </div>
                     	   </div>
                 	   </div>
               	    </div>
                     </c:forEach>
                             	</div>	
                       </div>
                
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