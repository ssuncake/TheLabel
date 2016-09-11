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
public class LabelSearchRequest extends AbstractRequest<NetworkResultLabelSearch> {
    private static final String path = "labels";
    Request request;
    public LabelSearchRequest(Context context, int page, int count, int position_id, int genre_id){

        HttpUrl url = getBaseUrlBuilder()
                .addPathSegment(path)
                .addQueryParameter("search", "true")
                .addQueryParameter("page",page+"")
                .addQueryParameter("count",count+"")
                .addQueryParameter("position_id",position_id+"")
                .addQueryParameter("genre_id",genre_id+"")
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
