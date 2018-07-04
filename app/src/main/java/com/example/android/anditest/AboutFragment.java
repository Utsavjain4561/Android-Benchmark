package com.example.android.anditest;

import android.app.Fragment;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by UTSAV JAIN on 6/2/2018.
 */

public class AboutFragment extends android.support.v4.app.Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.about,container,false);



        ArrayList<Information> information = new ArrayList<>();
        information.add(new Information("SERIAL",Build.SERIAL));
        information.add(new Information("MODEL",Build.MODEL));
        information.add(new Information("ID",Build.ID));
        information.add(new Information("MANUFACTURER",Build.MANUFACTURER));
        information.add(new Information("BRAND",Build.MANUFACTURER));
        information.add(new Information("TYPE",Build.TYPE));
        information.add(new Information("USER",Build.USER));
        information.add(new Information("BASE",Integer.toString(Build.VERSION_CODES.BASE)));
        information.add(new Information("INCREMENTAL",Build.VERSION.INCREMENTAL));
        information.add(new Information("SDK",Build.VERSION.SDK));
        information.add(new Information("BOARD",Build.BOARD));
        information.add(new Information("HOST",Build.HOST));
        information.add(new Information("FINGERPRINT",Build.FINGERPRINT));
        information.add(new Information("VERSION-CODES",Build.VERSION.RELEASE));

        InformationAdapter informationAdapter = new InformationAdapter(getActivity(),information);
        ListView softwareView  = (ListView) view.findViewById(R.id.software);

        softwareView.setAdapter(informationAdapter);

        return view;

    }
}
