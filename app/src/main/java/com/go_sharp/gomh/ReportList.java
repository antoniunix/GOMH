package com.go_sharp.gomh;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.go_sharp.gomh.dao.DaoReport;
import com.go_sharp.gomh.dto.DtoBundle;
import com.go_sharp.gomh.model.ModelReport;

public class ReportList extends AppCompatActivity {

    private DtoBundle dtoBundle;
    private ModelReport modelReport;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_list);
        /*Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);*/
        dtoBundle = (DtoBundle) getIntent().getExtras().get(getString(R.string.app_bundle_name));
        modelReport = new ModelReport(dtoBundle);

        FloatingActionButton fab = findViewById(R.id.add_new);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ReportList.this, MenuReport.class)
                        .putExtra(getString(R.string.app_bundle_name), dtoBundle));
            }
        });
    }
}
