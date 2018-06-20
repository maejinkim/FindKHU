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
import android.widget.Toast;

import com.example.maedin.findkhu.R;
import com.example.maedin.findkhu.adapter.ListViewAdapter;
import com.example.maedin.findkhu.list.ListVO;
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

public class FindBoard extends Fragment implements OnMapReadyCallback,  View.OnClickListener {

    View view;
    GoogleMap map;
    SupportMapFragment fragment;

    private ListView listView;
    private ListViewAdapter adapter;

    private MapView mapView;

    Button btn_map;
    Button btn_list;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.find_board, container, false);

        //변수 초기화

        listView = (ListView) view.findViewById(R.id.find_list_view);
        btn_map = (Button) view.findViewById(R.id.btn_view_find_map);
        btn_list = (Button) view.findViewById(R.id.btn_view_find_board);

        listView.setVisibility(View.INVISIBLE);

        FragmentManager fm = getChildFragmentManager();
        fragment = (SupportMapFragment) fm.findFragmentById(R.id.find_map);

        fragment.getMapAsync(this);

        btn_map.setOnClickListener(this);
        btn_list.setOnClickListener(this);

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

                Activity root = getActivity();
                Toast.makeText(root, "클릭", Toast.LENGTH_LONG).show();

                fragment.getView().setVisibility(View.INVISIBLE);
                listView.setVisibility(View.VISIBLE);

                ArrayList<ListVO> listItem = new ArrayList<>();


                //어뎁터를 통한 값 전달
                for(int i=0; i < 20; i++)
                {
                    ListVO item = new ListVO();
                    item.setText(i+"번째 정보");
                    item.onClickListener = this;
                    listItem.add(item);
                }
                //어뎁터 할당
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

        }
    }
}
