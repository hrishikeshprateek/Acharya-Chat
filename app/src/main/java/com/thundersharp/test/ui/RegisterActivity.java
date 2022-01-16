package com.thundersharp.test.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.AuthResult;
import com.thundersharp.test.MainActivity;
import com.thundersharp.test.R;
import com.thundersharp.test.core.helpers.LoginHelper;
import com.thundersharp.test.core.helpers.LoginObserver;
import com.thundersharp.test.core.util.EmailUtils;

public class RegisterActivity extends AppCompatActivity {

    AppCompatButton register_btn,login_btn;
    EditText email,password,confirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        register_btn = findViewById(R.id.register_btn_s);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        confirmPassword = findViewById(R.id.password_re_enter);
        login_btn = findViewById(R.id.login_page);

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterActivity.this,Login.class));
                finish();
            }
        });

        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!EmailUtils.isEmailValid(email.getText().toString())){
                    Toast.makeText(RegisterActivity.this, "Email id is not properly formatted !!", Toast.LENGTH_SHORT).show();
                }else if (!email.getText().toString().contains("@acharya.ac.in")){
                    Toast.makeText(RegisterActivity.this, "This is not a acharya email !!", Toast.LENGTH_SHORT).show();
                }else if (password.getText().toString().isEmpty()){
                    Toast.makeText(RegisterActivity.this,"Password cannot be empty !!",Toast.LENGTH_LONG).show();
                }else if (password.getText().toString().length() < 6){
                    Toast.makeText(RegisterActivity.this, "Password length should be greater than 6", Toast.LENGTH_SHORT).show();
                }else if (!password.getText().toString().equals(confirmPassword.getText().toString())){
                    Toast.makeText(RegisterActivity.this, "Passwords mismatched !!", Toast.LENGTH_SHORT).show();
                }else {
                    new LoginHelper(RegisterActivity.this)
                            .setUserId(email.getText().toString())
                            .setPassword(password.getText().toString())
                            .attachRegistrationObserver(new LoginObserver.EmailRegistration() {
                                @Override
                                public void onRegisterSuccess(AuthResult authResult) {
                                    Toast.makeText(RegisterActivity.this, "Welcome "+authResult.getUser().getEmail(), Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                                    finish();
                                }

                                @Override
                                public void onRegisterFailure(Exception e) {
                                    Toast.makeText(RegisterActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                }
            }
        });
    }
}