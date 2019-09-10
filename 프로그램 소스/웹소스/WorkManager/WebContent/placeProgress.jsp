<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
            <br>

            <div class="row">
                <div class="col-lg-12">
                    <h1 class="page-header">월급 합계 추이</h1>
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <!-- /.row -->

            <div class="row">
                <div class="col-lg-6">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            Area Chart Example
                        </div>
                        <!-- /.panel-heading -->
                        <div class="panel-body">
                            <div id="morris-area-chart"></div>
                        </div>
                        <!-- /.panel-body -->
                    </div>
                    <!-- /.panel -->
                </div>
                <!-- /.col-lg-6 -->

                <div class="col-lg-6">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            Bar Chart Example
                        </div>
                        <!-- /.panel-heading -->
                        <div class="panel-body">
                            <div id="morris-bar-chart"></div>
                        </div>
                        <!-- /.panel-body -->
                    </div>
                    <!-- /.panel -->
                </div>
                <!-- /.col-lg-6 -->
                
               
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
    
    <!-- Custom Theme JavaScript -->
    <script src="bootstrap/dist/js/sb-admin-2.js"></script>
	<script>
	
	$(function() {
	 Morris.Bar({
        element: 'morris-bar-chart',
        data: [{
            y: '2018-01',
            a: 100,
           
        }, {
            y: '2018-02',
            a: 75,
            
        }, {
            y: '2018-03',
            a: 50,
           
        }, {
            y: '2018-04',
            a: 75,
            
        }, {
            y: '2018-05',
            a: 50,
            
        }, {
            y: '2018-06',
            a: 75,
            
        }, {
            y: '2018-08',
            a: 100,
            
        }, {
            y: '2018-09',
            a: 50,
            
        }, {
            y: '2018-10',
            a: 70,
            
        }, {
            y: '2018-11',
            a: 10,
            
        }, {
            y: '2018-12',
            a: 90,
            
        }],
        xkey: 'y',
        ykeys: 'a',
        labels: '월급 통계',
        hideHover: 'auto',
        resize: true
    });
	 
	 
	 Morris.Area({
	        element: 'morris-area-chart',
	        data: [{
	            period: '2010 Q1',
	            iphone: 2666,
	            ipad: null,
	            itouch: 2647
	        }, {
	            period: '2010 Q2',
	            iphone: 2778,
	            ipad: 2294,
	            itouch: 2441
	        }, {
	            period: '2010 Q3',
	            iphone: 4912,
	            ipad: 1969,
	            itouch: 2501
	        }, {
	            period: '2010 Q4',
	            iphone: 3767,
	            ipad: 3597,
	            itouch: 5689
	        }, {
	            period: '2011 Q1',
	            iphone: 6810,
	            ipad: 1914,
	            itouch: 2293
	        }, {
	            period: '2011 Q2',
	            iphone: 5670,
	            ipad: 4293,
	            itouch: 1881
	        }, {
	            period: '2011 Q3',
	            iphone: 4820,
	            ipad: 3795,
	            itouch: 1588
	        }, {
	            period: '2011 Q4',
	            iphone: 15073,
	            ipad: 5967,
	            itouch: 5175
	        }, {
	            period: '2012 Q1',
	            iphone: 10687,
	            ipad: 4460,
	            itouch: 2028
	        }, {
	            period: '2012 Q2',
	            iphone: 8432,
	            ipad: 5713,
	            itouch: 1791
	        }
	        
	        
	        ],
	        xkey: 'period',
	        ykeys: ['iphone', 'ipad', 'itouch'],
	        labels: ['iPhone', 'iPad', 'iPod Touch'],
	        pointSize: 2,
	        hideHover: 'auto',
	        resize: true
	    });
	});
	</script>
</body>

</html>
