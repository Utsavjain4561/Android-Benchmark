package com.example.android.anditest;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class EasyTest extends AppCompatActivity {

    TextView easyScore;
    Button startbt;
    SystemInformation systemInformation;
    Long startTime, endTime;
    ProgressBar progressBar;
    RelativeLayout relativeLayout;
    LinearLayout linearLayout;
    float temp;
    int score, progress, progressStatus = 0;
    private String MESSAGE = "Hello World";
    public  static   int RESULT = Activity.RESULT_CANCELED;
    private Handler handler = new Handler();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_easy_test);

        // initializing views in layout
        easyScore = (TextView) findViewById(R.id.easyScore);
        startbt = (Button) findViewById(R.id.start);

        relativeLayout = (RelativeLayout) findViewById(R.id.root);
        linearLayout = (LinearLayout) findViewById(R.id.report);


        startbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RESULT = Activity.RESULT_OK;
                progress = 0;
                progressBar = (ProgressBar) findViewById(R.id.load);
                progressBar.setMax(20);
                progressBar.setVisibility(View.VISIBLE);
                relativeLayout.setBackgroundColor(getResources().getColor(R.color.oreo));

                final ComputeSHA myHash = new ComputeSHA(MESSAGE, "SHA-256");

                startbt.setVisibility(View.GONE);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        startTime = System.nanoTime();
                        while (progressStatus < 20) {

                            myHash.computeSHAHash();
                            progressStatus++;

                            handler.post(new Runnable() {
                                public void run() {
                                    progressBar.setProgress(progressStatus);
                                }
                            });
                        }
                        handler.post(new Runnable() {
                            public void run() {
                                progressBar.setVisibility(View.GONE);
                                relativeLayout.setBackgroundColor(getResources().getColor(R.color.white));
                                endTime = System.nanoTime() - startTime;
                                score = (int) (endTime / (int) Math.pow(10, 7));
                                easyScore.setText(Integer.toString(score));
                                Intent intent = getApplicationContext().registerReceiver(null, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
                                temp   = ((float) intent.getIntExtra(BatteryManager.EXTRA_TEMPERATURE,0)) / 10;
                                linearLayout.setVisibility(View.VISIBLE);
                                ArrayList<SystemInformation> systemList = new ArrayList<>();
                                systemList.add(new SystemInformation("Battery Temp:",temp,R.drawable.ic_battery));



                                systemInformation = new SystemInformation();
                                int noc = systemInformation.getNumCores();
                                systemList.add(new SystemInformation("CPU Cores:",(float)noc,R.drawable.ic_cpu));
                                Log.e("Cores",String.valueOf(noc));
                                for(int i=0;i<noc;i++){
                                    Log.e("CPU"+i,String.valueOf(systemInformation.getCurrentFrequency(i)/Math.pow(10,6))+
                                            "GHz");
                                    systemList.add(new SystemInformation("CPU "+i+":",
                                            (float)(systemInformation.getCurrentFrequency(i)/Math.pow(10,6)),
                                            R.drawable.ic_cpu));
                                }

                                SystemAdapter systemAdapter = new SystemAdapter(getApplicationContext(),systemList);
                                ListView report = (ListView) findViewById(R.id.cpu);
                                report.setAdapter(systemAdapter);




                            }
                        });
                    }


                }).start();



            }
        });
    }
    @Override
    public void onBackPressed() {

        Intent resultIntent = new Intent();
        resultIntent.putExtra("easy-score",Integer.toString(score));
        setResult(EasyTest.RESULT,resultIntent);
        finish();
        super.onBackPressed();
    }


}
