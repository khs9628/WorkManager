package android;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class YMCalSalary{
   private static YMCalSalary ymcalsalary = new YMCalSalary();

   public static YMCalSalary getYMCalSalary() {
      return ymcalsalary;
   }

   private String returns;
   private Connection conn = null;
   private PreparedStatement pstmt = null;
   private ResultSet rs = null;
   DataSource ds;
   String sWorkedDay, sStd_salary, sHoliday, sStartTime,sEndTime, sRestDay, sKindDay, sRestATime, sRestETime, sWorkTimeId, sHoliDay, sIsMoreFive, sBeforeWeekly, sEmpId, sRestCount, sHoliCount, sRestTime;
  
   public String select(String id, String year, String month) {
      try {
         
         if(month.length()<2)month="0"+month;
        returns ="";
        Context initctx = new InitialContext();

      //톰켓 서버에 정보를 담아놓은 곳으로 이동
      Context envctx = (Context) initctx.lookup("java:comp/env");

      //데이터 소스 객체를 선언
      ds = (DataSource) envctx.lookup("jdbc/WorkManager");

      conn = ds.getConnection();

      
      //String nowTime = getCurrentTime("YYYY/MM/dd hh:mm"); //현재 시간 출력
      int total, basic, extend, night, weekly, restDay, holiday, deduct, workedDay; //각 항목 변수 선언
      int[] absent = new int[6]; //결근 배열
      
      /**************매개변수 id(휴대폰번호)를 이용해서 EMP_NO 출력*******************/
       String query = "SELECT EMP_NO FROM EMPLOYEE WHERE EMP_ID='"+id+"'";
       pstmt = conn.prepareStatement(query);
        rs = pstmt.executeQuery();
        while(rs.next()) {
        sEmpId = rs.getString("EMP_NO");
        }
        int emp_id = Integer.parseInt(sEmpId);
        
        /**************근로자별 휴게 시간 출력*******************/ 
       query = "SELECT REST_TIME FROM HIREINFO where EMP_NO ='"+emp_id+"'";
       pstmt = conn.prepareStatement(query);
        rs = pstmt.executeQuery();
        while(rs.next()) {
        sRestTime = rs.getString("REST_TIME");
        }
        double restTime = Integer.parseInt(sRestTime);
        
        query = "SELECT KIND_DAY FROM WORKTIME where EMP_NO ='"+emp_id+"' AND to_char(A_TIME, 'YYYY/MM')='"+year+"/"+month+"' and E_time IS NOT NULL";
         pstmt = conn.prepareStatement(query);
           rs = pstmt.executeQuery();
           while(rs.next()) {
                sKindDay += rs.getString("KIND_DAY");
           } 
        
      /******** 이번 달 1일 부터 WORKTIME 에 기입된 총 근무 일수를 알아내기 위한 query문 ********/
      query = "SELECT COUNT(WORKTIME_ID)WORKED_DAY FROM WORKTIME where EMP_NO ='"+emp_id+"' AND to_char(A_TIME, 'YYYY/MM')='"+year+"/"+month+"' and E_time IS NOT NULL";
      pstmt = conn.prepareStatement(query);
        rs = pstmt.executeQuery();
        while(rs.next()) {
            sWorkedDay=rs.getString("WORKED_DAY"); 
      } 
        workedDay = Integer.parseInt(sWorkedDay); //이번 달의 총 출근 일수 workedDay에 대입  
      
       String[] aTimeArray = new String [workedDay]; //출근 시간 배열
       String[] eTimeArray = new String [workedDay]; //퇴근 시간 배열
       String[] plusETimeArray = new String [workedDay]; //퇴근 시간 + 2400 배열
       double[] TimeArray = new double [workedDay];  //근무 시간(퇴근 - 출근)배열
       double[] aDateTime = new double [workedDay];  //출근 시간 배열의 double형
       double[] eDateTime = new double [workedDay];  //퇴근 시간 배열의 double형
       Date[] aDate = new Date [workedDay];          //출근 시간 배열의 Date형 
       Date[] eDate = new Date [workedDay];         //퇴근 시간 배열의 Date형
       int i, j;                            //반복문 사용을 위한 변수 i,j
       
       /**************출퇴근 시간 배열(String형)에 DB 값 대입 **************/
       i=0;
       query = "SELECT to_char(a_time, 'YYYYMMDDHH24mi')atime, to_char(e_time, 'YYYYMMDDHH24mi')etime FROM WORKTIME where EMP_NO ='"+emp_id+"' AND TO_CHAR(A_TIME,'YYYY/MM')='"+year+"/"+month+"' and E_time IS NOT NULL order by worktime_id";
       pstmt = conn.prepareStatement(query);
       rs = pstmt.executeQuery();  
       while(rs.next()) {
           aTimeArray[i]=rs.getString("atime");
           eTimeArray[i++]=rs.getString("etime");
     } 
       
      /*****************Date Format 지정 및 출퇴근 배열의 Date형, DateTime형(double) 초기화*******************/
       SimpleDateFormat dateFormat = new SimpleDateFormat("YYYYMMDDHHmm");
       double Time;
       long longETime;
       for(i=0;i<workedDay;i++) {
          
          aDate[i] = (Date) dateFormat.parse(aTimeArray[i]);
          aDateTime[i] = aDate[i].getTime();
          
          eDate[i] = (Date) dateFormat.parse(eTimeArray[i]);
          eDateTime[i] = eDate[i].getTime();
          
          Time = eDateTime[i]-aDateTime[i];
         
          if(Time<0) {                                       //Time값이 음수일 경우를 위한 조건(야간 근무 설명 시, 자세히 설명)
             longETime = Long.parseLong(eTimeArray[i]);
             plusETimeArray[i] = Long.toString(longETime+2400);
             eDate[i] = (Date) dateFormat.parse(plusETimeArray[i]);
             eDateTime[i] = eDate[i].getTime();
             Time = eDateTime[i]-aDateTime[i];
          }
          
           TimeArray[i] = Time/60000/60-(restTime/60.0);   //조회기간중 휴게 시간을 제외한 일일 근무 시간 저장 배열
          
           if(TimeArray[i]<0){
             TimeArray[i]+=restTime/60;
          }
       }
       
      
       /**********기준 시급 대입***********/
        query = "SELECT std_salary FROM hireinfo h inner join employee e on h.EMP_NO = e.emp_no where e.emp_id ='"+id+"'";
      pstmt = conn.prepareStatement(query);
        rs = pstmt.executeQuery();
      
        while(rs.next()) {
           sStd_salary = rs.getString("std_salary");
      } 
        int std_salary = Integer.parseInt(sStd_salary);
        
        /************기본급 계산**************/
        basic = 0;
        for(i=0;i<workedDay;i++) {
           basic += (std_salary*TimeArray[i]);
        }
        
        /**************5인 이상 사업체 여부 대입****************/
        query = "SELECT ISMOREFIVE FROM hireinfo h inner join employee e on h.EMP_NO = e.emp_no where e.emp_id ='"+id+"'";
      pstmt = conn.prepareStatement(query);
        rs = pstmt.executeQuery();
        while(rs.next()) {
           sIsMoreFive = rs.getString("ISMOREFIVE");
      } 
        
        /********************출퇴근 배열 Calendar형 배열************************/
        int[] weekDay = new int[workedDay];
        Calendar[] aTimeCal = new Calendar[workedDay];
        Calendar[] eTimeCal = new Calendar[workedDay];
        double[] weeklyTimeArray = new double [6]; //주간 근무 시간
        
        /**********출근일이 그 달의 몇번째 주인지 대입하여, 각 주차 별로 근무 시간 추가**********/
        j=0;
        for(i=0;i<workedDay;i++) {
           aTimeCal[i] = getCalendar(aTimeArray[i]);
           eTimeCal[i] = getCalendar(eTimeArray[i]);
           weekDay[i] = aTimeCal[i].get(Calendar.WEEK_OF_MONTH);  //각 출근일이 몇주차인지 대입
           if(i==0)weeklyTimeArray[j] += (TimeArray[i]);
           if(0<i) {
           if(weekDay[i-1] == weekDay[i]) {      //전날과 오늘의 주차가 같을 경우
              weeklyTimeArray[j] += (TimeArray[i]); //같은 배열에 근무 시간 저장
           }
           else if(weekDay[i-1]!=weekDay[i]) {       //전날과 오늘의 주차가 다를 경우
              if(j<(5))j++;
              weeklyTimeArray[j] += (TimeArray[i]); //다음 주차의 배열에 근무 시간 저장
              }
           }
        }
        
        SimpleDateFormat HHmiFormat = new SimpleDateFormat("HHmm");   //시간용 DateFormat 지정
        SimpleDateFormat MMFormat = new SimpleDateFormat("MM");  //월용 DateFormat 지정
        
        extend = 0; night = 0;holiday=0; restDay=0;
        double weekly_time = 0;
       
        if(sIsMoreFive.length()>0) { //5인 이상 사업체일경우
           
           /***************야간 수당*********************/
           
           String ten = "2200", six = "0600";      //기준 시간
           
           Date tenDate = (Date) HHmiFormat.parse(ten);            //Date형과 DateTime형(double) 변수 초기화
           double tenDateTime = tenDate.getTime();
          Date sixDate = (Date) HHmiFormat.parse(six);
          double sixDateTime = sixDate.getTime();
          int[] intATimeArray = new int [workedDay];
           int[] intETimeArray = new int [workedDay];
         
          double buffer;                                    //buffer로 사용할 변수
          Long bufferTime;
              
           for(i=0;i<workedDay;i++)  {                           //이번 달의 모든 근무일에 대하여
              intATimeArray[i] = Integer.parseInt(aTimeArray[i].substring(8, 12));
                intETimeArray[i] = Integer.parseInt(eTimeArray[i].substring(8, 12));
              
              six = "0600";                                 //밑에서 six 값이 변하는 경우가 있기 때문에 원래대로 돌리기 위한 코드
              sixDate = (Date)HHmiFormat.parse(six);
              sixDateTime=sixDate.getTime();
              
              ten = "2200";                                 //밑에서 ten 값이 변하는 경우가 있기 때문에 원래대로 돌리기 위한 코드
              tenDate = (Date)HHmiFormat.parse(ten);
              tenDateTime=tenDate.getTime();
              
              aDate[i] = (Date) HHmiFormat.parse(aTimeArray[i].substring(8, 12));
              aDateTime[i]=aDate[i].getTime();
              eDate[i] = (Date) HHmiFormat.parse(eTimeArray[i].substring(8, 12));
              eDateTime[i]=eDate[i].getTime();
              
              if(intATimeArray[i]>=Integer.parseInt(ten)||intATimeArray[i]<Integer.parseInt(six)) {                     //출근시간이 22시 이후 일때   
                 if(intETimeArray[i]<=Integer.parseInt(six)) {                  //퇴근시간이 6시 이전일경우
                    night += (int) (std_salary*TimeArray[i]*0.5);   //야간 수당 = 기준시급*근무시간*0.5
                 }
                 else if(intETimeArray[i]>Integer.parseInt(six)&&intETimeArray[i]<Integer.parseInt(ten)) {   //퇴근시간이 06~22시 일 경우
                    
                    buffer=(sixDateTime-aDateTime[i])/60000/60-(restTime/60.0);
                    if(sixDateTime-aDateTime[i]/60000/60<restTime/60.0) buffer+=(restTime/60.0);
                    
                    if(sixDate.compareTo(aDate[i])<0) {                                    //시간 계산 값이 음수가 되는 것을 막기 위한 코드
                       bufferTime = Long.parseLong(six);                  //예) 출근 시간이 23시이고 퇴근 시간이 08시일 경우 08-23 = -15가 되기 때문에
                       six = Long.toString(bufferTime+2400);            //이를 막기 위한 퇴근 시간에 24시간을 더함
                       sixDate = (Date) HHmiFormat.parse(six);
                       sixDateTime = sixDate.getTime();
                       buffer=(sixDateTime-aDateTime[i])/60000/60-(restTime/60.0);         //출근 시간~6시까지의 시간만큼 야간 수당으로 인정
                       if(sixDateTime-aDateTime[i]/60000/60<restTime/60.0)buffer+= (restTime/60.0);
                    }
                    night += (int) (std_salary*(buffer)*0.5);
                 }
              }
              else if(intATimeArray[i]<=Integer.parseInt(ten)&&intATimeArray[i]>Integer.parseInt(six)) {                           //출근 시간이 22시 이전이고
                 if(intETimeArray[i]<=Integer.parseInt(six)) {                           //퇴근 시간이 6시 이전일 경우
                    buffer=(eDateTime[i]-tenDateTime)/60000/60-(restTime/60.0);                  //야간 수당 시간은 퇴근 시간 - 22시
                    if(eDateTime[i]-tenDateTime/60000/60<restTime/60.0)buffer+= (restTime/60.0);
                    if(eDate[i].compareTo(tenDate)<0) {                                       //음수일 경우 24시간 추가 연산
                       eDate[i] = (Date) HHmiFormat.parse(Integer.toString(Integer.parseInt(eTimeArray[i].substring(8, 12))+2400));
                       eDateTime[i] = eDate[i].getTime();
                       buffer=(eDateTime[i]-tenDateTime)/60000/60-(restTime/60.0);
                       if(eDateTime[i]-tenDateTime/60000/60<restTime/60.0)buffer+= (restTime/60.0);
                    }
                    night += (int) (std_salary*(buffer)*0.5);
                 }
              
                 else if(intETimeArray[i]>Integer.parseInt(six)&&intETimeArray[i]<=Integer.parseInt(ten)&&(!aTimeArray[i].substring(6, 8).equals(eTimeArray[i].substring(6, 8)))) {         //퇴근 시간이 6시~22시일 경우
                    
                    buffer = 8.0-(restTime/60.0);
                       night += (int) (std_salary*(buffer)*0.5);   //야간 수당 시간은 6시-22시
                       }
                    }
              }
           
           
           /**************연차 수당*******************/
           
           Date nowTimeDate = MMFormat.parse(month);

           String FebFir = "02";                              //연차 수당 계산일
           Date FebFirDate = MMFormat.parse(FebFir);                  //Date형으로 변환

           if(nowTimeDate.compareTo(FebFirDate)==0) {  //연차 수당은 매년 2월 1일에만 계산
          
              int leftHoli;                     //DB에서 남은 연차 일수 출력
              query = "SELECT holiday FROM hireinfo h inner join employee e on h.EMP_NO = e.emp_no where e.emp_id ='"+id+"'";
            pstmt = conn.prepareStatement(query);
              rs = pstmt.executeQuery();
              while(rs.next()) {
                 sHoliday = rs.getString("holiday");
            } 
           leftHoli = Integer.parseInt(sHoliday);
           //고용 정보에서 소정 출근 시간 및 퇴근 시간 출력 
           query = "SELECT TO_CHAR(START_TIME,'HH24mi')START_TIME,TO_CHAR(END_TIME,'HH24mi')END_TIME  FROM hireinfo h inner join employee e on h.EMP_NO = e.emp_no where e.emp_id ='"+id+"'";
         pstmt = conn.prepareStatement(query);
           rs = pstmt.executeQuery();
           double holiTime=0;
           while(rs.next()) {
              sStartTime = rs.getString("START_TIME");
              sEndTime = rs.getString("END_TIME");
              
              Date startDate = (Date) HHmiFormat.parse(sStartTime);
              Date endDate = (Date) HHmiFormat.parse(sEndTime);
              double startDateTime = startDate.getTime();
              double endDateTime = endDate.getTime();
              if(endDateTime-startDateTime<0) {
                 long longEndTime = Long.parseLong(sEndTime);
                 sEndTime = Long.toString(longEndTime+2400);
              }
              holiTime = (endDateTime-startDateTime)/60000/60;      //1일 통상 근무 시간
         } 
           
           holiday += leftHoli*holiTime*std_salary;               //남은 연차 수 * 1일 통상 임금 
          }
           
           /***************휴일 수당********************/ 
           double restADateTime, restEDateTime, restDateTime, restWorkedTime;
           Date restADate, restEDate;
           int restCount, holiCount;
           long longRestETime;
           
           query = "SELECT rest_day FROM hireinfo where EMP_NO ='"+emp_id+"'"; //해당 아이디의  주휴일 가져오기 
         pstmt = conn.prepareStatement(query);
           rs = pstmt.executeQuery();
           while(rs.next()) {
              sRestDay = rs.getString("rest_day");
           }
           query = "SELECT count(worktime_id)count_rest FROM worktime where EMP_NO ='"+emp_id+"'and kind_day='"+sRestDay+"' and a_time=TO_DATE('"+year+"/"+month+"','YYYY/MM') and E_time IS NOT NULL"; 
           pstmt = conn.prepareStatement(query);                                                      //휴일에 근무한 횟수 가져오기
             rs = pstmt.executeQuery();
             while(rs.next()) {   
                sRestCount = rs.getString("count_rest");
             }
             restCount = Integer.parseInt(sRestCount);
   
              sWorkTimeId=""; i=0;
              String[] workTimeId = new String [restCount];
              query = "SELECT worktime_id FROM worktime where EMP_NO ='"+emp_id+"' and kind_day='"+sRestDay+"'and a_time=TO_DATE('"+year+"/"+month+"','YYYY/MM') and E_time IS NOT NULL"; 
              pstmt = conn.prepareStatement(query);                                                      //근무일이 주휴일과 같을 경우, 해당  worktime_id를 가져오기
                rs = pstmt.executeQuery();
                while(rs.next()) {   
                   sWorkTimeId = rs.getString("worktime_id");
                   workTimeId[i] = sWorkTimeId;
                   if(i<(restCount-1))i++;
                }
              
                   for(i=0;i<restCount;i++) {
                       query = "SELECT TO_CHAR(a_time,'HH24mi')A_TIME, TO_CHAR(e_time,'HH24mi')E_TIME FROM worktime where EMP_NO ='"+emp_id+"' and worktime_id='"+workTimeId[i]+"'and a_time=TO_DATE('"+year+"/"+month+"','YYYY/MM') and E_time IS NOT NULL";
                      pstmt = conn.prepareStatement(query);                                                //worktime_id를 이용해 주휴일에 근무한 날의 출퇴근 시간 가져오기
                        rs = pstmt.executeQuery();
                        while(rs.next()) {                        //출퇴근 시간 DateTime형(double)으로 변환
                           sRestATime = rs.getString("A_TIME");
                           sRestETime = rs.getString("E_TIME");
                           restADate = (Date) HHmiFormat.parse(sRestATime);
                           restEDate = (Date) HHmiFormat.parse(sRestETime);
                           restADateTime = restADate.getTime();
                            restEDateTime = restEDate.getTime();
                           
                           restDateTime = restEDateTime-restADateTime; 
                           if(restDateTime<0) {
                              longRestETime = Long.parseLong(sRestETime);
                              sRestETime = Long.toString(longRestETime+2400);
                              restEDate = (Date) HHmiFormat.parse(sRestETime);
                              restEDateTime = restEDate.getTime();
                              restDateTime = restEDateTime-restADateTime;
                          }
                           restWorkedTime = restDateTime/60000/60-(restTime/60.0);
                           if((restDateTime/60000/60)<(restTime/60.0))restWorkedTime+=restTime/60.0;
                           restDay += std_salary*restWorkedTime*0.5;         //휴일 수당 +=기준 시급 *근무 시간 *0.5
                      }          
                   }   
                   
           sHoliDay=""; sWorkTimeId=""; 
           query = "SELECT count(holi_date)holi_count FROM holiday where to_char(holi_date,'MM')='"+month+"'";       //현재 달 기준 사내 지정 휴일의 개수(근로자의 날 포함) 가져오기
         pstmt = conn.prepareStatement(query);
           rs = pstmt.executeQuery();
           while(rs.next()) {
              sHoliCount = rs.getString("holi_count");
           }
           holiCount = Integer.parseInt(sHoliCount);
           
           i=0;
           String[] holiDate= new String[holiCount]; 
           query = "SELECT to_char(holi_date,'MM/DD')holi_date FROM holiday where to_char(holi_date,'MM')='"+month+"'";      //DB에서 사내 지정 휴일(근로자의 날 포함) 가져오기
         pstmt = conn.prepareStatement(query);
           rs = pstmt.executeQuery();
           while(rs.next()) {
              sHoliDay = rs.getString("holi_date");
              holiDate[i] = sHoliDay;
              if(i<(holiCount))i++;
           }
          
           workTimeId = new String[holiCount];
           for(i=0;i<holiCount;i++) {
              query = "SELECT worktime_id FROM worktime where EMP_NO ='"+emp_id+"'and to_char(a_time,'MM/DD')='"+holiDate[i]+"' and to_char(a_time,'YYYY/MM')='"+year+"/"+month+"' and E_time IS NOT NULL"; 
              pstmt = conn.prepareStatement(query);                                                      //휴일에 근무한 worktime_id 가져오기
                rs = pstmt.executeQuery();
                while(rs.next()) {   
                   sWorkTimeId = rs.getString("worktime_id");
                }
                workTimeId[i] = sWorkTimeId;
              }
           
           for(i=0;i<holiCount;i++) {      //사내 내규 휴일이면서 주휴일에 근무한 경우의 중복 제거
           query = "SELECT kind_day FROM worktime where EMP_NO ='"+emp_id+"'and worktime_id='"+workTimeId[i]+"' and E_time IS NOT NULL"; 
           pstmt = conn.prepareStatement(query);                                                      
             rs = pstmt.executeQuery();
             while(rs.next()) {   
                if(rs.getString("kind_day").equals(sRestDay))workTimeId[i]="-1";
                }
           }
    
           for(i=0;i<holiCount;i++) {
                     query = "SELECT TO_CHAR(a_time,'HH24mi')A_TIME, TO_CHAR(e_time,'HH24mi')E_TIME FROM worktime where EMP_NO ='"+emp_id+"' and worktime_id='"+workTimeId[i]+"' and E_time IS NOT NULL";
                     pstmt = conn.prepareStatement(query);                                 //worktime_id를 이용해 주휴일에 근무한 날의 출퇴근 시간 가져오기
                       rs = pstmt.executeQuery();
                       while(rs.next()) {
                          sRestATime = rs.getString("A_TIME");
                          sRestETime = rs.getString("E_TIME");
                           
                          restADate = (Date) HHmiFormat.parse(sRestATime);
                           restEDate = (Date) HHmiFormat.parse(sRestETime);
                           restADateTime = restADate.getTime();
                           restEDateTime = restEDate.getTime();
                           
                           restDateTime=restEDateTime-restADateTime;
                           
                          if(restDateTime<0) {
                              longRestETime = Long.parseLong(sRestETime);
                              sRestETime = Long.toString(longRestETime+2400);
                              restEDate = (Date) HHmiFormat.parse(sRestETime);
                              restEDateTime = restEDate.getTime();
                              restDateTime = restEDateTime-restADateTime;
                          }
                           restWorkedTime = restDateTime/60000/60-(restTime/60.0);
                           if((restDateTime/60000/60)<(restTime/60.0))restWorkedTime+=restTime/60.0;
                           restDay += std_salary*restWorkedTime*0.5;         //휴일 수당 +=기준 시급 *근무 시간 *0.5
                     }          
                  } 
                }
           
          
        
        
        
        
        /************주차별 결근 횟수를 구하기 위한 변수 선언 및 초기화****************/
        String hireWorkDay="";                        //근무 요일
        String sRecordWorkDay = new String();   //실제 출근한 날의 요일
        String[] weeklyRecordWorkDay = new String[6];      //주차별 출근한 날들의 요일배열
        for(j=0;j<6;j++) weeklyRecordWorkDay[j]="";         //배열 초기화
        
        /*******************근무 요일 DB에서 출력*************************/
        query = "SELECT WORK_DAY FROM hireinfo h inner join employee e on h.EMP_NO = e.emp_no where e.emp_id ='"+id+"'";
      pstmt = conn.prepareStatement(query);
        rs = pstmt.executeQuery();
        while(rs.next()) {
           hireWorkDay = rs.getString("work_day");
      } 
        /**************실제 출근한 날의 요일 DB에서 출력**********************/
        query = "SELECT kind_day FROM worktime w inner join employee e on w.EMP_NO = e.emp_no where to_char(a_time,'yyyy/mm')='"+year+"/"+month+"' and e.emp_id ='"+id+"' and E_time IS NOT NULL order by worktime_id";
      pstmt = conn.prepareStatement(query);
        rs = pstmt.executeQuery();
        i = 0;
        while(rs.next()) {
           sRecordWorkDay += rs.getString("kind_day");
      }
        String recordWorkDay="";
        for(i=0;i<(sRecordWorkDay.length()-1);i++) {
           recordWorkDay += dayList(sRecordWorkDay.substring(i,i+1));
        }
        /****************주차별로 출근 요일 문자열에 추가********************/
        Calendar cal; 
        for(i=0;i<(workedDay);i++) {
           cal = getCalendar(aTimeArray[i]);
           j = cal.get(Calendar.WEEK_OF_MONTH)-1;
           if(i==0) weeklyRecordWorkDay[j] += sRecordWorkDay.substring(i,i+1);
           
           if(i>0) {
           if(weekDay[i-1] == weekDay[i]) {               //전날과 오늘의 주차가 같으면
             weeklyRecordWorkDay[j] += sRecordWorkDay.substring(i,i+1);      //같은 배열에 요일 추가
          }
          else if(weekDay[i-1]!=weekDay[i]) {               //전날과 오늘의 주차가 다르면
            /* if(j<5)j++;*/
             weeklyRecordWorkDay[j] += sRecordWorkDay.substring(i,i+1);   //다음 주차 배열에 요일 추가
             }
           }
        }
        
        /*************근무 요일과 출근 요일 배열을 비교하기 위한 Calendar********************/
        String sWeeklyFirstWorkDay = hireWorkDay.substring(0,1);
        int weeklyFirstWorkDay = Integer.parseInt(dayList(sWeeklyFirstWorkDay)); //한주의 첫번쨰 의무 근무 요일 출력
        String sWeeklyLastWorkDay = hireWorkDay.substring((hireWorkDay.length()-1),hireWorkDay.length());
        int weeklyLastWorkDay = Integer.parseInt(dayList(sWeeklyLastWorkDay));   //한주의 마지막 의무 근무 요일 출력
        cal = Calendar.getInstance();
        cal.set(Calendar.DATE, 1);
        int firstDay = cal.get(Calendar.DAY_OF_WEEK);                   //이번달 1일의 요일
        if(firstDay==1)firstDay=7;
        cal.set(Calendar.DATE, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        int lastDay = cal.get(Calendar.DAY_OF_WEEK);                  //이번달 마지막 날의 요일
        //if(lastDay>hireWorkDay.length())lastDay=hireWorkDay.length();
        //if(lastDay == 1)lastDay=7;
        String lastWeekDay="";      
        if(lastDay==1)lastWeekDay="일";               //마지막주 근무 요일을 대입
        else if(lastDay==2)lastWeekDay="일월";
        else if(lastDay==3)lastWeekDay="일월화";
        else if(lastDay==4)lastWeekDay="일월화수";
        else if(lastDay==5)lastWeekDay="일월화수목";
        else if(lastDay==6)lastWeekDay="일월화수목금";
        else lastWeekDay="일월화수목금토";
        
        int n=1;
        boolean isAllDay;
        for(i=0;i<hireWorkDay.length();i++) {         //마지막주에 근무 요일이 전부 있으면 true 아니면 false
           n*=lastWeekDay.indexOf(hireWorkDay.substring(i,i+1));
        }
        if(n>0)isAllDay=true;
        else isAllDay=false;
        
        String lastWeekWorkDay="";
        /********(근무요일중에서) 첫째 주의 요일 문자열과 마지막 주의 요일 문자열을 대입********///근무일이 월화수목금일 때
        
        String firstWeekWorkDay= "일월화수목금토".substring(firstDay-1,weeklyLastWorkDay);    //첫째주는, 11월 기준으로 목금
        if(weeklyLastWorkDay>lastDay) lastWeekWorkDay= "일월화수목금토".substring((weeklyFirstWorkDay-1), lastDay);   //그 달의 마지막 날의 요일이 한 주의 마지막 의무 근무일의 요일보다 작으면 마지막 주는 그 달의 마지막 날까지 근무일
        else lastWeekWorkDay="일월화수목금토".substring((weeklyFirstWorkDay-1), weeklyLastWorkDay);   //마지막 날의 요일이 한 주의 마지막 의무 근무일의 요일보다 크면, 마지막 주는 한 주의 마지막 근무일까지 근무일
        cal = Calendar.getInstance();
      cal.set(Calendar.YEAR, Calendar.MONTH+1, cal.getActualMaximum(Calendar.DATE));   // 월의 마지막 날짜를 넣어서 셋팅해준다.
      int lastWeek = cal.get(Calendar.WEEK_OF_MONTH);
      for(i=0;i<firstWeekWorkDay.length();i++) {
         if(weeklyRecordWorkDay[0].indexOf(firstWeekWorkDay.substring(i,i+1))==-1)absent[0]++;
      }
        
        /***************2~6주의 결근 횟수 계산*******************/
        for(j=1;j<6;j++) {
           if(lastWeek-1 != j) {
              for(i=0;i<hireWorkDay.length();i++) {
                 if(weeklyRecordWorkDay[j].indexOf(hireWorkDay.substring(i,i+1))==-1)absent[j]++;
              }
           }
           else {
              for(i=0;i<lastWeekWorkDay.length();i++) {
                 if(weeklyRecordWorkDay[j].indexOf(lastWeekWorkDay.substring(i, i+1))==-1) absent[j]++;
              }
           }
        }
        
        /*****************주휴 수당**********************/
       
        weekly=0; sBeforeWeekly="0";
        String SQL, firWorkedDay="", xFirWorkedDay="";
        int thisMonth=0, beforeMonth=0;
        query = "SELECT MIN(to_char(a_time,'DD'))xFirWorkedDay from worktime where EMP_NO='"+emp_id+"' and to_char(a_time,'YYYYMM')='"+Integer.toString(Integer.parseInt(year+month)-1)+"' and E_time IS NOT NULL";
      pstmt = conn.prepareStatement(query);      //이번 달에 일을 한 첫 날의 날짜를 출력
        rs = pstmt.executeQuery(); 
        while(rs.next()) {
           
           xFirWorkedDay +=rs.getString("xFirWorkedDay");
      }
        if(xFirWorkedDay.length()<2)xFirWorkedDay="0"+xFirWorkedDay; //첫 날이 1의 자리의 수이거나 전날에 근무 하지 않았을 경우, 0을 앞에 붙임 ex)1->01
        
        
        query = "SELECT MIN(to_char(a_time,'DD'))firWorkedDay from worktime where EMP_NO='"+emp_id+"' and to_char(a_time,'YYYYMM')='"+Integer.toString(Integer.parseInt(year+month))+"' and E_time IS NOT NULL";
      pstmt = conn.prepareStatement(query);      //이번 달에 일을 한 첫 날의 날짜를 출력
        rs = pstmt.executeQuery(); 
        while(rs.next()) {
           
           firWorkedDay +=rs.getString("firWorkedDay");
      }
       
        if(firWorkedDay.length()<2)firWorkedDay="0"+firWorkedDay; //첫 날이 1의 자리의 수일 경우, 0을 앞에 붙임 ex)1->01
        
        for(j=0;j<6;j++) {
           weekly_time = weeklyTimeArray[j];      //주간 근무 시간
           if(j==0) {
              cal = Calendar.getInstance();
              cal.set(Calendar.YEAR, Calendar.MONTH+1, cal.getActualMaximum(Calendar.DATE));   // 월의 마지막 날짜를 넣어서 셋팅해준다.
             lastWeek = cal.get(Calendar.WEEK_OF_MONTH);
             cal = Calendar.getInstance();
             cal.add(Calendar.MONTH, -1); 
             beforeMonth = cal.get(Calendar.MONTH)+1;
  
             query = "SELECT before_weekly FROM worktime where EMP_NO='"+emp_id+"' and to_char(a_time, 'MMdd')='"+Integer.toString(beforeMonth)+firWorkedDay+"' and E_time IS NOT NULL";
             pstmt = conn.prepareStatement(query);
               rs = pstmt.executeQuery();
               
               while(rs.next()) {
                  sBeforeWeekly = rs.getString("before_weekly");
             }
               weekly_time +=Integer.parseInt(sBeforeWeekly);             
           }
           
           if(weekly_time>=15&&absent[j]==0) {      //주간 근무 시간이 15시간 이상이고, 그주에 개근 하였을 시
              if(lastWeek-1 != j){   //현재의 주가 마지막 주차가 아니면 
                       weekly += (int) (std_salary*(weekly_time/40.0)*8);   //주휴 수당 지급                       
              }
              else if(lastWeek-1 == j&&!isAllDay) {         //이번주가 마지막 주이고, 그주에 근무일이 전부 있지 않을 경우
                 cal = Calendar.getInstance();
                 cal.set(Calendar.DATE, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
                 thisMonth = cal.get(Calendar.MONTH)+1;                                          //WORKTIME의 해당 사용자의 이번달 일한 첫째 날 칼럼에 전 달의 마지막 주 근무 시간을 기입한다.
                 SQL = "update worktime set before_weekly='"+weekly_time+"' where to_char(a_time, 'MMdd')='"+Integer.toString(thisMonth)+firWorkedDay+"' and EMP_NO ='"+emp_id+"'";
                 pstmt = conn.prepareStatement(SQL);
              }
              else {                                 //마지막 주이지만, 그 주의 소정 근로일만큼 일을 마쳤을 경우
                 weekly += (int) (std_salary*(weekly_time/40.0)*8);   //주휴 수당 지급  
              }
           }
           /***********연장 수당************/
           
           if(sIsMoreFive.length()>0){               //5인 이상 사업체일 경우
             
             int[] startDay = new int [6];         //각 주의 첫째 날짜(일)을 저장하는 배열
             int[] endDay= new int [6];             //각 주의 마지막 날짜(일)을 저장하는 배열
             
             for(n=0;n<startDay.length;n++) {startDay[n]=-1;}  //배열 원소 -1로 초기화
              startDay[0] = 0;                  //첫번 째 주의 첫 날은 항상 1
             for(i=1, n=1;i<workedDay-1;i++) {
                 if(weekDay[i]!=weekDay[i-1]) {      //i일이 그 주의 첫째 날이면
                    startDay[n] = i;         //(i+1)값에 저장
                    if(n<5)n++;   
                 }
             }
             
               for(n=0;n<endDay.length;n++) {endDay[n]=-1;}  //배열 원소 -1로 초기화
                  for(i=0, n=0;i<workedDay-1;i++) {            
                     if(weekDay[i]!=weekDay[i+1]) { //i일이 그 주의 마지막 날이면
                        endDay[n] = i;         //(i+1)값에 저장
                        if(n<5)n++;               
                     }
                  }
                  endDay[n] = workedDay;             //월의 마지막 날짜(마지막 주의 마지막 날) 저장
                  
                 
                  if(lastWeek-1 == j && lastDay != 7) { //이번주가 마지막 주이고 마지막 날이 토요일이(완전한 주) 아닐 경우, 이번 주 근무 시간은 DB에 저장
                     cal = Calendar.getInstance();
                     cal.set(Calendar.DATE, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
                     thisMonth = cal.get(Calendar.MONTH)+1;                                          //WORKTIME의 해당 사용자의 이번달 마지막 날 칼럼에 전달의 마지막 주 근무 시간을 기입한다.
                     SQL = "update worktime set before_extend='"+weekly_time+"' where to_char(a_time, 'MMdd')='"+Integer.toString(thisMonth)+"01"+"' and EMP_NO ='"+emp_id+"'";
                     pstmt = conn.prepareStatement(SQL);
                  }
                  else if(weekly_time>40.0) {               //주간 근무 시간이 40시간 이상일시
                extend += (weekly_time-40.0)*std_salary*0.5;   //연장 수당
             }
                  else {
                     if(startDay[j]!=-1) {
                        for(i=startDay[j];i<endDay[j];i++) {
                           if(TimeArray[i]>8.0) {
                           extend += (TimeArray[i]-8.0)*std_salary*0.5;
                           }
                        }
                     } 
                  }
             }
           }
        
        /*************차감 내역(4대보험 + 원천세)*****************/
       deduct = 0;
       int preTotal = basic + extend + night + weekly + restDay + holiday; //기본 급과 수당을 모두 더한 값
       deduct += preTotal * (0.0844+0.033);      //계산식
       
       /***************종합 예상 월급******************/
       total = preTotal - deduct;      //1의 자리 올림
       if(total%10!=0) total = total/10*10+10;
       
       /***************각 항목 확인용 출력********************/
       System.out.print(year+"/"+month+":\t");
       returns = total+"-"+basic+"-"+extend+"-"+night+"-"+weekly+"-"+restDay+"-"+holiday+"-"+deduct;
       
       pstmt.executeUpdate();          
      } catch (Exception e) {
         e.printStackTrace();
      } // end try~catch

      finally {
          if (pstmt != null)
             try {
               if(rs != null) rs.close();
               if(pstmt != null) pstmt.close();
               if(conn != null) conn.close();
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
            Integer.parseInt(str.substring(6,8)), Integer.parseInt(str.substring(8,10)),
                  Integer.parseInt(str.substring(10,12))); 
      return cal;
   }
   public static String dayList(String str) {
         String list="";
         if(str.equals("일"))list="1";
       else if(str.equals("월"))list="2";
       else if(str.equals("화"))list="3";
       else if(str.equals("수"))list="4";
       else if(str.equals("목"))list="5";
       else if(str.equals("금"))list="6";
       else if(str.equals("토"))list="7";
         
         return list;
      
  }

}