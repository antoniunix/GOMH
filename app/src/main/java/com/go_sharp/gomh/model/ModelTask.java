package com.go_sharp.gomh.model;

import android.util.Log;

import com.go_sharp.gomh.adapter.RVAdapter;
import com.go_sharp.gomh.dao.DaoMessage;
import com.go_sharp.gomh.dao.DaoTask;
import com.go_sharp.gomh.dto.DtoTask;
import com.go_sharp.gomh.listener.OnItemClickListenerRV;

import java.util.List;

/**
 * Created by gnu on 4/05/18.
 */

public class ModelTask {

    private List<DtoTask> lstTask;
    private DaoTask daoTask;
    private RVAdapter adapter;

    public ModelTask() {
        daoTask = new DaoTask();
    }

    public RVAdapter getAdapter(OnItemClickListenerRV onItemClickListenerRV) {
        lstTask = daoTask.select();
        Log.e("task","taskm "+lstTask.toString());
        adapter = new RVAdapter(lstTask, onItemClickListenerRV);
        return adapter;
    }
}
