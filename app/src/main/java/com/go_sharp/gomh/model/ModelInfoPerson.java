package com.go_sharp.gomh.model;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.TextView;

import com.go_sharp.gomh.R;
import com.go_sharp.gomh.contextApp.ContextApp;
import com.go_sharp.gomh.dao.DaoImageLogin;
import com.go_sharp.gomh.dto.DtoImageLogin;
import com.go_sharp.gomh.util.ChangeFontStyle;
import com.go_sharp.gomh.util.Config;

/**
 * Created by gnu on 16/02/18.
 */

public class ModelInfoPerson {
    private TextView txtTBDate, txtTBTitle, txtTBSubTitle;
    private DtoImageLogin dtoImageLogin;
    private SharedPreferences preferences;

    public ModelInfoPerson(Activity activity) {
        txtTBDate = activity.findViewById(R.id.txtTBDate);
        txtTBTitle = activity.findViewById(R.id.txtTBTitle);
        txtTBSubTitle = activity.findViewById(R.id.txtTBSubTitle);
        dtoImageLogin = new DaoImageLogin().selectLast();
        preferences = ContextApp.context.getSharedPreferences(ContextApp.context.getString(R.string.app_share_preference_name), Context.MODE_PRIVATE);
        ChangeFontStyle.changeFont(txtTBDate,txtTBTitle,txtTBSubTitle);
    }

    public ModelInfoPerson loadInfo(String subtitle) {
        txtTBDate.setText(Config.formatDate());
        txtTBTitle.setText(preferences.getString(ContextApp.context.getString(R.string.app_share_preference_user_account), "").toUpperCase());
        txtTBSubTitle.setText(subtitle);
        return this;
    }
}
