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

public class UploadMusicContentRequest extends AbstractRequest<NetworkResult> {

    private static final String UPLOAD = "posts";
    MediaType Mp3 = MediaType.parse("mp3");
    Request request;

    public UploadMusicContentRequest(Context context, int filetype, File file, String filetitle, int label_id, int opento, String text) {
        HttpUrl url = getBaseUrlBuilder()
                .addPathSegment(UPLOAD)
                .build();
        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("filetype", "" + filetype)
                .addFormDataPart("filetitle", filetitle)
                .addFormDataPart("label_id", "" + label_id)
                .addFormDataPart("opento", ""+ opento)
                .addFormDataPart("text", text);
        if (file != null) {
            builder.addFormDataPart("file", file.getName(),
                    RequestBody.create(Mp3, file));
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
        return new TypeToken<NetworkResult>() {
        }.getType();
    }

    @Override
    public Request getRequest() {
        return request;
    }
}
