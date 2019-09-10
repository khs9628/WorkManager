package com.example.seunghyun.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText InputId, InputPw;
    String savedId, oj;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       savedId = SharedPreference.getAttribute(getApplicationContext(),"userId");
        String sendmsg = "signUp_list";

        try {
            String result = new Task(sendmsg).execute(savedId, "signUp_list").get();
            oj = new String(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(oj.trim().length()>0){
            Intent autoLogin = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(autoLogin);
            Toast.makeText(getApplicationContext(), oj.trim()+"님 환영합니다.",Toast.LENGTH_SHORT).show();
        }

        Button btnSign = (Button)findViewById(R.id.signUp);
        btnSign.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent1 = new Intent(MainActivity.this, SignActivity.class);
                startActivity(intent1);
            }
        });

        Button btnFind = (Button)findViewById(R.id.findPw);
        btnFind.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent2 = new Intent(MainActivity.this, FindPwActivity.class);
                startActivity(intent2);
            }
        });
        InputId = (EditText)findViewById(R.id.userid);
        InputPw = (EditText)findViewById(R.id.userPw);


        Button btnLogin = (Button)findViewById(R.id.login);
        btnLogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String id = InputId.getText().toString();
                String pw = InputPw.getText().toString();

                String sendmsg = "login_list";
                try {
                    String result = new Task(sendmsg).execute(id,pw, "login_list","pw_lsit").get();
                    String oj = result;

                    if (oj.trim().length() > 0) {
                        Toast.makeText(getApplicationContext(), oj+"님 환영합니다.",
                                Toast.LENGTH_SHORT).show();

                        SharedPreference.setAttribute(getApplicationContext(),"userId",id);
                        SharedPreference.setAttribute(getApplicationContext(),"userPw",pw);
                        SharedPreference.setAttribute(getApplicationContext(),"status","0");

                        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                        startActivity(intent);

                    } else {
                        Toast.makeText(getApplicationContext(), "아이디 혹은 비밀번호를 잘못 입력하셨습니다.",
                                Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }
}
