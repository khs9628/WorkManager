<%@page import="java.util.Calendar"%>
<%@page import="android.YMCalSalary"%>
<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>Insert title here</title>
</head>
<body>
	<%
	YMCalSalary test = YMCalSalary.getYMCalSalary();
	String returns = "";
	//for(int i= 1;i<13;i++){
	returns = test.select("01042379628", "2018", "02");
	System.out.println(returns);
	//}
	
	
	Calendar cal = Calendar.getInstance();
	 
	//현재 년도, 월, 일
	int year = cal.get ( cal.YEAR );
	int month = cal.get ( cal.MONTH ) + 1 ;
	int date = cal.get ( cal.DATE );
	
	System.out.print(month);

%>
</body>
</html>

    