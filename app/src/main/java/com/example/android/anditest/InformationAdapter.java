package com.example.android.anditest;

import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by UTSAV JAIN on 6/3/2018.
 */

public class InformationAdapter extends ArrayAdapter {
    public InformationAdapter(@NonNull Context context, ArrayList<Information> information) {
        super(context, 0,information);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View listItem = convertView;
        if(listItem == null) {
            listItem = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }

        Information information = (Information) getItem(position);
        TextView title = (TextView) listItem.findViewById(R.id.title);
        title.setText(information.getTitle());

        TextView info = (TextView) listItem.findViewById(R.id.info);
        info.setText(information.getInfo());

        return listItem;
    }
}
