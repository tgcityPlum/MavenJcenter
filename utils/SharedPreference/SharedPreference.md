# SharedPreference

## 模块说明
SharedPreference封装了缓存的业务

## 使用说明

* 创建SharedPreferences
```
 SharedPreferencesUtils sharedPreferencesUtils = new SharedPreferencesUtils(Context context, String spName);
 SharedPreferencesUtils sharedPreferencesUtils = new SharedPreferencesUtils(Context context, String spName, int mode);
```
* 存储数据
```
 sharedPreferencesUtils.put(String key, @Nullable String value);
```
* 取出数据
```
 sharedPreferencesUtils.getString(String key);
```