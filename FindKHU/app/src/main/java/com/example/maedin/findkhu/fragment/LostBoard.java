package com.example.maedin.findkhu.fragment;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.maedin.findkhu.R;
import com.example.maedin.findkhu.activity.MainActivity;
import com.example.maedin.findkhu.activity.MyApp;
import com.example.maedin.findkhu.adapter.ListViewAdapter;
import com.example.maedin.findkhu.item.InfoItem;
import com.example.maedin.findkhu.list.ListVO;
import com.example.maedin.findkhu.remote.IRemoteService;
import com.example.maedin.findkhu.remote.ServiceGenerator;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LostBoard extends Fragment implements OnMapReadyCallback,  View.OnClickListener{

    View view;
    GoogleMap map;
    SupportMapFragment fragment;

    ArrayList<InfoItem> listItem;

    private ListView listView;
    private ListViewAdapter adapter;

    private MapView mapView;

    Button btn_map;
    Button btn_list;
    Button btn_post;
    TextView board_name;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.lost_board, container, false);


        //변수 초기화

        listView = (ListView) view.findViewById(R.id.lost_list_view);
        btn_map = (Button) view.findViewById(R.id.btn_view_lost_map);
        btn_list = (Button) view.findViewById(R.id.btn_view_lost_board);
        btn_post = (Button) view.findViewById(R.id.btn_lost_post);
        board_name = (TextView) view.findViewById(R.id.board_name);

        listView.setVisibility(View.INVISIBLE);

        FragmentManager fm = getChildFragmentManager();
        fragment = (SupportMapFragment) fm.findFragmentById(R.id.lost_map);

        fragment.getMapAsync(this);

        btn_map.setOnClickListener(this);
        btn_list.setOnClickListener(this);
        btn_post.setOnClickListener(this);


        ArrayList<InfoItem> templist = ((MyApp)getActivity().getApplication()).getListItem();
        //ArrayList<>
        listItem = new ArrayList<>();

        for (int i =0; i < templist.size(); i++)
        {
            if (((MyApp)getActivity().getApplication()).getPostSelect() == templist.get(i).item_type)
                listItem.add(templist.get(i));
        }

        if (((MyApp)getActivity().getApplication()).getPostSelect() == 1)
            board_name.setText("분실물 게시판");
        else
            board_name.setText("습득물 게시판");


        return view;
    }

    @Override
    public void onMapReady(final GoogleMap map) {

        this.map = map;
        MapsInitializer.initialize(this.getActivity());


        LatLng SEOUL = new LatLng(37.56, 126.97);

        //지도 이동
        //map.moveCamera(CameraUpdateFactory.newLatLng(SEOUL));
        //map.animateCamera(CameraUpdateFactory.zoomTo(15));

        CameraPosition cp = new CameraPosition.Builder().target((SEOUL)).zoom(15).build();
        map.moveCamera(CameraUpdateFactory.newCameraPosition(cp));

        //마커 설정
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(SEOUL); //마커가 표시될 위치
        markerOptions.title("서울"); //마커타이틀
        markerOptions.snippet("한국의 수도");   //마커설명
        map.addMarker(markerOptions);   //지도에 마커 추가
    }

    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
            case R.id.btn_view_lost_board:
            {
                fragment.getView().setVisibility(View.INVISIBLE);
                listView.setVisibility(View.VISIBLE);

//                IRemoteService remoteService = ServiceGenerator.createService(IRemoteService.class);
//                Call<String> call = remoteService.listFoodInfo(1);
//                call.enqueue(new Callback<String>() {
//                    @Override
//                    public void onResponse(Call<String> call, Response<String> response) {
//                            //listItem.add(response.body().get(0));
//                        Toast.makeText(getActivity(),response.body(),Toast.LENGTH_SHORT).show();
//
//
//                        if(response.isSuccessful() && list != null){
//                            infoListAdapter.addItemList(list);
//                            if(infoListAdapter.getItemCount() == 0){
//                                noDataText.setVisibility(View.VISIBLE);
//                            } else {
//                                noDataText.setVisibility(View.GONE);
//                            }
//                       }
//                    }
//                    @Override
//                    public void onFailure(Call<String> call, Throwable t) {
//
//                    }
//                });
//


                //onclickListener 등록
                for(int i=0; i < listItem.size(); i++)
                    listItem.get(i).onClickListener = this;

                //어뎁터 할당
                if (listItem.size() == 0)
                {
                    Toast.makeText(getActivity(),"데이터 없음",Toast.LENGTH_SHORT).show();
                }
                adapter = new ListViewAdapter(listItem);
                listView.setAdapter(adapter);

                break;
            }
            case R.id.btn_view_lost_map:
            {
                Activity root = getActivity();
                Toast.makeText(root, "클릭", Toast.LENGTH_LONG).show();


                fragment.getView().setVisibility(View.VISIBLE);
                listView.setVisibility(View.INVISIBLE);
                break;
            }
            case R.id.btn_lost_post:
            {
                ((MainActivity)getActivity()).replaceFragment(new LostPost());
                break;
            }

        }
    }

}
