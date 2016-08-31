package team.nuga.thelabel.request;

import android.content.Context;

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
public class SignUpRequest extends AbstractRequest<NetworkResult<User>> {

    private static final String SIGNUP_REQUSET = "/users";

    Request request;
    public SignUpRequest(Context context, String email, String password, String nickname,
                         int gender_id, int position_id, int genre_id, int city_id, int town_id) {
        HttpUrl url = getBaseUrlBuilder()
                .addPathSegments(SIGNUP_REQUSET)
                .build();
        RequestBody body = new FormBody.Builder()
                .add("email", email)
                .add("password", password)
                .add("nickname",nickname)
                .add("gender_id", ""+gender_id)
                .add("position_id",""+position_id)
                .add("genre_id",""+genre_id)
                .add("city_id", ""+city_id)
                .add("town_id", ""+town_id)
                .build();

        request = new Request.Builder()
                .url(url)
                .post(body)
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
