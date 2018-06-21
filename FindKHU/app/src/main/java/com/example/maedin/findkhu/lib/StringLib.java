package com.example.maedin.findkhu.lib;

import android.content.Context;

import com.example.maedin.findkhu.R;

/**
 * Created by Administrator on 2017-09-22.
 */

public class StringLib {

    private static StringLib sStringLib;

    public static StringLib getInstance(){
        if(sStringLib == null){
            sStringLib = new StringLib();
        }
        return sStringLib;
    }

    /**
     * 문자열 null 측정
     */
    public boolean isBlank(String str){
        if(str == null || str.equals("")){
            return true;
        } else {
            return false;
        }
    }



}

