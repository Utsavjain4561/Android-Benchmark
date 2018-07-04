package com.example.android.anditest;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by UTSAV JAIN on 6/9/2018.
 */

public class LeaderboardAdapter extends FragmentStatePagerAdapter {
    public LeaderboardAdapter(FragmentManager fm) {
        super(fm);

    }

    @Override
    public Fragment getItem(int position) {
        if(position == 0){
            return new EasyResults();
        }
        else
            return new StressResults();
    }

    @Override
    public int getCount() {
        return 2;

    }
    @Override
    public CharSequence getPageTitle(int position) {
        if(position == 0){
            return "Easy Results";
        }
        else
            return "Stress Results";

    }
}
