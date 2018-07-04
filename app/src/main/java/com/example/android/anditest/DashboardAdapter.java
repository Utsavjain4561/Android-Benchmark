package com.example.android.anditest;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by UTSAV JAIN on 6/2/2018.
 */

public class DashboardAdapter extends FragmentStatePagerAdapter {

    public DashboardAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        if(position == 0){
            return new TestFragment();
        }
        else if(position == 1) {
            return new AboutFragment();
        }
        else{
            return new HistoryFragment();
        }


    }

    @Override
    public int getCount() {
        return 3;
    }



    @Override
    public CharSequence getPageTitle(int position) {
        if(position == 0){
            return "Home";
        }
        else if(position == 1) {
            return "About";
        }
        else
            return "History";

    }


}
