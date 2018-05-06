package com.go_sharp.gomh.dialog;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.WebView;
import android.widget.TextView;

import com.go_sharp.gomh.R;
import com.go_sharp.gomh.dao.DaoTask;
import com.go_sharp.gomh.dto.DtoTask;
import com.go_sharp.gomh.util.ChangeFontStyle;

/**
 * Created by gnu on 4/05/18.
 */

public class DialogTask extends DialogFragment {

    private View view;
    private TextView toolbar_title;
    private long idTask;
    private DtoTask dtoTask;
    private WebView wbMessage;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        view = inflater.inflate(R.layout.dialog_task, container);
        toolbar_title = view.findViewById(R.id.toolbar_title);
        wbMessage = view.findViewById(R.id.wbMessage);
        idTask = getArguments().getLong(getString(R.string.app_bundle_name));
        Log.e("task","task "+idTask);
        dtoTask = new DaoTask().selectById(idTask);


        ChangeFontStyle.changeFont(ChangeFontStyle.TYPE_FONT.NORMAL, toolbar_title);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        toolbar_title.setText(dtoTask.getTitle());
        wbMessage.loadData(dtoTask.getContent(), "text/html; charset=UTF-8", null);
    }
}
