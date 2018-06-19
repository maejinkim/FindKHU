package com.example.maedin.findkhu;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

public class FindBoard extends Fragment implements View.OnClickListener {

    View view;
    Button btn;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.find_board, container, false);

        btn = (Button) view.findViewById(R.id.btn_find_post);
        btn.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_find_post)
        {
            Activity root = getActivity();
            Toast.makeText(root, "클릭", Toast.LENGTH_LONG).show();
        }
    }
}
