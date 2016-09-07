package team.nuga.thelabel.request;

import android.content.Context;

import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.lang.reflect.Type;

import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import team.nuga.thelabel.data.NetworkResult;
import team.nuga.thelabel.data.User;


/**
 * Created by Tacademy on 2016-08-30.
 */
public class SignUpRequest extends AbstractRequest<NetworkResult<User>> {

    private static final String SIGNUP_REQUSET = "users";
MediaType jpeg = MediaType.parse("image/jpeg");
    Request request;
    public SignUpRequest(Context context, String email, String password, String nickname,
                         int gender_id, int position_id, int genre_id, int city_id, int town_id,File file) {
        HttpUrl url = getBaseUrlBuilder()
                .addPathSegment(SIGNUP_REQUSET)
                .build();

        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("email", email)
                .addFormDataPart("password", password)
                .addFormDataPart("nickname",nickname)
                .addFormDataPart("gender", String.valueOf(gender_id))
                .addFormDataPart("position_id", String.valueOf(position_id))
                .addFormDataPart("genre_id", String.valueOf(genre_id))
                .addFormDataPart("city_id", String.valueOf(city_id))
                .addFormDataPart("town_id", String.valueOf(town_id));
        if(file != null){
            builder.addFormDataPart("imagepath",file.getPath(),
                    RequestBody.create(jpeg,file));
        }
        RequestBody body = builder.build();
        request = new Request.Builder()
                .url(url)
                .post(body)
                .tag(context)
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
