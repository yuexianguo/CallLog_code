package com.phone.base.common.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.net.NetworkRequest;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

public class ConnectionStateMonitor extends LiveData<NetworkInfo> {

    private final Context mContext;
    private ConnectivityManager.NetworkCallback networkCallback = null;
    private NetworkReceiver networkReceiver;
    private final ConnectivityManager connectivityManager;
    private static final String TAG = "ConnectionStateMonitor";

    public ConnectionStateMonitor(Context context) {
        mContext = context;
        connectivityManager = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            networkCallback = new NetworkCallback(this);
        } else {
            networkReceiver = new NetworkReceiver();
        }
    }

    @Override
    protected void onActive() {
        super.onActive();
        updateConnection();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            NetworkRequest networkRequest = new NetworkRequest.Builder()
                    .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
                    .build();
            connectivityManager.registerNetworkCallback(networkRequest, networkCallback);
        } else {
            mContext.registerReceiver(networkReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        }
    }

    @Override
    protected void onInactive() {
        super.onInactive();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            connectivityManager.unregisterNetworkCallback(networkCallback);
        } else {
            mContext.unregisterReceiver(networkReceiver);
        }
    }

    class NetworkCallback extends ConnectivityManager.NetworkCallback {

        private final ConnectionStateMonitor mConnectionStateMonitor;

        public NetworkCallback(ConnectionStateMonitor connectionStateMonitor) {
            mConnectionStateMonitor = connectionStateMonitor;
        }

        @Override
        public void onAvailable(@NonNull Network network) {
            Log.d(TAG, "NetworkCallback onAvailable Called");
            if (connectivityManager != null) {
                NetworkInfo networkInfo = connectivityManager.getNetworkInfo(network);
                Log.d(TAG, "NetworkCallback:" + networkInfo);
                if (Build.VERSION.SDK_INT >= 23) {
                    connectivityManager.bindProcessToNetwork(network);
                }
                mConnectionStateMonitor.postValue(networkInfo);
            }
        }

        @Override
        public void onLost(@NonNull Network network) {
            Log.d(TAG, "NetworkCallback onLost Called");
            if (connectivityManager != null) {
                Log.d(TAG, "NetworkCallback: unbinding");
                if (Build.VERSION.SDK_INT >= 23) {
                    connectivityManager.bindProcessToNetwork(null);
                }
            }
            mConnectionStateMonitor.postValue(null);
        }
    }

    class NetworkReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (ConnectivityManager.CONNECTIVITY_ACTION.equals(intent.getAction())) {
                updateConnection();
            }
        }
    }

    private void updateConnection() {
        if (connectivityManager != null) {
            NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();

            if (activeNetwork != null && activeNetwork.isConnectedOrConnecting()) {
                postValue(activeNetwork);
            } else {
                postValue(null);
            }
        }
    }

}
