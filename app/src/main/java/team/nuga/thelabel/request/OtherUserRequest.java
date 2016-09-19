package team.nuga.thelabel.request;

import android.content.Context;

import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import okhttp3.HttpUrl;
import okhttp3.Request;
import team.nuga.thelabel.data.NetworkResultOtherUser;

/**
 * Created by Tacademy on 2016-09-19.
 */
public class OtherUserRequest extends AbstractRequest<NetworkResultOtherUser>{
//    users/:id?page=1&count=5
    private static final String path = "users";
    Request request;
    public OtherUserRequest(Context context, int id, int page, int count){
        HttpUrl url = getBaseUrlBuilder()
                .addPathSegment(path)
                .addPathSegment(id+"")
                .addQueryParameter("page",""+page)
                .addQueryParameter("count",""+count)
                .build();
        request = new Request.Builder()
                .url(url)
                .tag(context)
                .build();
    }
    @Override
    protected Type getType() {
        return new TypeToken<NetworkResultOtherUser>(){}.getType();
    }

    @Override
    public Request getRequest() {
        return request;
    }
}
