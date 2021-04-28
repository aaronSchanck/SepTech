package com.septech.centauri.ui.chat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.Menu;
import android.util.Log;
import android.view.MenuItem;

import com.septech.centauri.R;
import com.septech.centauri.domain.chat.models.Message;
import com.septech.centauri.domain.chat.models.User;
import com.stfalcon.chatkit.commons.ImageLoader;
import com.stfalcon.chatkit.messages.MessageInput;
import com.stfalcon.chatkit.messages.MessagesList;
import com.stfalcon.chatkit.messages.MessagesListAdapter;
import com.stfalcon.chatkit.utils.DateFormatter;
import com.septech.centauri.domain.chat.utils.ToastUtils;
import com.squareup.picasso.Picasso;

import java.util.Date;
import java.util.Locale;
import java.text.SimpleDateFormat;

public class MessagesActivity extends AppCompatActivity
        implements MessagesListAdapter.SelectionListener,
        MessagesListAdapter.OnLoadMoreListener,
        MessageInput.InputListener,
        MessageInput.AttachmentsListener,
        DateFormatter.Formatter {

    public static void open(Context context) {
        context.startActivity(new Intent(context, MessagesActivity.class));
    }

    private static final int TOTAL_MESSAGES_COUNT = 100;
    private static final String TAG = "MessagesActivity";

    protected final String senderId = "admin@chat.septech.me";
    protected ImageLoader imageLoader;
    protected MessagesListAdapter<Message> messagesAdapter;

    private BroadcastReceiver mBroadcastReceiver;

    private Menu menu;
    private int selectionCount;
    private Date lastLoadedDate;

    private MessagesList messagesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);

        imageLoader = ((imageView, url, payload) -> Picasso.get().load(url).into(imageView));

        messagesList = findViewById(R.id.messagesList);
        initAdapter();

        MessageInput input = findViewById(R.id.input);
        input.setInputListener(this);
        input.setAttachmentsListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        loadMessages();
        //messagesAdapter.addToStart(MessagesFixtures.getTextMessage(), true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
        getMenuInflater().inflate(R.menu.chat_actions_menu, menu);
        onSelectionChanged(0);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_delete:
                messagesAdapter.deleteSelectedMessages();
                break;
            case R.id.action_copy:
                messagesAdapter.copySelectedMessagesText(this, getMessageStringFormatter(), true);
                ToastUtils.showToast(this, "Message copied", true);
                break;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        if (selectionCount == 0) {
            super.onBackPressed();
        } else {
            messagesAdapter.unselectAllItems();
        }
    }

    @Override
    public void onLoadMore(int page, int totalItemsCount) {
        Log.i(TAG, "onLoadMore: " + page + " " + totalItemsCount);
        if (totalItemsCount < TOTAL_MESSAGES_COUNT) {
            loadMessages();
        }
    }

    @Override
    public void onSelectionChanged(int count) {
        this.selectionCount = count;
        menu.findItem(R.id.action_delete).setVisible(count > 0);
        menu.findItem(R.id.action_copy).setVisible(count > 0);
    }

    protected void loadMessages() {
        // TODO
        // load messages into messages array list
    }

    private MessagesListAdapter.Formatter<Message> getMessageStringFormatter() {
        // TODO - change to only copy message contents
        return message -> {
            String createdAt = new SimpleDateFormat("MMM d, EEE 'at' h:mm a", Locale.getDefault())
                    .format(message.getCreatedAt());

            String text = message.getText();

            if (text == null) {
                text = "[attachment]";
            }

            return String.format(Locale.getDefault(), "%s: %s (%s)",
                    message.getUser().getName(), text, createdAt);
        };
    }

    @Override
    public boolean onSubmit(CharSequence input) {
        if (ChatConnectionService.getState().equals(ChatConnection.ConnectionState.CONNECTED)) {
            Log.d(TAG, "Client is connected to server. Sending message.");

            Intent intent = new Intent(ChatConnectionService.SEND_MESSAGE);
            intent.putExtra(ChatConnectionService.BUNDLE_MESSAGE_BODY, input);
            intent.putExtra(ChatConnectionService.BUNDLE_TO, senderId);

            sendBroadcast(intent);
            messagesAdapter.addToStart(new Message(senderId, new User(senderId, senderId, null), input.toString()), true);
        }
        return true;
    }

    @Override
    public void onAddAttachments() {
        // TODO
        //messagesAdapter.addToStart(MessagesFixtures.getImageMessage(), true);
    }

    @Override
    public String format(Date date) {
        if (DateFormatter.isToday(date)) {
            return getString(R.string.date_header_today);
        } else if (DateFormatter.isYesterday(date)) {
            return getString(R.string.date_header_yesterday);
        } else {
            return DateFormatter.format(date, DateFormatter.Template.STRING_DAY_MONTH_YEAR);
        }
    }

    private void initAdapter() {
        messagesAdapter = new MessagesListAdapter<>(senderId, imageLoader);
        messagesAdapter.enableSelectionMode(this);
        messagesAdapter.setLoadMoreListener(this);
        messagesAdapter.setDateHeadersFormatter(this);
        messagesList.setAdapter(messagesAdapter);
    }

    protected void onPause() {
        super.onPause();
        unregisterReceiver(mBroadcastReceiver);
    }

    protected void onResume() {
        super.onResume();
        mBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                switch (action) {
                    case ChatConnectionService.NEW_MESSAGE:
                        String from = intent.getStringExtra(ChatConnectionService.BUNDLE_FROM_JID);
                        CharSequence body = intent.getCharSequenceExtra(ChatConnectionService.BUNDLE_MESSAGE_BODY);

                        if (from.equals(senderId)) {
                            messagesAdapter.addToStart(
                                    new Message("test_user@chat.septech.me",
                                            new User("test_user@chat.septech.me", "test", null),
                                            body.toString()),
                                    true);
                        } else {
                            Log.d(TAG, "Got a message from jid: " + from);
                        }
                        return;
                }
            }
        };

        IntentFilter filter = new IntentFilter(ChatConnectionService.NEW_MESSAGE);
        registerReceiver(mBroadcastReceiver, filter);
    }
}