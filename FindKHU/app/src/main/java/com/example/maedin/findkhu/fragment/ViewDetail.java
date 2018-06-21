package com.example.maedin.findkhu.fragment;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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

public class ViewDetail extends Fragment implements View.OnClickListener {

    View view;
    InfoItem infoItem;
    TextView txt_cat;
    TextView txt_title;
    TextView txt_writer;
    TextView txt_content;
    Button btn_notice;
    Button btn_complete;
    Button btn_map;


    private String[] cat = {"지갑","카드","가방","의류","휴대폰","귀금속","전자제품","기타"};


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.view_detail, container, false);


        txt_cat = (TextView) view.findViewById(R.id.txt_category);
        txt_title = (TextView) view.findViewById(R.id.txt_title);
        txt_writer = (TextView) view.findViewById(R.id.txt_writer);
        txt_content = (TextView) view.findViewById(R.id.txt_contents);
        btn_notice = (Button) view.findViewById(R.id.btn_notice_check);
        btn_complete = (Button) view.findViewById(R.id.btn_complete);
        btn_map = (Button) view.findViewById(R.id.btn_detail_map);
        btn_notice.setOnClickListener(this);
        btn_complete.setOnClickListener(this);
        btn_map.setOnClickListener(this);

        txt_cat.setText("[ "+cat[infoItem.cat_id-1]+" ] ");
        txt_title.setText(infoItem.item_title);
        txt_writer.setText(infoItem.user_id);
        txt_content.setText(infoItem.item_content);

        if (((MyApp)getActivity().getApplication()).getMemberID().equals(infoItem.user_id))
        {
            btn_notice.setVisibility(View.INVISIBLE);
            btn_complete.setVisibility(View.VISIBLE);

        }
        else
        {
            btn_notice.setVisibility(View.VISIBLE);
            btn_complete.setVisibility(View.INVISIBLE);

        }

        return view;
    }

    public void setInfoItem(InfoItem infoItem) {
        this.infoItem = infoItem;
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.btn_notice_check)
        {
            Toast.makeText(getActivity(), "즐겨찾기 추가!",Toast.LENGTH_SHORT).show();
            ((MyApp)getActivity().getApplication()).addNoticeItem(infoItem.item_id);
        }
        else if (v.getId() == R.id.btn_detail_map)
        {
            ViewMap viewMap = new ViewMap();
            viewMap.setInfoItem(infoItem);
            ((MainActivity)getActivity()).replaceFragment(viewMap);
        }
        else
        {
            ((MyApp)getActivity().getApplication()).addComoleteItem(infoItem);
            ((MainActivity)getActivity()).replaceFragment(new LostBoard());
        }

    }


}
