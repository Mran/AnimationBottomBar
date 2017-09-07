package com.mran.bottombar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by M on 2017/8/20.
 */

public class AnimationBottomBar extends ViewGroup {
    private static final int CHILDCOUNTMAX = 5;//bottombar中能存在的item最大数;

    private ArrayList<BottomItem> mBottomItemArrayList = new ArrayList<>();
    private Paint mPaint = new Paint();
    private Context mContext;
    private int itemCount;//item的数量
    private int childCount;//子view的数量
    private int barHeight;//bar的高度
    private int barWidth;//bar的宽度
    private int itemWidth;//平均每个item的宽度
    private int selectIndex = 0;//当前选中的位置
    private int selectLastIndex = 0;//上次选中的位置
    private float touchDownX;//记录手指第一次按下的X位置

    private int itemMoveLeft = 0;//item左边的位置
    private int itemMoveRight = 0;//item移动右边的位置
    private int itemMoveCenter = 0;//item移动中心的位置

    private int itemMoveLastLeft = 0;//上次item移动左边的位置
    private int itemMoveLastRight = 0;//上次item移动右边的位置
    private BottomAnimation mBottomAnimation = new BottomAnimation();


    private int backGroundColor;//背景的颜色
    private float textSize;
    private int textColor;
    private int selectTextColor;
    private int[] itemcolors = {0xFFF93008, 0xFF73ADCE, 0xFFEDC15E, 0xFFDC6394, 0xFF6BCEB0};//记录每个item的颜色
    private int[] itemCenterX = {0, 0, 0, 0, 0};//记录每个item的中心位置
    private float[] itemScale = {0.5f, 0.5f, 0.5f, 0.5f, 0.5f};//记录每个item的缩放比例
    private AttributeSet mAttributeSet;

    public AnimationBottomBar(Context context) {
        super(context);
//        init(context);
    }

