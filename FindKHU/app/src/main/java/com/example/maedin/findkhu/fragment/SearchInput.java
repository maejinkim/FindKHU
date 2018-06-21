package com.example.maedin.findkhu.fragment;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.maedin.findkhu.R;
import com.example.maedin.findkhu.activity.MainActivity;

public class SearchInput extends Fragment implements View.OnClickListener {

    View view;

    EditText edit_search;
    Button btn_search;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.search_input, container, false);

        edit_search = (EditText) view.findViewById(R.id.edit_search);
        btn_search = (Button) view.findViewById(R.id.btn_search);
        btn_search.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        if (edit_search.getText().toString().equals(""))
        {
            Toast.makeText(getActivity(), "검색어를 입력해주세요!",Toast.LENGTH_SHORT).show();
        }
        else
        {
            SearchResult result = new SearchResult();
            result.setInput(edit_search.getText().toString());
            ((MainActivity)getActivity()).replaceFragment(result);
        }
    }
}
