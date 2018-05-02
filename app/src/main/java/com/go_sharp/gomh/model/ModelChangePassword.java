package com.go_sharp.gomh.model;

import android.os.Handler;

import com.go_sharp.gomh.Network.NetworkConfig;
import com.go_sharp.gomh.contextApp.ContextApp;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;

/**
 * Created by LEONARDO on 20/08/2017.
 */

public class ModelChangePassword {
    private NetworkConfig networkConfig;
    private Handler handlerGUI;

    public ModelChangePassword(Handler handlerGUI) {
        this.handlerGUI = handlerGUI;
        networkConfig = new NetworkConfig(handlerGUI, ContextApp.context);
    }

    public void sendPassword(final String pass, final String lastpast) {
        new Thread() {
            public void run() {
                ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
                nameValuePairs.add(new BasicNameValuePair("pass", pass));
                networkConfig.POSTChangePass("psspolicy/update", nameValuePairs, "rsaa", lastpast);
            }
        }.start();
    }
}