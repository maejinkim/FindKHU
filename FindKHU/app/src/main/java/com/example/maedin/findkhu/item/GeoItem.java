package com.example.maedin.findkhu.item;

import com.google.android.gms.maps.model.LatLng;

/**
 * 위치 정보를 저장하는 객체
 */
public class GeoItem {

    public static double knownLatitude;
    public static double knownLongitude;

    /**
     * 사용자의 위도, 경도 객체를 반환한다. 만약 사용자의 위치를 알 수 없다면 서울 위치를 반환한다.
     * @return LatLng 위도,경도 객체
     */
    public static LatLng getKnownLocation(){
        // 저장한 주소가 없으면 서울 주소값 리턴
        if(knownLatitude == 0 || knownLongitude == 0){
            return new LatLng(37.2418785, 127.0797748);
        } else {
            return new LatLng(knownLatitude, knownLongitude);
        }
    }
}
