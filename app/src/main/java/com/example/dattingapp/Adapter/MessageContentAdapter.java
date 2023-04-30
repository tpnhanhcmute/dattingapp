package com.example.dattingapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dattingapp.Models.MessageContent;
import com.example.dattingapp.Models.User;
import com.example.dattingapp.R;
import com.example.dattingapp.utils.SharedPreference;

import java.util.List;

public class MessageContentAdapter  extends RecyclerView.Adapter<MessageContentAdapter.MyViewHolder>{
    public final int MESSAGE_RECEIVER =1;
    public  final int MESSAGE_SENDER =2;
    Context context;
    List<MessageContent> messageContentList;

    public MessageContentAdapter(Context context, List<MessageContent> messageContentList) {
        this.context = context;
        this.messageContentList = messageContentList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= null;
        switch (viewType){
            case MESSAGE_RECEIVER:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.holder_chat_content_receiver,null);
                break;
            case MESSAGE_SENDER:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.holder_chat_content_sender,null);
                break;
        }
        MessageContentAdapter.MyViewHolder myViewHolder = new MessageContentAdapter.MyViewHolder(view,viewType);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        MessageContent content = messageContentList.get(position);
        holder.textViewContent.setText(content.content);
        if(holder.viewType == MESSAGE_SENDER)
        {
            if(content.isSending){
                holder.imgViewIsSending.setVisibility(View.VISIBLE);
            }else {
                holder.imgViewIsSending.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public int getItemCount() {
        return messageContentList==null?0:messageContentList.size();
    }

    @Override
    public int getItemViewType(int position) {
        // check if is user and type content
        User user = SharedPreference.getInstance(context).GetUser();
        MessageContent message = messageContentList.get(position);
        return message.senderID.equals(user.userID)?MESSAGE_SENDER:MESSAGE_RECEIVER;

    }
    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView textViewContent;
        public  TextView textViewDate;
        public  int viewType;
        public ImageView imgViewIsSending;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

        }
        public MyViewHolder(View view, int viewType) {
            super(view);
            this.viewType = viewType;
            textViewContent= view.findViewById(R.id.textViewContent);
            textViewDate = view.findViewById(R.id.textViewDate);
            imgViewIsSending = view.findViewById(R.id.imageViewSending);

        }
    }
}


