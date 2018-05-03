package com.go_sharp.gomh;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.go_sharp.gomh.dao.DaoReport;
import com.go_sharp.gomh.dto.DtoBundle;
import com.go_sharp.gomh.model.ModelAHBottomNavigationMenuReport;
import com.go_sharp.gomh.model.ModelMenuReport;

public class MenuReport extends AppCompatActivity implements AHBottomNavigation.OnTabSelectedListener {

    private ModelMenuReport modelMenuReport;
    private ModelAHBottomNavigationMenuReport modelAHBottomNavigationMenuReport;
    private DtoBundle dtoBundle;

    private void init() {
        dtoBundle = (DtoBundle) getIntent().getExtras().get(getString(R.string.app_bundle_name));
        modelMenuReport = new ModelMenuReport(dtoBundle);
        modelAHBottomNavigationMenuReport = new ModelAHBottomNavigationMenuReport(this, modelMenuReport,
                this, dtoBundle);
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
        modelAHBottomNavigationMenuReport.onResume();
        long idReport = new DaoReport().getIdReportIncomplete();
        if (idReport > 0) {
            dtoBundle.setIdReportLocal(idReport);
        } else {
            modelMenuReport.createNewReport(this);
        }
    }

    @Override
    public boolean onTabSelected(int position, boolean wasSelected) {
            switch (position) {
                case 0://poll supervisor
                    break;
                case 1:// census
                    break;
                case 2://Photos
                    break;
                case 3://check out
                    break;
            }
        return true;
    }
}
