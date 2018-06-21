package com.example.maedin.findkhu.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.maedin.findkhu.R;
import com.example.maedin.findkhu.activity.MyApp;
import com.example.maedin.findkhu.item.InfoItem;

public class ViewDetail extends Fragment implements View.OnClickListener {

    View view;
    InfoItem infoItem;
    TextView txt_cat;
    TextView txt_title;
    TextView txt_writer;
    TextView txt_content;
    Button btn_notice;

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

        btn_notice.setOnClickListener(this);

        txt_cat.setText("[ "+cat[infoItem.cat_id-1]+" ] ");
        txt_title.setText(infoItem.item_title);
        txt_writer.setText(infoItem.user_id);
        txt_content.setText(infoItem.item_content);

        return view;
    }

    public void setInfoItem(InfoItem infoItem) {
        this.infoItem = infoItem;
    }

    @Override
    public void onClick(View v) {
        Toast.makeText(getActivity(), "즐겨찾기 추가!",Toast.LENGTH_SHORT).show();
        ((MyApp)getActivity().getApplication()).addNoticeItem(infoItem.item_id);
    }
}
