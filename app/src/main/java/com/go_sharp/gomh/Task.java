package com.go_sharp.gomh;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.go_sharp.gomh.adapter.RVAdapter;
import com.go_sharp.gomh.dialog.DialogTask;
import com.go_sharp.gomh.dto.DtoTask;
import com.go_sharp.gomh.listener.OnItemClickListenerRV;
import com.go_sharp.gomh.model.ModelTask;
import com.go_sharp.gomh.util.ChangeFontStyle;

public class Task extends AppCompatActivity implements OnItemClickListenerRV {

    private TextView txtTBTitle;
    private RecyclerView rcvMsg;
    private ModelTask model;
    private RVAdapter adapter;

    private void init() {
        txtTBTitle = findViewById(R.id.txtTBTitle);
        rcvMsg = findViewById(R.id.rcvMsg);
        model = new ModelTask();
        adapter = model.getAdapter(this);
        LinearLayoutManager lmy = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rcvMsg.setLayoutManager(lmy);
        txtTBTitle.setText(R.string.label_task);
        ChangeFontStyle.changeFont(txtTBTitle);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);
        getSupportActionBar().hide();
        init();
    }

    @Override
    protected void onResume() {
        super.onResume();
        rcvMsg.setAdapter(adapter);
    }

    @Override
    public void onItemClickListener(View v, int position) {
        DialogTask daiDialogTask = new DialogTask();
        long id =adapter.getItemId(position);

        Bundle bundle=new Bundle();
        bundle.putLong(getString(R.string.app_bundle_name),id);
        daiDialogTask.setArguments(bundle);
        daiDialogTask.show(getSupportFragmentManager(), getString(R.string.label_task));

    }
}
