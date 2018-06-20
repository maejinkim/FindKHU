package com.example.maedin.findkhu.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.example.maedin.findkhu.R;

public class SignInActivity extends Activity implements View.OnClickListener {


    Intent intent;
    Button btn_signup;
    Button btn_signin;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signin);


        btn_signup = (Button) findViewById(R.id.sign_button);
        btn_signup.setOnClickListener(this);
        btn_signup = (Button) findViewById(R.id.sign_button);
        btn_signup.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.sign_button)
        {
            intent = new Intent(SignInActivity.this, SignUpActivity.class);

            startActivity(intent);
        }
    }
}
