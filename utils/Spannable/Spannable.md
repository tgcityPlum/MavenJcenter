# Spannable

## 模块说明
Spannable封装了富文本业务

## 使用说明

* 设置不同颜色
```
 SpannableUtils
    .getInstance()
    .setDefaultColor(int defaultColor)
    .setSpannableForColor(Context context, String message, int partLength, boolean isFromStart);
```
* 设置不同大小
```
 SpannableUtils
    .getInstance()
    .setProportion(float proportion)
    .setSpannableSize(String message, int partLength, boolean isFromStart);
```