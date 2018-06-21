package com.example.maedin.findkhu.remote;

import com.example.maedin.findkhu.item.*;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Administrator on 2017-09-22.
 */

public interface IRemoteService {

    // 결국 레트로핏을 이해하려면 Http 프로토콜을 이해해야 하는 것은, 헤더와 바디에 메시지를 주고받는 형식을 알아야
    // 어떤 값이 오고가는지 알고 어떻게 갖다 쓰는지도 알고, 오류가 어디서 생기는지 알 수 있기 때문이다.

    // Query 는 int
    // Field 는 String
    // File 은 MultiPart

    // Using Retrofit 2, you need to use either OkHttp’s RequestBody or MultipartBody.Part classes and encapsulate your file into a request body


    // 검색은 @Query 로 한다고 생각하자


    String BASE_URL = "http://192.168.1.66:8000";
    String MEMBER_ICON_URL = BASE_URL+"/member/";
    String IMAGE_URL = BASE_URL+"/img/";
//==================================================================================================
//    사용자 정보
//==================================================================================================

    /**
     * IndexActivity
     */
    // 서버에서 번호로 계정 찾기
    @GET("/member/{id}")
    Call<MemberInfoItem> selectMemberInfo(@Path("id") String id);


    /**
     * ProfileActivity
     */
    // 회원가입
    @POST("/member/info")
    Call<ResponseBody> insertMemberInfo(@Body MemberInfoItem memberInfoItem);

    /**
     * ProfileActivity
     */
    // 로그인
    @POST("/member/id")
    Call<ResponseBody> loginCheck(@Body LoginItem loginItem);


    /**
     * ProfileIconActivity
     */
    // 이미지를 서버에 업로드
    @Multipart
    @POST("/member/icon_upload")
    Call<ResponseBody> uploadMemberIcon(@Part("member_seq")RequestBody memberseq, @Part MultipartBody.Part file);

//==================================================================================================
//    맛집 정보
//==================================================================================================

    /**
     * BestFoodRegisterInputFragment
     */
    // 서버 게시글 등록
    @POST("/item/info")
    Call<ResponseBody> insertItemInfo(@Body InfoItem _InfoItem);

    //item location 추가, return loc_id
    @POST("/item/loc")
    Call<ResponseBody> insertLocInfo(@Body LocItem locItem);


    /**
     * BestFoodRegisterImageFragment
     */
    // 맛집 이미지 업로드
    @Multipart
    @POST("/item/info/image")
    Call<ResponseBody> uploadFoodImage(
            @Part("item_type") RequestBody item_type,
            @Part("item_id") RequestBody item_id,
            @Part MultipartBody.Part file
    );

    // 서버에서 catgory 가져오기
    @GET("/item/cat")
    Call<List<CatItem>> getCatItem();

    /**
     * BestFoodListFragment
     */
    // item type에 따른 가져오기
    @GET("/item/list")
    Call<String> listFoodInfo(@Query("item_type") int item_type );

    /**
     * BestFoodInfoActivity
     */
    // 맛집 정보
    @GET("/food/list/{info_seq}")
    Call<InfoItem> selectFoodInfo(@Path("info_seq") int infoSeq, @Query("member_seq") int memberSeq);

//==================================================================================================
//    지도 정보
//==================================================================================================

    /**
     * BestFoodMapFragment
     */
    // 지도 프래그먼트 필요한 정보
    @GET("/map/list")
    Call<List<InfoItem>> listMap(@Query("member_seq") int memberSeq,
                                     @Query("latitude") double latitude,
                                     @Query("longitude") double longitude,
                                     @Query("distance") int distance,
                                     @Query("user_latitude") double userLatitude,
                                     @Query("user_longitude") double userLongitude);

//==================================================================================================
//    즐겨찾기
//==================================================================================================

    /**
     * BestFoodListFragment
     */
    // 즐겨찾기 추가
    @POST("/keep/{member_seq}/{info_seq}")
    Call<ResponseBody> insertKeep(@Path("member_seq") int memberSeq, @Path("info_seq") int infoSeq);

    // 즐겨찾기 삭제
    @POST("/keep/{member_seq}/{info_seq}")
    Call<String> deleteKeep(@Path("member_seq") int memberSeq, @Path("info_seq") int infoSeq);


    /**
     * BestFoodKeepFragment
     */
    // 즐겨찾기 페이지
    @GET("/keep/list")
    Call<ArrayList<KeepItem>> listKeep(
            @Query("member_seq") int memberSeq,
            @Query("user_latitude") double userLatitude,
            @Query("user_longitude") double userLongitude
    );


}

