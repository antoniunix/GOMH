package com.go_sharp.gomh.model;

import android.content.Context;

import com.go_sharp.gomh.adapter.DtoSimpleReport;
import com.go_sharp.gomh.contextApp.ContextApp;
import com.go_sharp.gomh.dao.DaoReport;
import com.go_sharp.gomh.dto.DtoBundle;

import java.util.List;

public class ModelReport {

    private DtoBundle dtoBundle;
    private Context context;

    public ModelReport(DtoBundle dtoBundle) {
        this.dtoBundle = dtoBundle;
        context = ContextApp.context;
    }

    public List<DtoSimpleReport> getReports(){
        return new DaoReport().getCompletedReports();
    }
}
