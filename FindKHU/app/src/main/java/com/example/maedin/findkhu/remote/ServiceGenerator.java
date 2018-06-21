package com.example.maedin.findkhu.remote;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 서버와 통신하기 위한 레트로핏을 사용하는 클래스
 */
public class ServiceGenerator {

    /**
     * 원격 호출을 정의한 인터페이스 메소드를 호출할 수 있는 서비스 생성
     */
    public static <S> S createService(Class<S> serviceClass){


        // 결국 이곳에서 레트로핏의 핵심 생성
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(IRemoteService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
//                .client(httpClient.build())
                .build();
        return retrofit.create(serviceClass);
    }


}
