# IntentFlag

## 模块说明
IntentFlag封装了打开intent系统的业务

## 使用说明

* 判断intent和它的bundle是否为空
```
 IntentUtils.isBundleEmpty(Intent intent);
```
* 获取卸载App的意图
```
 IntentUtils.getUninstallAppIntent(String packageName);
```
* 获取打开App的意图
```
 IntentUtils.getLaunchAppIntent(Context context, String packageName);
```
* 获取App具体设置的意图
```
 IntentUtils.getAppDetailsSettingsIntent(String packageName);
```
* 获取分享文本的意图
```
 IntentUtils.getShareTextIntent(String content);
```
* 获取分享图片的意图
```
 IntentUtils.getShareImageIntent(String content, Uri uri);
```
* 获取其他应用组件的意图
```
 IntentUtils.getComponentIntent(String packageName, String className);
```
* 获取其他应用组件的意图
```
 IntentUtils.getComponentIntent(String packageName, String className, Bundle bundle);
```
* 获取关机的意图
```
 IntentUtils.getShutdownIntent();
```
* 获取直接拨号意图
```
 IntentUtils.getPhoneIntent(String phoneNumber);
```
* 获取跳至拨号界面意图
```
 IntentUtils.getDialIntent(String phoneNumber);
```
* 获取拨打电话意图
```
 IntentUtils.getCallIntent(String phoneNumber);
```
* 获取跳至发送短信界面的意图
```
 IntentUtils.getSendSmsIntent(String phoneNumber, String content);
```
* 获取拍照的意图
```
 IntentUtils.getCaptureIntent(Uri outUri);
```
* 获取选择照片的Intent
```
 IntentUtils.getPickIntentWithGallery();
```
* 获取从文件中选择照片的Intent
```
 IntentUtils.getPickIntentWithDocuments();
```
* 获取裁剪意图
```
 IntentUtils.getImageCropIntent(Uri uriFrom, Uri uriTo, int outputX, int outputY, boolean returnData);
```
* 获取屏幕录像意图
```
 IntentUtils.getImageCaptureIntent(Uri uri);
```
* 获取系统设置意图
```
 IntentUtils.getSettingIntent();
```
* 获取时间设置意图
```
 IntentUtils.getDateIntent();
```
* 获取桌面意图
```
 IntentUtils.getHomeDesktopIntent();
```
* 获取网络意图
```
 IntentUtils.getNetworkIntent();
```
* 获取GPS意图
```
 IntentUtils.getGPSIntent();
```
* 获取跳转到APP详情页意图(储存,流量,权限,通知等)
```
 IntentUtils.getAppDetailSettingIntent(Context context);
```
* 获取跳转到浏览器意图
```
 IntentUtils.getBrowserIntent(String url);
```