package com.septech.centauri.ui.chat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.septech.centauri.R;
import com.squareup.picasso.Picasso;
import com.stfalcon.chatkit.commons.models.IDialog;
import com.stfalcon.chatkit.dialogs.DialogsList;
import com.stfalcon.chatkit.dialogs.DialogsListAdapter;
import com.stfalcon.chatkit.commons.ImageLoader;
import com.stfalcon.chatkit.dialogs.DialogsListAdapter;
import com.septech.centauri.data.model.chat.Dialog;
import com.septech.centauri.data.utils.ToastUtils;
import com.stfalcon.chatkit.utils.DateFormatter;

import java.util.Date;

public class DialogsActivity extends AppCompatActivity
        implements DialogsListAdapter.OnDialogClickListener<Dialog>,
        DialogsListAdapter.OnDialogLongClickListener,
        DateFormatter.Formatter {

    public static void open(Context context) {
        context.startActivity(new Intent(context, DialogsActivity.class));
    }

    protected ImageLoader imageLoader;
    protected DialogsListAdapter<Dialog> dialogsAdapter;
    private DialogsList dialogsList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialogs);

        imageLoader = (imageView, url, payload) -> Picasso.get().load(url).into(imageView);
        dialogsList = findViewById(R.id.dialogsList);
        initAdapter();
    }

    @Override
    public void onDialogLongClick(IDialog dialog) {
        ToastUtils.showToast(this, dialog.getDialogName(), false);
    }

    @Override
    public void onDialogClick(Dialog dialog) {
        MessagesActivity.open(this);
    }

    @Override
    public String format(Date date) {
        if (DateFormatter.isToday(date)) {
            return DateFormatter.format(date, DateFormatter.Template.TIME);
        } else if (DateFormatter.isYesterday(date)) {
            return getString(R.string.date_header_yesterday);
        } else if (DateFormatter.isCurrentYear(date)) {
            return DateFormatter.format(date, DateFormatter.Template.STRING_DAY_MONTH);
        } else {
            return DateFormatter.format(date, DateFormatter.Template.STRING_DAY_MONTH_YEAR);
        }
    }

    private void initAdapter() {
        // TODO
        dialogsAdapter = new DialogsListAdapter<>(imageLoader);

        //dialogsAdapter.setItems();

        dialogsAdapter.setOnDialogClickListener(this);
        dialogsAdapter.setOnDialogLongClickListener(this);
        dialogsAdapter.setDatesFormatter(this);

        dialogsList.setAdapter(dialogsAdapter);
    }
}