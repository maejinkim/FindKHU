package com.example.maedin.findkhu.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.maedin.findkhu.R;

public class SignUpActivity extends Activity implements View.OnClickListener{


    EditText edit_id;
    EditText edit_pw;
    EditText edit_name;
    EditText edit_nick;
    EditText edit_phone;
    EditText edit_major;

    Button btn_ok;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);

        edit_id = (EditText) findViewById(R.id.edit_signup_id);
        edit_pw = (EditText) findViewById(R.id.edit_signup_pw);
        edit_name = (EditText) findViewById(R.id.edit_signup_name);
        edit_nick = (EditText) findViewById(R.id.edit_signup_nickname);
        edit_phone = (EditText) findViewById(R.id.edit_signup_phone);
        edit_major = (EditText) findViewById(R.id.edit_signup_major);

        btn_ok = (Button) findViewById(R.id.btn_signup_ok);
        btn_ok.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

    }
}
