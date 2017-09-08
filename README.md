# AnimationBottomBar

I saw a awesome bottomBar Design from [dribbble](https://dribbble.com/shots/2071319-GIF-of-the-Tapbar-Interactions),and I make it come true.
just like this
  
![](http://oe38oe3ti.bkt.clouddn.com/17-9-6/16156701.jpg)

but i make it a little different

![](http://oe38oe3ti.bkt.clouddn.com/17-9-8/55867838.jpg)

and if you want to know how to make it ,you can see [this](https://mran.github.io/2017/09/08/%E5%81%9A%E4%B8%80%E4%B8%AAbottomBar/)

you can push issue to make it better,i'm happy to help you
# How to use
in your activity xml :
```xml
 <com.mran.bottombar.AnimationBottomBar
      android:layout_width="match_parent"
      android:layout_height="100dp"
      android:id="@+id/bottomBar"
      app:textColor="@color/colorAccent"
      app:backgruond="@color/white"
      app:textSize="16sp"
      app:selectTextColor="@color/blue"
  />
```
In your class
```java
 AnimationBottomBar mAnimationBottomBar;
 mAnimationBottomBar = (AnimationBottomBar) findViewById(R.id.bottom);
 mAnimationBottomBar.addItem(new BottomItem(R.drawable.h, "one"))
                    .addItem(new BottomItem(R.drawable.h, "two"))
                    .addItem(new BottomItem(R.drawable.h, "three"))
                    .addItem(new BottomItem(R.drawable.h, "four"))
                    .addItem(new BottomItem(R.drawable.h, "five"))
                    .build();
```
Add itemSelectedListener
```java
mAnimationBottomBar.setItemSelectListener(new AnimationBottomBar.OnItemSelectListener() {
    @Override
    public void onItemSelectListener(int position) {
        /*do something here*/
    }
});
```
# License
[Apache](http://www.apache.org/licenses/LICENSE-2.0.html)