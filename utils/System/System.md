# System

## 模块说明
System封装了提供获取系统方法的业务

## 使用说明

* 判断App是否是debug环境
```
 SystemUtils.isApkDebug(Context context);
```
* 判断App是否处于后台运行
```
 SystemUtils.isAppInBackground(Context context);
```
* 获取应用名称
```
 SystemUtils.getAppName(Context context);
```
* 是否是主线程
```
 SystemUtils.isMainProcess(Context context);
```