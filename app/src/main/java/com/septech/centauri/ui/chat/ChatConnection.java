package com.septech.centauri.ui.chat;

import android.content.Context;
import android.preference.PreferenceManager;
import android.util.Log;

import org.jivesoftware.smack.ConnectionListener;
import org.jivesoftware.smack.ReconnectionManager;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;

import java.io.IOException;


public class ChatConnection implements ConnectionListener{
    private static final String TAG = "ChatConnection";

    private final Context mApplicationContext;
    private final String mUsername;
    private final String mPassword;
    private final String mServiceName = "chat.septech.me";
    private XMPPTCPConnection mConnection;


    public static enum ConnectionState {
        CONNECTED, AUTHENTICATED, CONNECTING, DISCONNECTING, DISCONNECTED
    }

    public static enum LoggedInState {
        LOGGED_IN, LOGGED_OUT
    }

    public ChatConnection(Context context){
        Log.d(TAG, "ChatConnection Constructor called");
        mApplicationContext = context.getApplicationContext();
        String jid = PreferenceManager.getDefaultSharedPreferences(mApplicationContext).getString("xmpp_jid", null);
        mPassword = PreferenceManager.getDefaultSharedPreferences(mApplicationContext).getString("xmpp_password", null);

        if(jid != null){
            mUsername = jid.split("@")[0];
        } else {
            mUsername = "";
        }
    }

    public void connect() throws IOException, XMPPException, SmackException, InterruptedException {
        Log.d(TAG, "Connecting to server");
        XMPPTCPConnectionConfiguration.Builder builder = XMPPTCPConnectionConfiguration.builder();
        builder.setXmppDomain(mServiceName);
        builder.setUsernameAndPassword(mUsername, mPassword);
        builder.setResource("Centauri");

        //Set up the ui thread broadcast message receiver.
        //setupUiThreadBroadCastMessageReceiver();

        mConnection = new XMPPTCPConnection(builder.build());
        mConnection.addConnectionListener(this);
        mConnection.connect();
        mConnection.login();

        ReconnectionManager reconnectionManager = ReconnectionManager.getInstanceFor(mConnection);
        ReconnectionManager.setEnabledPerDefault(true);
        reconnectionManager.enableAutomaticReconnection();
    }


    public void disconnect(){
        Log.d(TAG, "Disconnecting from server");
        if (mConnection != null){
            mConnection.disconnect();
            ChatConnectionService.sConnectionState = ConnectionState.DISCONNECTED;
        }
        mConnection = null;
    }

    @Override
    public void connected(XMPPConnection connection) {
        ChatConnectionService.sConnectionState = ConnectionState.CONNECTED;
        Log.d(TAG, "Connected Successfully");
    }

    @Override
    public void authenticated(XMPPConnection connection, boolean resumed) {
        ChatConnectionService.sConnectionState = ConnectionState.CONNECTED;
        Log.d(TAG, "Authenticated Successfully");
    }

    @Override
    public void connecting(XMPPConnection connection) {
        ChatConnectionService.sConnectionState = ConnectionState.CONNECTING;
        Log.d(TAG, "connecting()");
    }

    @Override
    public void connectionClosed() {
        ChatConnectionService.sConnectionState = ConnectionState.DISCONNECTED;
        Log.d(TAG, "connectionClosed()");
    }

    @Override
    public void connectionClosedOnError(Exception e) {
        ChatConnectionService.sConnectionState = ConnectionState.DISCONNECTED;
        Log.d(TAG, "connectionClosedOnError, error " + e.toString());
    }
}
