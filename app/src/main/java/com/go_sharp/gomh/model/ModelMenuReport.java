package com.go_sharp.gomh.model;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;

import com.go_sharp.gomh.R;
import com.go_sharp.gomh.contextApp.ContextApp;
import com.go_sharp.gomh.dao.DaoReport;
import com.go_sharp.gomh.dto.DtoBundle;
import com.go_sharp.gomh.dto.DtoReport;
import com.go_sharp.gomh.geolocation.ServiceCheck;
import com.go_sharp.gomh.util.Config;
import com.go_sharp.gomh.util.SharePreferenceCustom;
import com.gshp.api.utils.Crypto;

/**
 * Created by gnu on 15/02/18.
 */

public class ModelMenuReport {

    private DtoBundle dtoBundle;
    private Context context;

    public ModelMenuReport(DtoBundle dtoBundle) {
        this.dtoBundle = dtoBundle;
        context = ContextApp.context;
    }

    public void createNewReport() {
        String version = "";
        try {
            version = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        DtoReport dtoReport = new DtoReport();
        dtoReport.setIdPdv(1).setIdSchedule(1).setVersion(version).setDate(System.currentTimeMillis()).
                setTz(Config.getTimeZone()).setImei(Config.getIMEI()).
                setHash(Crypto.MD5(System.currentTimeMillis() + " " + Math.random())).
                setSend(0).setTypeReport(1).setActive(1).setTypePoll(1);
        dtoBundle.setIdReportLocal(new DaoReport().insert(dtoReport));
        context.startService(new Intent(context, ServiceCheck.class).
                putExtra(context.getString(R.string.app_bundle_name), dtoBundle).
                putExtra("typeCheck", context.getResources().getInteger(R.integer.type_check_in)));

        SharePreferenceCustom.write(R.string.app_share_preference_name, R.string.first_report, "false");
    }

    public void closeReport() {
        if (!new DaoReport().deleteEmptyReport(dtoBundle.getIdReportLocal()))
            context.startService(new Intent(context, ServiceCheck.class).
                    putExtra(context.getString(R.string.app_bundle_name), dtoBundle).
                    putExtra("typeCheck", context.getResources().getInteger(R.integer.type_check_out)));
    }
}
