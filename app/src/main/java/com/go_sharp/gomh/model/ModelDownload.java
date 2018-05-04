package com.go_sharp.gomh.model;

import android.util.Log;
import android.view.View;

import com.go_sharp.gomh.adapter.AdapterDownload;
import com.go_sharp.gomh.dao.DaoDownloadableFiles;
import com.go_sharp.gomh.dto.DtoDownloadableFiles;

import java.util.List;

/**
 * Created by leo on 30/08/17.
 */

public class ModelDownload {
    private DaoDownloadableFiles daoDownloadableFiles;
    private List<DtoDownloadableFiles> lst;
    private AdapterDownload adapterDownload;
    private View.OnClickListener onClick;

    public ModelDownload(View.OnClickListener onClick) {
        this.onClick = onClick;
        daoDownloadableFiles = new DaoDownloadableFiles();
    }

    public ModelDownload() {
        daoDownloadableFiles = new DaoDownloadableFiles();
    }

    public AdapterDownload getAdapter() {
        lst = daoDownloadableFiles.select();
        adapterDownload = new AdapterDownload(lst, onClick, this);
        Log.e("list","list "+lst.toString());
        return adapterDownload;
    }
}
