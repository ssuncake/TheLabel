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
public class MessageRequest extends AbstractRequest<NetworkResult<User>> {


    Request request;
    public MessageRequest(Context context,int senderId,int receiverId,String message) {
        HttpUrl url = getBaseUrlBuilder()
                .addPathSegments("messages")
                .build();
        RequestBody body = new FormBody.Builder()
                .add("user_id", receiverId+"")
                .add("message", message)
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
