# FragmentNavigator

## 模块说明
FragmentNavigator封装了Fragment的使用方法

## 使用说明
* 构造方法
```
FragmentNavigatorUtils fragmentNavigatorUtils = new FragmentNavigatorUtils(FragmentManager fm, int id);
```

* 设置初始Fragment路径
```
void setCurrentFragmentTag(String simpleName);
```

* 获取当前的Fragment标识
```
String getCurrentFragmentTag()
```

* 显示指定的Fragment
```
void navigateTo(Fragment fragment, String tag)
````

* 返回上一个Fragment
```
void navigateBack()
```

* 显示Fragment
```
void navigateToSelf()
```