package com.example.maedin.findkhu.fragment;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.maedin.findkhu.R;
import com.example.maedin.findkhu.adapter.ListViewAdapter;
import com.example.maedin.findkhu.item.InfoItem;

import java.util.ArrayList;

public class NoticeView extends Fragment{

    View view;
    ArrayList<InfoItem> listItem;
    private ListView listView;
    private ListViewAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {


        view = inflater.inflate(R.layout.notice_view, container, false);
        listView = (ListView) view.findViewById(R.id.lost_list_view);

        
        return view;
    }
}
