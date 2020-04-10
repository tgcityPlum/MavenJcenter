# String

## 模块说明
String封装了富文本业务

## 使用说明

* 判断字符串是否为null或长度为0
```
StringUtils.isEmpty(CharSequence s);
```
* 格式化string
```
StringUtils.dislodgeEmptyToEmpty(String s);
StringUtils.dislodgeEmptyToZero(String s);
StringUtils.dislodgeEmptyToCustomize(String message, String format);
```
* 将double转为数值
```
StringUtils.double2String(double d, int num);
```