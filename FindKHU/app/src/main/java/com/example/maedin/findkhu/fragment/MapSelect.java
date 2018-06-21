package com.example.maedin.findkhu.fragment;

import android.location.Address;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.maedin.findkhu.R;
import com.example.maedin.findkhu.activity.MainActivity;
import com.example.maedin.findkhu.activity.MyApp;
import com.example.maedin.findkhu.activity.SignUpActivity;
import com.example.maedin.findkhu.item.InfoItem;
import com.example.maedin.findkhu.item.LocItem;
import com.example.maedin.findkhu.item.MemberInfoItem;
import com.example.maedin.findkhu.lib.GeoLib;
import com.example.maedin.findkhu.lib.StringLib;
import com.example.maedin.findkhu.remote.IRemoteService;
import com.example.maedin.findkhu.remote.ServiceGenerator;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MapSelect extends Fragment implements OnMapReadyCallback, GoogleMap.OnMapClickListener, View.OnClickListener {

    View view;

    GoogleMap map;
    SupportMapFragment fragment;

    TextView addressText;

    private LocItem locItem;

    Button btn_ok;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.map_select, container, false);

        FragmentManager fm = getChildFragmentManager();
        fragment = (SupportMapFragment) fm.findFragmentById(R.id.frg_select_map);
        fragment.getMapAsync(this);

        addressText = (TextView) view.findViewById(R.id.txt_address);

        btn_ok = (Button) view.findViewById(R.id.btn_map_select_ok);
        btn_ok.setOnClickListener(this);

        return view;
    }

    @Override
    public void onMapReady(final GoogleMap map) {

        this.map = map;
        MapsInitializer.initialize(this.getActivity());

        //map.setMyLocationEnabled(true);
        map.setOnMapClickListener(this);

        locItem = new LocItem();

        LatLng SEOUL = new LatLng(37.2418785, 127.0797748);

        //지도 이동
        //map.moveCamera(CameraUpdateFactory.newLatLng(SEOUL));
        //map.animateCamera(CameraUpdateFactory.zoomTo(15));

        CameraPosition cp = new CameraPosition.Builder().target((SEOUL)).zoom(15).build();
        map.moveCamera(CameraUpdateFactory.newCameraPosition(cp));

//        LatLng firstLocation = new LatLng(foodInfoItem.latitude, foodInfoItem.longitude);
//        if(foodInfoItem.latitude != 0 && foodInfoItem.longitude != 0){
//            addMarker(firstLocation, DEFAULT_ZOOM_LEVEL);
//        }
    }

    @Override
    public void onMapClick(LatLng latLng) {
        // 1. 위치 설정
        setCurrentLatLng(latLng);
        // 2. 주소 설정
        setAddressText(latLng);
        // 3, 4 마커 설정 & 이동
        addMarker(latLng, map.getCameraPosition().zoom);

    }

    /**
     * 지정된 latLng의 위도와 경도를 infoItem에 저장한다.
     *
     * @param latLng 위도, 경도 객체
     */
    private void setCurrentLatLng(LatLng latLng) {
        locItem.loc_lat = latLng.latitude;
        locItem.loc_lng = latLng.longitude;
    }

    /**
     * 위도 경도를 기반으로 주소를 addressText 뷰에 출력한다.
     *
     * @param latLng 위도, 경도 객체
     */
    private void setAddressText(LatLng latLng) {
        Address address = GeoLib.getInstance().getAddressString(getContext(), latLng);
        String addressStr = GeoLib.getInstance().getAddressString(address);
        if (!StringLib.getInstance().isBlank(addressStr)) {
            addressText.setText(addressStr);
            locItem.loc_address = addressStr;
        }
    }

    private void addMarker(LatLng markerPosition, float zoomLevel) {
        MarkerOptions options = new MarkerOptions();
        options.position(markerPosition);
        options.title("현재위치");
        options.draggable(true);

        // 이전 마커는 지워준다
        map.clear();
        map.addMarker(options);

        // 마커 위치로 이동한다
        moveLocation(markerPosition, zoomLevel);
    }

    /**
     * 위치 이동
     *
     * @param targetPosition
     * @param zoomLevel
     */
    private void moveLocation(LatLng targetPosition, float zoomLevel) {
        // 빌드 패턴 배울 것.
        CameraPosition cp = new CameraPosition.Builder().target(targetPosition).zoom(zoomLevel).build();
        map.moveCamera(CameraUpdateFactory.newCameraPosition(cp));
    }

    @Override
    public void onClick(View v) {

        // 변경 사항 있을 경우
        IRemoteService remoteService = ServiceGenerator.createService(IRemoteService.class);
        Call<ResponseBody> call = remoteService.insertLocInfo(locItem);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    String loc_id = null;
                    try {
                        loc_id = response.body().string();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Log.e("Response 리턴값", loc_id);
                    Log.e("marker 등록", "성공");

                    locItem.loc_id = Integer.parseInt(loc_id);
                    ((MyApp)getActivity().getApplication()).addLocItem(locItem);
                    ((MyApp)getActivity().getApplication()).setLoc_id(Integer.parseInt(loc_id));

                    ((MainActivity)getActivity()).replaceFragment(((MyApp)getActivity().getApplication()).getLostPost());




                } else {
                    Log.e("marker 등록", "오류");

                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("marker 등록", "서버 연결 실패");
            }
        });
    }

}

