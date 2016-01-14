package com.pengpeng.elifedataapi;

import com.pengpeng.elifemodel.Audio;
import com.pengpeng.elifemodel.UserAudioBehavior;

import java.util.List;

/**
 * Created by pengpeng on 16-1-12.
 */
public interface DataApi {

    public DataApiResponse<List<Audio>> getAudioListByUid(String userUUID);

    public DataApiResponse<Audio> getAudioById(UserAudioBehavior uaBehavior);

    public DataApiResponse<Audio> getAnswerByUAnswer(String audioId, String audioPartId, String userUUID, String userAnswer);

    public DataApiResponse<Void> sendGoodState(String audioId, String userUUID, int isGood);
}
