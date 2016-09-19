package com.example.android.navigationdrawer;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;


public class SplashActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

        //쓰레드(java)+핸들러(Android) => 타이머
        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent(getApplicationContext(),MainActivity.class);//엑티비티를 실행시키기 위해 꼭필요한 것 /getApplicationContext():현재의 문맥객체
                startActivity(intent);
                 overridePendingTransition(0,R.anim.zoom_exit);
                finish();//현재 엑티비티의 생을 마감시킴..
            }
        },3000);//쓰레드 객체를 이름없이 생성(익명클래스)/post:~한 뒤에 / 1000밀리세컨드 == 1초

    }

}
