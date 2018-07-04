package com.example.android.anditest;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by UTSAV JAIN on 6/6/2018.
 */

public class SystemAdapter extends ArrayAdapter {
    public SystemAdapter(@NonNull Context context,ArrayList<SystemInformation> systemList) {
        super(context, 0,systemList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if(listItem == null) {
            listItem = LayoutInflater.from(getContext()).inflate(
                    R.layout.report, parent, false);
        }

        SystemInformation systemInformation =(SystemInformation) getItem(position);
        TextView title = (TextView) listItem.findViewById(R.id.title);
        title.setText(systemInformation.getTitle());
        Log.e("Title",systemInformation.getTitle());
        TextView data = (TextView) listItem.findViewById(R.id.data);



        if(systemInformation.getTitle().equals("Battery Temp:"))
        data.setText(String.valueOf(systemInformation.getValue())+"°C");
        else if(systemInformation.getTitle().equals("CPU Cores:"))
            data.setText(String.valueOf(systemInformation.getValue()));
        else
            data.setText(String.valueOf(systemInformation.getValue())+"GHz");



        ImageView image  = (ImageView) listItem.findViewById(R.id.image);
        image.setImageResource(systemInformation.getResourceId());

        return listItem;

    }
}
