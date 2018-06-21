package com.example.maedin.findkhu.fragment;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.example.maedin.findkhu.R;
import com.example.maedin.findkhu.activity.MyApp;
import com.example.maedin.findkhu.adapter.ListViewAdapter;
import com.example.maedin.findkhu.item.InfoItem;

import java.util.ArrayList;

public class NoticeView extends Fragment implements View.OnClickListener{

    View view;
    ArrayList<InfoItem> listItem;
    private ListView listView;
    private ListViewAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {


        view = inflater.inflate(R.layout.notice_view, container, false);
        listView = (ListView) view.findViewById(R.id.notice_view);


        listItem = new ArrayList<InfoItem>();
        ArrayList<InfoItem> list = ((MyApp)getActivity().getApplication()).getListItem();
        ArrayList<Integer> templist = ((MyApp)getActivity().getApplication()).getListNotice();



        for (int i =0; i < templist.size(); i++)
        {
            if ( templist.get(i).equals(list.get(i).item_id))
                listItem.add(list.get(i));
        }
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

        return view;
    }

    @Override
    public void onClick(View v) {

    }
}
