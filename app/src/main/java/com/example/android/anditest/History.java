package com.example.android.anditest;

/**
 * Created by UTSAV JAIN on 6/7/2018.
 */

public class History {

    private int mEasyScore;
    private int mStressScore;
    private String mTime;
    private String mDate;


    public History(int easyScore , int stressScire ,String time, String date) {

        mEasyScore = easyScore;
        mStressScore = stressScire;
        mTime = time;
        mDate = date;

    }

    public int getEasyScore(){
        return mEasyScore;
    }


    public int getStressScore(){
        return mStressScore;
    }

    public String getTestTime(){
        return mTime;

    }

    public String getDate(){
        return mDate;
    }
}
