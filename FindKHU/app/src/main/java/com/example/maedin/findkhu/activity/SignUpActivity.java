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


public class SignUpActivity extends Activity implements View.OnClickListener{


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

       // item = ((MyApp)getApplication()).getMemberInfoItem();

    }

    /**
     * 사용자 계정 확인2. 가져온 정보 서버에서 확인
     */
    private void selectMemberInfoFromServer(String id){
        // 결국 Retrofit 생성 -> GET 요청으로 데이터 불러오기 -> 성공 여부에 따라 다음으로 넘어갈지 결정
        IRemoteService remoteService = ServiceGenerator.createService(IRemoteService.class);

        Call<MemberInfoItem> call = remoteService.selectMemberInfo(id);
        call.enqueue(new Callback<MemberInfoItem>() {
            @Override
                public void onResponse(Call<MemberInfoItem> call, Response<MemberInfoItem> response) {
                    Log.e("[index화면] 3.1 계정 불러오기", "성공");
                    MemberInfoItem item = response.body();
                if(response.isSuccessful() && !StringLib.getInstance().isBlank(item.name)){
                    Toast.makeText(SignUpActivity.this, "존재하는 아이디 입니다.", Toast.LENGTH_SHORT).show();
                    Log.e("[index화면] 3.2 계정 불러오기", "성공, 기존 계정 확인");
//                    setMemberInfo(item);
                } else {
                    Toast.makeText(SignUpActivity.this, "계정이 없습니다. 프로필 창으로 넘어갑니다", Toast.LENGTH_SHORT).show();
                    Log.e("[index화면] 3.2 계정 불러오기", "성공, 하지만 기존 계정 없음");
//                    goProfileActivity(item);
                }
            }
            @Override
            public void onFailure(Call<MemberInfoItem> call, Throwable t) {
                Log.e("[index화면] 3. 계정 불러오기", "서버 통신에 실패");
                Toast.makeText(SignUpActivity.this, "서버 통신에 실패했습니다.", Toast.LENGTH_SHORT).show();
            }
        });
    }

//    /**
//     * 사용자 계정 확인3. 서버로부터 데이터 받아오는데 성공했다면 현재 앱에 계정 정보 저장
//     */
//    private void setMemberInfo(MemberInfoItem item){
//        ((MyApp) getApplicationContext()).setMemberInfoItem(item);
//        startMain();
//    }

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

//    /**
//     * 계정 불러오기에 실패한경우
//     */
//    private void goProfileActivity(MemberInfoItem item){
//
//        // 만약 처음 등록한 사람 즉, 계정이 없는 사람이라면 번호를 등록해준다.
//        if(item == null || item.id <= 0){
//            Log.e("[index화면] 4.1 계정 없음", "계정 저장 시도");
//            insertMemberInfo();
//        }
//
//        // 메인 액티비티 띄운 다음
//        Intent intent = new Intent(this, MainActivity.class);
//        startActivity(intent);
//
//        // 프로필 액티비티에서 계정 작성 하도록 한다
//        Intent intent2 = new Intent(this, ProfileActivity.class);
//        startActivity(intent2);
//
//        finish();
//    }

    /**
     * 계정이 없는 경우 새로 서버에 등록한다.
     */
    private void insertMemberInfo(){

        String id = edit_id.getText().toString();

        final IRemoteService remoteService = ServiceGenerator.createService(IRemoteService.class);

        Call<ResponseBody> call = remoteService.insertMemberPhone(id);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    Toast.makeText(SignUpActivity.this, "계정 등록 성공", Toast.LENGTH_SHORT).show();
                    Log.e("[index화면] 4.2 계정 없음", "계정 저장 성공");
                } else {
                    int statusCode = response.code();
                    Toast.makeText(SignUpActivity.this, statusCode+" 오류 발생", Toast.LENGTH_SHORT).show();
                    ResponseBody errbody = response.errorBody();
                    Log.e("[index화면] 4.2 계정 없음", "계정 저장 실패 "+statusCode);
                    Log.e("[index화면] 4.2 계정 없음", errbody.toString());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(SignUpActivity.this, "서버 통신에 실패했습니다", Toast.LENGTH_SHORT).show();
                Log.e("[index화면] 4.2 계정 없음", "계정 저장 서버 통신 실패");
            }
        });
    }


    @Override
    public void onClick(View v) {

        save();

    }

    /**
     * 사용자가 입력한 정보를 MemberInfoItem 객체에 저장해서 반환한다.
     * @return 사용자 정보 객체
     */
    private MemberInfoItem getMemberInfoItem() {
        MemberInfoItem item = new MemberInfoItem();
        item.name = edit_name.getText().toString();
        item.id = edit_id.getText().toString();
        item.pw = edit_pw.getText().toString();
        item.phone = edit_phone.getText().toString();
        item.nickname = edit_nick.getText().toString();
        item.major = edit_major.getText().toString();;
        return item;
    }

    /**
     * 사용자가 이름을 입력했는지를 확인한다.
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

//        // 변경 사항이 없음
//        if (!isChanged(newItem)) {
//            Toast.makeText(this, "바뀐 내용이 없습니다", Toast.LENGTH_SHORT).show();
//            return;
//        }

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
//                    try {
//                        item.name = name;
//                        if (item.seq == 0) {
//                            return;
//                        }
//                    } catch (Exception e) {
//                        return;
//                    }
//                    item.name = newItem.name;
//                    item.id = newItem.id;
//                    item.nickname = newItem.nickname;
//                    item.pw = newItem.pw;
//                    item.major = newItem.major;
//                    item.phone = newItem.phone;
                    Log.e("프로필 설정", "성공");
                    Toast.makeText(SignUpActivity.this, "성공!", Toast.LENGTH_SHORT).show();
                    //finish();
                } else {
                    Log.e("프로필 설정", "오류");
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
