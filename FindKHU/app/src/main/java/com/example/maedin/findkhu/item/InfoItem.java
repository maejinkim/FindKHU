package com.example.maedin.findkhu.item;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;




/**
 * Created by Administrator on 2017-09-22.
 */

public class InfoItem implements Parcelable{

    public int item_type;
    public int item_id;
    public String user_id;
    public String item_title;
    public String item_content;
    public String item_reg_date;
    public String item_date;
    public int cat_id;
    public int loc_id;
    public int pic_id;

    public  InfoItem(Parcel in){
        item_type=in.readInt();
        item_id=in.readInt();
        user_id=in.readString();
        item_title=in.readString();
        item_content=in.readString();
        item_reg_date=in.readString();
        item_date=in.readString();
    }
    public static final Creator<InfoItem> CREATOR = new Creator<InfoItem>() {
        @Override
        public InfoItem createFromParcel(Parcel in) {
            return new InfoItem(in);
        }

        @Override
        public InfoItem[] newArray(int size) {
            return new InfoItem[size];
        }
    };

    @Override
    public String toString() {
        return "InfoItem{" +
                "item_type"+item_type+
                ", item_id"+item_id+
                ", user_id"+user_id+
                ", item_title"+item_title+
                ", item_content"+item_content+
                ", item_reg_date"+item_reg_date+
                ", item_date"+item_date+
                ", cat_id"+cat_id+
                ", loc_id"+loc_id+
                ", pic_id"+pic_id+'}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(android.os.Parcel dest, int flags) {
        dest.writeInt(item_type);
        dest.writeInt(item_id);
        dest.writeString(user_id);
        dest.writeString(item_title);
        dest.writeString(item_content);
        dest.writeString(item_reg_date);
        dest.writeString(item_date);
        dest.writeInt(cat_id);
        dest.writeInt(loc_id);
        dest.writeInt(pic_id);
    }
}
