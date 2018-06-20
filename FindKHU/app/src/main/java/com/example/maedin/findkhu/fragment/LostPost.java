package com.example.maedin.findkhu.fragment;

import android.app.Activity;
import android.app.DatePickerDialog;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.maedin.findkhu.R;
import com.example.maedin.findkhu.activity.MainActivity;
import com.example.maedin.findkhu.activity.MyApp;
import com.example.maedin.findkhu.item.MemberInfoItem;

public class LostPost extends Fragment implements View.OnClickListener{

    int mYear, mMonth, mDay;
    View view;
    Button btn_post;
    Button btn_pic;
    Button btn_map;
    Button btn_date;
    EditText edit_title;
    EditText edit_contents;
    TextView edit_date;
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
        edit_date = (TextView) view.findViewById(R.id.edit_lost_date);
        btn_date = (Button) view.findViewById(R.id.btn_select_date);

        btn_post.setOnClickListener(this);
        btn_map.setOnClickListener(this);
        btn_pic.setOnClickListener(this);
        btn_date.setOnClickListener(this);

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
                ((MyApp)getActivity().getApplication()).setLostPost(this);
                ((MainActivity)getActivity()).replaceFragment(new MapSelect());
            break;
            case R.id.btn_select_date:
                new DatePickerDialog(this.getActivity(), mDateSetListener, mYear, mMonth, mDay).show();
                break;
        }

    }

    DatePickerDialog.OnDateSetListener mDateSetListener =

            new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub

                    mYear = year;

                    mMonth = monthOfYear;

                    mDay = dayOfMonth;

                    //텍스트뷰의 값을 업데이트함

            UpdateNow();

                }

            };

    void UpdateNow(){
        edit_date.setText(String.format("%d년 %d월 %d일", mYear, mMonth+1, mDay));
    }




}
