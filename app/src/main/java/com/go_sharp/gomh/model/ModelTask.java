package com.go_sharp.gomh.model;

import com.go_sharp.gomh.adapter.RVAdapter;
import com.go_sharp.gomh.dao.DaoMessage;
import com.go_sharp.gomh.dto.DtoMessage;
import com.go_sharp.gomh.listener.OnItemClickListenerRV;

import java.util.List;

/**
 * Created by gnu on 4/05/18.
 */

public class ModelTask {

    private List<DtoMessage> lstMessage;
    private DaoMessage daoMessage;
    private RVAdapter adapter;

    public ModelTask() {
        daoMessage = new DaoMessage();
    }

    public RVAdapter getAdapter(OnItemClickListenerRV onItemClickListenerRV) {
        lstMessage = daoMessage.select();
        adapter = new RVAdapter(lstMessage, onItemClickListenerRV);
        return adapter;
    }
}
