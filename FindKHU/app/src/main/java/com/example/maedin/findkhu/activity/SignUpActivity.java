package com.example.maedin.findkhu.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.maedin.findkhu.R;
import com.example.maedin.findkhu.fragment.SignIn;
import com.example.maedin.findkhu.item.MemberInfoItem;
import com.example.maedin.findkhu.remote.IRemoteService;
import com.example.maedin.findkhu.remote.ServiceGenerator;
import com.example.maedin.findkhu.lib.RemoteLib;
import com.example.maedin.findkhu.lib.StringLib;


import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SignUpActivity extends Activity implements View.OnClickListener {


    EditText edit_id;
    EditText edit_pw;
    EditText edit_name;
    EditText edit_nick;
    EditText edit_phone;
    EditText edit_major;

    Button btn_ok;

    MemberInfoItem item;

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


    /**
     * ******경   축******
     * 로그인 액티비티로 넘어감
     */
    private void returnLogin() {
        Intent intent = new Intent(this, SignInActivity.class);
        startActivity(intent);
        Log.e("[signup 화면] 회원가입 완료 ", "로그인 화면으로 넘어감");
        finish();
    }



    @Override
    public void onClick(View v) {
        save();
    }

    /**
     * 사용자가 입력한 정보를 MemberInfoItem 객체에 저장해서 반환한다.
     *
     * @return 사용자 정보 객체
     */
    private MemberInfoItem getMemberInfoItem() {
        MemberInfoItem item = new MemberInfoItem();
        item.name = edit_name.getText().toString();
        item.id = edit_id.getText().toString();
        item.pw = edit_pw.getText().toString();
        item.phone = edit_phone.getText().toString();
        item.nickname = edit_nick.getText().toString();
        item.major = edit_major.getText().toString();
        ;
        return item;
    }

    /**
     * 사용자가 이름을 입력했는지를 확인한다.
     *
     * @param newItem 사용자가 새로 입력한 정보 객체
     * @return 입력하지 않았다면 true, 입력했다면 false
     */
    private boolean isNoName(MemberInfoItem newItem) {
        if (StringLib.getInstance().isBlank(newItem.name)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 사용자가 입력한 정보를 저장한다.
     */
    private void save() {
        final MemberInfoItem newItem = getMemberInfoItem();


        // 변경 사항 있을 경우
        IRemoteService remoteService = ServiceGenerator.createService(IRemoteService.class);
        Call<ResponseBody> call = remoteService.insertMemberInfo(newItem);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    String name = null;
                    try {
                        name = response.body().string();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Log.e("Response 리턴값", name);
//
                    Log.e("회원가입", "성공");
                    Toast.makeText(SignUpActivity.this, "성공!", Toast.LENGTH_SHORT).show();
                    returnLogin();
                } else {
                    Log.e("회원가입", "오류");
                    Toast.makeText(SignUpActivity.this, "실패!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("프로필 설정", "서버 연결 실패");
            }
        });
    }
}
