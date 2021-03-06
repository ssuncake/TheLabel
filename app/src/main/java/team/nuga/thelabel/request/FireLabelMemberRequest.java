package team.nuga.thelabel.request;

import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Request;
import okhttp3.RequestBody;
import team.nuga.thelabel.data.NetworkResult;


/**
 * Created by Tacademy on 2016-08-30.
 */
public class FireLabelMemberRequest extends AbstractRequest<NetworkResult> {

    private static final String FIRE= "labels";

    Request request;
    public FireLabelMemberRequest(int label_id, int user_id) {
        HttpUrl url = getBaseUrlBuilder()
                .addPathSegment(FIRE)
                .addPathSegment(""+label_id)
                .addQueryParameter("members","true")
                .build();
        RequestBody body = new FormBody.Builder()
                .add("user_id", ""+user_id)
                .build();

        request = new Request.Builder()
                .url(url)
                .delete(body)
                .build();
    }



    @Override
    protected Type getType() {
        return new TypeToken<NetworkResult>(){}.getType();
    }

    @Override
    public Request getRequest() {
        return request;
    }
}
