package com.septech.centauri.ui.chat;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.os.Handler;
import android.util.Log;
import android.os.IBinder;
import android.os.Looper;
import android.content.Intent;

import androidx.annotation.Nullable;

import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPException;

import java.io.IOException;

public class ChatConnectionService extends Service {
    private static final String TAG = "ChatService";

    public static final String UI_AUTHENTICATED = "com.septech.centauri.uiauthenticated";
    public static final String SEND_MESSAGE = "com.septech.centauri.sendmessage";
    public static final String BUNDLE_MESSAGE_BODY = "b_body";
    public static final String BUNDLE_TO = "b_to";

    public static final String NEW_MESSAGE = "com.septech.centauri.newmessage";
    public static final String BUNDLE_FROM_JID = "b_from";

    public static ChatConnection.ConnectionState sConnectionState;
    public static ChatConnection.LoggedInState sLoggedInState;

    private boolean mActive;
    private Thread mThread;
    private Handler mTHandler;
    private ChatConnection mConnection;

    public ChatConnectionService(){

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate()");
    }

    private void initConnection(){
        Log.d(TAG, "initConnection()");
        if (mConnection == null){
            mConnection = new ChatConnection(this);
        }
        try {
            mConnection.connect();
        } catch (IOException | SmackException | XMPPException | InterruptedException e){
            Log.d(TAG, "Error while connecting. Retry");
            e.printStackTrace();
            stopSelf();
        }
    }

    public void start() {
        Log.d(TAG, "Service start()");
        if (!mActive){
            mActive = true;
            if (mThread == null || !mThread.isAlive()){
                mThread = new Thread(() -> {
                    Looper.prepare();
                    mTHandler = new Handler();
                    initConnection();
                    Looper.loop();
                });
                mThread.start();
            }
        }
    }

    public void stop() {
        Log.d(TAG, "stop()");
        mActive = false;
        mTHandler.post(() -> {
            if (mConnection != null) {
                mConnection.disconnect();
            }
        });
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand()");
        start();
        return Service.START_STICKY;
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy()");
        super.onDestroy();
        stop();
    }

    public static ChatConnection.ConnectionState getState(){
        if (sConnectionState == null){
            return ChatConnection.ConnectionState.DISCONNECTED;
        }
        return sConnectionState;
    }

    public static ChatConnection.LoggedInState getsLoggedInState(){
        if (sLoggedInState == null){
            return ChatConnection.LoggedInState.LOGGED_OUT;
        }
        return sLoggedInState;
    }

}
