package com.go_sharp.gomh.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.go_sharp.gomh.R;

public class ReportItem  extends RecyclerView.ViewHolder {

    private static final String TAG = "ReportItem";

    public TextView createdAt;
    public TextView title;
    //public TextView description;

    public ReportItem(View v) {
        super(v);

        title = v.findViewById(R.id.report_title);
        createdAt = v.findViewById(R.id.report_description);
        //description = v.findViewById(R.id.status);
    }
}
