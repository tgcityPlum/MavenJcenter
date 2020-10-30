# EditViewClear

## 模块说明
EditViewClear封装了EditView的清除操作

##  使用说明

* .class file
```
   etName.onFocusChangeListener = ClearViewFocusChangeListener(ivAccountCancel)

   etName.addTextChangedListener(object : ClearViewTextWatcher(ivAccountCancel) {
        override fun afterTextChanged(s: Editable) {
        btnLogin.isEnabled = !TextUtils.isEmpty(StringUtils.getStringFromView(etPassword)) && s.length > DigitalUtils.LEVEL_0
        }
   })
```


 
