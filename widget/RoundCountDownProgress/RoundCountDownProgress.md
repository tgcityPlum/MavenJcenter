##  简介
这是一个圆形倒计时控件，常见的使用场景是启动页右上角的倒计时
##  使用步骤
1.xml file
```
<com.tgcity.widget.RoundCountDownProgress
    android:id="@+id/progressView"
    android:layout_width="@dimen/dp_40"
    android:layout_height="@dimen/dp_40"
    android:layout_marginTop="@dimen/dp_20"
    android:layout_marginRight="@dimen/dp_20"
    android:textSize="@dimen/sp_13"
    app:layout_constraintRight_toRightOf="parent"
    app:layout_constraintTop_toTopOf="parent"
    app:circSolidColor="@color/color_D3D3D3"
    app:circFrameColor="@color/color_C5C5C5"
    app:progressColor="@color/color_view_blue" />
```
 2.class file
```
   @BindView(R.id.progressView)
   RoundCountDownProgress progressView;
   
   public void initView() {
        progressView.setTimeMillis(1000);
        progressView.setProgressType(RoundCountDownProgress.ProgressType.COUNT_BACK);
        progressView.start();
   }
   
   public void setListener() {
        progressView.setOnClickListener(v -> onProgressClick());
   
        progressView.setProgressListener(progress -> {
            if (progress == 0) {
                onProgressClick();
            }
        });
   }
   
   public void finish() {
        progressView.stop();
        super.finish();
   }
```


 
