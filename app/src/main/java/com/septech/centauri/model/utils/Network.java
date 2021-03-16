package com.septech.centauri.model.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class Network {

    public static NetworkConnectivity checkInternetConnection(Context ctx) {
        NetworkConnectivity connectionState;

        ConnectivityManager connManager =
                (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo netInfo = connManager.getActiveNetworkInfo();

        if (netInfo == null) return NetworkConnectivity.NOCONNECTION;
        else if (netInfo.isConnected()) return NetworkConnectivity.CONNECTED;
        else if(netInfo.isConnectedOrConnecting()) return NetworkConnectivity.CONNECTING;
        //should be unreachable
        else return null;
    }
}
