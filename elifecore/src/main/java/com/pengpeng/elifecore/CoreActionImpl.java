package com.pengpeng.elifecore;

import com.pengpeng.elifedataapi.DataApi;
import com.pengpeng.elifedataapi.DataApiImpl;
import com.pengpeng.elifedataapi.DataApiResponse;
import com.pengpeng.elifemodel.Audio;
import com.pengpeng.elifemodel.UserAudioBehavior;

import java.util.List;

/**
 * Created by pengpeng on 16-1-13.
 */
public class CoreActionImpl implements CoreAction {

    private DataApi mDataApi;

    public CoreActionImpl() {
        mDataApi = new DataApiImpl();
    }

    @Override
    public void getAudioList(String userUUID, ResponseCallbackListener<List<Audio>> listener) {
        if (userUUID != null) {

            DataApiResponse<List<Audio>> response = mDataApi.getAudioListByUid(userUUID);
            if (listener != null && response != null) {
                if (response.isSuccess()) {
                    listener.onSuccess(response.getData());
                } else {
                    listener.onFailure(response.getError_code(), response.getReason());
                }
            }
        }
    }

    @Override
    public void getAudio(UserAudioBehavior uaBehavior, ResponseCallbackListener<Audio> listener) {
        if (uaBehavior != null) {
            DataApiResponse<Audio> response = mDataApi.getAudioById(uaBehavior);
            if (listener != null && response != null) {
                if (response.isSuccess()) {
                    listener.onSuccess(response.getData());
                } else {
                    listener.onFailure(response.getError_code(), response.getReason());
                }
            }

        }
    }

    @Override
    public Audio getAnswer(String audioId, String audioPartId, String userUUID, String userAnswer) {
        return null;
    }

    @Override
    public void sendState(String audioId, String userUUID, int isGood) {

    }
}
