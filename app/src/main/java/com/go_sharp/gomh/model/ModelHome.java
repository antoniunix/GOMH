package com.go_sharp.gomh.model;

import android.content.Context;

import com.go_sharp.gomh.R;
import com.go_sharp.gomh.contextApp.ContextApp;
import com.go_sharp.gomh.dao.DaoReport;
import com.go_sharp.gomh.util.SharePreferenceCustom;

/**
 * Created by gnu on 15/02/18.
 */

public class ModelHome {

    private Context context;

    public ModelHome() {
        context = ContextApp.context;
    }

    public int statusReport() {
        if (!SharePreferenceCustom.contains(R.string.app_share_preference_name, R.string.first_report)) {
            return context.getResources().getInteger(R.integer.first_report);
        }
        if (getReportId() > 0) {
            return context.getResources().getInteger(R.integer.open_report);
        }
        return context.getResources().getInteger(R.integer.complete_report);
    }

    public long getReportId() {
        return new DaoReport().getIdReportIncomplete();
    }
}
