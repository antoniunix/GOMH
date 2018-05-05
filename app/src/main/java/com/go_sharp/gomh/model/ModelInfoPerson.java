package com.go_sharp.gomh.model;

import android.app.Activity;
import android.content.Context;
import android.widget.TextView;

import com.go_sharp.gomh.R;
import com.go_sharp.gomh.contextApp.ContextApp;
import com.go_sharp.gomh.dao.DaoImageLogin;
import com.go_sharp.gomh.dto.DtoImageLogin;
import com.go_sharp.gomh.util.ChangeFontStyle;
import com.go_sharp.gomh.util.Config;
import com.go_sharp.gomh.util.SharePreferenceCustom;

/**
 * Created by gnu on 16/02/18.
 */

public class ModelInfoPerson {
    private TextView txtTBDate, txtTBTitle, txtTBSubTitle;
    private Context context;

    public ModelInfoPerson(Activity activity) {
        txtTBDate = activity.findViewById(R.id.txtTBDate);
        txtTBTitle = activity.findViewById(R.id.txtTBTitle);
        txtTBSubTitle = activity.findViewById(R.id.txtTBSubTitle);
        ChangeFontStyle.changeFont(txtTBDate,txtTBTitle,txtTBSubTitle);
        context = ContextApp.context;
    }

    public ModelInfoPerson loadInfo(String subtitle) {
        txtTBDate.setText(Config.formatDate());
        txtTBTitle.setText(SharePreferenceCustom.read(context.getString(R.string.app_share_preference_name), context.getString(R.string.app_share_preference_user_account), "").toUpperCase());
        txtTBSubTitle.setText(subtitle);
        return this;
    }

    public ModelInfoPerson loadInfo(String title, String subtitle) {
        txtTBDate.setText(Config.formatDate());
        txtTBTitle.setText(title);
        txtTBSubTitle.setText(subtitle);
        return this;
    }
}
