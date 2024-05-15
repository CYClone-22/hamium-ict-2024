package com.example.chatpractice;

import java.util.Date;

public class Chat {
    Date date;
    String id;
    String nickname;
    String message;
    int readNumbs;

    public Chat(Date date, String id, String nickname, String message, int readNumbs) {
        this.date = date;
        this.id = id;
        this.nickname = nickname;
        this.message = message;
        this.readNumbs = readNumbs;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getReadNumbs() {
        return readNumbs;
    }

    public void setReadNumbs(int readNumbs) {
        this.readNumbs = readNumbs;
    }
}
