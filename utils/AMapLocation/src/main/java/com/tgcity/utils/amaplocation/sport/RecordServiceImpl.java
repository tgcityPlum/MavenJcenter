package com.tgcity.utils.amaplocation.sport;

import android.content.Context;

import com.amap.api.maps.model.LatLng;

/**
 * 记录运动信息的实现类
 */
public class RecordServiceImpl implements RecordService {

    private Context mContext;

    public RecordServiceImpl(Context context) {
        this.mContext = context;
    }

    @Override
    public void recordSport(LatLng latLng, String location) {

    }

}
