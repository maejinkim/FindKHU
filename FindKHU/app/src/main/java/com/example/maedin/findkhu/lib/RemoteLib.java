package com.example.maedin.findkhu.lib;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.util.Log;

import com.example.maedin.findkhu.remote.IRemoteService;
import com.example.maedin.findkhu.remote.ServiceGenerator;

import java.io.File;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 2017-09-22.
 */

public class RemoteLib {

    private static RemoteLib sRemoteLib;

    public static RemoteLib getInstance() {
        if (sRemoteLib == null) {
            sRemoteLib = new RemoteLib();
        }
        return sRemoteLib;
    }

    /**
     * 커넥션 여부 리턴
     *
     * @param context
     * @return
     */
    public boolean isConnected(Context context) {
        try {
            // 시스템으로부터 커넥션 서비스를 얻어온다.
            ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            // 커넥션 서비스로부터 네트워크 상태를 받아오고
            NetworkInfo info = manager.getActiveNetworkInfo();

            if (info != null) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    public void uploadMemberIcon(int memberSeq, File file) {
        IRemoteService remoteService = ServiceGenerator.createService(IRemoteService.class);

        // 설명 - RequestBody 에 wrap 되어서 데이터가 넘어감
        RequestBody memberSeqBody = RequestBody.create(MediaType.parse("multipart/form-data"), "" + memberSeq);

        // 파일 - MediaType.parse("multipart/form-data") 타입의 file File 객체를 담고 있는 RequestBody 를 만든다는 의미
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        // 서버에 파일을 전송하기 위해 필요하다
        MultipartBody.Part body = MultipartBody.Part.createFormData("file", file.getName(), requestFile);

        Call<ResponseBody> call = remoteService.uploadMemberIcon(memberSeqBody, body);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    Log.e("[ProfileIcon 확인]", "업로드 성공");
                } else {
                    Log.e("[ProfileIcon 확인]", "업로드 오류");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("[ProfileIcon 확인]", "업로드 서버 실패");
            }
        });
    }

    /**
     * 맛집 이미지를 서버에 업로드한다.
     * @param item_id 맛집 정보 일련번호
     * @param item_type 이미지 설명
     * @param file 파일 객체
     * @param handler 처리 결과를 응답할 핸들러
     */
    public String uploadItemImage(int item_id, int item_type, File file, final Handler handler) {
        final String[] pic_id = {null};
        IRemoteService remoteService = ServiceGenerator.createService(IRemoteService.class);

        RequestBody requestFile =
                RequestBody.create(MediaType.parse("multipart/form-data"), file);

        MultipartBody.Part body =
                MultipartBody.Part.createFormData("file", file.getName(), requestFile);

        RequestBody ItemIdBody =
                RequestBody.create(
                        MediaType.parse("multipart/form-data"), "" + item_id);
        RequestBody ItemTypeBody =
                RequestBody.create(
                        MediaType.parse("multipart/form-data"), "" + item_type);

        Call<ResponseBody> call =
                remoteService.uploadFoodImage(ItemIdBody, ItemTypeBody, body);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call,
                                   Response<ResponseBody> response) {
                handler.sendEmptyMessage(0);
                try {
                    pic_id[0] = response.body().string();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
            }
        });
        return pic_id[0];
    }


}
