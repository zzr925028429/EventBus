package com.example.zzr.eventbus;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;

//Fragment，Activity，Service，线程之间传递消息
public class MainActivity extends AppCompatActivity {
    private TextView mText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mText = (TextView) findViewById(R.id.mytext);
        EventBus.getDefault().register(this); //注册
        thread();

    }

    public void thread(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                String json = "111";
                EventBus.getDefault().post(json);//子线程发布消息
            }
        }).start();
    }

    //订阅者
    @Subscribe(threadMode = ThreadMode.MainThread)//定义ThreadMode.MainThread，其含义为该方法在UI线程完成
    public void helloEventBus(String message) {
        mText.setText(message);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);//取消注册
    }

    public void gomain2(View view) {
        startActivity(new Intent(MainActivity.this, Main2Activity.class));
    }
}
