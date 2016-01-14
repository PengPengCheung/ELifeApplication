package com.pengpeng.elifedataapi;

import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;
import com.pengpeng.elifedataapi.NetUtils.CommonResource;
import com.pengpeng.elifedataapi.NetUtils.HttpUtils;
import com.pengpeng.elifemodel.Audio;
import com.pengpeng.elifemodel.UserAudioBehavior;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by pengpeng on 16-1-12.
 */
public class DataApiImpl implements DataApi {




    @Override
    public DataApiResponse<List<Audio>> getAudioListByUid(String userUUID) {
        return null;
    }

    @Override
    public DataApiResponse<Audio> getAudioById(UserAudioBehavior uaBehavior) {

        HttpUtils mHttpUtils = HttpUtils.getInstance();

        //用DataApiResponse<Audio>作为接收返回数据的类，是DataApiResponse<Audio>类型而不是DataApiResponse<UserAudioBehavior>
        //此时的Type类型为DataApiResponse<Audio>, 要求与返回的数据类型要一致，而不是按传入的数据
        Type type = new TypeToken<DataApiResponse<Audio>>(){}.getType();

        try{
            return mHttpUtils.handlePost(CommonResource.Path.testPath, uaBehavior, type);
        }catch(JsonParseException ex){
            return new DataApiResponse<Audio>(CommonResource.Event.DATA_WRONG_EVENT, CommonResource.Event.DATA_WRONG_EVENT_MSG);
        }
    }

    @Override
    public DataApiResponse<Audio> getAnswerByUAnswer(String audioId, String audioPartId, String userUUID, String userAnswer) {
        return null;
    }

    @Override
    public DataApiResponse<Void> sendGoodState(String audioId, String userUUID, int isGood) {
        return null;
    }
}
