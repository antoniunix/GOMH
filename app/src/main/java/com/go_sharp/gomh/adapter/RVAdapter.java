package com.go_sharp.gomh.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;

import com.go_sharp.gomh.R;
import com.go_sharp.gomh.dto.DtoMessage;
import com.go_sharp.gomh.listener.OnItemClickListenerRV;
import com.go_sharp.gomh.util.ChangeFontStyle;

import java.util.List;

/**
 * Created by gnu on 4/05/18.
 */

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.ViewHolder> {

    private List<DtoMessage> lstMsg;
    private OnItemClickListenerRV onItemClickListenerRV;

    public RVAdapter(List<DtoMessage> lstMsg, OnItemClickListenerRV onItemClickListenerRV) {
        this.lstMsg = lstMsg;
        this.onItemClickListenerRV = onItemClickListenerRV;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_message, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        DtoMessage dtoMessage = lstMsg.get(position);
        holder.txtTitle.setText(dtoMessage.getTitle());

        holder.txtTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickListenerRV.onItemClickListener(view, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return lstMsg.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtTitle,txtDescription;

        ViewHolder(View itemView) {
            super(itemView);
            txtTitle = itemView.findViewById(R.id.txtTitle);
            txtDescription =  itemView.findViewById(R.id.txtDescription);
            ChangeFontStyle.changeFont(txtTitle);
        }

    }
}
