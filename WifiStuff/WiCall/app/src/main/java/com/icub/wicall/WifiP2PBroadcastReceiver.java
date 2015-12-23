package com.icub.wicall;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.p2p.WifiP2pManager;
import android.util.Log;

public class WifiP2PBroadcastReceiver extends BroadcastReceiver {


    private WifiP2pManager mManager;
    private WifiP2pManager.Channel mChannel;
    private MainActivity mActivity;
    private WifiP2pManager.PeerListListener myPeerListListener;

    public WifiP2PBroadcastReceiver(){}

    public WifiP2PBroadcastReceiver(WifiP2pManager manager, WifiP2pManager.Channel channel, MainActivity activity, WifiP2pManager.PeerListListener peerListListener) {
        this.mManager = manager;
        this.mChannel = channel;
        this.mActivity = activity;
        this.myPeerListListener = peerListListener;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION.equals(action)) {
            Log.e("P2P_STATE_CHANGED","P2P_STATE_CHANGED");
        } else if (WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION.equals(action)) {
            Log.e("P2P_PEERS_CHANGED","P2P_PEERS_CHANGED");
            if (mManager != null) {
                mManager.requestPeers(mChannel, myPeerListListener);
            }
        } else if (WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION.equals(action)) {
            Log.e("P2P_CONNECTION_CHANGED","P2P_CONNECTION_CHANGED");
        } else if (WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION.equals(action)) {
            Log.e("P2P_THIS_DEVICE_CHANGED","P2P_THIS_DEVICE_CHANGED");
        }
    }
}