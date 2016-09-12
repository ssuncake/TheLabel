package team.nuga.thelabel.request;

import android.content.Context;

import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import okhttp3.HttpUrl;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import team.nuga.thelabel.data.NetworkResult;

public class UploadYoutubeContentRequest extends AbstractRequest<NetworkResult> {

    private static final String UPLOAD = "posts";
    Request request;

    public UploadYoutubeContentRequest(Context context, int filetype, String file, String filetitle, int label_id, int opento, String text) {
        HttpUrl url = getBaseUrlBuilder()
                .addPathSegments(UPLOAD)
                .build();
        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("filetype", "" + filetype)
                .addFormDataPart("file", file)
                .addFormDataPart("filetitle", filetitle)
                .addFormDataPart("label_id", "" + label_id)
                .addFormDataPart("opento", String.valueOf(opento))
                .addFormDataPart("text", text);
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
