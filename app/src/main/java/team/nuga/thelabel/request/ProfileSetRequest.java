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


/**
 * Created by Tacademy on 2016-08-30.
 */
public class ProfileSetRequest extends AbstractRequest<NetworkResult> {

    private static final String PROFILESET = "users";
MediaType jpeg = MediaType.parse("image/jpeg");
    Request request;
    public ProfileSetRequest(Context context, String nickname,int gender_id, int position_id,
                             int genre_id,String text, int city_id, int town_id, File file,int need) {
        HttpUrl url = getBaseUrlBuilder()
                .addPathSegment(PROFILESET)
                .addPathSegment("me")
                .build();
        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("pass","true")
                .addFormDataPart("nickname",nickname)
                .addFormDataPart("gender", String.valueOf(gender_id))
                .addFormDataPart("text",text)
                .addFormDataPart("position_id", String.valueOf(position_id))
                .addFormDataPart("genre_id", String.valueOf(genre_id))
                .addFormDataPart("city_id", String.valueOf(city_id))
                .addFormDataPart("town_id", String.valueOf(town_id))
                .addFormDataPart("need",""+need);
        if(file != null){
            builder.addFormDataPart("image",file.getName(),
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
        return new TypeToken<NetworkResult>(){}.getType();
    }

    @Override
    public Request getRequest() {
        return request;
    }
}
