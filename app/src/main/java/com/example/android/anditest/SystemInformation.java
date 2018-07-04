package com.example.android.anditest;

import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Pattern;

/**
 * Created by UTSAV JAIN on 6/6/2018.
 */

public class SystemInformation {

    private static final int INSERTION_POINT = 27;
    private String mTitle;
    private float mValue;
    private int mId;

    public SystemInformation() {
        super();
    }

    public SystemInformation(String title, float value, int id) {
        mTitle = title;
        mValue = value;
        mId = id;

    }

    public String getTitle(){
        return mTitle;
    }
    public float getValue(){
        return mValue;

    }
    public int getResourceId(){
        return mId;

    }

    public int getNumCores() {
        //Private Class to display only CPU devices in the directory listing
        class CpuFilter implements FileFilter {
            @Override
            public boolean accept(File pathname) {
                //Check if filename is "cpu", followed by a single digit number
                if(Pattern.matches("cpu[0-9]", pathname.getName())) {
                    return true;
                }
                return false;
            }
        }

        try {
            //Get directory containing CPU info
            File dir = new File("/sys/devices/system/cpu/");
            //Filter to only list the devices we care about
            File[] files = dir.listFiles(new CpuFilter());
            Log.e( "CPU Count: ",String.valueOf(files.length));
            //Return the number of cores (virtual CPU devices)
            return files.length;
        } catch(Exception e) {
            //Print exception
            //Log.d(TAG, "CPU Count: Failed.");
            e.printStackTrace();
            //Default to return 1 core
            return 1;
        }
    }


    public  String getCurFrequencyFilePath(int whichCpuCore){
        StringBuilder filePath = new StringBuilder("/sys/devices/system/cpu/cpu/cpufreq/scaling_cur_freq");
        filePath.insert(INSERTION_POINT, whichCpuCore);
        return filePath.toString();
    }

    public  int getCurrentFrequency(int whichCpuCore){

        int curFrequency = -1;
        String cpuCoreCurFreqFilePath = getCurFrequencyFilePath(whichCpuCore);

        if(new File(cpuCoreCurFreqFilePath).exists()){

            try {
                BufferedReader br = new BufferedReader(new FileReader(new File(cpuCoreCurFreqFilePath)));
                String aLine;
                while ((aLine = br.readLine()) != null) {

                    try{
                        curFrequency = Integer.parseInt(aLine);
                    }
                    catch(NumberFormatException e){

                        //Log.e(getPackageName(), e.toString());
                    }

                }
                if (br != null) {
                    br.close();
                }
            }
            catch (IOException e) {
                //Log.e(getPackageName(), e.toString());
            }

        }

        return curFrequency;
    }

}

