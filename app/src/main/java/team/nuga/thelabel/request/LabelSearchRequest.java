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
    Request request;
    public LabelSearchRequest(Context context, int page, int count, int search, int position_id, int genre_id){
        String path = "labels?search=true&page=1&count=10&position_id=&genre_id=";
        HttpUrl url = getBaseUrlBuilder()
                .addPathSegment("labels")
                .addPathSegment("search")
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
