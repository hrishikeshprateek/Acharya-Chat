package com.thundersharp.test.core.helpers;

import android.content.Context;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginHelper {

    private Context context;
    private LoginObserver.EmailRegistration emailRegistration;
    private LoginObserver.EmailLogin loginObserver;

    private String userId,password;

    public LoginHelper(Context context) {
        this.context = context;
    }

    public static LoginHelper getInstance(Context context){
        return new LoginHelper(context);
    }

    public LoginHelper setUserId(String userId){
        this.userId = userId;
        return this;
    }

    public LoginHelper setPassword(String password){
        this.password = password;
        return this;
    }

    public void attachLoginObserver(LoginObserver.EmailLogin loginObserver){
        this.loginObserver = loginObserver;
        initializeLogin(userId,password);
    }

    public void attachRegistrationObserver(LoginObserver.EmailRegistration emailRegistrationObserver){
        this.emailRegistration = emailRegistrationObserver;
        initlizeRegesteration(userId,password);
    }

    private void initializeLogin(String userId,String password){
        FirebaseAuth
                .getInstance()
                .signInWithEmailAndPassword(userId, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            loginObserver.onLoginSuccess();
                        }else loginObserver.onLoginFailure(task.getException());
                    }
                });
    }

    private void initlizeRegesteration(String userId, String password) {
        FirebaseAuth
                .getInstance()
                .createUserWithEmailAndPassword(userId, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            emailRegistration.onRegisterSuccess(task.getResult());
                        }else emailRegistration.onRegisterFailure(task.getException());
                    }
                });
    }
}
