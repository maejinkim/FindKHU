package com.example.maedin.findkhu.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.maedin.findkhu.R;
import com.example.maedin.findkhu.activity.MainActivity;
import com.example.maedin.findkhu.item.InfoItem;
import com.example.maedin.findkhu.list.ListVO;

import java.util.ArrayList;
import java.util.List;

public class ListViewAdapter extends BaseAdapter {

    //넣어줄 데이터 리스트
    //private ArrayList<ListVO> listVO = null;
    private ArrayList<InfoItem> listVO = null;
    private int listCnt = 0;
    private String[] cat = {"지갑","카드","가방","의류","휴대폰","귀금속","전자제품","기타"};

    LayoutInflater inflater = null;


    //생성자 : 데이터 셋팅
    public ListViewAdapter(ArrayList<InfoItem> _listVO) {
        listVO = _listVO;
        listCnt = listVO.size();

    }

    //화면 갱신 전 호출, 아이템 갯수 결정
    @Override
    public int getCount() {
        return listCnt;
    }



    //리스트 뷰에 데이터를 넣어줌 - 화면 표시, position: 몇번째아이템
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        //postion: List View의 위치
        //첫번째면 position = 0;
        final int pos = position;
        final Context context = parent.getContext();

        if (convertView == null)
        {
            if (inflater == null)
                inflater = (LayoutInflater)  context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.custom_listview, parent, false);
        }

        //위젯과 연결
        TextView text = (TextView) convertView.findViewById(R.id.text);

        //"[ "+listVO.get(position).+" ] "
        //아이템 내 각 위젯에 데이터 반영
        text.setText("[ "+cat[listVO.get(position).cat_id-1]+" ] "+listVO.get(position).item_title);


        convertView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                ((MainActivity)v.getContext()).replaceDetail(listVO.get(pos));
            }
        });

        convertView.setTag(""+position);

        return convertView;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Object getItem(int position) {
        return listVO.get(position);
    }

//    //데이터값 추가
//    public void addVO(String text)
//    {
//        InfoItem item = new InfoItem();
//        item.setText(text);
//        listVO.add(item);
//    }
}
