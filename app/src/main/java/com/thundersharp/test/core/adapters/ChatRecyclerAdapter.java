package com.thundersharp.test.core.adapters;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.thundersharp.test.R;
import com.thundersharp.test.core.model.ChatMessageModel;
import com.thundersharp.test.core.model.UserDataModel;

import java.util.List;

public class ChatRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int VIEW_TYPE_ME = 1;
    private static final int VIEW_TYPE_OTHER = 2;


    private Context context;
    private List<ChatMessageModel> messageModels;
    private int messageType;

    public ChatRecyclerAdapter(Context context, List<ChatMessageModel> messageModels, int messageType) {
        this.context = context;
        this.messageModels = messageModels;
        this.messageType = messageType;
    }

    public void addMessages(ChatMessageModel chatMessageModel){
        messageModels.add(chatMessageModel);
        notifyItemInserted(messageModels.size() -1);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);

        RecyclerView.ViewHolder viewHolder = null;
        switch (viewType){
            case VIEW_TYPE_ME:
                View viewChatMy = layoutInflater.inflate(R.layout.item_message_from_me,parent,false);
                viewHolder = new MyChatHolder(viewChatMy);
                break;
            case VIEW_TYPE_OTHER:
                View viewChatOther = layoutInflater.inflate(R.layout.item_message_to_me,parent,false);
                viewHolder = new OtherChatHolder(viewChatOther);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (TextUtils.equals(messageModels.get(position).getSenderUid(),FirebaseAuth.getInstance().getUid())){
            MyChatHolder myChatHolder = (MyChatHolder) holder;
            myChatHolder.message.setText(messageModels.get(position).getMessage());
            myChatHolder.name.setText(messageModels.get(position).getSenderName());
        }else {
            OtherChatHolder otherChatHolder = (OtherChatHolder) holder;
            otherChatHolder.messageOther.setText(messageModels.get(position).getMessage());
            otherChatHolder.nameOther.setText(messageModels.get(position).getSenderName());
        }
    }

    @Override
    public int getItemCount() {
        if (messageModels != null) return messageModels.size();else return 0;
    }

    @Override
    public int getItemViewType(int position) {
        if (messageModels.get(position).getSenderUid().equals(FirebaseAuth.getInstance().getUid())){
            return VIEW_TYPE_ME;
        }else return VIEW_TYPE_OTHER;
    }

    class MyChatHolder extends RecyclerView.ViewHolder {

        TextView name,message;
        public MyChatHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            message = itemView.findViewById(R.id.message);

        }
    }

    class OtherChatHolder extends RecyclerView.ViewHolder {
        TextView nameOther,messageOther;

        public OtherChatHolder(@NonNull View itemView) {
            super(itemView);
            nameOther = itemView.findViewById(R.id.userName);
            messageOther = itemView.findViewById(R.id.message);
        }
    }
}
