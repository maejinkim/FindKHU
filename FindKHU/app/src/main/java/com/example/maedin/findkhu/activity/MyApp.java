package com.example.maedin.findkhu.activity;

import android.app.Application;
import android.os.StrictMode;

import com.example.maedin.findkhu.fragment.LostPost;
import com.example.maedin.findkhu.item.ImageItem;
import com.example.maedin.findkhu.item.InfoItem;
import com.example.maedin.findkhu.item.LocItem;
import com.example.maedin.findkhu.item.MemberInfoItem;

import java.util.ArrayList;

/**
 * 어플리케이션 단위에서 지속적으로 가져다 써야 하는 자원(사용자 정보)을 임시 저장해 둔다.
 *
 * 1. 사용자 정보
 * 2. 음식 정보
 */

public class MyApp extends Application {

    private MemberInfoItem memberInfoItem;
    private ArrayList<InfoItem> listItem;
    private ArrayList<LocItem> listIoc;
    private ArrayList<ImageItem> listImg;
    private ArrayList<Integer> listNotice;
    private ArrayList<InfoItem> listComplete;
    private int loc_id;
    private int pic_id;
    private int postSelect;
    private LostPost lostPost;
//    private FoodInfoItem foodInfoItem;

    @Override
    public void onCreate() {
        super.onCreate();

        listItem = new ArrayList<>();
        listIoc = new ArrayList<>();
        listImg = new ArrayList<>();
        listNotice = new ArrayList<>();
        listComplete =new ArrayList<>();

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
    }

    public MemberInfoItem getMemberInfoItem() {
        if(memberInfoItem == null){
            memberInfoItem = new MemberInfoItem();
        }
        return memberInfoItem;
    }

    public void setMemberInfoItem(MemberInfoItem memberInfoItem) {
        this.memberInfoItem = memberInfoItem;
    }

    public String getMemberName() {
        return memberInfoItem.user_name;
    }

    public String getMemberID() {
        return memberInfoItem.user_id;
    }

    public int getLoc_id() {
        return loc_id;
    }

    public void setLoc_id(int loc_id) {
        this.loc_id = loc_id;
    }

    public int getPic_id() {
        return pic_id;
    }

    public void setPic_id(int pic_id) {
        this.pic_id = pic_id;
    }

    public int getPostSelect() {
        return postSelect;
    }

    public void setPostSelect(int postSelect) {
        this.postSelect = postSelect;
    }

    public LostPost getLostPost() {
        return lostPost;
    }

    public void setLostPost(LostPost lostPost) {
        this.lostPost = lostPost;
    }

    public ArrayList<InfoItem> getListItem() {
        return listItem;
    }

    public void setListItem(ArrayList<InfoItem> listItem) {
        this.listItem = listItem;
    }

    public ArrayList<LocItem> getListIoc() {
        return listIoc;
    }

    public void setListIoc(ArrayList<LocItem> listIoc) {
        this.listIoc = listIoc;
    }

    public ArrayList<ImageItem> getListImg() {
        return listImg;
    }

    public void setListImg(ArrayList<ImageItem> listImg) {
        this.listImg = listImg;
    }

    public void addInfoItem(InfoItem item)
    {
        listItem.add(item);
    }

    public void addLocItem(LocItem item)
    {
        listIoc.add(item);
    }

    public void addImgItem(ImageItem item)
    {
        listImg.add(item);
    }

    public void addNoticeItem(int item)
    {
        listNotice.add(item);
    }

    public ArrayList<Integer> getListNotice() {
        return listNotice;
    }

    public void setListNotice(ArrayList<Integer> listNotice) {
        this.listNotice = listNotice;
    }

    public ArrayList<InfoItem> getListComplete() {
        return listComplete;
    }

    public void setListComplete(ArrayList<InfoItem> listComplete) {
        this.listComplete = listComplete;
    }

    public void addComoleteItem(InfoItem item)
    {
        listComplete.add(item);
        listItem.remove(item);
    }
    //
//    public FoodInfoItem getFoodInfoItem() {
//        return foodInfoItem;
//    }

//    public void setFoodInfoItem(FoodInfoItem foodInfoItem) {
//        this.foodInfoItem = foodInfoItem;
//    }
}
