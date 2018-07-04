package com.example.android.anditest;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by UTSAV JAIN on 6/7/2018.
 */

public class HistoryAdapter extends ArrayAdapter {
    public HistoryAdapter(@NonNull Context context, ArrayList<History> history) {
        super(context, 0,history);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {


        View listItem = convertView;
        if(listItem == null) {
            listItem = LayoutInflater.from(getContext()).inflate(
                    R.layout.history_item, parent, false);
        }

        History history = (History) getItem(position);

        TextView scoreView = (TextView) listItem.findViewById(R.id.scoreView);
        scoreView.setText(String.valueOf(history.getEasyScore())+":"+String.valueOf(history.getStressScore()));

        TextView timeView = (TextView) listItem.findViewById(R.id.timeView);
        timeView.setText(history.getTestTime());

        TextView dateView = (TextView) listItem.findViewById(R.id.dateView);
        dateView.setText(history.getDate());

        return listItem;

    }
}
