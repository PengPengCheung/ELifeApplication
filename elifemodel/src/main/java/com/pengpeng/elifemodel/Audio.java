package com.pengpeng.elifemodel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pengpeng on 16-1-12.
 */
public class Audio extends EModel{
    private String audioId;         //音频id
    private String audioTitle;      //音频标题
    private String audioImageUrl;   //音频图片资源url，来自服务器而不是网络
    private String audioDate;       //音频发布日期
    private String audioUrl;        //音频资源url，来自服务器而不是网络
    private String audioText;       //音频文本
    private List<Integer> audioPartEndTime;        //音频各部分的开始时间，其列表的下表即音频的分段编号，列表长度即切割成的段数，暂时的段数设为3段

    public String getAudioId() {
        return audioId;
    }

    public void setAudioId(String audioId) {
        this.audioId = audioId;
    }

    public String getAudioTitle() {
        return audioTitle;
    }

    public void setAudioTitle(String audioTitle) {
        this.audioTitle = audioTitle;
    }

    public String getAudioImageUrl() {
        return audioImageUrl;
    }

    public void setAudioImageUrl(String audioImageUrl) {
        this.audioImageUrl = audioImageUrl;
    }

    public String getAudioDate() {
        return audioDate;
    }

    public void setAudioDate(String audioDate) {
        this.audioDate = audioDate;
    }

    public String getAudioUrl() {
        return audioUrl;
    }

    public void setAudioUrl(String audioUrl) {
        this.audioUrl = audioUrl;
    }

    public String getAudioText() {
        return audioText;
    }

    public void setAudioText(String audioText) {
        this.audioText = audioText;
    }


    public List<Integer> getAudioPartEndTime() {
        return audioPartEndTime;
    }

    public void setAudioPartEndTime(ArrayList<Integer> audioPartEndTime) {
        this.audioPartEndTime = audioPartEndTime;
    }

}
