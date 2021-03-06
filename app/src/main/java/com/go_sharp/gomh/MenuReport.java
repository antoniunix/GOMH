package com.go_sharp.gomh;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.go_sharp.gomh.dto.DtoBundle;
import com.go_sharp.gomh.model.ModelToolBar;
import com.go_sharp.gomh.model.ModelMenuReport;

import net.gshp.apiencuesta.Encuesta;

public class MenuReport extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "MenuReport";

    private ModelMenuReport modelMenuReport;
    private DtoBundle dtoBundle;
    private LinearLayout opt1, opt2, opt3;

    private void init() {
        dtoBundle = (DtoBundle) getIntent().getExtras().get(getString(R.string.app_bundle_name));
        modelMenuReport = new ModelMenuReport(dtoBundle);
        opt1 = findViewById(R.id.opt1);
        opt2 = findViewById(R.id.opt2);
        opt3 = findViewById(R.id.opt3);
        new ModelToolBar(this).loadInfo(getString(R.string.menu_report), getString(R.string.bigadist));

        opt1.setOnClickListener(this);
        opt2.setOnClickListener(this);
        opt3.setOnClickListener(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_report);
        init();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (dtoBundle.getIdReportLocal() == 0) {
            modelMenuReport.createNewReport();
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == opt1.getId()) {
            startActivity(new Intent(this, Encuesta.class)
                    .putExtra("idReporte", dtoBundle.getIdReportLocal())
                    .putExtra("idEncuesta", 1L));
        } else if (v.getId() == opt2.getId()) {
            startActivity(new Intent(this, ReportPublicity.class)
                    .putExtra(getString(R.string.app_bundle_name), dtoBundle));
        } else if (v.getId() == opt3.getId()) {
            Log.w(TAG, "finish report");
            modelMenuReport.closeReport();
            finish();
        }
    }
}
