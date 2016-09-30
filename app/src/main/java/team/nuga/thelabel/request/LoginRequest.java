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
public class LoginRequest extends AbstractRequest<NetworkResult<User>> {

    private static final String LOGINADDRESS = "auth/local/login";

    Request request;

    /**
     *
     * @param email 로그인 유저 아이디
     * @param password 로그인 유저의 패스워드
     * @param regid 로그인할 유저의 registration ID
     */
    public LoginRequest(Context context,String email,String password,String regid) {
        HttpUrl url = getBaseUrlBuilder()
                .addPathSegments(LOGINADDRESS)
                .build();
        RequestBody body = new FormBody.Builder()
                .add("email", email)
                .add("password", password)
                .add("regId",regid)
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
