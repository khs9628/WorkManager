<%@page import="member.memberDAO"%>
<%@page import="member.CalData"%>
<%@page import="member.memberDTO"%>
<%@page import="java.util.ArrayList"%>
<%@page import="android.YMCalSalary"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="kor">
<%

/* DB에 저장된 근무자를 ArrayList 형태로 가져옴 */
ArrayList<memberDTO> memberList = new memberDAO().getMemberHire();

/* ArrayList<CalData>를 통해  월별 데이터를 리스트 구조로 저장함*/
ArrayList<CalData> oData = new ArrayList<>();

/* 월급 관련 변수를 선언 및 초기화  */
int total = 0;
int basic= 0;
int extend= 0;
int night= 0;
int weekly= 0;
int restDay= 0;
int holiday= 0;
int deduct= 0;

// 1 - 12 월 데이터를 뽑기위해 for문 사용
for(int j = 3; j <= 12; j++){
	
//DB에 저장된 모든 회원(EMP_STAT = 'N')을 가져오기위해 for문 사용
for(int i = 0; i < memberList.size(); i++){ 
	memberDTO member = memberList.get(i);	

YMCalSalary test = YMCalSalary.getYMCalSalary();
String returns = "";

/* j(월)이 01형태로 들어가기 때문에 j가 10보다 작을 경우 0을 붙여줘야함 */
if(j < 10){
returns = test.select(member.getEMP_ID(), "2018", ("0"+j).trim());
System.out.println(returns);
}else{
returns = test.select(member.getEMP_ID(), "2018", String.valueOf(j));
System.out.println(returns);
}



/* 세분화 returns값은 (total - basic - extend - night - weekly - restDay - holiday - deduct) 
     각 employee의 데이터들을 += (더하기)시켜서 총 지출을 저장한다.  										*/
String element[] = returns.split("-");
total += Integer.parseInt(element[0]);
basic += Integer.parseInt(element[1]);
extend += Integer.parseInt(element[2]);
night += Integer.parseInt(element[3]);
weekly += Integer.parseInt(element[4]);
restDay += Integer.parseInt(element[5]);
holiday += Integer.parseInt(element[6]);
deduct += Integer.parseInt(element[7]);
/* ----  */
}

System.out.println(j +"월 , 토탈"+total+"기본급 "+basic+"연장 "+extend+"야간 "+night+"주휴 "+weekly+"휴일 "+restDay+"연차 "+holiday+"빼기 "+deduct);

/* 1월부터 12월 까지의 데이터 를 ArrayList로 저장  */
CalData CalData = new CalData();
CalData.setMonth(j);
CalData.setTotal(total);
CalData.setBasic(basic);
CalData.setExtend(extend);
CalData.setNight(night);
CalData.setWeekly(weekly);
CalData.setRestDay(restDay);
CalData.setHoliday(holiday);
CalData.setDeduct(deduct);
oData.add(CalData);

/* 월별 데이터를 뽑기위해 for문이 끝나기전 초기화  */
 total = 0;
 basic= 0;
 extend= 0;
 night= 0;
 weekly= 0;
 restDay= 0;
 holiday= 0;
 deduct= 0;
 

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
                    <h1 class="page-header">사업 진행 상황</h1>
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <!-- /.row -->

           
               
                    <div class="panel panel-default">
                        <div class="panel-heading">
                         	월급별 추이
                        </div>
                        <!-- /.panel-heading -->
                        <div class="panel-body">
                            <div id="morris-area-chart"></div>
                        </div>
                        <!-- /.panel-body -->
                    </div>
                    <!-- /.panel -->
                

               
                    <div class="panel panel-default">
                        <div class="panel-heading">
                           	달별 월급합계
                        </div>
                        <!-- /.panel-heading -->
                        <div class="panel-body">
                            <div id="morris-bar-chart"></div>
                        </div>
                        <!-- /.panel-body -->
                    </div>
                    <!-- /.panel -->
              <div class="row">
               
              <div class="panel panel-default">
              <div class="panel-heading">
                           	저번달과 비교
              </div>
                        <!-- /.panel-heading -->
                    <div class="panel panel-default">
                       <div class="panel-body">
                        <div class="col-lg-6">
                            <div id="morris-bar-compare"></div>
                        </div>
                        <div class="col-lg-6">
                            <div class="panel panel-default">
                        				<div class="panel-heading">
											<h1 style ="text-align:center;">저번달 보다 - 감소했고 -늘어 났습니다.</h1>                            	
                                		</div>
                            </div>
                             <div class="panel panel-info">
                        				<div class="panel-heading">
											<h1 style ="text-align:center;">저저번달 보다 - 감소했고  -늘어 났습니다.</h1>                            	
                                		</div>
                            </div>
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
        data:  [
            <% for(int i=0; i< oData.size(); i++){%>
            { y : '<%= oData.get(i).getMonth() %>'+'월',
			  a : <%= oData.get(i).getTotal() %>,
			  }, 
              <% }; %>
          ],
        xkey: 'y',
        ykeys: 'a',
        labels: '총합',
        hideHover: 'auto',
        resize: true
    });
	 
	 
	 Morris.Bar({
	        element: 'morris-bar-compare',
	        data: [{
	            y: '2018-11 / 2018-10',
	            a: 100,
	            b: 90
	        }, {
	            y: '2018-11 / 2018-09',
	            a: 75,
	            b: 65
	        }, ],
	        xkey: 'y',
	        ykeys: ['a', 'b'],
	        labels: ['이번달/저번달', '이번달/저저번달'],
	        hideHover: 'auto',
	        resize: true
	    });
	 
	 Morris.Area({
	        element: 'morris-area-chart',
	        data: [{
	     period: '2001',
	            총합: 2666,
	            야간: null,
	            연장: 2647,
	            주휴: 2778
	        }, {
	            period: '2002',
	            총합: 2778,
	            야간: 2294,
	            연장: 2441,
	            주휴: 2778
	        }, {
	            period: '2003',
	            총합: 4912,
	            야간: 1969,
	            연장: 2501,
	            주휴: 2778
	        }, {
	            period: '2004',
	            총합: 3767,
	            야간: 3597,
	            연장: 5689,
	            주휴: 2778
	        }, {
	            period: '2005',
	            총합: 6810,
	            야간: 1914,
	            연장: 2293,
	            주휴: 2778
	        }, {
	            period: '2006',
	            총합: 5670,
	            야간: 4293,
	            연장: 1881,
	            주휴: 2778
	        }, 
	        ],
	        xkey: 'period',
	        ykeys: ['총합', '야간', '연장', '주휴'],
	        labels: ['총합', '야간', '연장', '주휴'],
	        pointSize: 2,
	        hideHover: 'auto',
	        resize: true
	    });
	});
	</script>
</body>

</html>
