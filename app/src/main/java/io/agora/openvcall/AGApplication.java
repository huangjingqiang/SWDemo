package io.agora.openvcall;

import android.app.Application;

import com.facebook.stetho.Stetho;
import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.hanzi.apirestful.ApiRESTful.ALog;
import com.hanzi.apirestful.ApiRESTful.ApiRESTful;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.agora.openvcall.model.CurrentUserSettings;
import io.agora.openvcall.model.WorkerThread;
import okhttp3.OkHttpClient;

public class AGApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        if (BuildConfig.DEBUG){
            Stetho.initializeWithDefaults(this);
        }
        ApiRESTful apiRESTful = ApiRESTful.getApiRESTful(this, false);
        apiRESTful.setModelUrl(AppConfig.HOST);
        apiRESTful.setPushWs("");
        apiRESTful.setAutoToWs(false);
        apiRESTful.setCardId("04df-ee031b69-0000-4e02-8a56-41736fc1226a-e0000000");
        apiRESTful.setHeadersPrefix("x-hz-");
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.addNetworkInterceptor(new StethoInterceptor());
        //apiRESTful.setOKHttpClientBuider(builder);
        ALog.debug = true;
    }

    public  static String channelKey = "6396e142bb834a718e5b776db163b12a";
    public static int uid;
    private WorkerThread mWorkerThread;

    public synchronized void initWorkerThread() {
        if (mWorkerThread == null) {
            mWorkerThread = new WorkerThread(getApplicationContext());
            mWorkerThread.start();

            mWorkerThread.waitForReady();
        }
    }

    public synchronized WorkerThread getWorkerThread() {
        return mWorkerThread;
    }

    public synchronized void deInitWorkerThread() {
        mWorkerThread.exit();
        try {
            mWorkerThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        mWorkerThread = null;
    }

    public static final CurrentUserSettings mVideoSettings = new CurrentUserSettings();
}
