package com.example.android.anditest;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by UTSAV JAIN on 6/7/2018.
 */

public class CalendarInfo {
    private int mHour;
    private int mMinute;
    private int mSecond;
    private  String mDate ;
    private long mEpochTime;

    public CalendarInfo() {
        Calendar calendar = Calendar.getInstance(Locale.getDefault());
        mHour = calendar.get(Calendar.HOUR_OF_DAY);
        mMinute = calendar.get(Calendar.MINUTE);
        mSecond = calendar.get(Calendar.SECOND);
        mDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        mEpochTime = System.currentTimeMillis();
    }

    public long getEpochTime(){
        return mEpochTime;
    }
    public int getHour(){
        return mHour;
    }
    public int getMinute(){
        return mMinute;
    }
    public int getSecond(){
        return mHour;
    }
    public String getDate(){
        return  mDate;
    }
    public int getMonth(){
        return mHour;
    }
    public int getYear(){
        return mHour;
    }

}
