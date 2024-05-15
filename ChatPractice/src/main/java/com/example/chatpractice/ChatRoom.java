package com.example.chatpractice;

public class ChatRoom {
    private String roomId;
    private String userId;
    private String nickname;
    private String lastMessage;
    private long lastMessageTime;

    public ChatRoom(String roomId, String userId, String nickname, String lastMessage, long lastMessageTime) {
        this.roomId = roomId;
        this.userId = userId;
        this.nickname = nickname;
        this.lastMessage = lastMessage;
        this.lastMessageTime = lastMessageTime;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    public long getLastMessageTime() {
        return lastMessageTime;
    }

    public void setLastMessageTime(long lastMessageTime) {
        this.lastMessageTime = lastMessageTime;
    }
}
