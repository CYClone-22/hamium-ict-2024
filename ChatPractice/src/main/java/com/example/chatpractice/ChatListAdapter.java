package com.example.chatpractice;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ChatListAdapter extends RecyclerView.Adapter<ChatListAdapter.ChatRoomViewHolder> {

    private List<ChatRoom> chatRooms;

    public ChatListAdapter(List<ChatRoom> chatRooms) {
        this.chatRooms = chatRooms;
    }

    @NonNull
    @Override
    public ChatRoomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat_room, parent, false);
        return new ChatRoomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatRoomViewHolder holder, int position) {
        ChatRoom chatRoom = chatRooms.get(position);
        holder.bind(chatRoom);
    }

    @Override
    public int getItemCount() {
        return chatRooms.size();
    }

    static class ChatRoomViewHolder extends RecyclerView.ViewHolder {

        private TextView textViewUser;

        public ChatRoomViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewUser = itemView.findViewById(R.id.textViewUser);
        }

        public void bind(ChatRoom chatRoom) {
            // 사용자 정보 설정
            String userInfo = chatRoom.getNickname() + " (" + chatRoom.getUserId() + ")";
            textViewUser.setText(userInfo);
            // 채팅방 클릭 이벤트 등 필요한 작업 수행
        }
    }
}
