package com.go_sharp.gomh.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.go_sharp.gomh.util.ChangeFontStyle;

import java.util.List;

/**
 * Created by leo on 17/02/18.
 */

public class AdapterSpinnerCp extends ArrayAdapter<String> {

    public AdapterSpinnerCp(Context context, int resource, List<String> items) {
        super(context, resource, items);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        TextView view = (TextView) super.getView(position, convertView, parent);
        ChangeFontStyle.changeFont(view);
        return view;
    }

    @Override
    public View getDropDownView(int position, View convertView, @NonNull ViewGroup parent) {
        TextView view = (TextView) super.getDropDownView(position, convertView, parent);
        ChangeFontStyle.changeFont(view);
        return view;
    }
}
