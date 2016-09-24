package com.ihc.cefet.cidadealerta;

import android.app.Application;

import java.util.concurrent.ExecutorService;

public class CAApp extends Application {

    private static CAApp mInstance;

    @Override
    public void onCreate() {
        super.onCreate();

        mInstance = this;
    }

    public static synchronized CAApp getInstance() {
        return mInstance;
    }

    public void setConnectivityListener(ConnectivityReceiver.ConnectivityReceiverListener listener) {
        ConnectivityReceiver.connectivityReceiverListener = listener;
    }
}