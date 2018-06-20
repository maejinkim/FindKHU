package com.example.maedin.findkhu.item;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2017-09-25.
 */

public class ImageItem {

    @SerializedName("pic_id") public int pic_id;
    @SerializedName("item_type") public int item_type;
    @SerializedName("item_id") public String item_id;
    @SerializedName("pic_name") public String pic_name;

    @Override
    public String toString() {
        return "ImageItem{" +
                "pic_id=" + pic_id +
                ", item_type=" + item_type +
                ", item_id='" + item_id + '\'' +
                ", pic_name='" + pic_name + '\''+
                '}';
    }
}
