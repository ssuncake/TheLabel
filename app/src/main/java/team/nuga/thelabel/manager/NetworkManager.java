package team.nuga.thelabel.manager;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.franmontiel.persistentcookiejar.ClearableCookieJar;
import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.text.ParseException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManagerFactory;

import okhttp3.Cache;
import okhttp3.Call;
import okhttp3.Dispatcher;
import okhttp3.OkHttpClient;
import team.nuga.thelabel.MyApplication;
import team.nuga.thelabel.R;

/**
 * Created by Tacademy on 2016-08-29.
 */
public class NetworkManager {
    private static NetworkManager instance;
    public static NetworkManager getInstance(){
        if(instance == null){
            instance = new NetworkManager();
        }
        return instance;
    }

    OkHttpClient client;

    private  NetworkManager() {

        OkHttpClient.Builder networkBuilder = new OkHttpClient.Builder();
        Context context = MyApplication.getContext();
        ClearableCookieJar cookieJar =
                new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(context));
        networkBuilder.cookieJar(cookieJar);
        networkBuilder.followRedirects(true);
        networkBuilder.addNetworkInterceptor(new RedirectInterceptor());

        File cacheDir = new File(context.getCacheDir(), "network");
        if(!cacheDir.exists()){
            cacheDir.mkdir();
        }
        Cache cache = new Cache(cacheDir,10 * 1024 * 1024);
        networkBuilder.cache(cache);

        networkBuilder.connectTimeout(30, TimeUnit.SECONDS);
        networkBuilder.readTimeout(10, TimeUnit.SECONDS);
        networkBuilder.writeTimeout(10, TimeUnit.SECONDS);

        disableCertificateValidation(context,networkBuilder);
        client = networkBuilder.build();
    }

    public OkHttpClient getClient(){return client;}

    private static final int MESSAGE_SUCCESS = 1;
    private static final int MESSAGE_FAIL = 2;

    Handler mHandler = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            NetworkRequest<?> request = (NetworkRequest) msg.obj;
            switch (msg.what){
                case MESSAGE_SUCCESS:
                    request.sendSuccess();
                    break;
                case MESSAGE_FAIL:
                    request.sendFail();
                    break;
            }
        }
    };

    public interface OnResultListener<T>{
        public void onSuccess(NetworkRequest<T> request,T result) throws ParseException;

        public void onFail(NetworkRequest<T> request,int errorCode, String errorMessage, Throwable e);
    }

    void sendSuccess(NetworkRequest<?> request){
        Message msg = mHandler.obtainMessage(MESSAGE_SUCCESS, request);
        mHandler.sendMessage(msg);
    }

    void sendFail(NetworkRequest<?> request) {
        Message msg = mHandler.obtainMessage(MESSAGE_FAIL, request);
        mHandler.sendMessage(msg);
    }

    public <T> void getNetworkData(NetworkRequest<T> request, OnResultListener<T> listener) {
        request.setOnResultListener(listener);
        request.process(client);
    }

    public <T> T getNetworkDataSync(NetworkRequest<T> request) throws IOException {
        return request.processSync(client);
    }

    public void cancelAll() {
        client.dispatcher().cancelAll();
    }

    public void cancelAll(Object tag) {
        Dispatcher dispatcher = client.dispatcher();
        List<Call> list = dispatcher.queuedCalls();
        for (Call call : list) {
            if (call.request().tag().equals(tag)) {
                call.cancel();
            }
        }
        list = dispatcher.runningCalls();
        for (Call call : list) {
            if (call.request().tag().equals(tag)) {
                call.cancel();
            }
        }
    }


    static void disableCertificateValidation(Context context, OkHttpClient.Builder builder){
        try{
            CertificateFactory cf = CertificateFactory.getInstance("X.509");
            InputStream caInput = context.getResources().openRawResource(R.raw.site);
            Certificate ca;
            try{
                ca = cf.generateCertificate(caInput);
                System.out.println("ca="+((X509Certificate) ca).getSubjectDN());
            }finally{
                caInput.close();
            }
            String keyStoreType = KeyStore.getDefaultType();
            KeyStore keyStore = KeyStore.getInstance(keyStoreType);
            keyStore.load(null,null);
            keyStore.setCertificateEntry("ca",ca);

            String tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
            TrustManagerFactory tmf = TrustManagerFactory.getInstance(tmfAlgorithm);
            tmf.init(keyStore);

            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, tmf.getTrustManagers(), null);
            HostnameVerifier hv = new HostnameVerifier() {
                @Override
                public boolean verify(String s, SSLSession sslSession) {
                    return true;
                }
            };
            sc.init(null,tmf.getTrustManagers(), null);
            builder.sslSocketFactory(sc.getSocketFactory());
            builder.hostnameVerifier(hv);

        }catch (CertificateException e) {
            e.printStackTrace();
        }catch (NoSuchAlgorithmException e){
            e.printStackTrace();
        }catch (KeyStoreException e){
            e.printStackTrace();
        }catch(KeyManagementException e ){
            e.printStackTrace();
        }catch (IOException e ){
            e.printStackTrace();
        }
    }
}
