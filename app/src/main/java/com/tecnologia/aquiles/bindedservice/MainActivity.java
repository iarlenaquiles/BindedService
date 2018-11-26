package com.tecnologia.aquiles.bindedservice;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    final String LOG_TAG = "service_logs";

    boolean bound = false;
    ServiceConnection sConn;
    Intent intent;

    long interval;
    MyService service;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        intent = new Intent(this, MyService.class);

        sConn = new ServiceConnection() {
            public void onServiceConnected(ComponentName name, IBinder binder) {
                Log.i(LOG_TAG, "MainActivity onServiceConnected");
                service = ((MyService.MyBinder) binder).getService();
                bound = true;
            }

            public void onServiceDisconnected(ComponentName name) {
                Log.i(LOG_TAG, "MainActivity onServiceDisconnected");
                bound = false;
            }
        };
    }

    public void start(View v) {
        bindService(intent, sConn, BIND_AUTO_CREATE);
    }

    public void stop(View v) {
        if (!bound) return;
        unbindService(sConn);
        bound = false;
    }

    public void faster(View v) {
        if (!bound) return;
        interval = service.downInterval(100);
        getSupportActionBar().setTitle("interval = " + interval);
    }

    public void slower(View v) {
        if (!bound) return;
        interval = service.upInterval(100);
        getSupportActionBar().setTitle("interval = " + interval);
    }
}
