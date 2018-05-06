package com.go_sharp.gomh;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.go_sharp.gomh.adapter.RVAdapter;
import com.go_sharp.gomh.dialog.DialogTask;
import com.go_sharp.gomh.dto.DtoTask;
import com.go_sharp.gomh.listener.OnItemClickListenerRV;
import com.go_sharp.gomh.model.ModelTask;
import com.go_sharp.gomh.model.ModelToolBar;
import com.go_sharp.gomh.util.BottomNavigationViewHelper;
import com.go_sharp.gomh.util.ChangeFontStyle;

public class Task extends AppCompatActivity implements OnItemClickListenerRV,
        BottomNavigationView.OnNavigationItemSelectedListener {

    private RecyclerView rcvMsg;
    private ModelTask model;
    private RVAdapter adapter;
    private BottomNavigationView bottomNavigationView;

    private void init() {
        rcvMsg = findViewById(R.id.rcvMsg);
        model = new ModelTask();
        adapter = model.getAdapter(this);
        LinearLayoutManager lmy = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rcvMsg.setLayoutManager(lmy);
        new ModelToolBar(this).loadInfo(getString(R.string.label_task), "");
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);

        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.action_task);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);
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
        long id = adapter.getItemId(position);

        Bundle bundle = new Bundle();
        bundle.putLong(getString(R.string.app_bundle_name), id);
        daiDialogTask.setArguments(bundle);
        daiDialogTask.show(getSupportFragmentManager(), getString(R.string.label_task));

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_home:
                finish();
                break;
            case R.id.action_training:
                startActivity(new Intent(this, Training.class));
                finish();
                break;
            case R.id.action_exit:
                finish();
                break;
        }
        return true;
    }
}
