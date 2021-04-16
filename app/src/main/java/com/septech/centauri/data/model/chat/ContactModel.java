package com.septech.centauri.data.model.chat;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

public class ContactModel {
    private static ContactModel sContactModel;
    private List<Contact> mContacts;

    public static ContactModel get(Context context) {
        if (sContactModel == null) {
            sContactModel = new ContactModel(context);
        }
        return sContactModel;
    }

    private ContactModel(Context context) {
        mContacts = new ArrayList<>();
        populateWithInitialContacts(context);
    }

    private void populateWithInitialContacts(Context context) {
        Contact contact1 = new Contact("User1@chat.septech.me");
        mContacts.add(contact1);
        Contact contact2 = new Contact("User2@chat.septech.me");
        mContacts.add(contact2);
        Contact contact3 = new Contact("User3@chat.septech.me");
        mContacts.add(contact3);
        Contact contact4 = new Contact("User4@chat.septech.me");
        mContacts.add(contact4);
        Contact contact5 = new Contact("User5@chat.septech.me");
        mContacts.add(contact5);
        Contact contact6 = new Contact("User6@chat.septech.me");
        mContacts.add(contact6);
        Contact contact7 = new Contact("User7@chat.septech.me");
        mContacts.add(contact7);
    }

    public List<Contact> getmContacts() {
        return mContacts;
    }
}
