<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="kor">

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
	<link href="bootstrap/dist/css/custom.css" rel="stylesheet">

    <!-- Custom Fonts -->
    <link href="bootstrap/vendor/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">
	
   
		
	<!-- 회원가입 체크  -->
	<script type="text/javascript">
	
	function registerCheckFunction(){
		var userID = $('#userID').val();
		$.ajax({
			type: 'POST' ,
			url : './userRegisterCheck',
			data: {userID: userID},
			success: function(result){
				if(result == 1){
					$('#checkMessage').html('사용할 수 있는 아이디입니다.');
					$('#checkType').attr('class', 'modal-content panel-success');
				} 
				 else{
					$('#checkMessage').html('사용할 수 없는 아이디입니다.');
					$('#checkType').attr('class', 'modal-content panel-warning');
				}
			 $('#checkModal').modal("show");
			}
		});
	}
	
	function passwordCheckFunction(){
		var userPassword = $('#userPassword').val();
		var userPassword2 = $('#userPassword2').val();
		
		if(userPassword != userPassword2){
			$('#passwordCheckMessage').html('비밀번호가 서로 일치하지 않습니다.');
		}else{
			$('#passwordCheckMessage').html('비밀번호가 서로 일치 합니다..');
		}
	}
	</script>
	
		
	
</head>
<body>
	<%
		String userID = null;
		if(session.getAttribute("userID") != null){
			userID = (String) session.getAttribute("userID");
		}
		if(userID != null){
			request.getSession().setAttribute("messageType", "오류 메세지");
			request.getSession().setAttribute("messageContent", "현재 로그인이 되어 있는 상태입니다.");
			response.sendRedirect("index.jsp");
			return;
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
                                    <a href="blank.jsp">Blank Page</a>
                                </li>
                                <li>
                                    <a href="login.jsp">Login Page</a>
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




  <!-- page-wrapper -->
    <div id="page-wrapper">
          <br>
          <div class="container">
              <form method ="post" action ="./userRegister">
                            <table width="100%" class="table table-striped table-bordered table-hover" style="text-align:center; border: 1px solid #dddddd">
                                    <thead>
                                    <tr>
                                    	<th colspan="3" style="text-align:center">회원가입 화면</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <tr>
                                    <td style="width:110px;"><h5>아이디</h5></td>
                                    <td><input type = "text" class="form-control" placeholder ="아이디(핸드폰 번호)" id ="userID" name ="userID" maxlength ="20"></td>
                                    <td style="width:110px;"><button class ="btn btn-primary" type="button" onclick="registerCheckFunction();">중복 체크</button></td>
                                    </tr>
                                    <tr>
                                    <td style="width:110px;"><h5>비밀번호</h5></td>
                                    <td colspan="2"><input type = "password" class="form-control" placeholder ="비밀번호" name ="userPassword" id ="userPassword" onkeyup="passwordCheckFunction();" maxlength ="20"></td>
                                    </tr>
                                     <tr>
                                    <td style="width:110px;"><h5>비밀번호 확인</h5></td>
									<td colspan="2"><input type = "password" class="form-control" placeholder ="비밀번호확인" name ="userPassword2" id ="userPassword2" onkeyup="passwordCheckFunction();" maxlength ="20"></td>
                                    </tr>
                                       <tr>
                                    <td style="width:110px;"><h5>이름</h5></td>
									<td colspan="2"><input type = "text" class="form-control" placeholder ="이름" name =userName id = "userName" maxlength ="20"></td>
                                    </tr>
                                        <tr>
                                    <td style="width:110px;"><h5>이메일</h5></td>
									<td colspan="2"><input type = "email" class="form-control" placeholder ="e-mail" id="userEmail" name ="userEmail" maxlength ="50"></td>
                                    </tr>
                                      <tr>
                                    <td style="width:110px;"><h5>성별</h5></td>
									<td colspan="2">
                                    <div class ="form-group" style ="text-align : center; margin: 0 auto;">
                                      <div class = "btn-group" data-toggle = "buttons">
                                        <label class = "btn btn-primary active">
                                     		 <input type ="radio" name ="userGender" autocomplete = "off" value ="남자" checked>남자
                                      </label>
                                        <label class = "btn btn-primary">
                                          <input type ="radio" name ="userGender" autocomplete = "off" value ="여자">여자
                                        </label>
                                      </div>
                                      </div>
                                      </td>
                                      </tr>
                                      <tr>
                                      <td colspan="3" style ="text-align:left"><h5 style="color:red;" id="passwordCheckMessage"></h5><input class ="btn btn-primary pull-right" type ="submit" value ="회원 가입"></td>
                                  	</tr>
                                  </tbody>
                                  </table>
                                  </form>
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
	<script type="text/javascript" src="bootstrap/vendor/jquery/jquery.min.js"></script>
   
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
<div class="modal fade" id ="checkModal" tabindex ="-1" role ="dialog" aria-hidden ="true">
		<div class="vertical-alignment-helper">
			<div class="modal-dialog vertical-align-center">
				<div id ="checkType" class="modal-content panel-info">
					<div class ="modal-header panel-heading">
						<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">&times;</span>
						<span class="sr-only">Close</span>
						</button>
						<h4 class ="modal-title">
							확인 메세지
						</h4>	
					</div>
					<div id ="checkMessage" class="modal-body">
						
					</div>
					<div class="modal-footer">
					 <button type ="button" class="btn btn-primary" data-dismiss="modal">확인</button>
					</div>
				</div>
			</div>
		</div>
	</div>

	
</body>
</html>