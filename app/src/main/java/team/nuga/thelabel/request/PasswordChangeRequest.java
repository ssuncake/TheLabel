package team.nuga.thelabel.request;

import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Request;
import okhttp3.RequestBody;
import team.nuga.thelabel.data.NetworkResult;
import team.nuga.thelabel.data.User;


/**
 * Created by Tacademy on 2016-08-30.
 */
public class PasswordChangeRequest extends AbstractRequest<NetworkResult> {

    private static final String USERS= "users";
    private static final String ME = "me";

    Request request;
    public PasswordChangeRequest(String password, String new_password) {
        HttpUrl url = getBaseUrlBuilder()
                .addPathSegment(USERS)
                .addPathSegment(ME)
                .addQueryParameter("pass","true")
                .build();
        RequestBody body = new FormBody.Builder()
                .add("password", password)
                .add("new_password", new_password)
                .build();

        request = new Request.Builder()
                .url(url)
                .put(body)
                .build();
    }



    @Override
    protected Type getType() {
        return new TypeToken<NetworkResult<User>>(){}.getType();
    }

    @Override
    public Request getRequest() {
        return request;
    }
}
