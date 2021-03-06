package com.go_sharp.gomh.Network;

import android.content.Context;
import android.os.Handler;

import com.go_sharp.gomh.R;
import com.go_sharp.gomh.util.SharePreferenceCustom;

import net.gshp.APINetwork.APINetwork;
import net.gshp.APINetwork.NetworkTask;
import net.gshp.APINetwork.NetworkTask.TaskMode;

import org.apache.http.NameValuePair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class NetworkConfig {

    private Handler handler;
    private Context context;

    public NetworkConfig(Handler handler, Context context) {
        this.handler = handler;
        this.context = context;

        APINetwork.setUSERNAME(SharePreferenceCustom.read(R.string.app_share_preference_name, R.string.app_share_preference_user_account, ""));
        APINetwork.setPASSWORD(SharePreferenceCustom.read(R.string.app_share_preference_name, R.string.app_share_preference_user_pass, ""));
        APINetwork.setSOCKET_TIMEOUT(1000 * 20);

        /*try {
            CustomKeyStore customKeyStore = new CustomKeyStore(context);
            String pathTrustStore = customKeyStore.readCaCert();
            if (pathTrustStore != null)
                APINetwork.setKeyStore(customKeyStore.getTrustStoreFromCRT(pathTrustStore));
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("CERTIFICADO", e.toString());
        }*/

        APINetwork.setSERVICE_IP(context.getString(R.string.network_ip));
        APINetwork.setSERVICE_NAME(context.getString(R.string.network_context));
    }

    public void POST(String params, String bodyText, String tag, Map<String, String> headers) {
        NetworkTask Ntask = new NetworkTask(handler).setMode(NetworkTask.TaskMode.POST)
                .setTag(tag).setWithOutNameValuePair(true).setBodyText(bodyText).setParams(params).setGzip(true);
        if (headers != null) {
            headers.put(context.getString(R.string.network_header_token), SharePreferenceCustom.read(R.string.app_share_preference_name, R.string.app_share_preference_toke_webservices, ""));
            Ntask.setCustomHeaders(headers);
        }
        APINetwork.taskManager.addTask(Ntask);
    }

    public void POST_IMAGE(String params, String bodyText, String Path, String tag, Map<String, String> headers) {

        NetworkTask Ntask = new NetworkTask(handler).setMode(TaskMode.POST_MULTIPART)
                .setTag(tag).setParams(params).setFilepath(Path)
                .setBasicauth(true).setGzip(true).setBodyText(bodyText);
        if (headers != null) {
            headers.put(context.getString(R.string.network_header_token), SharePreferenceCustom.read(R.string.app_share_preference_name, R.string.app_share_preference_toke_webservices, ""));
            Ntask.setCustomHeaders(headers);
        }
        APINetwork.taskManager.addTask(Ntask);
    }

    public void multipartFile(String params, String path, ArrayList<NameValuePair> nameValuePairs, String tag, boolean sendHeader) {
        NetworkTask Ntask = new NetworkTask(handler)
                .setMode(NetworkTask.TaskMode.POST_MULTIPART_FILE)
                .setTag(tag)
                .setPayloadTipe("file")
                .setPayload(nameValuePairs)
                .setFilepath(path)
                .setParams(params);
        if (sendHeader) {
            Map<String, String> header = new HashMap<>();
            header.put(context.getString(R.string.network_header_token), SharePreferenceCustom.read(R.string.app_share_preference_name, R.string.app_share_preference_toke_webservices, ""));
            Ntask.setCustomHeaders(header);
        }

        APINetwork.taskManager.addTask(Ntask);
    }

    public void GET(String params, String tag) {
        Map<String, String> header = new HashMap<>();
        header.put(context.getString(R.string.network_header_token), SharePreferenceCustom.read(R.string.app_share_preference_name, R.string.app_share_preference_toke_webservices, ""));
        NetworkTask Ntask = new NetworkTask(handler).setMode(NetworkTask.TaskMode.GET)
                .setTag(tag).setParams(params).setBasicauth(true).setGzip(true).setCustomHeaders(header);
        APINetwork.taskManager.addTask(Ntask);
    }

    public void POSTChangePass(String params, ArrayList<NameValuePair> nameValuePairs,
                               String tag, String lastPass) {
        APINetwork.setPASSWORD(lastPass);
        NetworkTask Ntask = new NetworkTask(handler).setMode(TaskMode.POST)
                .setTag(tag).setPayload(nameValuePairs).setParams(params)
                .setBasicauth(true).setGzip(true);
        APINetwork.taskManager.addTask(Ntask);
    }

    public void POST_MULTIPART_FILE(String params, String path, ArrayList<NameValuePair> nameValuePairs, String tag) {
        NetworkTask Ntask = new NetworkTask(handler).setMode(TaskMode.POST_MULTIPART_FILE)
                .setTag(tag).setPayload(nameValuePairs).setFilepath(path).setParams(params)
                .setBasicauth(true);
        APINetwork.taskManager.addTask(Ntask);
    }
}
