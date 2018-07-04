package com.example.android.anditest;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by UTSAV JAIN on 6/7/2018.
 */

public class HistoryFragment extends Fragment {

    FirebaseDatabase firebaseDatabase;
    Information firebaseInformation;
    Long easyScore, stressScore;
    String time, date;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.history, container, false);

        final ArrayList<History> history = new ArrayList<>();

        firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("info").child(Build.MANUFACTURER)
                .child(Build.MODEL).child(Build.SERIAL);
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                firebaseInformation = dataSnapshot.getValue(Information.class);
                if (firebaseInformation != null) {
                    for (DataSnapshot messageSnapshot : dataSnapshot.getChildren()) {

                        easyScore = (Long) messageSnapshot.child("easyScore").getValue();
                        stressScore = (Long) messageSnapshot.child("stressScore").getValue();
                        time = (String) messageSnapshot.child("time").getValue();
                        date = (String) messageSnapshot.child("date").getValue();


                        history.add(new History(easyScore.intValue(), stressScore.intValue(), time, date));

                    }

                    HistoryAdapter historyAdapter = new HistoryAdapter(getActivity(), history);
                    ListView historyListView = (ListView) view.findViewById(R.id.history);
                    historyListView.setAdapter(historyAdapter);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return view;
    }


}
