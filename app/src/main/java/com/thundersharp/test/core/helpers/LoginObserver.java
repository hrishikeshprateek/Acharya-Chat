package com.thundersharp.test.core.helpers;

import com.google.firebase.auth.AuthResult;

public interface LoginObserver {

    interface EmailLogin{
        void onLoginSuccess();
        void onLoginFailure(Exception e);
    }

    interface EmailRegistration {
        void onRegisterSuccess(AuthResult authResult);
        void onRegisterFailure(Exception e);
    }

}
