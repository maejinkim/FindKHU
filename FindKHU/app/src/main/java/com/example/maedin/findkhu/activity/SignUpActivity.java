package com.example.maedin.findkhu.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.util.SortedList;
import android.telecom.Call;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.maedin.findkhu.R;
import com.example.maedin.findkhu.item.MemberInfoItem;
import com.example.maedin.findkhu.remote.IRemoteService;
import com.example.maedin.findkhu.remote.ServiceGenerator;
import com.google.android.gms.common.api.Response;

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

    /**
     * 사용자 계정 확인2. 가져온 정보 서버에서 확인
     */
    private void selectMemberInfoFromServer(String id){
        // 결국 Retrofit 생성 -> GET 요청으로 데이터 불러오기 -> 성공 여부에 따라 다음으로 넘어갈지 결정
        IRemoteService remoteService = ServiceGenerator.createService(IRemoteService.class);
        Call<MemberInfoItem> call = remoteService.selectMemberInfo(id);
        call.enqueue(new SortedList.Callback<MemberInfoItem>() {
            @Override
            public void onResponse(Call<MemberInfoItem> call, Response<MemberInfoItem> response) {
                Log.e("[index화면] 3.1 계정 불러오기", "성공");
                MemberInfoItem item = response.body();
                if(response.isSuccessful() && !StringLib.getInstance().isBlank(item.name)){
                    Log.e("[index화면] 3.2 계정 불러오기", "성공, 기존 계정 확인");
                    setMemberInfo(item);
                } else {
                    Toast.makeText(IndexActivity.this, "계정이 없습니다. 프로필 창으로 넘어갑니다", Toast.LENGTH_SHORT).show();
                    Log.e("[index화면] 3.2 계정 불러오기", "성공, 하지만 기존 계정 없음");
                    goProfileActivity(item);
                }
            }
            @Override
            public void onFailure(Call<MemberInfoItem> call, Throwable t) {
                Log.e("[index화면] 3. 계정 불러오기", "서버 통신에 실패");
                Toast.makeText(IndexActivity.this, "서버 통신에 실패했습니다", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 사용자 계정 확인3. 서버로부터 데이터 받아오는데 성공했다면 현재 앱에 계정 정보 저장
     */
    private void setMemberInfo(MemberInfoItem item){
        ((MyApp) getApplicationContext()).setMemberInfoItem(item);
        startMain();
    }

    /**
     * ******경   축******
     * 메인 액티비티로 넘어감
     */
    private void startMain(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        Log.e("[index화면] 4. 초기 작업 완료", "메인 화면으로 넘어감");
        finish();
    }

    /**
     * 계정 불러오기에 실패한경우
     */
    private void goProfileActivity(MemberInfoItem item){

        // 만약 처음 등록한 사람 즉, 계정이 없는 사람이라면 번호를 등록해준다.
        if(item == null || item.seq <= 0){
            Log.e("[index화면] 4.1 계정 없음", "계정 저장 시도");
            insertMemberInfo();
        }

        // 메인 액티비티 띄운 다음
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

        // 프로필 액티비티에서 계정 작성 하도록 한다
        Intent intent2 = new Intent(this, ProfileActivity.class);
        startActivity(intent2);

        finish();
    }

    /**
     * 계정이 없는 경우 새로 서버에 등록한다.
     */
    private void insertMemberInfo(){
        String phoneNumber = EtcLib.getInstance().getPhoeNumber(this);

        final IRemoteService remoteService = ServiceGenerator.createService(IRemoteService.class);

        Call<ResponseBody> call = remoteService.insertMemberPhone(phoneNumber);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    Toast.makeText(IndexActivity.this, "번호 등록 성공", Toast.LENGTH_SHORT).show();
                    Log.e("[index화면] 4.2 계정 없음", "계정 저장 성공");
                } else {
                    int statusCode = response.code();
                    Toast.makeText(IndexActivity.this, statusCode+" 오류 발생", Toast.LENGTH_SHORT).show();
                    ResponseBody errbody = response.errorBody();
                    Log.e("[index화면] 4.2 계정 없음", "계정 저장 실패 "+statusCode);
                    Log.e("[index화면] 4.2 계정 없음", errbody.toString());
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(IndexActivity.this, "서버 통신에 실패했습니다", Toast.LENGTH_SHORT).show();
                Log.e("[index화면] 4.2 계정 없음", "계정 저장 서버 통신 실패");
            }
        });
    }


    @Override
    public void onClick(View v) {

    }
}
