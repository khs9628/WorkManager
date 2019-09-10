package com.example.seunghyun.myapplication;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;



public class ReviseInfoActivity extends AppCompatActivity {
    EditText InputName, InputId, InputEmail, InputPw1, InputPw2;
    String NewName, NewId, NewEmail, NewPw1, NewPw2;
    String sendmsg, index, id, emp_no, oj;
    boolean isCheckId, isId, isEmail, isPw;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_revise_info);

        id = SharedPreference.getAttribute(getApplicationContext(), "userId");
        sendmsg = "reviseInfo_list";
        try {
            String result = new Task(sendmsg).execute(id, "reviseInfo_list").get();//디비값을 가져오기
            oj = new String(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        emp_no = oj;

        isCheckId = false;
        isId = false;
        isEmail = false;
        isPw = false;

        InputName = findViewById(R.id.newName);
        InputId = findViewById(R.id.newId);
        InputEmail=findViewById(R.id.newEmail);
        InputPw1 = findViewById(R.id.newPw1);
        InputPw2 = findViewById(R.id.newPw2);

        Button ReviseName = findViewById(R.id.reviseName);  //이름 수정
        ReviseName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendmsg = "reviseInfo_write";
                index = "0";
                NewName = InputName.getText().toString();
                try {
                    String rst = new Task(sendmsg).execute(NewName, index, emp_no, "reviseInfo_write" ,"index_write","emp_no_write").get();
                    Toast.makeText(getApplicationContext(), "수정이 완료되었습니다.",
                            Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        InputId.setFilters(new InputFilter[]{filterAlphaNum});
        InputId.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 11) isId = true;
                else isId = false;

                isCheckId = false;
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        Button CheckId = (Button) findViewById(R.id.checkId);
        CheckId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                id = InputId.getText().toString();
                sendmsg = "reviseInfo_list";
                try {
                    String result = new Task(sendmsg).execute(id, "reviseInfo_list").get();//디비값을 가져오기
                    oj = new String(result);  //result 값(문자열 String "true")로 oj 초기화

                    if(!isId) {
                        Toast.makeText(getApplicationContext(), "아이디를 다시 입력해주세요",
                                Toast.LENGTH_SHORT).show();
                    }else if(oj.trim().length()>0) {      //예상하는 결과
                        isCheckId = false;
                        Toast.makeText(getApplicationContext(), "사용중인 아이디 입니다.",
                                Toast.LENGTH_SHORT).show();
                    } else {
                        isCheckId = true;
                        Toast.makeText(getApplicationContext(), "사용하실 수 있는 아이디 입니다.",
                                Toast.LENGTH_SHORT).show();}
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        Button ReviseId = findViewById(R.id.reviseId);
        ReviseId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendmsg = "reviseInfo_write";
                NewId = InputId.getText().toString();
                index = "1";
                try {
                    if(isCheckId) {
                        String rst = new Task(sendmsg).execute(NewId,index,emp_no, "reviseInfo_write" ,"index_write","emp_no_write").get();
                        Toast.makeText(getApplicationContext(), "수정이 완료되었습니다.",
                                Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(getApplicationContext(), "아이디 중복확인을 해주세요",
                                Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        InputEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (checkEmail(s.toString())) isEmail = true;
                else isEmail = false;
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        Button ReviseEmail = findViewById(R.id.reviseEmail);
        ReviseEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendmsg = "reviseInfo_write";
                NewEmail = InputEmail.getText().toString();
                index = "2";
                try {
                    if (isEmail) {
                        String rst = new Task(sendmsg).execute(NewEmail,index,emp_no, "reviseInfo_write" ,"index_write","emp_no_write").get();
                        Toast.makeText(getApplicationContext(), "수정이 완료되었습니다.",
                                Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "이메일을 다시 입력해주세요",
                                Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        InputPw1.setFilters(new InputFilter[]{(filterAlphaNum)});
        InputPw1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() >= 8 && s.length() <= 16) isPw = true;
                else isPw = false;
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        Button RevisePw = findViewById(R.id.revisePw);
        RevisePw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendmsg = "reviseInfo_write";
                NewPw1 = InputPw1.getText().toString();
                index = "3";
                try {
                    if(!InputPw1.getText().toString().equals(InputPw2.getText().toString())){
                        Toast.makeText(getApplicationContext(), "비밀번호가 일치하지 않습니다.",
                                Toast.LENGTH_SHORT).show();
                    }
                    else if (!isPw) {
                        Toast.makeText(getApplicationContext(), "비밀번호는 8자 이상, 16자 이하로 입력해주세요.",
                                Toast.LENGTH_SHORT).show();
                    } else {
                        String rst = new Task(sendmsg).execute(NewPw1,index,emp_no, "reviseInfo_write" ,"index_write","emp_no_write").get();
                        Toast.makeText(getApplicationContext(), "수정이 완료되었습니다.",
                                Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        Button Finish = findViewById(R.id.finish);
        Finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
    public InputFilter filterAlphaNum = new InputFilter() {
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            Pattern ps = Pattern.compile("^[a-zA-Z0-9]*$");
            if (!ps.matcher(source).matches()) {
                return "";
            }
            return null;
        }
    };

    public static boolean checkEmail(String email) {

        String regex = "^[_a-zA-Z0-9-\\.]+@[\\.a-zA-Z0-9-]+\\.[a-zA-Z]+$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(email);
        boolean isNormal = m.matches();
        return isNormal;
    }
}
