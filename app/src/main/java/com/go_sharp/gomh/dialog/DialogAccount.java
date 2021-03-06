package com.go_sharp.gomh.dialog;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.go_sharp.gomh.R;
import com.go_sharp.gomh.listener.OnDissmisDialogListener;
import com.go_sharp.gomh.util.ChangeFontStyle;
import com.go_sharp.gomh.util.SharePreferenceCustom;

/**
 * Created by LEONARDO on 20/08/2017.
 */

public class DialogAccount extends DialogFragment implements View.OnClickListener {

    private View view;
    private TextView txtWarning, txtTitle;
    private EditText edt_user_name, edt_pass;
    private LinearLayout lyt_account, lyt_warning;
    private Button btn_sync, btn_sync_agree;
    private OnDissmisDialogListener onDissmisDialogListener;

    public DialogAccount() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        view = inflater.inflate(R.layout.dialog_account, container);
        txtTitle = view.findViewById(R.id.toolbar_title);
        txtWarning = view.findViewById(R.id.txt_warning_account);
        edt_user_name = view.findViewById(R.id.edt_user_name);
        edt_pass = view.findViewById(R.id.edt_pass);
        lyt_account = view.findViewById(R.id.lyt_account);
        lyt_warning = view.findViewById(R.id.lyt_warning);

        btn_sync = view.findViewById(R.id.btn_sync);
        btn_sync_agree = view.findViewById(R.id.btn_sync_agree);

        btn_sync.setOnClickListener(this);
        btn_sync_agree.setOnClickListener(this);

        //si existe una cuenta guardada se muestra en los textviews
        edt_user_name.setText(SharePreferenceCustom.read(R.string.app_share_preference_name, R.string.app_share_preference_user_account, "").toUpperCase());
        edt_pass.setText(SharePreferenceCustom.read(R.string.app_share_preference_name, R.string.app_share_preference_user_pass, ""));

        ChangeFontStyle.changeFont(ChangeFontStyle.TYPE_FONT.NORMAL, view.findViewById(R.id.toolbar_title), view.findViewById(R.id.edt_user_name),
                view.findViewById(R.id.edt_pass), view.findViewById(R.id.btn_sync)
                , view.findViewById(R.id.txt_warning_account), view.findViewById(R.id.btn_sync_agree));

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_sync:
                if (edt_user_name.getText().toString().isEmpty()) {
                    edt_user_name.setError(getString(R.string.error_empty_user_name));
                } else if (edt_pass.getText().toString().isEmpty()) {
                    edt_pass.setError(getString(R.string.error_empty_password));
                } else if (SharePreferenceCustom.read(R.string.app_share_preference_name, R.string.app_share_preference_user_account, null) != null &&
                        !SharePreferenceCustom.read(R.string.app_share_preference_name, R.string.app_share_preference_user_account, "").toUpperCase().equals(edt_user_name.getText().toString().trim())) {
                    lyt_account.setVisibility(View.GONE);
                    lyt_warning.setVisibility(View.VISIBLE);
                } else {
                    SharePreferenceCustom.write(R.string.app_share_preference_name, R.string.app_share_preference_user_account, edt_user_name.getText().toString().trim());
                    SharePreferenceCustom.write(R.string.app_share_preference_name, R.string.app_share_preference_user_pass, edt_pass.getText().toString().trim());
                    DialogSync diFragmentSync = new DialogSync();
                    diFragmentSync.setOnDissmiDialogListener(onDissmisDialogListener).setCancelable(false);
                    diFragmentSync.show(getFragmentManager(), "DialogFragmentSync");
                    dismiss();
                }
                break;
            case R.id.btn_sync_agree:
                SharePreferenceCustom.write(R.string.app_share_preference_name, R.string.app_share_preference_user_account, edt_user_name.getText().toString().trim());
                SharePreferenceCustom.write(R.string.app_share_preference_name, R.string.app_share_preference_user_pass, edt_pass.getText().toString().trim());
                //prefs.edit().remove(getString(R.string.app_share_preference_privacy_politic)).commit();
                SharePreferenceCustom.remove(R.string.app_share_preference_name, R.string.app_share_preference_time_synch);
                SharePreferenceCustom.remove(R.string.app_share_preference_name, R.string.app_share_preference_first_synch);
                getContext().deleteDatabase(getResources().getString(R.string.app_db_name));
                Intent i = getActivity().getBaseContext().getPackageManager()
                        .getLaunchIntentForPackage(getActivity().getBaseContext().getPackageName());
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                /*DialogSync diFragmentSync = new DialogSync();
                diFragmentSync.setCancelable(false);
                diFragmentSync.show(getFragmentManager(), "DialogFragmentSync");*/
                getActivity().finish();
                dismiss();
                break;
            default:
                break;
        }
    }

    public DialogAccount setOnDissmiDialogListener(OnDissmisDialogListener onDissmisDialogListener) {
        this.onDissmisDialogListener = onDissmisDialogListener;
        return this;
    }
}
