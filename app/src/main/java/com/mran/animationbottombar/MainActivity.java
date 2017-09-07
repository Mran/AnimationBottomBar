package com.mran.animationbottombar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.mran.bottombar.AnimationBottomBar;
import com.mran.bottombar.BottomItem;

public class MainActivity extends AppCompatActivity {
    AnimationBottomBar mAnimationBottomBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAnimationBottomBar = (AnimationBottomBar) findViewById(R.id.bottom);
        try {
            mAnimationBottomBar.addItem(new BottomItem(R.drawable.h, "0000"))
                    .addItem(new BottomItem(R.drawable.h, "1111"))
                    .addItem(new BottomItem(R.drawable.h, "2222"))
                    .addItem(new BottomItem(R.drawable.h, "3333"))
                    .addItem(new BottomItem(R.drawable.h, "4444"))
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
