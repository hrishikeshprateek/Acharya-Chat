package com.thundersharp.test.core.adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.thundersharp.test.R;
import com.thundersharp.test.core.model.LastMessageModel;
import com.thundersharp.test.core.model.UserDataModel;
import com.thundersharp.test.ui.ChatActivity;

import java.util.List;

public class RecentChatAdapter extends RecyclerView.Adapter<RecentChatAdapter.ViewHolder>{

    private List<LastMessageModel> dataModelList;

    public RecentChatAdapter(List<LastMessageModel> dataModelList) {
        this.dataModelList = dataModelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.holder_all_users,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        LastMessageModel userDataModel = dataModelList.get(position);
        holder.email.setText("Message: "+userDataModel.MESSAGE);
        //holder.profile_pic.setText(userDataModel.NAME.substring(0,1).toUpperCase());
    }

    @Override
    public int getItemCount() {
        if (dataModelList != null) return dataModelList.size();
        else return 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView name,email,profile_pic;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.name);
            email = itemView.findViewById(R.id.email);
            profile_pic = itemView.findViewById(R.id.profile_pic);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String uid1 = dataModelList.get(getAdapterPosition()).KEY.substring(0,dataModelList.get(getAdapterPosition()).KEY.indexOf("_"));
                    String uid2 = dataModelList.get(getAdapterPosition()).KEY.substring(dataModelList.get(getAdapterPosition()).KEY.indexOf("_")+1);

                    if (uid1.equals(FirebaseAuth.getInstance().getUid())){
                        itemView.getContext().startActivity(new Intent(itemView.getContext(), ChatActivity.class).putExtra("data",new UserDataModel("","",uid2)));
                    }else if (uid2.equals(FirebaseAuth.getInstance().getUid())){
                        itemView.getContext().startActivity(new Intent(itemView.getContext(), ChatActivity.class).putExtra("data",new UserDataModel("","",uid1)));

                    }else {
                        Toast.makeText(itemView.getContext(), "ERROR CANNOT START CHAT",Toast.LENGTH_LONG).show();
                    }

                    Toast.makeText(itemView.getContext(), uid1+"\n"+uid2, Toast.LENGTH_SHORT).show();
                    //
                }
            });

        }
    }
}
