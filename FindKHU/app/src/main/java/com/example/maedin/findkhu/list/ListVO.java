package com.example.maedin.findkhu.list;

import android.view.View;

//List Item Data Class
public class ListVO {

    private String text;
    public View.OnClickListener onClickListener;

    public String getText()
    {
        return text;
    }
    public void setText(String text)
    {
        this.text = text;
    }
}
