##  简介
这是一个由左右两个TextView组合而来的自定义控件，可以自主设置自定义控件和两个控件的属性
##  使用步骤
1.xml file
```
 <com.tgcity.xwidget.TitleBar
     android:id="@+id/titleBar"
     android:layout_width="match_parent"
     android:layout_height="wrap_content"
     android:background="@color/color_white"
     app:xtb_rightImgRes="@mipmap/mine_edit"
     app:xtb_back="@mipmap/back"
     app:xtb_title="@string/mine_information" />
```
 2.class file
```
   @BindView(R.id.titleBar)
   TitleBar titleBar;
   
   titleBar.setTitleStr("xxx");
   titleBar.setBackListener(view -> finish());
```


 
