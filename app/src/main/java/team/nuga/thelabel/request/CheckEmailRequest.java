package team.nuga.thelabel.request;

import android.content.Context;

import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import okhttp3.HttpUrl;
import okhttp3.Request;
import team.nuga.thelabel.data.CheckEmailResult;

/**
 * Created by Blissun on 2016-09-06.
 */
public class CheckEmailRequest extends AbstractRequest<CheckEmailResult>{




        private static final String USER = "users";
        private static final String ME = "me";

        Request request;
        public CheckEmailRequest(Context context,String email) {
            HttpUrl url = getBaseUrlBuilder()
                    .addPathSegment(USER)
                    .addPathSegment(ME)
                    .addQueryParameter("dup","true")
                    .addQueryParameter("email",email)
                    .build();
            request = new Request.Builder()
                    .url(url)
                    .tag(context)
                    .build();
        }



        @Override
        protected Type getType() {
            return new TypeToken<CheckEmailResult>(){}.getType();
        }

        @Override
        public Request getRequest() {
            return request;
        }

}
