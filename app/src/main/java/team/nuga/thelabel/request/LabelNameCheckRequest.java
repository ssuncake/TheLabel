package team.nuga.thelabel.request;

import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import okhttp3.HttpUrl;
import okhttp3.Request;
import team.nuga.thelabel.data.NetworkLabelNameCheck;


/**
 * Created by Tacademy on 2016-08-30.
 */
public class LabelNameCheckRequest extends AbstractRequest<NetworkLabelNameCheck> {

    private static final String PATH = "labels";

    Request request;
    public LabelNameCheckRequest(String labelName) {
        HttpUrl url = getBaseUrlBuilder()
                .addPathSegments(PATH)
                .addQueryParameter("dup","ture")
                .addQueryParameter("label_name",labelName)
                .build();
        request = new Request.Builder()
                .url(url)
                .build();
    }



    @Override
    protected Type getType() {
        return new TypeToken<NetworkLabelNameCheck>(){}.getType();
    }

    @Override
    public Request getRequest() {
        return request;
    }
}