    public AnimationBottomBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs);
    }

    public AnimationBottomBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs);
    }


    private void init(Context context,AttributeSet attrs) {
        this.mContext = context;
        mAttributeSet = attrs;
        getAttrs();
        setWillNotDraw(false);//设置false之后才能调用ondraw()
        mBottomAnimation.setDuration(300);

        mBottomAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                TextView textView=(TextView)getChildAt(selectLastIndex+itemCount);
                textView.setTextColor(textColor);
                TextView selectTextView=(TextView)getChildAt(selectIndex+itemCount);
                selectTextView.setTextColor(selectTextColor);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                clearAnimation();
                itemMoveLastLeft = itemMoveLeft;
                itemMoveLastRight = itemMoveRight;
                selectLastIndex = selectIndex;

                Log.d("AnimationBottomBar", "onAnimationEnd:itemMoveLastRight " + itemMoveLastRight);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    public AnimationBottomBar addItem(BottomItem bottomItem) {
        mBottomItemArrayList.add(bottomItem);
        return this;
    }
int count=0;
    public void build() throws Exception {
        itemCount = mBottomItemArrayList.size();
        if (itemCount > CHILDCOUNTMAX) {
            throw new Exception("The maximum number of items is 5");
        }
        Log.d("AnimationBottomBar", "build: width"+getWidth());
        Log.d("AnimationBottomBar", "build: count"+count++);

        itemWidth=getLayoutParams().width/itemCount;
        for (BottomItem bottomItem : mBottomItemArrayList) {
            ImageView imageView = new ImageView(mContext);
            imageView.setImageResource(bottomItem.drawableRes);
            addView(imageView, itemWidth, 20);
        }
        for (BottomItem bottomItem : mBottomItemArrayList) {
            TextView textView=new TextView(mContext);
            textView.setTextSize(textSize);
            textView.setText(bottomItem.title);
            textView.setTextColor(textColor);
            addView(textView,itemWidth,20);
        }
        setSelectIndex(0);
        Log.d("AnimationBottomBar", "build: itemwidth"+itemWidth);
    }

    //手动选择选择的item
    public void setSelectIndex(int i) {
        selectIndex = i;
        itemMoveLeft = itemWidth * selectIndex;
        itemMoveRight = itemWidth * (selectIndex + 1);
        itemMoveLastRight = itemMoveRight;
        itemMoveLastLeft = itemMoveLeft;
        itemMoveCenter=itemMoveLeft+itemWidth/2;
        selectLastIndex = i;
        postInvalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Log.d("AnimationBottomBar", "onMeasure: count"+count++);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        childCount = getChildCount();
        barWidth = getLayoutParams().width;//bottombar的宽度
        barHeight = getLayoutParams().height;//--的高度
        itemWidth = barWidth / itemCount;
        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);
            measureChild(childView, widthMeasureSpec, heightMeasureSpec);
        }
    }


    @Override
    protected void onDraw(Canvas canvas) {
//        mPaint.setStrokeWidth(20);
        //绘制item颜色
        for (int i = 0; i < 5; i++) {
            mPaint.setColor(itemcolors[i]);
            canvas.drawRect(itemWidth * i, 0, itemWidth * (i + 1), barHeight, mPaint);
            canvas.save();
        }
        mPaint.setColor(backGroundColor);
        //画出背景,两个长方形
        canvas.drawRect(0, 0, itemMoveLeft, barHeight, mPaint);
        canvas.drawRect(itemMoveRight, 0, itemWidth * 5, barHeight, mPaint);
        canvas.save();
        for (int i = 0; i < itemCount; i++) {
            int deltaX=Math.abs(itemMoveCenter-itemCenterX[i]);//获得当前item移动中心点和item固定中心点的距离
            if (deltaX<itemWidth){
                itemScale[i]= (float) (-0.5*deltaX/itemWidth+1);//当距离小于一个item的宽度时调整item的缩放系数
            }
            else itemScale[i]=0.5f;//非选中的item的缩放系数固定为0.5
            //对item的大小进行调整
            View childImageView = getChildAt(i);
            childImageView.setScaleX(itemScale[i]);
            childImageView.setScaleY(itemScale[i]);
            View childTextView = getChildAt(itemCount+i);
            childTextView.setScaleX(itemScale[i]);
            childTextView.setScaleY(itemScale[i]);
        }
        Log.d("AnimationBottomBar", "onDraw: +itemScale"+selectIndex+"   "+itemScale[selectIndex]);
        super.onDraw(canvas);

    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        Log.d("AnimationBottomBar", "onLayout: count"+count++);



        for (int i = 0; i < itemCount; i++) {//遍历每一个item,放置item的位置
            itemCenterX[i] = (int) (itemWidth * (i + 0.5));//记录每个item的中心位置

            View childImageView = getChildAt(i);
//            childImageView.setTag("childimage");
            childImageView.layout(itemWidth * i, 0, itemWidth * (i + 1), 100);
            View childTextView=getChildAt(itemCount+i);
            childTextView.layout(itemWidth * i+childTextView.getWidth()/4,100,itemWidth * (i + 1),barHeight);
//            childTextView.setTag("childText");

        }
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
//        Log.d("AnimationBottomBar", "onInterceptTouchEvent: action"+action);
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                touchDownX = ev.getX();
                break;
            case MotionEvent.ACTION_UP:
                if (ev.getX() / itemWidth == touchDownX / itemWidth) {
                    selectIndex = (int) (ev.getX() / itemWidth);
                    //点击时开始动画
                    startAnimation(mBottomAnimation);
                }
                break;

        }
        return true;
    }
    private void getAttrs() {
        TypedArray typedArray = mContext.obtainStyledAttributes(mAttributeSet, R.styleable.AnimationBottomBar);


        backGroundColor = typedArray.getColor(R.styleable.AnimationBottomBar_backgruond, 0xFF5C4E71);
        textSize = typedArray.getDimension(R.styleable.AnimationBottomBar_textSize, 25.0f);
        textColor = typedArray.getColor(R.styleable.AnimationBottomBar_textColor, 0xffffff);
        selectTextColor = typedArray.getColor(R.styleable.AnimationBottomBar_selectTextColor, 0x639fff);
    }
    private class BottomAnimation extends Animation {
        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {
            super.applyTransformation(interpolatedTime, t);
//            Log.d("BottomAnimation", "applyTransformation: interpolatedTime" + interpolatedTime);
            int position = selectIndex - selectLastIndex;
            //判断不同方向的移动
            if (position < 0) {//向左滑动
                itemMoveRight = (int) (itemMoveLastRight + interpolatedTime * itemWidth * position);
                itemMoveLeft = (int) (itemMoveLastLeft + setFirst(interpolatedTime) * itemWidth * position);
                itemMoveCenter = (int) (itemMoveLastRight + interpolatedTime * itemWidth * position) -itemWidth / 2;//记录中心点移动的位置
            } else {//向右滑动

                itemMoveRight = (int) (itemMoveLastRight + setFirst(interpolatedTime) * itemWidth * position);
                itemMoveLeft = (int) (itemMoveLastLeft + interpolatedTime * itemWidth * position);
                itemMoveCenter = (int) (itemMoveLastLeft + interpolatedTime * itemWidth * position) + itemWidth / 2;//记录中心点移动的位置

            }
            postInvalidate();
        }

        //为了实现果冻效果,先移动的一侧要有快速效果
        private float setFirst(float interpolatedTime) {
            return (float) Math.sin(interpolatedTime * 0.5 * Math.PI);
        }

    }


}
