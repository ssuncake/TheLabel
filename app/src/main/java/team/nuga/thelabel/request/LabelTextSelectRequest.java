package team.nuga.thelabel.request;

import android.content.Context;

import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import okhttp3.HttpUrl;
import okhttp3.Request;
import team.nuga.thelabel.data.NetworkResultLabelSearch;

/**
 * Created by kuhyebin on 2016-09-14.
 */
public class LabelTextSelectRequest extends AbstractRequest<NetworkResultLabelSearch> {
//    abels?nameSearch=true&page=1&count=10&text=&sort=
    private static final String path = "labels";
    Request request;
    public LabelTextSelectRequest(Context context, int page, int count, String text, String sort){
        HttpUrl url = getBaseUrlBuilder()
                .addPathSegment(path)
                .addQueryParameter("nameSearch","true")
                .addQueryParameter("page",""+page)
                .addQueryParameter("count",""+count)
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
