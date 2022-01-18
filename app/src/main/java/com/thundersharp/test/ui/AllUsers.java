package com.thundersharp.test.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.thundersharp.test.R;
import com.thundersharp.test.core.adapters.AllUsersAdapter;
import com.thundersharp.test.core.model.UserDataModel;
import com.thundersharp.test.core.util.Constants;

import java.util.ArrayList;
import java.util.List;


public class AllUsers extends Fragment {

    private RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_all_users, container, false);

        recyclerView = view.findViewById(R.id.recycler_view);
        List<UserDataModel> userDataModels = new ArrayList<>();

        FirebaseDatabase
                .getInstance()
                .getReference(Constants.DATABASE_NODE_USERS) // /USERS/
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()){
                            for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                                UserDataModel userDataModel = dataSnapshot.getValue(UserDataModel.class);
                                if (!FirebaseAuth.getInstance().getUid().equals(userDataModel.UID)){
                                    userDataModels.add(userDataModel);
                                }

                            }
                            AllUsersAdapter allUsersAdapter = new AllUsersAdapter(userDataModels);
                            recyclerView.setAdapter(allUsersAdapter);
                        }else
                            Toast.makeText(getActivity(), "ERROR : NO USERS FOUND", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        return view;
    }
}