package com.example.maedin.findkhu;

<<<<<<< HEAD
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
=======
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
>>>>>>> 1eb93bda9f7838ab8df7c5e7c8aaa3724157f647
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import sungkyul.ac.kr.customframework.R;
import sungkyul.ac.kr.customframework.retrofit.RetrofitRepo;
import sungkyul.ac.kr.customframework.retrofit.RetrofitService;

public class TestActivity extends Fragment {

    View mView;
    TextView textViewIndex, textViewPost, textViewMap;
    static final String URL = "http://www.dxmnd.com/blog/";

<<<<<<< HEAD
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_retrofit, container, false);
=======
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
>>>>>>> 1eb93bda9f7838ab8df7c5e7c8aaa3724157f647

        textViewIndex = (TextView)mView.findViewById(R.id.txtRetrofitTest);
        textViewPost = (TextView)mView.findViewById(R.id.txtRetrofitPost);
        textViewMap = (TextView)mView.findViewById(R.id.txtRetrofitMap);

        index();
        post();
        map();

        return mView;
    }


    public void index() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RetrofitService retrofitService = retrofit.create(RetrofitService.class);
        Call<RetrofitRepo> call = retrofitService.getIndex("mos");
        call.enqueue(new Callback<RetrofitRepo>() {
            @Override
            public void onResponse(Call<RetrofitRepo> call, Response<RetrofitRepo> response) {
                RetrofitRepo repo = response.body();
                textViewIndex.setText(repo.getName());
            }

            @Override
            public void onFailure(Call<RetrofitRepo> call, Throwable t) {

            }
        });
    }

    public void post() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RetrofitService retrofitService = retrofit.create(RetrofitService.class);
        Call<RetrofitRepo> call = retrofitService.getPost("post");
        call.enqueue(new Callback<RetrofitRepo>() {
            @Override
            public void onResponse(Call<RetrofitRepo> call, Response<RetrofitRepo> response) {
                RetrofitRepo repo = response.body();
                textViewPost.setText(repo.getOutput());
            }

            @Override
            public void onFailure(Call<RetrofitRepo> call, Throwable t) {

            }
        });
    }

    public void map() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Map map = new HashMap();
        map.put("name","map");

        RetrofitService retrofitService = retrofit.create(RetrofitService.class);
        Call<RetrofitRepo> call = retrofitService.getItem(map);
        call.enqueue(new Callback<RetrofitRepo>() {
            @Override
            public void onResponse(Call<RetrofitRepo> call, Response<RetrofitRepo> response) {
                RetrofitRepo repo = response.body();
                String a = "";
                for(int i=0;i<repo.getInfo().size();i++) {
                    a += repo.getInfo().get(i).getName() + "\n";
                    a += repo.getInfo().get(i).getOutput();
                }
                textViewMap.setText(a);
            }

            @Override
            public void onFailure(Call<RetrofitRepo> call, Throwable t) {

            }
        });
    }

    }
