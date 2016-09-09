package team.nuga.thelabel.request;

import android.content.Context;

import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.lang.reflect.Type;
import java.util.List;

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
public class LabelMakeRequest extends AbstractRequest<NetworkResult<User>> {

    private static final String Path = "labels";
    MediaType jpeg = MediaType.parse("image/jpeg");
    Request request;
    public LabelMakeRequest(Context context, String labelName, int genre, String text, File file, List<Integer> positions) {
        HttpUrl url = getBaseUrlBuilder()
                .addPathSegment(Path)
                .build();

        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("label_name", labelName)
                .addFormDataPart("genre_id", String.valueOf(genre))
                .addFormDataPart("text", text);
        if(positions !=null){
            for(int s : positions){
                builder.addFormDataPart("need_position_id", String.valueOf(genre));
            }
        }
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
        return new TypeToken<NetworkResult<User>>(){}.getType();
    }

    @Override
    public Request getRequest() {
        return request;
    }
}
