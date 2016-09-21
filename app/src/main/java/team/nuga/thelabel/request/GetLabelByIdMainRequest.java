package team.nuga.thelabel.request;

import android.content.Context;

import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import okhttp3.HttpUrl;
import okhttp3.Request;
import team.nuga.thelabel.data.NetworkResultLabeMain;


/**
 * Created by Tacademy on 2016-08-30.
 */
public class GetLabelByIdMainRequest extends AbstractRequest<NetworkResultLabeMain> {

    private static final String PATH = "labels/";
    private static final String PATH2 = ":label_id";

    Request request;
    public GetLabelByIdMainRequest(Context context, int id,int page,int count) {
        HttpUrl url = getBaseUrlBuilder()
                .addPathSegments(PATH+id)
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
        return new TypeToken<NetworkResultLabeMain>(){}.getType();
    }

    @Override
    public Request getRequest() {
        return request;
    }
}
