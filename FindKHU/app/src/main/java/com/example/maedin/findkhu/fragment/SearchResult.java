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

public class SearchResult extends Fragment implements View.OnClickListener {


    View view;
    String search;

    ArrayList<InfoItem> listItem;
    private ListView listView;
    private ListViewAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.search_result, container, false);


        view = inflater.inflate(R.layout.notice_view, container, false);
        listView = (ListView) view.findViewById(R.id.search_result_view);


        listItem = ((MyApp)getActivity().getApplication()).getListItem();
        ArrayList<InfoItem> result = new ArrayList<>();

        for (int i =0; i <listItem.size(); i++)
        {
            //포함할때 리스트에 추가
            if (listItem.get(i).item_title.contains(search))
                result.add(listItem.get(i));
        }

        //onclickListener 등록
        for(int i=0; i < result.size(); i++)
            result.get(i).onClickListener = this;

        //어뎁터 할당
        if (result.size() == 0)
        {
            Toast.makeText(getActivity(),"검색 결과 없음",Toast.LENGTH_SHORT).show();
        }
        else {
            adapter = new ListViewAdapter(result);
            listView.setAdapter(adapter);
        }
        return view;

    }

    public void setInput(String input)
    {
        search = input;
    }

    @Override
    public void onClick(View v) {

    }
}
