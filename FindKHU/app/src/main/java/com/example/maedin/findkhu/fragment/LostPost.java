package com.example.maedin.findkhu.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import com.example.maedin.findkhu.R;
import com.example.maedin.findkhu.activity.MainActivity;
import com.example.maedin.findkhu.activity.MyApp;
import com.example.maedin.findkhu.item.MemberInfoItem;

public class LostPost extends Fragment implements View.OnClickListener{


    View view;
    Button btn_post;
    Button btn_pic;
    Button btn_map;
    EditText edit_title;
    EditText edit_contents;
    EditText edit_date;
    Spinner category;


    SpinnerAdapter sAdapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view =  inflater.inflate(R.layout.lost_post, container, false);


        btn_post = (Button) view.findViewById(R.id.btn_lost_post_ok);
        btn_pic = (Button) view.findViewById(R.id.btn_lost_pic);
        btn_map = (Button) view.findViewById(R.id.btn_lost_select_map);
        edit_title = (EditText) view.findViewById(R.id.edit_lost_title);
        edit_contents = (EditText) view.findViewById(R.id.edit_lost_contents);
        edit_date = (EditText) view.findViewById(R.id.edit_lost_date);

        btn_post.setOnClickListener(this);
        btn_map.setOnClickListener(this);
        btn_pic.setOnClickListener(this);


        final Activity root = getActivity();
        category = (Spinner) view.findViewById(R.id.spinner_category); //butterknife 없을경우
        sAdapter = ArrayAdapter.createFromResource(root, R.array.category, android.R.layout.simple_spinner_dropdown_item);

        category.setAdapter(sAdapter);
//        category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            public void onItemSelected(AdapterView<!--?-->  parent, View view, int position, long id) {
//
//                Toast.makeText(root,
//                        sAdapter.getItem(position), Toast.LENGTH_SHORT).show();
//            }
//
//        });





        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btn_lost_select_map:

                ((MyApp)getActivity().getApplication()).setPostSelect(1);
                ((MainActivity)getActivity()).replaceFragment(new MapSelect());

        }

    }
}
