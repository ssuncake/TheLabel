/**
 * Copyright 2015 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package team.nuga.thelabel.gcm;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.android.gms.gcm.GcmListenerService;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;

import team.nuga.thelabel.Utils;
import team.nuga.thelabel.data.ChatContract;
import team.nuga.thelabel.data.Message;
import team.nuga.thelabel.data.NetworkResult;
import team.nuga.thelabel.data.NetworkResultMessageList;
import team.nuga.thelabel.data.User;
import team.nuga.thelabel.manager.DBManager;
import team.nuga.thelabel.manager.NetworkManager;
import team.nuga.thelabel.manager.NetworkRequest;
import team.nuga.thelabel.request.MessageListRequest;
import team.nuga.thelabel.request.MessageReceiverGetRequest;

public class MyGcmListenerService extends GcmListenerService {

    private static final String TAG = "MyGcmListenerService";
    public static final String ACTION_CHAT = "team.nuga.thelabel.action.chatmessage";
    public static final String EXTRA_CHAT_USER = "chatuser";
    public static final String EXTRA_RESULT = "result";

    LocalBroadcastManager mLBM;



    @Override
    public void onCreate() {
        super.onCreate();
        mLBM = LocalBroadcastManager.getInstance(this);
    }

    @Override
    public void onMessageReceived(String from, Bundle data) {
        String message = data.getString("message");
        String reids = data.getString("receiverId");
        Log.e(TAG, "From: " + from);
        Log.e(TAG, "Message: " + message);
        Log.e(TAG, "receiverId: " + reids);
        int reid = Integer.parseInt(reids);

        if (from.startsWith("/topics/")) {
        } else {
            // 메세지보내기
            long lastTime = DBManager.getInstance().getLastReceiveDate();
            Date date = new Date(lastTime);

            Log.e(TAG, "date: " + date.toString());
            MessageListRequest request = new MessageListRequest(this,reid,date);
            try {
                NetworkResultMessageList result = NetworkManager.getInstance().getNetworkDataSync(request);
                Message[] list = result.getMessage();
                if(list != null){
                    Log.e(TAG, "messagelist size: " + list.length);
                }

                for( Message m : list){

                        final Message messagetemp = m;
                    if(m != null){
                        Log.e(TAG, "message: " + m.getText()+" myid : "+m.getYou_user_id()+" youid : "+m.getUser_id());
                    }
                        MessageReceiverGetRequest request1 = new MessageReceiverGetRequest(m.getUser_id());
                        NetworkManager.getInstance().getNetworkData(request1, new NetworkManager.OnResultListener<NetworkResult<User>>() {
                            @Override
                            public void onSuccess(NetworkRequest<NetworkResult<User>> request, NetworkResult<User> result){
                                User other = result.getUser();
                                Log.e("GCM test  ","너의 유저객체 id : "+other.getUserID());
                                try {
                                    Log.e("GCM test addmessage ","내아이디 : "+messagetemp.getYou_user_id()+" 너아이디 : "+other.getUserID()+" date : "+messagetemp.getDate()+" // "+Utils.convertStringToTime(messagetemp.getDate()));
                                    DBManager.getInstance().addMessage(messagetemp.getYou_user_id(),other,ChatContract.ChatMessage.TYPE_RECEIVE, messagetemp.getText(),Utils.convertStringToTime(messagetemp.getDate()));
                                    Intent i = new Intent(ACTION_CHAT);
                                    i.putExtra(EXTRA_CHAT_USER,other);
                                    mLBM.sendBroadcastSync(i);
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onFail(NetworkRequest<NetworkResult<User>> request, int errorCode, String errorMessage, Throwable e) {

                            }
                        });

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }




}
