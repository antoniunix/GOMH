package com.go_sharp.gomh;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.go_sharp.gomh.adapter.DtoSimpleReport;
import com.go_sharp.gomh.adapter.ReportItem;
import com.go_sharp.gomh.dto.DtoBundle;
import com.go_sharp.gomh.model.ModelInfoPerson;
import com.go_sharp.gomh.model.ModelReport;

import java.util.List;

public class ReportList extends AppCompatActivity {

    private DtoBundle dtoBundle;
    private ModelReport modelReport;
    private ReportAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_list);
        /*Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);*/
        dtoBundle = (DtoBundle) getIntent().getExtras().get(getString(R.string.app_bundle_name));
        modelReport = new ModelReport(dtoBundle);
        new ModelInfoPerson(this).loadInfo(getString(R.string.list_report), getString(R.string.bigadist));

        RecyclerView mRecyclerView = findViewById(R.id.report_list);
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this,
                mLayoutManager.getLayoutDirection());
        mRecyclerView.addItemDecoration(dividerItemDecoration);

        // specify an adapter (see also next example)
        List<DtoSimpleReport> reports = modelReport.getReports();
        mAdapter = new ReportAdapter(reports);
        mRecyclerView.setAdapter(mAdapter);

        FloatingActionButton fab = findViewById(R.id.add_new);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ReportList.this, MenuReport.class)
                        .putExtra(getString(R.string.app_bundle_name), dtoBundle));
                finish();
            }
        });
    }

    private class ReportAdapter extends RecyclerView.Adapter<ReportItem> {

        private List<DtoSimpleReport> reports;

        ReportAdapter(List<DtoSimpleReport> reports) {
            this.reports = reports;
        }

        @NonNull
        @Override
        public ReportItem onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            // create a new view
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_report, parent, false);
            return new ReportItem(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ReportItem holder, int position) {
            holder.title.setText(reports.get(position).getTitle());
            holder.createdAt.setText(reports.get(position).getCreatedAt());
            holder.sent.setVisibility(reports.get(position).getSent() ? View.VISIBLE : View.GONE);
        }

        @Override
        public int getItemCount() {
            return reports.size();
        }
    }
}
