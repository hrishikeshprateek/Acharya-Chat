package com.thundersharp.test.core.model;

import java.io.Serializable;

public class ChatMessageModel implements Serializable {

    public ChatMessageModel(){}

    public String key,message,recieverName,recieverUid,senderName,senderUid;

    public ChatMessageModel(String key, String message, String recieverName, String recieverUid, String senderName, String senderUid) {
        this.key = key;
        this.message = message;
        this.recieverName = recieverName;
        this.recieverUid = recieverUid;
        this.senderName = senderName;
        this.senderUid = senderUid;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getRecieverName() {
        return recieverName;
    }

    public void setRecieverName(String recieverName) {
        this.recieverName = recieverName;
    }

    public String getRecieverUid() {
        return recieverUid;
    }

    public void setRecieverUid(String recieverUid) {
        this.recieverUid = recieverUid;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getSenderUid() {
        return senderUid;
    }

    public void setSenderUid(String senderUid) {
        this.senderUid = senderUid;
    }
}
