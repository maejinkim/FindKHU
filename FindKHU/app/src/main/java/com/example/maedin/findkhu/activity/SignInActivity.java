package com.example.maedin.findkhu.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.maedin.findkhu.R;
import com.example.maedin.findkhu.fragment.SignIn;
import com.example.maedin.findkhu.item.LoginItem;
import com.example.maedin.findkhu.item.MemberInfoItem;
import com.example.maedin.findkhu.lib.StringLib;
import com.example.maedin.findkhu.remote.IRemoteService;
import com.example.maedin.findkhu.remote.ServiceGenerator;


import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SignInActivity extends AppCompatActivity implements View.OnClickListener {


    Intent intent;
    Button btn_signup;
    Button btn_signin;

    EditText edit_id;
    EditText edit_pw;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signin);


        btn_signup = (Button) findViewById(R.id.sign_button);
        btn_signup.setOnClickListener(this);
        btn_signin = (Button) findViewById(R.id.login_button);
        btn_signin.setOnClickListener(this);

        edit_id  = (EditText) findViewById(R.id.edit_id);
        edit_pw = (EditText) findViewById(R.id.edit_pw);

    }


    /**
     * 사용자 계정 확인2. 가져온 정보 서버에서 확인
     */
    private void CheckLoginInfo(final LoginItem loginItem){


        // 결국 Retrofit 생성 -> GET 요청으로 데이터 불러오기 -> 성공 여부에 따라 다음으로 넘어갈지 결정
        IRemoteService remoteService = ServiceGenerator.createService(IRemoteService.class);

        Call<ResponseBody> call = remoteService.loginCheck(loginItem);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.e("[index화면] 3.1 계정 불러오기", "성공");
                //MemberInfoItem item = response.body();

                //response 성공 및 패스워드 일치
                if(response.isSuccessful()){

                    IRemoteService remoteService2 = ServiceGenerator.createService(IRemoteService.class);
                    Call<MemberInfoItem> call2 = remoteService2.selectMemberInfo(loginItem.id);

                    call2.enqueue(new Callback<MemberInfoItem>() {
                        @Override
                        public void onResponse(Call<MemberInfoItem> _call, Response<MemberInfoItem> _response) {
                            Log.e("[login화면] 계정 불러오기", "성공");
                            MemberInfoItem item = _response.body();
                            ((MyApp) getApplicationContext()).setMemberInfoItem(item);
                            startMain();
                        }
                        @Override
                        public void onFailure(Call<MemberInfoItem> _call, Throwable _t) {
                            Log.e("[login화면] 계정 불러오기", "서버 통신에 실패");
                            Toast.makeText(SignInActivity.this, "서버 통신에 실패했습니다", Toast.LENGTH_SHORT).show();
                        }
                    });

                }else{
                    Toast.makeText(SignInActivity.this, "로그인 실패!", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("[index화면] 3. 계정 불러오기", "서버 통신에 실패");
                Toast.makeText(SignInActivity.this, "서버 통신에 실패했습니다.", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void startMain(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        Log.e("[login 화면] 로그인 완료", "메인 화면으로 넘어감");

    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.sign_button)
        {
            intent = new Intent(SignInActivity.this, SignUpActivity.class);

            startActivity(intent);
        }
        else if (v.getId() == R.id.login_button)
        {

            LoginItem loginItem = new LoginItem();
            loginItem.id = edit_id.getText().toString();
            loginItem.pw = edit_pw.getText().toString();

            Log.e("[login 화면] 아이디,패스워드 입력", loginItem.id +"");
            CheckLoginInfo(loginItem);
        }
    }
}
