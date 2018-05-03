package com.go_sharp.gomh.model;

import android.content.Context;

import com.go_sharp.gomh.contextApp.ContextApp;
import com.go_sharp.gomh.dto.DtoBundle;

public class ModelReport {

    private DtoBundle dtoBundle;
    private Context context;

    public ModelReport(DtoBundle dtoBundle) {
        this.dtoBundle = dtoBundle;
        context = ContextApp.context;
    }
}
