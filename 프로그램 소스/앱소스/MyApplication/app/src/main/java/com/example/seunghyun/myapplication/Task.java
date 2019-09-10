package com.example.seunghyun.myapplication;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by SeungHyun on 2018-10-28.
 */

public class Task extends AsyncTask<String, Void, String> {
    public  static String ip ="192.168.43.61:8081";
    String sendMsg, receiveMsg;
    String serverip = "http://"+ip+"/WorkManager/Android.jsp"; // 연결할 jsp주소

    Task(String sendmsg){
        this.sendMsg = sendmsg;
    }
    @Override
    protected String doInBackground(String... strings) {
        try {
            String str;
            URL url = new URL(serverip);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestMethod("POST");
            OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream());

            if(sendMsg.equals("login_list")){ //TaskLogin
                sendMsg = "login_list="+strings[0]
                        +"&pw_list="+strings[1]
                        +"&type="+strings[2];
            }

            else if(sendMsg.equals("signUp_write")){  //TaskSignUp
                sendMsg = "signUp_write="+strings[0]
                        + "&id_write="+strings[1]
                        + "&email_write="+strings[2]
                        + "&pw_write="+strings[3]
                        + "&type="+strings[4];
            }
            else if(sendMsg.equals("signUp_list")){
                sendMsg = "signUp_list="+strings[0]
                        +"&type="+strings[1];
            }

            else if(sendMsg.equals("findPw_write")){  //TaskFindPw
                sendMsg = "findPw_write="+strings[0] //임시비밀번호 저장
                        +"&id_write="+strings[1]
                        +"&type="+strings[2];
            }

            else if(sendMsg.equals("findPw_list")){
                sendMsg = "findPw_list="+strings[0]
                        +"&name_list="+strings[1]
                        +"&email_list="+strings[2]
                        +"&type="+strings[3];
            }

            else if(sendMsg.equals("calendar_write")){  //TaskCalendar
                sendMsg = "calendar_write="+strings[0]
                        + "&memberId_write="+strings[1]
                        + "&date_write="+strings[2]
                        + "&type="+strings[3];
            }

            else if(sendMsg.equals("calendar_list")){
                sendMsg = "calendar_list="+strings[0]
                        +"&type="+strings[1];
            }

            else if(sendMsg.equals("reviseRequest_write")) {  //TaskReviseRecord
                sendMsg = "reviseRequest_write=" + strings[0]
                        + "&sTime_write=" + strings[1]
                        + "&eTime_write=" + strings[2]
                        + "&content_write=" + strings[3]
                        + "&memberId_write=" + strings[4]
                        + "&type=" + strings[5];
            }

            else if(sendMsg.equals("hireInfo_list")) {  //TaskHireInfo
                sendMsg = "hireInfo_list=" + strings[0]
                        + "&type=" + strings[1];
            }

            else if(sendMsg.equals("reviseInfo_write")){  //TaskReviseInfo
                sendMsg = "reviseInfo_write="+strings[0]
                        + "&index_write="+strings[1]
                        + "&emp_no_write="+strings[2]
                        + "&type="+strings[3];
            }

            else if(sendMsg.equals("reviseInfo_list")){
                sendMsg = "reviseInfo_list="+strings[0]
                        +"&type="+strings[1];
            }

            else if(sendMsg.equals("calSalary_list")){
                sendMsg = "calSalary_list="+strings[0]
                        +"&type="+strings[1];
            }
            else if(sendMsg.equals("byPeriod_list")){
                sendMsg = "byPeriod_list="+strings[0]
                        + "&startDate_list="+strings[1]
                        + "&endDate_list="+strings[2]
                        +"&type="+strings[3];
            }
            else if(sendMsg.equals("addWorktime_write")){
                sendMsg = "addWorktime_write="+strings[0]
                        + "&status="+strings[1]
                        +"&type="+strings[2];
            }

            else if(sendMsg.equals("NFCMain_list")){
                sendMsg = "NFCMain_list="+strings[0]
                        +"&type="+strings[1];
            }



            osw.write(sendMsg);
            osw.flush();
            if(conn.getResponseCode() == conn.HTTP_OK) {
                InputStreamReader tmp = new InputStreamReader(conn.getInputStream(), "UTF-8");
                BufferedReader reader = new BufferedReader(tmp);
                StringBuffer buffer = new StringBuffer();
                while ((str = reader.readLine()) != null) {
                    buffer.append(str);
                }
                receiveMsg = buffer.toString();
            } else {
                Log.i("통신 결과", conn.getResponseCode()+"에러");
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return receiveMsg;
    }
}