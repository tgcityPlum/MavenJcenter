# GradientButton

## 模块说明
GradientButton封装了背景色渐变按钮

##  使用说明

* .xml file
```
 <com.tgcity.utils.StrongGradientButton
    android:id="@+id/tvStartPatrol"
    android:layout_width="match_parent"
    android:layout_height="@dimen/dp_40"
    android:layout_marginTop="@dimen/dp_10"
    android:layout_marginLeft="@dimen/dp_5"
    android:layout_marginRight="@dimen/dp_5"
    android:text="开始巡更"
    app:gb_normalBackgroundColor_end_Gradient="@color/color_07A1FB"
    app:gb_normalBackgroundColor_start_Gradient="@color/color_11CFFD"
    app:gb_pressBackgroundColor_end_Gradient="@color/color_07A1FB"
    app:gb_pressBackgroundColor_start_Gradient="@color/color_11CFFD"
    app:gb_radius_Gradient="@dimen/dp_2"
    app:gb_unableBackgroundColor_end_Gradient="@color/color_07A1FB"
    app:gb_unableBackgroundColor_start_Gradient="@color/color_11CFFD"/>
```

* .class file
```
    tvStartPatrol.setText("巡更中");
```


 
