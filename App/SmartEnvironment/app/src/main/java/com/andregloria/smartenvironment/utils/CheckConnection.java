package com.andregloria.smartenvironment.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by ANDRE on 10/05/16.
 */
public class CheckConnection {

    private Context context;

    public CheckConnection(Context context){
        this.context = context;
    }

    /**
     * Checking for all possible internet providers
     * **/
    public boolean isConnected(){
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = connectivity.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }
}
