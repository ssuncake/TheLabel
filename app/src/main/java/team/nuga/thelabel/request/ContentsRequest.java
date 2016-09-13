package team.nuga.thelabel.request;

import android.content.Context;

import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import okhttp3.HttpUrl;
import okhttp3.Request;
import team.nuga.thelabel.data.NetworkResultMyAccount;

/**
 * Created by Tacademy on 2016-08-31.
 */
public class ContentsRequest extends AbstractRequest<NetworkResultMyAccount> {

    Request request;
    public ContentsRequest(Context context, String page, String count ){
//        String path = "users?page="+page+"&count="+count;
        String path = "users/me?page=1&count=5";
        HttpUrl url = getBaseUrlBuilder()
                .addPathSegment("users")
                .addPathSegment("me")
                .addQueryParameter("page",page)
                .addQueryParameter("count",count)
                .build();
        request = new Request.Builder()
                .url(url)
                .tag(context)
                .build();
    }
    @Override
    protected Type getType() {
        return new TypeToken<NetworkResultMyAccount>(){}.getType();
    }

    @Override
    public Request getRequest() {
        return request;
    }
}
