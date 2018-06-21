package com.example.maedin.findkhu.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.maedin.findkhu.R;
import com.example.maedin.findkhu.activity.MainActivity;
import com.example.maedin.findkhu.activity.MyApp;
import com.example.maedin.findkhu.item.InfoItem;
import com.example.maedin.findkhu.item.LocItem;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class ViewMap extends Fragment implements View.OnClickListener, OnMapReadyCallback {

    GoogleMap map;
    SupportMapFragment fragment;
    View view;
    InfoItem infoItem;
    Button btn;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.view_map, container, false);

        FragmentManager fm = getChildFragmentManager();
        fragment = (SupportMapFragment) fm.findFragmentById(R.id.frg_map);
        fragment.getMapAsync(this);
        btn = (Button) view.findViewById(R.id.btn_back);
        btn.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        ((MainActivity)getActivity()).replaceDetail(infoItem);
    }
    public void setInfoItem(InfoItem infoItem) {
        this.infoItem = infoItem;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.map = googleMap;
        MapsInitializer.initialize(this.getActivity());

        ArrayList<LocItem> templist = ((MyApp)getActivity().getApplication()).getListIoc();
        LocItem loc = new LocItem();

        for (int i =0; i < templist.size(); i++)
        {
            if(infoItem.loc_id == templist.get(i).loc_id)
            {
                loc = templist.get(i);
                break;
            }
        }


        LatLng tmp = new LatLng(loc.loc_lat, loc.loc_lng);

        CameraPosition cp = new CameraPosition.Builder().target((tmp)).zoom(15).build();
        map.moveCamera(CameraUpdateFactory.newCameraPosition(cp));

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(tmp); //마커가 표시될 위치
        markerOptions.title(loc.loc_address); //마커타이틀
        map.addMarker(markerOptions);
    }
}
