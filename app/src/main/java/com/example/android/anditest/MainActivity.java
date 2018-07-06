package com.example.android.anditest;


import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import android.support.design.widget.NavigationView;
import android.os.Bundle;
import android.support.v4.content.FileProvider;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    Toolbar toolbar;
    DrawerLayout drawer;

    NavigationView navigationView;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View header = (View) navigationView.getHeaderView(0);

        String version = Build.VERSION.RELEASE;
        switch (version.charAt(0)){
            case '4':  if(version.charAt(1)=='1')
                        header.setBackground(getResources().getDrawable(R.drawable.kitkat));
                        else
                        header.setBackground(getResources().getDrawable(R.drawable.kitkat));
                        break;
            case '5':  header.setBackground(getResources().getDrawable(R.drawable.lolipop));
                    break;
            case '6':  header.setBackground(getResources().getDrawable(R.drawable.marshmello));
                        break;
            case '7':  header.setBackground(getResources().getDrawable(R.drawable.noughat));
                        break;
            case '8':  header.setBackground(getResources().getDrawable(R.drawable.oreo));
                        break;
            default: header.setBackground(getResources().getDrawable(R.drawable.ic_launcher_background));
        }


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_draw_open, R.string.navigation_draw_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.frame, new DashboardFragment()).commit();
            navigationView.setCheckedItem(R.id.dashboard);

        }


        Log.e("ActivityCreated", "Created");


    }

    @Override
    public void onBackPressed() {

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
            finish();
            System.exit(0);
        }

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.dashboard:

                getSupportFragmentManager().beginTransaction().replace(R.id.frame,
                        new DashboardFragment()).commit();
                break;
            case R.id.results:
                Log.e("FRAGMENT", String.valueOf(getSupportFragmentManager().findFragmentById(R.id.frame)));


                getSupportFragmentManager().beginTransaction().replace(R.id.frame, new LeaderboardFragment()).addToBackStack(null)
                        .commit();
                break;
            case R.id.share:
                Toast.makeText(MainActivity.this, "Share", Toast.LENGTH_SHORT).show();
                shareApplication();
                break;
            case R.id.feedback:
                Toast.makeText(MainActivity.this, "Feedback", Toast.LENGTH_SHORT).show();
                Intent Email = new Intent(Intent.ACTION_SEND);
                Email.setType("text/email");
                Email.putExtra(Intent.EXTRA_EMAIL, new String[] { "utsavslife@gmail.com" });
                Email.putExtra(Intent.EXTRA_SUBJECT, "Feedback");

                startActivity(Intent.createChooser(Email, "Send Feedback:"));
                break;


        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    private void shareApplication() {
        ApplicationInfo app = getApplicationContext().getApplicationInfo();
        String filePath = app.sourceDir;

        Intent intent = new Intent(Intent.ACTION_SEND);

        // MIME of .apk is "application/vnd.android.package-archive".
        // but Bluetooth does not accept this. Let's use "*/*" instead.
        intent.setType("*/*");

        // Append file and send Intent
        File originalApk = new File(filePath);

        try {
            //Make new directory in new location
            File tempFile = new File(getExternalCacheDir() + "/ExtractedApk");
            //If directory doesn't exists create new
            if (!tempFile.isDirectory())
                if (!tempFile.mkdirs())
                    return;
            //Get application's name and convert to lowercase
            tempFile = new File(tempFile.getPath() + "/" + getString(app.labelRes).replace(" ","").toLowerCase() + ".apk");
            //If file doesn't exists create new
            if (!tempFile.exists()) {
                if (!tempFile.createNewFile()) {
                    return;
                }
            }
            //Copy file to new location
            InputStream in = new FileInputStream(originalApk);
            OutputStream out = new FileOutputStream(tempFile);

            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
            in.close();
            out.close();
            System.out.println("File copied.");
            //Open share dialog
           Uri uri =  FileProvider.getUriForFile(MainActivity.this, BuildConfig.APPLICATION_ID + ".provider",tempFile);
            intent.putExtra(Intent.EXTRA_STREAM, uri);
            startActivity(Intent.createChooser(intent, "Share app via"));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


