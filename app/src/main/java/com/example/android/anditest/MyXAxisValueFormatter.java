package com.example.android.anditest;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

/**
 * Created by UTSAV JAIN on 6/9/2018.
 */

public class MyXAxisValueFormatter implements IAxisValueFormatter{

    private String[] mValue;
    public MyXAxisValueFormatter(String[] values) {
        this.mValue = values;
    }

    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        return mValue[(int) value];
    }

}


