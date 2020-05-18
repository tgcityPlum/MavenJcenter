# Softhidekeyboard

## 模块说明
Softhidekeyboard封装了解决键盘档住输入框的业务

## 使用说明

* 创建SoftHideKeyBoardUtil
```
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SoftHideKeyBoardUtil.assistActivity(this);
    }
```
