package team.nuga.thelabel.request;

import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.lang.reflect.Type;

import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import team.nuga.thelabel.data.NetworkResult;

public class UploadImageContentRequest extends AbstractRequest<NetworkResult> {

    private static final String UPLOAD = "posts";
    MediaType jpeg = MediaType.parse("image/jpeg");
    Request request;
    public UploadImageContentRequest( int filetype, File file, String filetitle, int label_id, int opento, String text) {
        HttpUrl url = getBaseUrlBuilder()
                .addPathSegment(UPLOAD)
                .build();
        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("filetype", ""+filetype)
                .addFormDataPart("filetitle",filetitle)
                .addFormDataPart("label_id", ""+label_id)
                .addFormDataPart("opento", ""+opento)
                .addFormDataPart("text", text);
        if(file != null){
            builder.addFormDataPart("file",file.getName(),
                    RequestBody.create(jpeg,file));
        }
        RequestBody body = builder.build();
        request = new Request.Builder()
                .url(url)
                .post(body)
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
