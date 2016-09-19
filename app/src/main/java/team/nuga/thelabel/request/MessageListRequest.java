package team.nuga.thelabel.request;

import android.content.Context;

import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Date;

import okhttp3.HttpUrl;
import okhttp3.Request;
import team.nuga.thelabel.Utils;
import team.nuga.thelabel.data.NetworkResultMessageList;


/**
 * Created by Administrator on 2016-08-23.
 */
public class MessageListRequest extends AbstractRequest<NetworkResultMessageList> {
    Request mRequest;
    public MessageListRequest(Context context,int receiveId, Date date) {
        String dateString = Utils.convertTimeToString(date);
        HttpUrl url = getBaseUrlBuilder()
                .addPathSegment("messages")
                .addPathSegment(receiveId+"")
                .addQueryParameter("date",dateString)
                .build();
        mRequest = new Request.Builder()
                .url(url)
                .tag(context)
                .build();
    }
    @Override
    protected Type getType() {
        return new TypeToken<NetworkResultMessageList>(){}.getType();
    }

    @Override
    public Request getRequest() {
        return mRequest;
    }
}
