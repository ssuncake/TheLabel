package team.nuga.thelabel.request;

import android.content.Context;

import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import okhttp3.HttpUrl;
import okhttp3.Request;
import team.nuga.thelabel.data.NetworkResultLabelSearch;

/**
 * Created by Tacademy on 2016-09-09.
 */
public class TextSearchLabelRequest extends AbstractRequest<NetworkResultLabelSearch> {
    private static final String path = "labels";
    Request request;
    public TextSearchLabelRequest(Context context, int page, int count, String text, String sort){

        HttpUrl url = getBaseUrlBuilder()
                .addPathSegment(path)
                .addQueryParameter("namesearch", "true")
                .addQueryParameter("page", ""+page)
                .addQueryParameter("count", ""+count)
                .addQueryParameter("text",text)
                .addQueryParameter("sort",sort)
                .build();
        request = new Request.Builder()
                .url(url)
                .tag(context)
                .build();
            }


    @Override
    protected Type getType() {
        return new TypeToken<NetworkResultLabelSearch>(){}.getType();
    }

    @Override
    public Request getRequest() {
        return request;
    }
}
