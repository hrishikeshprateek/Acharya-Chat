package com.thundersharp.test.core.adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.thundersharp.test.R;
import com.thundersharp.test.core.model.UserDataModel;
import com.thundersharp.test.ui.ChatActivity;

import org.w3c.dom.Text;

import java.util.List;

public class AllUsersAdapter extends RecyclerView.Adapter<AllUsersAdapter.ViewHolder>{

    private List<UserDataModel> dataModelList;

    public AllUsersAdapter(List<UserDataModel> dataModelList) {
        this.dataModelList = dataModelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.holder_all_users,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        UserDataModel userDataModel = dataModelList.get(position);
        holder.email.setText(userDataModel.EMAIL);
        holder.name.setText(userDataModel.NAME);
        holder.profile_pic.setText(userDataModel.NAME.substring(0,1).toUpperCase());
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
                    itemView.getContext().startActivity(new Intent(itemView.getContext(), ChatActivity.class).putExtra("data",dataModelList.get(getAdapterPosition())));
                }
            });

        }
    }
}
