package team.nuga.thelabel.request;

import android.content.Context;

import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import okhttp3.HttpUrl;
import okhttp3.Request;
import team.nuga.thelabel.data.Contents;
import team.nuga.thelabel.data.NetworkResult;

/**
 * Created by Tacademy on 2016-08-31.
 */
public class ContentsRequest extends AbstractRequest<NetworkResult<Contents[]>> {

    Request request;
    public ContentsRequest(Context context, int page, int count ){
//        String path = "users?page="+page+"&count="+count;
        String path = "users?page=1&count=5";
        HttpUrl url = getBaseUrlBuilder()
                .addPathSegment("users")
                .addQueryParameter("page",page+"")
                .addQueryParameter("count",count+"")
                .build();
        request = new Request.Builder()
                .url(url)
                .tag(context)
                .build();
    }
    @Override
    protected Type getType() {
        return new TypeToken<NetworkResult<Contents[]>>(){}.getType();
    }

    @Override
    public Request getRequest() {
        return request;
    }
}
