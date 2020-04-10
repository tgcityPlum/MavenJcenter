# ClearView

## 模块说明
ClearView封装了销毁控件的业务

## 使用说明
* 清理所有控件
```
ClearViewUtils.clearAll(view);
```
* 清理控件事件
```
ClearViewUtils.clearListener(view);
```
* 清理imageview中的图片
```
ClearViewUtils.releaseImageViewResource(imageView,viewGroup);
```