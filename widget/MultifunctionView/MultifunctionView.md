# DoubleTextView

## 模块说明
DoubleTextView封装了左右两个TextView组合而来的自定义控件，可以自主设置自定义控件和两个控件的属性

##  使用说明
* .xml file
```
<com.tgcity.widget.TwoTextViewLayout
    android:id="@+id/ttv_riverName"
    android:layout_width="match_parent"
    android:layout_height="@dimen/dp_45"
    android:layout_marginLeft="@dimen/dp_6"
    app:dt_isShowLine="true"
    app:dt_leftTitleCol="@color/color_ff000000"
    app:dt_leftTitleStr="问题河道"
    app:dt_rightTitleCol="@color/color_a6000000" />
```
* .class file
```
   tv.setRightContent("xxx");
   tv.setContentSize(14);
   tv.setPaddingWidth(0);
```


 
