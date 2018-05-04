package com.go_sharp.gomh.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.go_sharp.gomh.util.ChangeFontStyle;

import java.util.List;

/**
 * Created by leo on 4/05/18.
 */

public class MySpinnerAdapter extends ArrayAdapter<String> {

    public MySpinnerAdapter(Context context, int resource, List<String> items){
        super(context,resource,items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView view = (TextView) super.getView(position, convertView, parent);
        ChangeFontStyle.changeFont(view);
        return view;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        TextView view = (TextView) super.getDropDownView(position, convertView, parent);
        ChangeFontStyle.changeFont(view);
        return view;
    }

}
