package team.nuga.thelabel.request;

import android.content.Context;

import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import okhttp3.HttpUrl;
import okhttp3.Request;
import team.nuga.thelabel.data.NetworkResultMyLike;


/**
 * Created by Tacademy on 2016-08-30.
 */
public class MyLikeContentsRequest extends AbstractRequest<NetworkResultMyLike> {

    private static final String PATH = "likes";
    private static final String PATH2 = "me";

    Request request;
    public MyLikeContentsRequest(Context context, int page, int count) {
        HttpUrl url = getBaseUrlBuilder()
                .addPathSegment(PATH)
                .addPathSegment(PATH2)
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
        return new TypeToken<NetworkResultMyLike>(){}.getType();
    }

    @Override
    public Request getRequest() {
        return request;
    }
}
