package team.nuga.thelabel.request;

import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import okhttp3.HttpUrl;
import okhttp3.Request;
import team.nuga.thelabel.data.NetworkResult;
import team.nuga.thelabel.data.User;


/**
 * Created by Tacademy on 2016-08-30.
 */
public class LabelGetFireMemberRequest extends AbstractRequest<NetworkResult<User[]>> {

    private static final String PATH = "labels";

    Request request;
    public LabelGetFireMemberRequest(int labelId) {
        HttpUrl url = getBaseUrlBuilder()
                .addPathSegments(PATH)
                .addPathSegment(":"+labelId)
                .addQueryParameter("del","ture")
                .build();
        request = new Request.Builder()
                .url(url)
                .build();
    }



    @Override
    protected Type getType() {
        return new TypeToken<NetworkResult<User[]>>(){}.getType();
    }

    @Override
    public Request getRequest() {
        return request;
    }
}
