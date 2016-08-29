package team.nuga.thelabel.request;

import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.ResponseBody;
import team.nuga.thelabel.manager.NetworkRequest;

/**
 * Created by Tacademy on 2016-08-29.
 */
public abstract class AbstractRequest<T> extends NetworkRequest<T> {


    private static final String SERVERADRESS = "ec2-52-78-137-47.ap-northeast-2.compute.amazonaws.com:4433";

    protected HttpUrl.Builder getBaseUrlBuilder(){
        HttpUrl.Builder builder = new HttpUrl.Builder();
        builder.scheme("https");
        builder.host(SERVERADRESS);
        return builder;
    }

    @Override
    protected T parse(ResponseBody body) throws IOException {
        String text = body.string();
        Gson gson = new Gson();
        // 0829

    }
}
