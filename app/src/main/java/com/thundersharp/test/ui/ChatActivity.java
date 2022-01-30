package com.thundersharp.test.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.thundersharp.test.R;
import com.thundersharp.test.core.adapters.ChatRecyclerAdapter;
import com.thundersharp.test.core.model.ChatMessageModel;
import com.thundersharp.test.core.model.UserDataModel;
import com.thundersharp.test.core.util.Constants;

import java.util.ArrayList;

public class ChatActivity extends AppCompatActivity {

    UserDataModel userDataModel;
    Toolbar toolbar;

    private AppCompatImageButton sendMessage;
    private EditText message;
    private RecyclerView chatHolder;

    private ChildEventListener childEventListener;
    private ChatRecyclerAdapter recyclerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        userDataModel = (UserDataModel)getIntent().getSerializableExtra("data");


        toolbar = findViewById(R.id.toolbar);
        sendMessage = findViewById(R.id.buttonSend);
        message = findViewById(R.id.message);
        chatHolder = findViewById(R.id.chatHolder);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        toolbar.setTitle(userDataModel.NAME);
        toolbar.setSubtitle(userDataModel.EMAIL);

        sendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (message.getText().toString().trim().isEmpty()){
                    Toast.makeText(ChatActivity.this, "Cannot send empty messages", Toast.LENGTH_SHORT).show();
                }else{
                    String name;
                    if (FirebaseAuth.getInstance().getCurrentUser().getDisplayName() == null){
                        name = FirebaseAuth.getInstance().getCurrentUser().getEmail();
                    }else name = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();

                    ChatMessageModel chatMessageModel = new ChatMessageModel(String.valueOf(System.currentTimeMillis()),
                            message.getText().toString().trim(),
                            userDataModel.NAME,
                            userDataModel.UID,
                            name,
                            FirebaseAuth.getInstance().getUid());

                    sendMessageToFirebase(chatMessageModel);
                    message.setText(null);
                }
            }
        });

        retrieveMessageFromFirebase(FirebaseAuth.getInstance().getUid(),userDataModel.UID);

    }

    private void retrieveMessageFromFirebase(String sendersUid,String recieversUid){
        String token1 = sendersUid+"_"+recieversUid;
        String token2 = recieversUid+"_"+sendersUid;

        FirebaseDatabase
                .getInstance()
                .getReference(Constants.DATABASE_NODE_CHAT_NODE)
                .getRef()
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.hasChild(token1)){

                            FirebaseDatabase
                                    .getInstance()
                                    .getReference(Constants.DATABASE_NODE_CHAT_NODE)
                                    .child(token1)
                                    .addChildEventListener(new ChildEventListener() {
                                        @Override
                                        public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                                            if (snapshot.exists()){
                                                if (recyclerAdapter == null) {
                                                    recyclerAdapter = new ChatRecyclerAdapter(ChatActivity.this, new ArrayList<>(), 0);
                                                    recyclerAdapter.addMessages(snapshot.getValue(ChatMessageModel.class));
                                                    chatHolder.setAdapter(recyclerAdapter);
                                                }else {
                                                    recyclerAdapter.addMessages(snapshot.getValue(ChatMessageModel.class));
                                                    recyclerAdapter.notifyDataSetChanged();
                                                }

                                            }
                                        }

                                        @Override
                                        public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                                        }

                                        @Override
                                        public void onChildRemoved(@NonNull DataSnapshot snapshot) {

                                        }

                                        @Override
                                        public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {

                                        }
                                    });

                        }else if (snapshot.hasChild(token2)){

                            FirebaseDatabase
                                    .getInstance()
                                    .getReference(Constants.DATABASE_NODE_CHAT_NODE)
                                    .child(token2)
                                    .addChildEventListener(new ChildEventListener() {
                                        @Override
                                        public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                                            if (snapshot.exists()){
                                                if (recyclerAdapter == null) {
                                                    recyclerAdapter = new ChatRecyclerAdapter(ChatActivity.this, new ArrayList<>(), 0);
                                                    recyclerAdapter.addMessages(snapshot.getValue(ChatMessageModel.class));
                                                    chatHolder.setAdapter(recyclerAdapter);
                                                }else {
                                                    recyclerAdapter.addMessages(snapshot.getValue(ChatMessageModel.class));
                                                    recyclerAdapter.notifyDataSetChanged();
                                                }
                                            }
                                        }

                                        @Override
                                        public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                                        }

                                        @Override
                                        public void onChildRemoved(@NonNull DataSnapshot snapshot) {

                                        }

                                        @Override
                                        public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {

                                        }
                                    });


                        }else {
                            Toast.makeText(ChatActivity.this, "No messages till now, Start some conversation first !", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(ChatActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

    }

    private void sendMessageToFirebase(ChatMessageModel chatMessageModel) {
        String tokenType1 = chatMessageModel.getSenderUid()+"_"+chatMessageModel.getRecieverUid();
        String tokenType2 = chatMessageModel.getRecieverUid()+"_"+chatMessageModel.getSenderUid();

        FirebaseDatabase
                .getInstance()
                .getReference(Constants.DATABASE_NODE_CHAT_NODE)
                .getRef()
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.hasChild(tokenType1)){
                            FirebaseDatabase
                                    .getInstance()
                                    .getReference(Constants.DATABASE_NODE_CHAT_NODE)
                                    .child(tokenType1)
                                    .child(chatMessageModel.getKey())
                                    .setValue(chatMessageModel);
                        }else if (snapshot.hasChild(tokenType2)){
                            FirebaseDatabase
                                    .getInstance()
                                    .getReference(Constants.DATABASE_NODE_CHAT_NODE)
                                    .child(tokenType2)
                                    .child(chatMessageModel.getKey())
                                    .setValue(chatMessageModel);

                        }else {
                            FirebaseDatabase
                                    .getInstance()
                                    .getReference(Constants.DATABASE_NODE_CHAT_NODE)
                                    .child(tokenType1)
                                    .child(chatMessageModel.getKey())
                                    .setValue(chatMessageModel);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(ChatActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}