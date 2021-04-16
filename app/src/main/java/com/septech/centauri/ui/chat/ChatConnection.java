package com.septech.centauri.ui.chat;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.preference.PreferenceManager;
import android.util.Log;

import com.septech.centauri.data.model.chat.Message;
import com.septech.centauri.data.model.chat.User;

import org.jivesoftware.smack.ConnectionListener;
import org.jivesoftware.smack.ReconnectionManager;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.chat2.Chat;
import org.jivesoftware.smack.chat2.ChatManager;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;
import org.jxmpp.jid.EntityBareJid;
import org.jxmpp.jid.impl.JidCreate;
import org.jxmpp.stringprep.XmppStringprepException;

import java.io.IOException;


public class ChatConnection implements ConnectionListener{
    private static final String TAG = "ChatConnection";

    private final Context mApplicationContext;
    private final String mUsername;
    private final String mPassword;
    private final String mServiceName = "chat.septech.me";
    private XMPPTCPConnection mConnection;
    private BroadcastReceiver uiThreadMessageReceiver;


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
        showContactListActivityWhenAuthenticated();
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

    private void showContactListActivityWhenAuthenticated() {
        Intent i = new Intent(ChatConnectionService.UI_AUTHENTICATED);
        i.setPackage(mApplicationContext.getPackageName());
        mApplicationContext.sendBroadcast(i);
        Log.d(TAG, "Sent the broadcast that it was authenticated");
    }

    private void setupUiThreadBroadcastMessageReceiver() {
        uiThreadMessageReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                if (action.equals(ChatConnectionService.SEND_MESSAGE)) {
                    sendMessage(intent.getStringExtra(ChatConnectionService.BUNDLE_MESSAGE_BODY),
                            intent.getStringExtra(ChatConnectionService.BUNDLE_TO));
                }
            }
        };

        IntentFilter filter = new IntentFilter();
        filter.addAction(ChatConnectionService.SEND_MESSAGE);
        mApplicationContext.registerReceiver(uiThreadMessageReceiver, filter);
    }

    private void sendMessage (String body, String toJid) {
        Log.d(TAG, "Sending message to: " + toJid);

        EntityBareJid jid = null;

        ChatManager chatManager = ChatManager.getInstanceFor(mConnection);

        try {
            jid = JidCreate.entityBareFrom(toJid);
        } catch (XmppStringprepException e) {
            e.printStackTrace();
        }

        Chat chat = chatManager.chatWith(jid);
        try {
            chat.send(body);
        } catch (SmackException.NotConnectedException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
