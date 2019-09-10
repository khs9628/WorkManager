<%@page import="member.memberDAO"%>
<%@page import="java.util.ArrayList"%>
<%@page import="android.CalSalary_Board"%>
<%@page import="member.memberDTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="kor">

<head>
	<meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="stylesheet" href="popup/needpopup.min.css">


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

    	<%
		String userID = null;
		if(session.getAttribute("userID") != null){
			userID = (String) session.getAttribute("userID");
		}
			
		
		ArrayList<memberDTO> memberList1 = new memberDAO().getMemberHire();
		ArrayList<memberDTO> memberList = new memberDAO().getMemberWork();
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
                                  <a href="placeProgress.jsp">사업 진행율</a>
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
                            <h1 class="page-header">&nbsp;&nbsp; Salary
                        <button class="btn btn-info pull-right" type="button" data-toggle="modal" data-target="#myModal1">기간별 데이터</button></h1>
                          </div>
                        <!-- /.col-lg-12 -->
             
              <div class="modal fade" id="myModal1" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                                	<div class="modal-dialog">
                                    <div class="modal-content">
                                        <div class="modal-header">
                                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                                            <h4 class="modal-title" id="myModalLabel">기간별 월급 조회</h4>
                                        </div>
                                        <div class="modal-body">
  								
									<form method = "post" action ="SelectSalaryContent.jsp" style = "backgroundcolor: gray;">
          							<table class = "table table-striped" style = "text-align : center; border : 1px solid #dddddd">
          							<tbody>
          							<tr>
          						<td>id : <input type ="text" class ="form-control" placeholder = "id(핸드폰 번호)" name ="EMP_ID" maxlength = "50"></td>
          							</tr>
          							<tr>
          					<td>시작기간<input type ="date" class ="form-control" placeholder = "출근시간" name ="A_TIME"></td>
          							</tr>
          							<tr>
          					<td>마감기간<input type ="date" class ="form-control" placeholder = "퇴근시간" name ="E_TIME"></td>
          							</tr>
          							<tr>
          					<td><input type ="submit" class ="btn btn-primary pull-right" value ="검색"></td>
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
                    </div>
                    <!-- /.row -->
                  
                    <div class="row">
                        <div class="col-lg-12">
                            <div class="panel panel-default">
                                <div class="panel-heading">
                                   			 Salary &nbsp;&nbsp;
                        
                           <button class="fa fa-info-circle" data-toggle="modal" data-target="#myModal">
                            </button>
                            <!-- Modal -->
                            <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                                <div class="modal-dialog">
                                    <div class="modal-content">
                                        <div class="modal-header">
                                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                                            <h4 class="modal-title" id="myModalLabel">월급정보</h4>
                                        </div>
                                        <div class="modal-body">
                                        	<img src="bootstrap/img/Salary.PNG"  style="width: 100%;">  
                                         </div>
                                        <div class="modal-footer">
                                            <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                                        </div>
                                    </div>
                                    <!-- /.modal-content -->
                                </div>
                                <!-- /.modal-dialog -->
                            </div>
                            <!-- /.modal -->
                        </div>	
				
                                <!-- /.panel-heading -->
                                <div class="panel-body">
                                   <ul class="nav nav-tabs">
                                <li class="active"><a href="#SalAll" data-toggle="tab">전체 데이터</a>
                                </li>
                                <li><a href="#SalMonth" data-toggle="tab">월별 데이터</a>
                                </li>
                             </ul>

                            <!-- Tab panes -->
                              <!-- Tab panes -->
                            <div class="tab-content">
                              <div class="tab-pane fade" id="SalAll">
                                    <table width="100%" class="table table-striped table-bordered table-hover" id="dataTables-example">
                                        <thead>
                                            <tr>
                                               	<th style = "text-align :center">직원명</th>
                     							<th style = "text-align :center">시급</th>                         
                                                <th style = "text-align :center">쉬는시간</th>
                                                <th style = "text-align :center">출근시간 퇴근시간</th>
                                                <th style = "text-align :center">근무 인정시간</th></tr>
                                        </thead>
                                       <tbody>
                             
                               <%
								for(int i =0; i < memberList.size(); i++){ 
									memberDTO member = memberList.get(i);	
								%>		
                                          <tr class="odd gradeX">
                                                <td style = "text-align :center"><%=member.getEMP_NAME() %></td>
                                               <td style = "text-align :center"><%=member.getSTD_Salary() %></td>
                                               <td style = "text-align :center"><%=member.getREST_TIME() %></td>
                                               <td style = "text-align :center"><%=member.getA_TIME() %> ~ <%=member.getE_TIME() %></td>
                                               <td style = "text-align :center">9시간 <%=member.getA_TIME().subSequence(0, 11) %></td>
                                            </tr>
                             	 <%
									}
								%>
                                        </tbody>
                               			</table>
                               	 </div>
                                    
									<div class="tab-pane fade in active" id="SalMonth">
                                    <table width="100%" class="table table-striped table-bordered table-hover" id="dataTables-example1">
                                        <thead>
                                            <tr>
                                                <th style = "text-align :center">직원명</th>
                                          		<th style = "text-align :center">시급</th>                         
                                                <th style = "text-align :center">기타 근무수당</th>
                                                <th style = "text-align :center">합계</th>
                                                <th style = "text-align :center">옵션</th> </tr>
                                        </thead>
                                       <tbody>
                                 <%
                                               
                                     for(int i =0; i < memberList1.size(); i++){ 
                                     memberDTO member1 = memberList1.get(i);   
                                     System.out.println(member1.getEMP_ID());
                                     
                                     CalSalary_Board vision_board = CalSalary_Board.getCalSalary_Board();
                                     String returns = "";
                                     returns = vision_board.select(member1.getEMP_ID());
                                     System.out.println(returns);
                                     String element[] = returns.split("-");

                                        
                                         String total = element[0];
                                         String STD_day = element[1];
                                         String basic = element[2];
                                         String extend = element[3];
                                         String night = element[4];
                                         String weekly = element[5];
                                         String restDay = element[6];
                                         String holiday = element[7];
                                         String deduct = element[8];
                                  
                                     %>
                                       
                                      <tr class="odd gradeX">
                                                <td style = "text-align :center"><%= member1.getEMP_NAME() %></td>
                                                  <td style = "text-align :center"><%= member1.getSalType() %></td>
                                                  <td style = "text-align :center">기본금(<%=basic %>) 연장수당(<%=extend %>) 야간수당(<%=night %>) 주휴수당(<%=weekly %>)휴일수당(<%=restDay %>)연차수당(<%=holiday %>)
                                                  </td>
                                                 <td style = "text-align :center"><%= total %></td>
                                                 <td style = "text-align :center">
                                                 <button class="btn btn-default fa fa-search-plus" onclick= "location.href='SalaryContent.jsp?EMP_ID=<%=member1.getEMP_ID()%>'"></button>
                                                 <button class="btn btn-default fa fa-eye" onclick= "location.href='SalaryContent.jsp?EMP_ID=<%=member1.getEMP_ID()%>'"></button>  
                                                  </td>
                                       </tr>
                                            <%
                                     }
                                     %> 
                                        </tbody>
                                    </table>
                                    </div>                          
                                
                            		</div>
                        			</div>                                  
                        				</div>
                            <!-- /.panel -->
                        </div>
                        <!-- /.col-lg-12 -->
                    </div>
                    <!-- /.row -->

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
