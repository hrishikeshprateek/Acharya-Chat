package com.thundersharp.test.core.model;

import java.io.Serializable;

public class UserDataModel implements Serializable {

    public UserDataModel(){}

    public String NAME,EMAIL,UID;

    public UserDataModel(String NAME, String EMAIL, String UID) {
        this.NAME = NAME;
        this.EMAIL = EMAIL;
        this.UID = UID;
    }
}
