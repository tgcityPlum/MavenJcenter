# StartActivity

## 模块说明
Spannable封装了富文本业务

## 使用说明

* 直接跳转界面
```
 StartActivityUtils.getInstance()
    .setContext(context)
    .setClass(XXX.class)
    .putExtra(name, value)
    .startActivity();
```
* 跳转界面返回时并更新界面
```
 StartActivityUtils.getInstance()
    .setActivity(Activity activity)
    .setClass(XXX.class)
    .putExtra(name, value)
    .startActivityForResult(int requestCode);
```