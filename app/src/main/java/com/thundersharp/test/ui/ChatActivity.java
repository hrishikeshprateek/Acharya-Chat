package com.thundersharp.test.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;

import com.thundersharp.test.R;
import com.thundersharp.test.core.model.UserDataModel;

public class ChatActivity extends AppCompatActivity {

    UserDataModel userDataModel;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        userDataModel = (UserDataModel)getIntent().getSerializableExtra("data");


        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        toolbar.setTitle(userDataModel.NAME);
        toolbar.setSubtitle(userDataModel.EMAIL);



    }
}