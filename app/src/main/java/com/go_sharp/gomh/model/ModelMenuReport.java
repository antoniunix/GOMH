package com.go_sharp.gomh.model;

import android.content.Context;

import com.go_sharp.gomh.contextApp.ContextApp;
import com.go_sharp.gomh.dto.DtoBundle;

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

}
