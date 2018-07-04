package com.example.android.anditest;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


/**
 * Created by UTSAV JAIN on 6/2/2018.
 */

public class TestFragment extends android.support.v4.app.Fragment {

    FirebaseDatabase firebaseDatabase;
    Information information;

    String date , time;
    View view;
    long epochTime;
    CalendarInfo calendarInfo;
    DashboardAdapter dashboardAdapter;
    public  TextView easyTest,stressTest,easyScoreView,stressScoreView;
    private final int EASY_SCORE=0,STRESS_SCORE=1;
    private int easy_score = 0,stress_score = 0,pEasyScore = 0,pStressScore = 0;
    private int final_score = 0;
    private boolean databaseSet = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {


        view = inflater.inflate(R.layout.fragment_test,container,false);
        Log.e("CreateView","Created");
        easyScoreView = (TextView) view.findViewById(R.id.easy_score_tv);
        stressScoreView = (TextView) view.findViewById(R.id.stress_score_tv);
        easyTest = (TextView) view.findViewById(R.id.easy);
        stressTest = (TextView) view.findViewById(R.id.stress);


        if(getArguments()!=null){
            information = getArguments().getParcelable(DashboardFragment.INFO_KEY);

        }



        if(information!=null) {
            pEasyScore  = information.getEasyScore();
            pStressScore = information.getStressScore();
            easyScoreView.setText(Integer.toString(pEasyScore));
            stressScoreView.setText(Integer.toString(pStressScore));
            databaseSet = information.getDatabaseFlag();
        }
       // if(information!=null)
            //Toast.makeText(getActivity(),"Easy scoer:"+Integer.toString(information.getEasyScore()),Toast.LENGTH_SHORT).show();




        easyTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                easy_score = pEasyScore;
                stress_score = pStressScore;
                Log.e("PreviousEasy Score",Integer.toString(pEasyScore));
                Intent easyIntent = new Intent(getActivity(),EasyTest.class);
                startActivityForResult(easyIntent,EASY_SCORE);

            }
        });

        stressTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                easy_score = pEasyScore;
                stress_score = pStressScore;
                Intent stressIntent = new Intent(getActivity(),StressTest.class);
                startActivityForResult(stressIntent,STRESS_SCORE);

            }
        });

        return view;

    }




    @Override
    public  void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);



        switch(requestCode){
            case EASY_SCORE:
                if(resultCode == Activity.RESULT_OK){
                    easy_score = Integer.parseInt(data.getStringExtra("easy-score"));
                    pEasyScore = easy_score;

                }break;
            case STRESS_SCORE:
                if(resultCode == Activity.RESULT_OK){
                    stress_score = Integer.parseInt(data.getStringExtra("stress-score"));
                    pStressScore = stress_score;
                }break;


        }

        setScore(easy_score,stress_score);

        if(!databaseSet) {

            setFirebase();
        }
        else{
            setFirebase();
        }


    }

    private void setScore(int easy_score,int stress_score){
        Log.e(Integer.toString(easy_score),Integer.toString(stress_score));

        final_score = (easy_score + stress_score) /2;
        easyScoreView.setText(Integer.toString(easy_score));
        stressScoreView.setText(Integer.toString(stress_score));
    }

    public void setFirebase(){

        calendarInfo = new CalendarInfo();
        date = calendarInfo.getDate();
        time = String.valueOf(calendarInfo.getHour()) + ":" + String.valueOf(calendarInfo.getMinute());
        epochTime = calendarInfo.getEpochTime();
        Log.e("Epoch",String.valueOf(epochTime));

        final Information firebaseInformation = new Information(Build.MANUFACTURER,Build.MODEL,
                easy_score,stress_score,true,date,time,epochTime);


        try {
            firebaseDatabase = FirebaseDatabase.getInstance();
            final DatabaseReference databaseReference = firebaseDatabase.getReference("info").child(Build.MANUFACTURER)
                    .child(Build.MODEL).child(Build.SERIAL);
            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    databaseReference.push().setValue(firebaseInformation);

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
        catch (Exception e){
            e.printStackTrace();
        }

        databaseSet = true;


    }



    @Override
    public void onStop() {
        super.onStop();
        Log.e("TestFragment","Stopped");

    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e("TestFragment","Resumed");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.e("TestFragment","Created");
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.e("TestFrag","onSavedInstance");


    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.e("TestFrag","Attached");

    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.e("TestFrag","Detached");
    }

}


