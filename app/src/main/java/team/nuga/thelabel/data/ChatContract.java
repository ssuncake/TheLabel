package team.nuga.thelabel.data;

import android.provider.BaseColumns;

/**
 * Created by Administrator on 2016-08-11.
 */
public class ChatContract {
    public interface ChatUser extends BaseColumns {
        public static final String TABLE = "chatuser";
        public static final String OTHER_ID = "outherid";
        public static final String ME_ID="myid";
        public static final String OTHER_NAME = "othername";
       public static final String OTHER_IMAGE = "othgerimage";
        public static final String COLUMN_LAST_MESSAGE_ID = "lastid";
    }

    public interface ChatMessage extends BaseColumns {
        public static final int TYPE_SEND = 0;
        public static final int TYPE_RECEIVE = 1;
        public static final int TYPE_DATE = 2;

        public static final String TABLE = "chatmessage";
        public static final String COLUMN_CONNET_ID = "cid";
        public static final String COLUMN_TYPE = "type";
        public static final String COLUMN_MESSAGE = "message";
        public static final String COLUMN_CREATED = "created";
    }
}
