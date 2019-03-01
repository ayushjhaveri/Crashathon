package com.example.ayush.crashathon;

public class Data {
private String position;
private String nickname;
private String score;

    public Data(String position, String nickname, String score) {
        this.position = position;
        this.nickname = nickname;
        this.score = score;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }
}
