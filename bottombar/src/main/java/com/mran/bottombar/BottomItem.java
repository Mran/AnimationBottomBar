package com.mran.bottombar;

import android.support.annotation.DrawableRes;

/**
 * Created by M on 2017/8/29.
 */

public class BottomItem {
    int drawableRes;
     String title;
//    private ImageView mImageView;
    public BottomItem(@DrawableRes int drawableRes,String title){
        this.drawableRes=drawableRes;
        this.title=title;
    }
}
