package com.thundersharp.test.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.thundersharp.test.MainActivity;
import com.thundersharp.test.R;
import com.thundersharp.test.core.helpers.LoginHelper;
import com.thundersharp.test.core.helpers.LoginObserver;

public class Login extends AppCompatActivity {

    private AppCompatButton login,register;
    private EditText email,password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login = findViewById(R.id.login_btn);
        register = findViewById(R.id.register_btn);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);


        register.setOnClickListener(r -> {
            startActivity(new Intent(this,RegisterActivity.class));
            finish();
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (email.getText().toString().isEmpty() || password.getText().toString().isEmpty()){
                    Toast.makeText(Login.this, "All params are required", Toast.LENGTH_SHORT).show();
                }else {
                    new LoginHelper(Login.this)
                            .setPassword(password.getText().toString())
                            .setUserId(email.getText().toString())
                            .attachLoginObserver(new LoginObserver.EmailLogin() {
                                @Override
                                public void onLoginSuccess() {
                                    Toast.makeText(Login.this, "Welcome "+FirebaseAuth.getInstance().getCurrentUser().getEmail(), Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(Login.this, MainActivity.class));
                                    finish();
                                }

                                @Override
                                public void onLoginFailure(Exception e) {
                                    Toast.makeText(Login.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                }
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        if (FirebaseAuth.getInstance().getCurrentUser() != null){
            startActivity(new Intent(Login.this, MainActivity.class));
            finish();
        }
    }
}