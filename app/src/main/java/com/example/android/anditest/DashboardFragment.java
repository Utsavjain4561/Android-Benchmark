package com.example.android.anditest;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by UTSAV JAIN on 6/9/2018.
 */

public class DashboardFragment extends Fragment {
    public static final String INFO_KEY = "info-key";
    FirebaseDatabase firebaseDatabase;
    ViewPager viewPager;
    CalendarInfo calendarInfo;
    DashboardAdapter dashboardAdapter;
    TabLayout tabLayout;
    long epochTime;

    Information firebaseInformation;

    Long easyScore, stressScore;
    String date, time;
    boolean flag;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dashboard, container, false);

        calendarInfo = new CalendarInfo();
        firebaseDatabase = FirebaseDatabase.getInstance();
        final DatabaseReference databaseReference = firebaseDatabase.getReference("info");
        Query query = databaseReference.child(Build.MANUFACTURER).child(Build.MODEL).child(Build.SERIAL);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                firebaseInformation = dataSnapshot.getValue(Information.class);
                System.out.println(String.valueOf(firebaseInformation));
                if (firebaseInformation != null) {

                    for (DataSnapshot messageSnapshot : dataSnapshot.getChildren()) {
                        epochTime = (Long) messageSnapshot.child("epochTime").getValue();

                        easyScore = (Long) messageSnapshot.child("easyScore").getValue();
                        stressScore = (Long) messageSnapshot.child("stressScore").getValue();
                        flag = (Boolean) messageSnapshot.child("databaseFlag").getValue();
                        date = (String) messageSnapshot.child("date").getValue();
                        time = (String) messageSnapshot.child("time").getValue();
                    }


                    Log.e("EasyScore", String.valueOf(easyScore));
                    Log.e("Flag", Boolean.toString(flag));
                    Information newInfo = new Information(Build.MANUFACTURER, Build.MODEL, easyScore.intValue(),
                            stressScore.intValue(), true, date, time, epochTime);

                    Bundle bundle = new Bundle();
                    bundle.putParcelable(INFO_KEY, newInfo);
                    TestFragment testFragment = new TestFragment();
                    testFragment.setArguments(bundle);


                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container, testFragment)
                            .commit();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        viewPager = (ViewPager) view.findViewById(R.id.view_pager);

        dashboardAdapter = new DashboardAdapter(getActivity().getSupportFragmentManager());

        viewPager.setAdapter(dashboardAdapter);
        viewPager.setOffscreenPageLimit(3);
        tabLayout = (TabLayout) view.findViewById(R.id.tab);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }
        });
        return view;
    }
}
