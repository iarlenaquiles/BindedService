package com.tecnologia.aquiles.bindedservice;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

public class MyService extends Service {
    final String LOG_TAG = "service_logs";

    MyBinder binder = new MyBinder();
    long interval = 1000;
    Handler h;
    Runnable r;
    int counter = 0;

    public void onCreate() {
        super.onCreate();
        Log.i(LOG_TAG, "MyService onCreate");

        h = new Handler();
        r = new Runnable() {
            @Override
            public void run() {
                counter++;
                Log.i(LOG_TAG, counter + "");
                h.postDelayed(this, interval);
            }
        };
        h.postDelayed(r, interval);
    }

    long upInterval(long gap) {
        interval = interval + gap;
        return interval;
    }

    long downInterval(long gap) {
        interval = interval - gap;
        if (interval < 100) interval = 100;
        return interval;
    }

    public IBinder onBind(Intent intent) {
        Log.i(LOG_TAG, "MyService onBind");
        return binder;
    }

    class MyBinder extends Binder {
        MyService getService() {
            return MyService.this;
        }
    }

    public boolean onUnbind(Intent intent) {
        Log.i(LOG_TAG, "MyService onUnbind");
        return true;
    }

    public void onDestroy() {
        super.onDestroy();
        h.removeCallbacks(r);
        Log.i(LOG_TAG, "MyService onDestroy");
    }
}
