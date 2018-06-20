package com.example.maedin.findkhu.fragment;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import com.example.maedin.findkhu.R;

public class CenterInfo extends Fragment implements View.OnClickListener {

    View view;
    Button btn_bus;
    Button btn_bus2;
    Button btn_daewon;
    Button btn_daewon2;
    Button btn_yongnam;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.center_info, container, false);

        btn_bus = (Button) view.findViewById(R.id.btn_call_bus);
        btn_bus2 = (Button) view.findViewById(R.id.btn_call_bus2);
        btn_daewon = (Button) view.findViewById(R.id.btn_call_daewon);
        btn_daewon2 = (Button) view.findViewById(R.id.btn_call_daewon2);
        btn_yongnam = (Button) view.findViewById(R.id.btn_call_yongnam);

        btn_bus.setOnClickListener(this);
        btn_bus2.setOnClickListener(this);
        btn_daewon.setOnClickListener(this);
        btn_daewon2.setOnClickListener(this);
        btn_yongnam.setOnClickListener(this);

        btn_bus.setSelected(true);
        return view;
    }

    @Override
    public void onClick(View v) {

        v.setSelected(true);

        btn_bus.setSelected(false);
        btn_bus2.setSelected(false);
        btn_daewon.setSelected(false);
        btn_daewon2.setSelected(false);
        btn_yongnam.setSelected(false);

        Activity root = getActivity();

        //Intent intent = new Intent();
        //intent.setAction(Intent.ACTION_DIAL);
        //intent.setData(Uri.parse("tel:12345"));


        switch (v.getId())
        {
            case R.id.btn_call_bus:
            openDial1(v);
            break;

            case R.id.btn_call_bus2:
                openDial1_2(v);
                break;

            case R.id.btn_call_daewon:
                openDial2(v);
                break;

            case R.id.btn_call_daewon2:
                openDial2_2(v);
                break;

            case R.id.btn_call_yongnam:
                openDial3(v);
                break;

        }

    }
    public void openDial1(View view)
    {
        Intent intent=new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:0312012915"));
        startActivity(intent);
    }
    public void openDial1_2(View view)
    {
        Intent intent=new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:01067246221"));
        startActivity(intent);
    }
    public void openDial2(View view)
    {
        Intent intent=new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:0312046657"));
        startActivity(intent);
    }
    public void openDial2_2(View view)
    {
        Intent intent=new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:0312061570"));
        startActivity(intent);
    }
    public void openDial3(View view)
    {
        Intent intent=new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:0312817105"));
        startActivity(intent);
    }

}


