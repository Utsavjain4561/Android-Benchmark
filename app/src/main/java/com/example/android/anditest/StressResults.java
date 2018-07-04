package com.example.android.anditest;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
import java.util.List;

/**
 * Created by UTSAV JAIN on 6/9/2018.
 */

public class StressResults extends Fragment {
    HorizontalBarChart horizontalBarChart;
    int easyScore , index=0;
    String modelName;
    BarData data;
    View view;
    List<BarEntry> valueSet1 = new ArrayList<BarEntry>();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.stress_results,container,false);
        horizontalBarChart = (HorizontalBarChart) view.findViewById(R.id.bar_chart);
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
                                easyScore = ((Long) info.child("stressScore").getValue()).intValue();


                                modelName = (String) info.child("model").getValue();


                            }
                        }


                        BarEntry barEntry = new BarEntry(index++,easyScore);
                        valueSet1.add(barEntry);
                    }

                }



                BarDataSet dataSet = new BarDataSet(valueSet1,"Stress Results");
                ArrayList<IBarDataSet> dataSets =  new ArrayList<IBarDataSet>();
                dataSets.add(dataSet);
                dataSet.setDrawValues(true);
                dataSet.setValueTextSize(30f);


                data = new BarData(dataSets);
                data.setBarWidth(0.5f);
                YAxis left = horizontalBarChart.getAxisLeft();
                left.setDrawLabels(false);
                int size = valueSet1.size();
                String[] values = new String[size];
                for(int i=0;i<size;i++){
                    values[i]=String.valueOf(i);
                }
                XAxis xAxis = horizontalBarChart.getXAxis();
                xAxis.setValueFormatter(new MyXAxisValueFormatter(values));

                horizontalBarChart.setData(data);

                Description description =  new Description();
                description.setText("Stress Results");
                horizontalBarChart.setDescription(description);
                horizontalBarChart.setVisibleXRangeMaximum(5);
                horizontalBarChart.moveViewToX(10);
                horizontalBarChart.getLegend().setEnabled(false);
                horizontalBarChart.setDrawValueAboveBar(false);
                horizontalBarChart.setDrawGridBackground(false);
                horizontalBarChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM_INSIDE);
                horizontalBarChart.animateY(1000);
                horizontalBarChart.invalidate();

            }



            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

}
