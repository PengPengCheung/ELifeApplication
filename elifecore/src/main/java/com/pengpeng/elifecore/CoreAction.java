package com.pengpeng.elifecore;

import com.pengpeng.elifemodel.Audio;
import com.pengpeng.elifemodel.UserAudioBehavior;

import java.util.List;

/**
 * Created by pengpeng on 16-1-13.
 */
public interface CoreAction {
    /**
     * @param userUUID 用户手机标识号
     * @return 通过手机标识号获取的某一用户的音频列表，返回前端显示
     */
    public List<Audio> getAudioList(String userUUID);

    /**
     *
     * @param uaBehavior  用户与音频的行为模型，主要用于传递手机标识号和用户在主页点击的音频的id
     * @return  对应id的音频
     */
    public void getAudio(UserAudioBehavior uaBehavior, ResponseCallbackListener listener);

    /**
     *
     * @param audioId   用户所听的当前音频id
     * @param audioPartId   用户所听音频的当前部分
     * @param userUUID  用户的手机标识号
     * @param userAnswer    用户答案
     * @return  标准答案
     */
    public Audio getAnswer(String audioId, String audioPartId, String userUUID, String userAnswer);

    /**
     *
     * @param audioId   用户所听的当前音频id
     * @param userUUID  用户手机标识号
     * @param isGood    用户对此音频的点赞/收藏状态
     */
    public void sendState(String audioId, String userUUID, int isGood);
}
