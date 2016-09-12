package team.nuga.thelabel.request;

import android.content.Context;

import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import okhttp3.HttpUrl;
import okhttp3.Request;
import team.nuga.thelabel.data.NetworkResult;
import team.nuga.thelabel.data.User;

/**
 * Created by Blissun on 2016-09-06.
 */
public class GetUserImageByIdRequest extends AbstractRequest<NetworkResult<User>> {




        private static final String USER = "users";
        private static final String ME = ":id";

        Request request;
        public GetUserImageByIdRequest(Context context, int userId) {
            HttpUrl url = getBaseUrlBuilder()
                    .addPathSegment(USER)
                    .addPathSegment(userId+"")
                    .addQueryParameter("message","true")
                    .build();
            request = new Request.Builder()
                    .url(url)
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
