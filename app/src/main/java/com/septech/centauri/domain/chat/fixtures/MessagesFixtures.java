package com.septech.centauri.domain.chat.fixtures;

import com.septech.centauri.domain.chat.models.Message;
import com.septech.centauri.domain.chat.models.User;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public final class MessagesFixtures {

    private MessagesFixtures() {
        throw new AssertionError();
    }

    public static Message getImageMessage(String id, User user, String url) {
        Message message = new Message(id, user, null);
        message.setImage(new Message.Image(url));
        return message;
    }

    public static Message getTextMessage(String id, User user, String text) {
        return new Message(id, user, text);
    }

    public static ArrayList<Message> getMessages(Date startDate) {
        ArrayList<Message> messages = new ArrayList<>();
        // populate list with messages
        return messages;
    }

    private static User getUser() {
        return null;
    }
}
