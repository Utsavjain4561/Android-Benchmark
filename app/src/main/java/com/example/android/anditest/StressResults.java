package com.example.android.anditest;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by UTSAV JAIN on 6/9/2018.
 */

public class StressResults extends Fragment {

    int stressScore;
    String modelName;
    BarData data;
    View view;

    ArrayList<Information> stressResults =  new ArrayList<Information>();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.stress_results,container,false);

        getFirebaseData();
        return view;
    }
    public void getFirebaseData() {



        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("info");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                for (DataSnapshot manufacturer : dataSnapshot.getChildren()) {

                    for (DataSnapshot model : manufacturer.getChildren()) {
                        for (DataSnapshot serial : model.getChildren()) {
                            for (DataSnapshot info : serial.getChildren()) {
                                stressScore = ((Long) info.child("stressScore").getValue()).intValue();


                                modelName = (String) info.child("model").getValue();


                            }
                        }
                        if(stressScore!=0)
                        stressResults.add(new Information(modelName,stressScore));



                    }

                }

                Collections.sort(stressResults);
                StressAdapter stressAdapter = new StressAdapter(getActivity(),stressResults);
                ListView sList = (ListView) view.findViewById(R.id.stressList);
                sList.setAdapter(stressAdapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
class StressAdapter extends ArrayAdapter {

    public StressAdapter(@NonNull Context context, ArrayList<Information> info) {
        super(context, 0, info);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if(listItem == null) {
            listItem = LayoutInflater.from(getContext()).inflate(
                    R.layout.leaderboard_item, parent, false);
        }
        Information info = (Information) getItem(position);
        TextView name = (TextView) listItem.findViewById(R.id.name);
        switch (position){
            case 0: name.setTextSize(55f);
                    name.setTextColor(Color.BLACK);
                    break;
            case 1: name.setTextSize(40f);
                name.setTextColor(Color.BLACK);
                break;
            case 2: name.setTextSize(25f);
                name.setTextColor(Color.BLACK);
                break;

        }
        name.setText(Integer.toString(position+1)+"."+info.getModel());

        TextView score = (TextView) listItem.findViewById(R.id.score);
        score.setText(Integer.toString(info.getScore()));

        return listItem;
    }
}


