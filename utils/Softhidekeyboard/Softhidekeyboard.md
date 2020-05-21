# Softhidekeyboard

## 模块说明
Softhidekeyboard封装了解决键盘档住输入框的业务

## 使用说明

* 创建SoftHideKeyBoardUtil，处理软键盘和沉浸式冲突问题
```
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SoftHideKeyBoardUtil.getInstall(this);
    }
```
* 隐藏界面然键盘
```
    SoftHideKeyBoardUtil.hideInputKeyboard(activity);
```
* 换起界面然键盘
```
    SoftHideKeyBoardUtil.showInputKeyboard(activity);
```
