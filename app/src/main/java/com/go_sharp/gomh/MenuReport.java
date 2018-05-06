package com.go_sharp.gomh;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.go_sharp.gomh.dto.DtoBundle;
import com.go_sharp.gomh.model.ModelMenuReport;

public class MenuReport extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "MenuReport";

    private ModelMenuReport modelMenuReport;
    private DtoBundle dtoBundle;
    private LinearLayout opt1, opt2, opt3, opt4;

    private void init() {
        dtoBundle = (DtoBundle) getIntent().getExtras().get(getString(R.string.app_bundle_name));
        modelMenuReport = new ModelMenuReport(dtoBundle);
        opt1 = findViewById(R.id.opt1);
        opt2 = findViewById(R.id.opt2);
        opt3 = findViewById(R.id.opt3);
        opt4 = findViewById(R.id.opt4);

        opt2.setOnClickListener(this);
        opt4.setOnClickListener(this);

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


        } else if (v.getId() == opt2.getId()) {
            startActivity(new Intent(this, ReportPublicity.class).putExtra(getString(R.string.app_bundle_name), dtoBundle));

        } else if (v.getId() == opt3.getId()) {

        } else if (v.getId() == opt4.getId()) {
            Log.w(TAG, "finish report");
            modelMenuReport.closeReport();
            finish();
        }
    }
}
