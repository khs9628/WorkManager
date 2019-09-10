package com.example.seunghyun.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class FindPwActivity extends AppCompatActivity {
    EditText InputId, InputName, InputEmail;
    TextView Text;
    boolean isIt = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_pw);

        InputId = (EditText)findViewById(R.id.input_id);
        InputName = (EditText)findViewById(R.id.input_name);
        InputEmail = (EditText)findViewById(R.id.input_email);

        Button btnSendPw = (Button)findViewById(R.id.send_pw);
        btnSendPw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String id = InputId.getText().toString();
                String name = InputName.getText().toString();
                String email = InputEmail.getText().toString();

                String sendmsg = "findPw_list";
                try {
                    String result = new Task(sendmsg).execute(id,name,email, "findPw_list", "name_list", "email_list").get();//이메일 디비값을 가져오기
                    String oj = result.trim(); //해당 아이디의 이메일 값 oj에 저장

                    if (oj.length()>0) {

                        StringBuffer temp = new StringBuffer();
                        Random rnd = new Random();
                        for (int i = 0; i < 16; i++) {
                            int rIndex = rnd.nextInt(3);
                            switch (rIndex) {
                                case 0:
                                    // a-z
                                    temp.append((char) ((int) (rnd.nextInt(26)) + 97));
                                    break;
                                case 1:
                                    // A-Z
                                    temp.append((char) ((int) (rnd.nextInt(26)) + 65));
                                    break;
                                case 2:
                                    // 0-9
                                    temp.append((rnd.nextInt(10)));
                                    break;
                            }
                        }
                        sendmsg = "findPw_write";   //회원 정보 업데이트
                        String pw = temp.toString();
                        try {
                            String rst = new Task(sendmsg).execute(pw, id, "findPw_write", "id_write").get();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        Toast.makeText(getApplicationContext(), "이메일로 임시 비밀번호가 발송되었습니다.",
                                Toast.LENGTH_SHORT).show();
                        finish();
                    }
                    else{
                        Toast.makeText(getApplicationContext(), "존재하지 않는 사용자입니다.",
                                Toast.LENGTH_SHORT).show();
                    }
                }
                catch(Exception e){
                    e.printStackTrace();
                }
            }
        });
    }

}

