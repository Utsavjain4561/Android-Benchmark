package com.example.android.anditest;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by UTSAV JAIN on 6/3/2018.
 */

public class Information implements Parcelable,Comparable {

    private String mTitle,mInfo , mManufacturer , mModel,mTime ;
    private int mEasyScore , mStressScore , mScore;
    private boolean mDatabaseSet;
    private long mEpochTime;
    private String mDate;
    private HashMap<String,Object> infoObject;
    public Information() {
        super();
    }

    public Information(String title, String info) {

        mTitle = title;
        mInfo =info;

    }
    public Information(String model,int score){
        mModel = model;
        mScore = score;
    }


    public Information(String manufacturer, String model , int easyScore , int stressScore,boolean databaseSet
            ,String date,String currentTime,long epochTime) {
        mManufacturer = manufacturer;
        mModel = model;
        mEasyScore = easyScore;
        mStressScore = stressScore;
        mDatabaseSet = databaseSet;
       mTime = currentTime;
        mDate = date;
        mEpochTime = epochTime;
    }

    protected Information(Parcel in) {
        mTitle = in.readString();
        mInfo = in.readString();
        mManufacturer = in.readString();
        mModel = in.readString();
        mEasyScore = in.readInt();
        mStressScore = in.readInt();
        mDatabaseSet = in.readByte() != 0;
    }



    public static final Creator<Information> CREATOR = new Creator<Information>() {
        @Override
        public Information createFromParcel(Parcel in) {
            return new Information(in);
        }

        @Override
        public Information[] newArray(int size) {
            return new Information[size];
        }
    };
    public int getScore(){return mScore;}
    public void setScore(int score){mScore = score;}
    public long getEpochTime(){
        return  mEpochTime;
    }
    public String getDate(){
        return mDate;
    }
    public String getTime(){
        return mTime;
    }
    public String getManufacturer(){
        return mManufacturer;
    }
    public void setManufacturer(String manufacturer){
        mManufacturer = manufacturer;
     }
    public String getModel(){
        return mModel;
    }
    public void setModel(String model){
        mModel = model;
    }
    public int getEasyScore(){
        return mEasyScore;
    }
    public void setEasyScore(int easyScore){
        mEasyScore = easyScore;
    }
    public int getStressScore(){
        return mStressScore;
    }
    public void setStressScore(int stressScore){
        mStressScore = stressScore;
    }

    public boolean getDatabaseFlag(){
        return mDatabaseSet;
    }

    public void setDatabaseFlag(boolean databaseSet){
        mDatabaseSet = databaseSet;
    }


    public String getTitle(){
        return mTitle;
    }

    public String getInfo(){
        return mInfo;
    }
    public HashMap<String,Object> getGetInfoObject(){
        return infoObject;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mTitle);
        parcel.writeString(mInfo);
        parcel.writeString(mManufacturer);
        parcel.writeString(mModel);
        parcel.writeInt(mEasyScore);
        parcel.writeInt(mStressScore);
        parcel.writeByte((byte) (mDatabaseSet ? 1 : 0));
    }


    @Override
    public int compareTo(@NonNull Object o) {
        int score = ((Information)o).getScore();
        return  this. mScore - score;
    }
}
