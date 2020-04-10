# AMapLocation

## 模块说明
AMapLocation封装了百度地图的基础业务

## 使用说明
* 初始化组件
```
AMapLocationUtils.getInstance().initLocation(context);
```

* 开启定位  
```
AMapLocationUtils.getInstance().onStart();
```
* 停止定位  
```
AMapLocationUtils.getInstance().onStop();
```

* 设置回调  
```
AMapLocationUtils.getInstance().setLocationListener(mapLocationListener);
```

* 销毁定位  
```
AMapLocationUtils.getInstance().onDestroy();
```
