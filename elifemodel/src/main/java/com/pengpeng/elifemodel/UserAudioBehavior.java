package com.pengpeng.elifemodel;

/**
 * Created by pengpeng on 16-1-12.
 */
public class UserAudioBehavior extends EModel{

    private String audioId;
    private String userUUID;
    private int isGood;             //是否点赞，1表示点赞，0表示不点赞
    private String userAnswer;      //用户的练习答案

    public String getAudioId() {
        return audioId;
    }

    public void setAudioId(String audioId) {
        this.audioId = audioId;
    }

    public String getUserUUID() {
        return userUUID;
    }

    public void setUserUUID(String userUUID) {
        this.userUUID = userUUID;
    }

    public int getIsGood() {
        return isGood;
    }

    public void setIsGood(int isGood) {
        this.isGood = isGood;
    }

    public String getUserAnswer() {
        return userAnswer;
    }

    public void setUserAnswer(String userAnswer) {
        this.userAnswer = userAnswer;
    }


}
