package com.example.seunghyun.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class ReviseInfoFragment extends Fragment {
    String userPw;
    EditText Pw;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.fragment_revise_info, container, false);

        userPw = SharedPreference.getAttribute(getContext(),"userPw");
        Pw = view.findViewById(R.id.input_pw);

        Button btnConfrim = view.findViewById(R.id.confirm);
        btnConfrim.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String inputPw = Pw.getText().toString();
                if(inputPw.equals(userPw)){
                    Intent intent = new Intent(getActivity(), ReviseInfoActivity.class);
                    startActivity(intent);
                }
                else{
                    Activity root = getActivity();
                    Toast.makeText(root, "비밀번호를 잘못 입력하였습니다.", Toast.LENGTH_SHORT).show();
                }


            }
        });
        return view;
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_revise_info, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_revise_info) {
            Toast.makeText(getActivity(), "Clicked on " + item.getTitle(), Toast.LENGTH_SHORT)
                    .show();
        }
        return true;
    }
}
