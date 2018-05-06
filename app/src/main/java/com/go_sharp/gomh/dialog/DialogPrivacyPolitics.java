package com.go_sharp.gomh.dialog;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import com.go_sharp.gomh.R;
import com.go_sharp.gomh.dao.DaoPolitic;
import com.go_sharp.gomh.dto.DtoPolitic;
import com.go_sharp.gomh.util.ChangeFontStyle;

/**
 * Created by leo on 21/08/17.
 */

public class DialogPrivacyPolitics extends DialogFragment implements CompoundButton.OnCheckedChangeListener, View.OnClickListener {

    private View view;
    private EditText edtPolicy;
    private CheckBox chbAgree;
    private TextView txtTitle;
    private Button btnAgree, btnCancel;
    private SharedPreferences sh;
    private DaoPolitic daoPolitics;
    private DtoPolitic dtoPolitics;
    private String versionPolitic = "TERMS_1.0";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        view = inflater.inflate(R.layout.dialog_privacy_policy, container);
        txtTitle = view.findViewById(R.id.toolbar_title);
        edtPolicy = view.findViewById(R.id.edt_policy);
        chbAgree = view.findViewById(R.id.chb_agree);
        btnAgree = view.findViewById(R.id.btn_agree);
        btnCancel = view.findViewById(R.id.btn_cancel);
        sh = getActivity().getSharedPreferences(getString(R.string.app_share_preference_name), Context.MODE_PRIVATE);
        daoPolitics = new DaoPolitic();
        dtoPolitics = daoPolitics.Select();

        if (dtoPolitics.getValue() == null || dtoPolitics.getValue().isEmpty()) {
            edtPolicy.setText(view.getContext().getResources().getString(R.string.privacy_politics));
        } else {
            edtPolicy.setText(dtoPolitics.getValue());
            versionPolitic = dtoPolitics.getVersion();
        }

        chbAgree.setOnCheckedChangeListener(this);
        btnAgree.setOnClickListener(this);
        btnCancel.setOnClickListener(this);

        ChangeFontStyle.changeFont(chbAgree, btnAgree, btnCancel, edtPolicy, view.findViewById(R.id.toolbar_title));
        return view;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_cancel) {
            dismiss();
            getActivity().finish();
        } else if (view.getId() == R.id.btn_agree) {
            sh.edit().putString(getString(R.string.app_share_preference_privacy_politic), versionPolitic).apply();
            dismiss();
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean b) {
        if (b) {
            btnAgree.setVisibility(View.VISIBLE);
        } else {
            btnAgree.setVisibility(View.GONE);
        }
    }

}
