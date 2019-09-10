package android;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class ByPeriod_Board {
	   private static ByPeriod_Board byperiod_board = new ByPeriod_Board();

	   public static ByPeriod_Board getByPeriod_Board() {
	      return byperiod_board;
	   }

	   private String returns;
	   private Connection con = null;
	   private PreparedStatement pstmt = null;
	   private ResultSet rs = null;
	   DataSource ds;
	   public String select(String id, String startDate, String endDate) {
		   String sEmpId="", sResult="", sSelectDay="", sRestTime="", kindDay="", sWorkDay="",sJoinDate="", everyDayList="" ,sAbsent="", sLate="", sEarly="", sTodayATime="", sTodayETime="";
		   int emp_id=0,selectDay=0, daysCount=0, totalWorkDay=0;;
		   double restTime;
	      try {
	        returns ="";
	        Context initctx = new InitialContext();

			//톰켓 서버에 정보를 담아놓은 곳으로 이동
			Context envctx = (Context) initctx.lookup("java:comp/env");

			//데이터 소스 객체를 선언
			ds = (DataSource) envctx.lookup("jdbc/WorkManager");

			con = ds.getConnection();
			/**************매개변수 id(휴대폰번호)를 이용해서 EMP_NO 출력*******************/
			 String query = "SELECT EMP_NO FROM EMPLOYEE WHERE EMP_ID='"+id+"'";
			 pstmt = con.prepareStatement(query);
		     rs = pstmt.executeQuery();
		     while(rs.next()) {
		     sEmpId = rs.getString("EMP_NO");
		     }
		    emp_id = Integer.parseInt(sEmpId);
	        query = "select COUNT(WORKTIME_ID)SELECT_DAY FROM WORKTIME WHERE EMP_NO='" +emp_id+ "' AND TO_CHAR(A_TIME, 'YYYY/MM/DD')>='"+startDate+"' AND TO_CHAR(A_TIME, 'YYYY/MM/DD')<='"+endDate+"' and E_time IS NOT NULL order by worktime_id";
			pstmt = con.prepareStatement(query);
	        rs = pstmt.executeQuery();
	        while(rs.next()) {
	        	sSelectDay = rs.getString("SELECT_DAY");
	        }
	        selectDay = Integer.parseInt(sSelectDay);
	        sResult += (selectDay+"-");
	        query = "SELECT workplace, saltype, rest_time, work_day, to_char(joindate,'YYYYMMDD')joindate from hireInfo where EMP_NO='"+emp_id+"'";
			pstmt = con.prepareStatement(query);
	        rs = pstmt.executeQuery();
	        while(rs.next()) {
	           sResult += rs.getString("workplace");
	           sResult += "-";
	           sResult += rs.getString("saltype");
	           sResult += "-";
	           sRestTime = rs.getString("rest_time");
	           sWorkDay = rs.getString("work_day");
	           sJoinDate = rs.getString("joindate");
	         } 
	        
	        restTime = Double.parseDouble(sRestTime);
	        
	        String[] aTimeArray = new String [selectDay]; //출근 시간 배열
	        String[] eTimeArray = new String [selectDay]; //퇴근 시간 배열
	        String[] plusETimeArray = new String [selectDay]; //퇴근 시간 + 2400 배열
	        double[] TimeArray = new double [selectDay];  //근무 시간(퇴근 - 출근)배열
	        double[] aDateTime = new double [selectDay];  //출근 시간 배열의 double형
	        double[] eDateTime = new double [selectDay];  //퇴근 시간 배열의 double형
	        Date[] aDate = new Date [selectDay];			 //출근 시간 배열의 Date형 
	        Date[] eDate = new Date [selectDay];  		 //퇴근 시간 배열의 Date형
	        int i, j;									 //반복문 사용을 위한 변수 i,j
	        
	        i=0;
	        query = "select TO_CHAR(A_TIME,'YYYYMMDDHH24mi')A_TIME, TO_CHAR(E_TIME, 'YYYYMMDDHH24mi')E_TIME, KIND_DAY FROM WORKTIME WHERE EMP_NO='" +emp_id+ "' AND TO_CHAR(A_TIME, 'YYYY/MM/DD')>='"+startDate+"' AND TO_CHAR(A_TIME, 'YYYY/MM/DD')<='"+endDate+"' and E_time IS NOT NULL order by worktime_id";
			pstmt = con.prepareStatement(query);
	        rs = pstmt.executeQuery();
	        while(rs.next()) {
	        	aTimeArray[i]=rs.getString("A_TIME");
	        	eTimeArray[i++]=rs.getString("E_TIME");
	        	kindDay +=rs.getString("KIND_DAY");
	        }
	        
	        SimpleDateFormat dateFormat = new SimpleDateFormat("YYYYMMDDHHmm");
	        double Time;
	        long longETime;
	        for(i=0;i<selectDay;i++) {
	     	   
	 	       aDate[i] = (Date) dateFormat.parse(aTimeArray[i]);
	 	       aDateTime[i] = aDate[i].getTime();
	 	       
	 	      
	 	       eDate[i] = (Date) dateFormat.parse(eTimeArray[i]);
	 	       eDateTime[i] = eDate[i].getTime();
	 	       
	 	       Time = eDateTime[i]-aDateTime[i];
	 	      
	 	       if(Time<0) {													//Time값이 음수일 경우를 위한 조건(야간 근무 설명 시, 자세히 설명)
	 	    	   longETime = Long.parseLong(eTimeArray[i]);
	 	    	   plusETimeArray[i] = Long.toString(longETime+2400);
	 	    	   eDate[i] = (Date) dateFormat.parse(plusETimeArray[i]);
	 		       eDateTime[i] = eDate[i].getTime();
	 		       Time = eDateTime[i]-aDateTime[i];
	 	       }
	 	       TimeArray[i] = (Time/60000/60)-(restTime/60.0);   //조회기간중 휴게 시간을 제외한 일일 근무 시간 저장 배열
	 	       if(TimeArray[i]<0) {
	 	    	   TimeArray[i] += (restTime/60.0);
	 	       }
	 	   }
	        
	        double totalTime=0.0;
	        for(i=0;i<selectDay;i++) {
	        	totalTime+=TimeArray[i];
	        }
	       
	        String[] sWorkDayToken=new String[sWorkDay.length()];
	        int [] workDayToken = new int[sWorkDay.length()];
	        int []restDay = {1,2,3,4,5,6,7};
	        int restDayCount = 0;
	        
	        for(i=0;i<sWorkDay.length();i++){	//소정 근무일을 하나씩 나눈것, '월', '화', '수', '목', '금'
	        	sWorkDayToken[i]=sWorkDay.substring(i, i+1);
	        	workDayToken[i] = dayList(sWorkDayToken[i]); //'2', '3', '4', '5', '6'
	        }
	        
	        for(i=0;i<7;i++) {
	        	for(j=0;j<sWorkDay.length();j++) {
	        		if(restDay[i]==workDayToken[j]) {
	        			restDay[i]=0;
	        		}
	        	}
	        }
	        int[] kindDayList=new int[selectDay];
	        for(i=0;i<selectDay;i++) {
	        	kindDayList[i] = dayList(kindDay.substring(i,i+1));
	        }
	        
	        for(i=0;i<selectDay;i++) {
	        	for(j=0;j<7;j++) {
	        		if(kindDayList[i]==restDay[j])restDayCount++;
	        	}
	        }
	        String nowTime = getCurrentTime("YYYYMMdd");
	        Calendar aCal = getCalendar(startDate.replace("/", ""));
	        Calendar eCal = getCalendar(endDate.replace("/", ""));
	        
	        if(Integer.parseInt(startDate.replace("/",""))<Integer.parseInt(sJoinDate))
	        	aCal = getCalendar(sJoinDate);
	        
	        if(Integer.parseInt(endDate.replace("/",""))>Integer.parseInt(nowTime))
	        	eCal = getCalendar(nowTime);
	       
	        eCal.add(Calendar.DATE, 1);
	        
	        while(aCal.compareTo(eCal)!=0) {
	        	everyDayList+=Integer.toString(aCal.get(Calendar.DAY_OF_WEEK));
	        	aCal.add(Calendar.DATE,1);
	        	daysCount++;
	        }
	        query = "select to_char(a_time,'YYYYMMDDHH24mi')a_time ,to_char(e_time,'YYYYMMDDHH24mi')e_time from worktime where to_char(a_time,'YYYYMMDD')='"+nowTime+"' and emp_no='"+emp_id+"'";
			pstmt = con.prepareStatement(query);
	        rs = pstmt.executeQuery();
	        while(rs.next()) {
	        	sTodayATime+=rs.getString("a_time");
	        	sTodayETime+=rs.getString("e_time");
	        }
	        if((sTodayATime.length()>0)&&(sTodayETime.equals("null")))daysCount--; 	//오늘자 퇴근 시간이 기록되어있지 않을 경우(아직 퇴근 하지 않을 경우) 조회 기간에서 오늘 날짜 제외
	        for(i=0;i<daysCount;i++) {
	        	for(j=0;j<sWorkDay.length();j++) {
	        		if(Integer.parseInt(everyDayList.substring(i, i+1))==workDayToken[j])totalWorkDay++;
	        	}
	        }
	
	        sAbsent = Integer.toString((totalWorkDay+restDayCount)-selectDay);
	        System.out.println("total: "+totalWorkDay+"\trestDay: "+restDayCount+"\tselectDay: "+selectDay);
	        if(Integer.parseInt(sAbsent)<0)sAbsent=Integer.toString(0);
	        
	        query = "select count(a_time)LATE from worktime w inner join hireinfo h on w.EMP_NO=h.EMP_NO where to_char(w.a_time,'hh24mi')>to_char(h.start_time,'hh24mi') and w.EMP_NO='"+emp_id+"' and to_char(a_time,'yyyy/mm/dd')>='"+startDate+"' and to_char(a_time,'yyyy/mm/dd')<='"+endDate+"' and E_time IS NOT NULL order by worktime_id";
			pstmt = con.prepareStatement(query);
	        rs = pstmt.executeQuery();
	        while(rs.next()) {
	        	sLate=rs.getString("LATE");
	        }
	        query = "select count(e_time)EARLY from worktime w inner join hireinfo h on w.EMP_NO=h.EMP_NO where to_char(w.e_time,'hh24mi')<to_char(h.end_time,'hh24mi') and w.EMP_NO='"+emp_id+"' and to_char(a_time,'yyyy/mm/dd')>='"+startDate+"' and to_char(a_time,'yyyy/mm/dd')<='"+endDate+"' and E_time IS NOT NULL order by worktime_id";
			pstmt = con.prepareStatement(query);
	        rs = pstmt.executeQuery();
	        while(rs.next()) {
	        	sEarly=rs.getString("EARLY");
	        }

	        sResult += (startDate+"-"+endDate+"-"+(Double.toString(totalTime)+"-")+ sLate + "-" + sAbsent + "-" + sEarly +"-");
	        
	        for(i=0;i<selectDay;i++) {
	        	sResult += (aTimeArray[i].substring(4, 8)+"-"); 
	        }
	        for(i=0;i<selectDay;i++) {
	        	sResult += (kindDay.substring(i, i+1)+"-");
	        }
	        for(i=0;i<selectDay;i++) {
	        	sResult += (aTimeArray[i].substring(8, 12)+"-");
	        }
	        for(i=0;i<selectDay;i++) {
	        	sResult += (eTimeArray[i].substring(8, 12)+"-");
	        }
	        for(i=0;i<selectDay;i++) {
	        	sResult += (Double.toString(TimeArray[i])+"-");
	        }
	        
	        System.out.println("success");

	        return sResult;
	         
	      } catch (Exception e) {
	         e.printStackTrace();
	      } // end try~catch

	      finally {
	          if (pstmt != null)
	        	  try {
						if(rs != null) rs.close();
						if(pstmt != null) pstmt.close();
						if(con != null) con.close();
					} catch (Exception e) {
						e.printStackTrace();
					}
	      }
	      return returns;
	   }// end select()
	   public static String getCurrentTime(String timeFormat) {
	       return new SimpleDateFormat(timeFormat).format(System.currentTimeMillis());
	    }
	   public static Calendar getCalendar(String str) {
			Calendar cal = Calendar.getInstance();
			cal.set(Integer.parseInt(str.substring(0, 4)), Integer.parseInt(str.substring(4,6))-1,
					Integer.parseInt(str.substring(6,8))); 
			return cal;
		}
	   public static int dayList(String str) {
		   	int list=0;
		   	if(str.equals("일"))list=1;
	        else if(str.equals("월"))list=2;
	        else if(str.equals("화"))list=3;
	        else if(str.equals("수"))list=4;
	        else if(str.equals("목"))list=5;
	        else if(str.equals("금"))list=6;
	        else if(str.equals("토"))list=7;
		   	
		   	return list;
		   
	   }
	}
