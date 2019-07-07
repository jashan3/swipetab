package com.example.home.swipetab;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.util.Arrays;
import java.util.List;

public class IntroActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.intro_activity);
//        IntroGroupView si =new IntroGroupView(this);
//
//        View view1 = View.inflate(this,R.layout.layout_1,null);
//        View view2 = View.inflate(this,R.layout.layout_2,null);
//        View view3 = View.inflate(this,R.layout.layout_3,null);
//        View view4 = View.inflate(this,R.layout.layout_4,null);
//
//        List<View> view_group = Arrays.asList(view1,view2,view3,view4);
//        for(View vg : view_group) {
//            si.addView(vg);
//        }
//
//        setContentView(si);

        //3초뒤 시작
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent main_intent = new Intent(IntroActivity.this, MainActivity.class);
                main_intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                main_intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(main_intent);
            }
        },2000);


    }
}
