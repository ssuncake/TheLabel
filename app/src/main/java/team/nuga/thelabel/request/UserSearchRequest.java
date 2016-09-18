package team.nuga.thelabel.request;

import android.content.Context;

import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import okhttp3.HttpUrl;
import okhttp3.Request;
import team.nuga.thelabel.data.NetworkResultUserSearch;

/**
 * Created by kuhyebin on 2016-09-18.
 */
public class UserSearchRequest extends AbstractRequest<NetworkResultUserSearch> {
    private static final String path ="users";
    Request request;
//    users?search=true&page=1&count=10&position_id=&genre_id=&city_id=&town_id=
    public UserSearchRequest(Context context,int page, int count, int position_id, int genre_id,int city_id, int town_id){
        HttpUrl url = getBaseUrlBuilder()
                .addPathSegment(path)
                .addQueryParameter("search","true")
                .addQueryParameter("page",""+page)
                .addQueryParameter("count",""+count)
                .addQueryParameter("position_id",""+position_id)
                .addQueryParameter("genre_id",""+genre_id)
                .addQueryParameter("city_id",""+city_id)
                .addQueryParameter("town_id",""+town_id)
                .build();
        request = new Request.Builder()
                .url(url)
                .tag(context)
                .build();
    }
    @Override
    protected Type getType() {
        return new TypeToken<NetworkResultUserSearch>(){}.getType();
    }

    @Override
    public Request getRequest() {
        return request;
    }
}
