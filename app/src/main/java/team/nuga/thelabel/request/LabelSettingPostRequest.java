package team.nuga.thelabel.request;

import android.util.Log;

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
public class LabelSettingPostRequest extends AbstractRequest<NetworkResult<User>> {

    private static final String PATH = "labels";
    MediaType jpeg = MediaType.parse("image/jpeg");

    Request request;
    public LabelSettingPostRequest(int labelId,String text,File image, int genre,List<Integer> positions ) {
        HttpUrl url = getBaseUrlBuilder()
                .addPathSegment(PATH)
                .addPathSegment(""+labelId)
                .build();

        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("genre_id", String.valueOf(genre))
                .addFormDataPart("text", text);
        if(positions !=null){
            for(int s : positions){
                Log.e("레이블 생성",s+"");
                builder.addFormDataPart("need_position_id", s+"");
            }
        }
        if(image != null){
            builder.addFormDataPart("image",image.getName(),
                    RequestBody.create(jpeg,image));
        }
        RequestBody body = builder.build();
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
