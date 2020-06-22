# Spinner

## 模块说明
Spinner封装了自定义的下拉框业务

## 使用说明
* xml布局  
### xml中的使用
```
<com.tgcity.widget.spinner.MaterialSpinner
    android:id="@+id/msKind"
    android:layout_width="@dimen/dp_100"
    android:layout_height="wrap_content"
    android:layout_marginLeft="@dimen/dp_16"
    app:layout_constraintBottom_toBottomOf="@id/tvKindTitle"
    app:layout_constraintLeft_toRightOf="@id/tvKindTitle"
    app:layout_constraintTop_toTopOf="@id/tvKindTitle"
    app:ms_background_color="@color/color_ui_bg_F9F9F9"
    app:ms_dropdown_max_height="@dimen/dp_300"
    app:ms_hint="@string/btn_please_select"
    app:ms_text_color="@color/color_333333" />
```
### styleable的使用
|  样式   | 内容  |   
| :-----| ----: |  
| ms_is_right  | 文案是否居右 |   
| ms_arrow_tint  | 箭头颜色 |   
| ms_hide_arrow  | 是否隐藏箭头 |   
| ms_background_color  | 背景色 |   
| ms_text_color  | 文案颜色 |   
| ms_dropdown_max_height  | 下拉框最大高度 |   
| ms_dropdown_height  | 下拉框高度 |   
| ms_background_selector  | 背景色选择器 |   
| ms_is_background_selector  | 是否显示背景色 |   
| ms_padding_top  | 居上间距 |   
| ms_padding_left  | 居左间距 |   
| ms_padding_bottom  | 居下间距 |   
| ms_padding_right  | 居右间距 |   
| ms_popup_padding_top  | 弹框居上间距 |   
| ms_popup_padding_left  | 弹框居左间距 |   
| ms_popup_padding_bottom  | 弹框居下间距 |   
| ms_padding_right  | 弹框居右间距 |   
| ms_hint  | 默认文案 |   
| ms_hint_color  | 默认文案颜色 |   

* 使用方法  
```  
private val kindList = listOf("雷管", "枪支", "毒品", "违禁物品", "野生动物", "其它")
msKind.setItems(kindList)  
msKind.setOnItemSelectedListener { view, position, id, item ->  }
```
