package team.nuga.thelabel.request;

import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import okhttp3.HttpUrl;
import okhttp3.Request;
import team.nuga.thelabel.data.NetworkResultLabelMemberManage;


/**
 * Created by Tacademy on 2016-08-30.
 */
public class LabelGetFireMemberRequest extends AbstractRequest<NetworkResultLabelMemberManage> {

    private static final String PATH = "labels";

    Request request;

    /**
     * @param labelId Currently in label ID // 현재 들어가있는 레이블 아이디 값
     */
    public LabelGetFireMemberRequest(int labelId) {
        HttpUrl url = getBaseUrlBuilder()
                .addPathSegment(PATH)
                .addPathSegment(labelId+"")
                .addQueryParameter("del","true")
                .build();
        request = new Request.Builder()
                .url(url)
                .build();
    }



    @Override
    protected Type getType() {
        return new TypeToken<NetworkResultLabelMemberManage>(){}.getType();
    }

    @Override
    public Request getRequest() {
        return request;
    }
}
