package com.example.chatpractice;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ChatListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ChatListAdapter adapter;
    private List<ChatRoom> chatRooms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_list);

        // RecyclerView 초기화
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // 가상의 채팅방 목록 생성 (실제 데이터는 서버에서 가져와야 함)
        chatRooms = createChatRooms();

        // Adapter 설정
        adapter = new ChatListAdapter(chatRooms);
        recyclerView.setAdapter(adapter);

    }

    // 가상의 채팅방 목록 생성 (임시 데이터)
    private List<ChatRoom> createChatRooms() {
        List<ChatRoom> rooms = new ArrayList<>();

        // 각 채팅방에 대한 정보를 명시적으로 지정하여 ChatRoom 객체 생성
        rooms.add(new ChatRoom("1", "userId1", "User1", "Hello!", System.currentTimeMillis()));
        rooms.add(new ChatRoom("2", "userId2", "User2", "Hi!", System.currentTimeMillis()));
        rooms.add(new ChatRoom("3", "userId3", "User3", "Hey!", System.currentTimeMillis()));

        return rooms;
    }

}
