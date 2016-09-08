package team.nuga.thelabel.request;

import android.content.Context;

import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import okhttp3.HttpUrl;
import okhttp3.Request;
import team.nuga.thelabel.data.NetworkResult;

/**
 * Created by Tacademy on 2016-08-31.
 */
public class LogoutRequest extends AbstractRequest<NetworkResult> {
    Request request;
    public LogoutRequest(Context context){
        HttpUrl url = getBaseUrlBuilder()
                .addPathSegment("auth")
                .addPathSegment("local")
                .addPathSegment("logout")
                .build();
        request = new Request.Builder()
                .url(url)
                .tag(context)
                .build();
    }
    @Override
    protected Type getType() {
        return new TypeToken<NetworkResult>(){}.getType();
    }

    @Override
    public Request getRequest() {
        return request;
    }
}
