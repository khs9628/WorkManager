package com.example.seunghyun.myapplication;

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

public class SignActivity extends AppCompatActivity {

    public EditText Name, NewId, NewEmail, NewPw1, NewPw2;
    public TextView Text;

    boolean isId, isEmail, isPw, isCheckId = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign);

        Name = (EditText) findViewById(R.id.name);
        NewId = (EditText) findViewById(R.id.newId);
        NewEmail = (EditText) findViewById(R.id.newEmail);
        NewPw1 = (EditText) findViewById(R.id.newPw1);
        NewPw2 = (EditText) findViewById(R.id.newPw2);
        Text = (TextView) findViewById(R.id.text);

        NewId.setFilters(new InputFilter[]{filterAlphaNum});
        NewId.addTextChangedListener(new TextWatcher() {
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

        NewEmail.addTextChangedListener(new TextWatcher() {
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


        NewPw1.setFilters(new InputFilter[]{(filterAlphaNum)});
        NewPw1.addTextChangedListener(new TextWatcher() {
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


        Button btnCheck = (Button) findViewById(R.id.checkid);
        btnCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    String id = NewId.getText().toString();
                    String sendmsg = "signUp_list";

                try {
                    String result = new Task(sendmsg).execute(id, "signUp_list").get();//디비값을 가져오기
                    String oj = new String(result);  //result 값(문자열 String "true")로 oj 초기화
                    if(oj.trim().length()>0) {      //예상하는 결과
                        Toast.makeText(getApplicationContext(), "사용중인 아이디 입니다.\n 비밀번호 찾기를 이용해주세요",
                                Toast.LENGTH_SHORT).show();
                    } else {    //itIt에 false가 대입되어 나오는 결과
                        isCheckId = true;
                        Toast.makeText(getApplicationContext(), "사용하실 수 있는 아이디 입니다.",
                                Toast.LENGTH_SHORT).show();}
                } catch (Exception e) {
                    e.printStackTrace();
                }
        }
        });

        Button btnSign = (Button) findViewById(R.id.signUpButton);
        btnSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Name.getText().toString().equals("") || NewId.getText().toString().equals("") || NewEmail.getText().toString().equals("") || NewPw1.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "모든 항목을 입력해주세요",
                            Toast.LENGTH_SHORT).show();
                } else if (!isId) {
                    Toast.makeText(getApplicationContext(), "아이디를 다시 입력하세요",
                            Toast.LENGTH_SHORT).show();
                } else if (!isEmail) {
                    Toast.makeText(getApplicationContext(), "이메일을 다시 입력하세요",
                            Toast.LENGTH_SHORT).show();

                } else if (!NewPw1.getText().toString().equals(NewPw2.getText().toString())) {
                    Toast.makeText(getApplicationContext(), "비밀번호가 일치하지 않습니다.",
                            Toast.LENGTH_SHORT).show();
                } else if (!isPw) {
                    Toast.makeText(getApplicationContext(), "비밀번호는 8자 이상, 16자 이하로 입력해주세요",
                            Toast.LENGTH_SHORT).show();
                } else if (!isCheckId) {
                    Toast.makeText(getApplicationContext(), "아이디 중복확인을 해주세요",
                            Toast.LENGTH_SHORT).show();
                } else {

                    String sendmsg = "signUp_write";

                    String name = Name.getText().toString();
                    String id = NewId.getText().toString();
                    String email = NewEmail.getText().toString();
                    String pw = NewPw1.getText().toString();


                    try {
                        String rst = new Task(sendmsg).execute(name, id, email, pw, "signUp_write", "id_write", "email_write", "pw_write").get();
                        Toast.makeText(getApplicationContext(), "회원가입이 완료되었습니다.",
                                Toast.LENGTH_SHORT).show();
                        finish();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }
        });

        Button btnCancel = (Button) findViewById(R.id.cancelButton);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "취소되었습니다.",
                        Toast.LENGTH_SHORT).show();
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
