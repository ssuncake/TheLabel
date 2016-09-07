package team.nuga.thelabel.manager;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import team.nuga.thelabel.MyApplication;
import team.nuga.thelabel.data.ChatContract;
import team.nuga.thelabel.data.User;

/**
 * Created by Administrator on 2016-08-11.
 */
public class DBManager extends SQLiteOpenHelper {
    private static DBManager instance;
    static User mainUser;

    public static DBManager getInstance(User user) { // 여기체크
        if(user!=null){
            mainUser = user;
            user.setUserID(999);
        Log.e("DBM 처음 유저","userid : "+user.getLongUserID());
        }

        if (instance == null) {
            instance = new DBManager();
        }
        return instance;
    }



    private static final String DB_NAME = "chat_db";
    private static final int DB_VERSION = 1;

    private DBManager() {
        super(MyApplication.getContext(), DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + ChatContract.ChatUser.TABLE + "(" +
                ChatContract.ChatUser._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                ChatContract.ChatUser.ME_ID + " INTEGER," +
                ChatContract.ChatUser.OTHER_ID + " INTEGER," +
                ChatContract.ChatUser.OTHER_NAME + " TEXT," +
                ChatContract.ChatUser.COLUMN_LAST_MESSAGE_ID + " INTEGER);";
        db.execSQL(sql);

        sql = "CREATE TABLE " + ChatContract.ChatMessage.TABLE + "(" +
                ChatContract.ChatMessage._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                ChatContract.ChatMessage.COLUMN_CONNET_ID + " INTEGER," +
                ChatContract.ChatMessage.COLUMN_TYPE + " INTEGER," +
                ChatContract.ChatMessage.COLUMN_MESSAGE + " TEXT," +
                ChatContract.ChatMessage.COLUMN_CREATED + " INTEGER);";
        db.execSQL(sql);

        // 더미 데이터 삽입

        values.clear();
        values.put(ChatContract.ChatUser.ME_ID, 300);
        values.put(ChatContract.ChatUser.OTHER_ID, 400);
        values.put(ChatContract.ChatUser.OTHER_NAME, "더르미");
        values.put(ChatContract.ChatUser.COLUMN_LAST_MESSAGE_ID,1);
        Log.e("DBM add User","더미 추가");
        db.insert(ChatContract.ChatUser.TABLE, null, values);

        values.clear();
        values.put(ChatContract.ChatMessage.COLUMN_CONNET_ID, 1);
        values.put(ChatContract.ChatMessage.COLUMN_TYPE, 1);
        values.put(ChatContract.ChatMessage.COLUMN_MESSAGE, "우왓우왓우왓!");
        values.put(ChatContract.ChatMessage.COLUMN_CREATED,"22:00");
        Log.e("DBM add User","더미메세지 추가");
        db.insert(ChatContract.ChatMessage.TABLE, null, values);

        values.clear();
        values.put(ChatContract.ChatMessage.COLUMN_CONNET_ID, 1);
        values.put(ChatContract.ChatMessage.COLUMN_TYPE, 0);
        values.put(ChatContract.ChatMessage.COLUMN_MESSAGE, "므아아아아");
        values.put(ChatContract.ChatMessage.COLUMN_CREATED,"22:01");
        Log.e("DBM add User","더미메세지1 추가");
        db.insert(ChatContract.ChatMessage.TABLE, null, values);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    Map<String, Long> resolveConnectID = new HashMap<>(); // 로그인한 아이디와 대화하려는 유저의 아이디를 스트링으로 더한값을 키로.. meid+""+otherid
    ContentValues values = new ContentValues();

    public long getConnectionId(long otherid) {
        int mid = mainUser.getUserID();
        Log.e("DBM getConnection ID","메인유저 id : "+mid + "oherid :"+otherid);
        String table="(SELECT * FROM "+ChatContract.ChatUser.TABLE+" WHERE "+ChatContract.ChatUser.ME_ID +"='"+mid+"')";
        // select* form ChatContract.ChatUser.TABLE where ChatContract.ME_ID = MainUser.id
        String[] columns = {ChatContract.ChatUser._ID};
        String selection = ChatContract.ChatUser.OTHER_ID + " = ?";
        String[] args = {""+otherid};
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.query(table, columns, selection, args, null, null, null);
        try {
            if (c.moveToNext()) {
                long id = c.getLong(c.getColumnIndex(ChatContract.ChatUser._ID));
                return id;
            }
        } finally {
            c.close();
        }
        return -1;
    }


    public long addUser(User user) {
        if (getConnectionId(user.getLongUserID()) == -1) {
            SQLiteDatabase db = getWritableDatabase();
            values.clear();
            values.put(ChatContract.ChatUser.ME_ID, mainUser.getLongUserID());
            values.put(ChatContract.ChatUser.OTHER_ID, user.getLongUserID());
            values.put(ChatContract.ChatUser.OTHER_NAME, user.getUserName());
            Log.e("DBM add User","main : "+  mainUser.getLongUserID()+" + user : "+user.getUserID()+"// "+user.getUserName()+" 가 추가될껄");
            return db.insert(ChatContract.ChatUser.TABLE, null, values);
        }
        Log.e("DBM add User","main : "+mainUser.getUserID()+" + user : "+user.getUserID()+"// "+user.getUserName()+" 가 이미 있음");
        throw new IllegalArgumentException("aleady user added");
    }


    public long addMessage(User me,User user, int type, String message) {
        return addMessage(me,user, type, message, new Date());
    }
    public long addMessage(User me,User user, int type, String message, Date date) {
        String mykey = me.getUserID()+""+user.getUserID();
        Long uid = resolveConnectID.get(mykey);
        if (uid == null) {
            long id = getConnectionId(user.getLongUserID());
            if (id == -1) {
                id = addUser(user);
            }
            resolveConnectID.put(mykey, id);
            Log.e("DBM addMessage : ","해쉬맵에서 키값 확인"+mykey+" -> "+id);
            uid = id;
        }

        SQLiteDatabase db = getWritableDatabase();
        values.clear();
        values.put(ChatContract.ChatMessage.COLUMN_CONNET_ID, uid);
        values.put(ChatContract.ChatMessage.COLUMN_TYPE, type);
        values.put(ChatContract.ChatMessage.COLUMN_MESSAGE, message);
        long current = date.getTime();
        values.put(ChatContract.ChatMessage.COLUMN_CREATED, current);

        try {
            db.beginTransaction();
            long mid = db.insert(ChatContract.ChatMessage.TABLE, null, values);
            values.clear();
            values.put(ChatContract.ChatUser.COLUMN_LAST_MESSAGE_ID, mid);
            String selection = ChatContract.ChatUser._ID + " = ?";
            String[] args = {"" + uid};
            db.update(ChatContract.ChatUser.TABLE, values, selection, args);
            db.setTransactionSuccessful();
            Log.w("DBM 메세지추가","성공");
            return mid;
        } finally {
            db.endTransaction();
        }
    }

    public Cursor getChatUser() {
        String table = ChatContract.ChatUser.TABLE + " INNER JOIN " +
                ChatContract.ChatMessage.TABLE + " ON " +
                ChatContract.ChatUser.TABLE + "." + ChatContract.ChatUser.COLUMN_LAST_MESSAGE_ID + " = " +
                ChatContract.ChatMessage.TABLE + "." + ChatContract.ChatMessage._ID;
        String[] columns = {ChatContract.ChatUser.TABLE + "." + ChatContract.ChatUser._ID,
                ChatContract.ChatUser.ME_ID,
                ChatContract.ChatUser.OTHER_ID,
                ChatContract.ChatUser.OTHER_NAME,
                ChatContract.ChatMessage.COLUMN_MESSAGE};
        String selection = ChatContract.ChatUser.ME_ID + " = ?";
        String[] args = {"" + mainUser.getUserID()};
        Log.e("DBM getchatUser","mainUserid : "+mainUser.getUserID());
        String sort = ChatContract.ChatUser.OTHER_NAME + " COLLATE LOCALIZED ASC";
        SQLiteDatabase db = getReadableDatabase();
        return db.query(table, columns, selection, args, null, null, sort);
    }

    public Cursor getChatMessage(User user) {
        long userid = -1;
        String mykey = mainUser.getUserID()+""+user.getUserID();
        Log.w("DBM getChatMessage","키확인 "+mykey);
        Long uid = resolveConnectID.get(mykey);
        if (uid == null) {
            long id = getConnectionId(user.getLongUserID());
            if (id != -1) {
                resolveConnectID.put(mykey, id);
                userid = id;
            }
        } else {
            userid = uid;
        }

        String[] columns = {ChatContract.ChatMessage._ID,
                ChatContract.ChatMessage.COLUMN_TYPE,
                ChatContract.ChatMessage.COLUMN_MESSAGE,
                ChatContract.ChatMessage.COLUMN_CREATED};
        String selection = ChatContract.ChatMessage.COLUMN_CONNET_ID + " = ?";
        String[] args = {"" + userid};
        String sort = ChatContract.ChatMessage.COLUMN_CREATED + " ASC";
        SQLiteDatabase db = getReadableDatabase();
        return db.query(ChatContract.ChatMessage.TABLE, columns, selection, args, null, null, sort);
    }

    public long getLastReceiveDate() {
        SQLiteDatabase db = getReadableDatabase();
        String[] columns = {ChatContract.ChatMessage.COLUMN_CREATED};
        String selection = ChatContract.ChatMessage.COLUMN_TYPE + " = ?";
        String[] args = {"" + ChatContract.ChatMessage.TYPE_RECEIVE};
        String orderBy = ChatContract.ChatMessage.COLUMN_CREATED + " DESC";
        String limit = "1";
        Cursor c = db.query(ChatContract.ChatMessage.TABLE, columns, selection, args, null, null, orderBy, limit);
        try {
            if (c.moveToNext()) {
                long time = c.getLong(c.getColumnIndex(ChatContract.ChatMessage.COLUMN_CREATED));
                return time;
            }
        } finally {
            c.close();
        }
        return 0;
    }

}
