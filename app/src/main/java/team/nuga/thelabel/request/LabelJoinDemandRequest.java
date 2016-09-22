package team.nuga.thelabel.request;

import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import okhttp3.HttpUrl;
import okhttp3.Request;
import team.nuga.thelabel.data.NetworkResultSimpleMessage;


/**
 * Created by Tacademy on 2016-08-30.
 */
public class LabelJoinDemandRequest extends AbstractRequest<NetworkResultSimpleMessage> {

    private static final String PATH = "";

    Request request;
    public LabelJoinDemandRequest(int userId,int labelId,int labelleaderId) {
        HttpUrl url = getBaseUrlBuilder()
                .addPathSegment(PATH)
                .build();
        request = new Request.Builder()
                .url(url)
                .build();
    }



    @Override
    protected Type getType() {
        return new TypeToken<NetworkResultSimpleMessage>(){}.getType();
    }

    @Override
    public Request getRequest() {
        return request;
    }
}
