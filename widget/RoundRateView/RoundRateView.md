#  简介
这是一个圆环比例表控件

##  使用步骤
1.xml file
```
    <com.tgcity.widget.RoundRateView
        android:id="@+id/rrv"
        android:layout_width="@dimen/dp_110"
        android:layout_height="@dimen/dp_110"
        android:layout_gravity="center_horizontal"
        android:layout_marginTop="@dimen/dp_16"
        app:layout_constraintLeft_toLeftOf="parent"
        app:layout_constraintRight_toRightOf="parent"
        app:layout_constraintTop_toBottomOf="@id/vLinePatrol"
        app:rrv_aboveTextColor="@color/color_666666"
        app:rrv_aboveTextSize="19sp"
        app:rrv_belowTextColor="@color/colorAccent"
        app:rrv_belowTextSize="14sp"
        app:rrv_circleWidth="17dp"
        app:rrv_intervalColor="#f6f6f6"
        app:rrv_isShowText="false" />
```
 2.class file
```
   List<Double> countList = new ArrayList<>();
   countList.add((double) unChooseList.size());
   countList.add((double) chooseList.size());
   rrv.setList(countList);
```


 
