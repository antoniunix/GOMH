package com.go_sharp.gomh;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.go_sharp.gomh.listener.OnItemClickListenerRV;
import com.go_sharp.gomh.model.ModelTask;

public class Task extends AppCompatActivity implements OnItemClickListenerRV {

    private TextView txtTBTitle;
    private ModelTask model;

    private void init() {
        model = new ModelTask();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);
    }

    @Override
    public void onItemClickListener(View v, int position) {

    }
}
