package team.nuga.thelabel.request;

import android.content.Context;

import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import okhttp3.HttpUrl;
import okhttp3.Request;
import team.nuga.thelabel.data.Label;
import team.nuga.thelabel.data.NetworkResult;


/**
 * Created by Tacademy on 2016-08-30.
 */
public class LabelSelectRequest extends AbstractRequest<NetworkResult<Label[]>> {

    private static final String PATH = "labels";

    Request request;
    public LabelSelectRequest(Context context) {
        HttpUrl url = getBaseUrlBuilder()
                .addPathSegments(PATH)
                .build();


        request = new Request.Builder()
                .url(url)
                .tag(context)
                .build();
    }



    @Override
    protected Type getType() {
        return new TypeToken<NetworkResult<Label[]>>(){}.getType();
    }

    @Override
    public Request getRequest() {
        return request;
    }
}
