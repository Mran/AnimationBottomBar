package com.mran.animationbottombar;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

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
            mAnimationBottomBar.addItem(new BottomItem(R.drawable.h, "zero"))
                    .addItem(new BottomItem(R.drawable.h, "one"))
                    .addItem(new BottomItem(R.drawable.h, "two"))
                    .addItem(new BottomItem(R.drawable.h, "three"))
                    .addItem(new BottomItem(R.drawable.h, "four"))
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
        }
mAnimationBottomBar.setItemSelectListener(new AnimationBottomBar.OnItemSelectListener() {
    @Override
    public void onItemSelectListener(int position) {
        Log.d("MainActivity", "onItemSelectListener: position"+position);
    }
});
    }
}
