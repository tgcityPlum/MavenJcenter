# Update

## 模块说明
Update封装了自更新业务

## 使用说明

```
    /*
      * @param isForceUpdate             是否强制更新
      * @param desc                      更新文案
      * @param url                       下载链接
      * @param apkFileName               apk下载文件路径名称
      * @param packName                  包名
    */
    UpdateFragment.showFragment(MainActivity.this, true, downloadPath, UpdateFragment.APK_NAME, desc, BuildConfig.APPLICATION_ID);
```
