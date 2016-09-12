package team.nuga.thelabel.request;

import android.content.Context;

import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import okhttp3.HttpUrl;
import okhttp3.Request;
import team.nuga.thelabel.data.NewsFeedContents;


/**
 * Created by Tacademy on 2016-08-30.
 */
public class NewsFeedRequest extends AbstractRequest<NewsFeedContents> {

    private static final String PATH = "posts";

    Request request;
    public NewsFeedRequest(Context context, int page, int count, int meet) {
        HttpUrl url = getBaseUrlBuilder()
                .addPathSegments(PATH)
                .addQueryParameter("page",page+"")
                .addQueryParameter("count",count+"")
                .addQueryParameter("meet",meet+"")
                .build();


        request = new Request.Builder()
                .url(url)
                .tag(context)
                .build();
    }



    @Override
    protected Type getType() {
        return new TypeToken<NewsFeedContents>(){}.getType();
    }

    @Override
    public Request getRequest() {
        return request;
    }
}
