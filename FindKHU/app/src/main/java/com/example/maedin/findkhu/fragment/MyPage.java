package com.example.maedin.findkhu.fragment;


import android.app.Activity;
import android.app.FragmentManager;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.maedin.findkhu.R;

public class MyPage extends Fragment implements View.OnClickListener {

    View view;
    Button btn_edit_my_info;
    Button btn_my_comment;
    Button btn_commented_post;
    Button btn_completed_post;
    Button btn_and_more;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {


        view = inflater.inflate(R.layout.mypage, container, false);

        btn_edit_my_info = (Button) view.findViewById(R.id.edit_my_info);
        btn_my_comment = (Button) view.findViewById(R.id.myComment);
        btn_commented_post = (Button) view.findViewById(R.id.CommentedPost);
        btn_completed_post = (Button) view.findViewById(R.id.completedPost);
        btn_and_more = (Button) view.findViewById(R.id.andMore);

        btn_edit_my_info.setOnClickListener(this);
        btn_my_comment.setOnClickListener(this);
        btn_commented_post.setOnClickListener(this);
        btn_completed_post.setOnClickListener(this);
        btn_and_more.setOnClickListener(this);

        btn_my_comment.setSelected(true);
        return view;
    }

    @Override
    public void onClick(View v) {
        //여기에 할 일 적기
        v.setSelected(true);

        btn_my_comment.setSelected(false);
        btn_commented_post.setSelected(false);
        btn_completed_post.setSelected(false);
        btn_and_more.setSelected(false);

        Activity root = getActivity();

        switch (v.getId())
        {
            case R.id.edit_my_info:
                Toast.makeText(root, "정보 수정", Toast.LENGTH_LONG).show();
                break;

            case R.id.myComment:
                //manager.beginTransaction().replace(R.id.find_board_layout, new FindBoard()).commit();
                Toast.makeText(root, "내가 쓴글", Toast.LENGTH_LONG).show();
                break;

            case R.id.CommentedPost:
                Toast.makeText(root, "댓글 단글", Toast.LENGTH_LONG).show();
                break;

            case R.id.completedPost:
                Toast.makeText(root, "완료된 글", Toast.LENGTH_LONG).show();
                break;

            case R.id.andMore:
                Toast.makeText(root, "즐겨찾기", Toast.LENGTH_LONG).show();
                break;

        }

    }

}