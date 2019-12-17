##  简介
这是一个加载圆形图片的自定义控件，场景比如用户头像
##  使用步骤
1.xml file
```
<com.tgcity.base.widget.TwoTextViewLayout
    android:id="@+id/ttv_riverName"
    android:layout_width="match_parent"
    android:layout_height="@dimen/dp_45"
    android:layout_marginLeft="@dimen/dp_6"
    app:dt_isShowLine="true"
    app:dt_leftTitleCol="@color/color_ff000000"
    app:dt_leftTitleStr="问题河道"
    app:dt_rightTitleCol="@color/color_a6000000" />
```
 2.class file
```
   tv.setRightContent("xxx");
   tv.setContentSize(14);
   tv.setPaddingWidth(0);
```


 
