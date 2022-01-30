package com.thundersharp.test.core.model;

public class LastMessageModel {

    public LastMessageModel(){}

    public String KEY,MESSAGE,NAME,SENDERUID;

    public LastMessageModel(String KEY, String MESSAGE, String NAME, String SENDERUID) {
        this.KEY = KEY;
        this.MESSAGE = MESSAGE;
        this.NAME = NAME;
        this.SENDERUID = SENDERUID;
    }

}
