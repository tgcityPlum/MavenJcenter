# File

## 模块说明
File封装了常量数字业务

##  使用步骤

* 根据文件路径获取文件
```
 FileUtils.getFileByPath(filePath);
```
* 判断文件是否存在
```
 FileUtils.createOrExistsFile(filePath);
```
* 判断目录是否存在
```
 FileUtils.createOrExistsDir(file);
```
* 文件转base64
```
 FileUtils.fileToBase64(file);
```
* 保存图片
```
 FileUtils.saveBitmap(Bitmap src, String filePath, Bitmap.CompressFormat format, boolean recycle);
```




