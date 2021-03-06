# ImagePicker

## 模块说明
ImagePicker封装了图片选择器

## 使用说明

* Application中配置
```
//设置图片选择器
ImagePickerManager.init();
```
* class中的配置
    1. 全局配置
        ```
        private var imageItemList: ArrayList<ImageItem>? = null
        private val limitSize = 3
        private var pictureAdapter: NinePictureAdapter? = null
        ```
    2. 配置适配器
        ```
        override fun init() {
           imageItemList = ArrayList()
           pictureAdapter = NinePictureAdapter(activity, imageItemList)
           //修改 删除原来的图片
           pictureAdapter!!.setLimiteSize(limitSize)
           cgvIcon!!.adapter = pictureAdapter
        }
        ```
    3. 选择图片
        ```
        private fun selectPicture() {
           ImagePicker.getInstance().isMultiMode = false
           ImagePicker.getInstance().selectLimit = limitSize

           startActivityForResult(Intent(activity, ImageGridActivity::class.java), DigitalUtils.REQUEST_CODE_CAMERA)
       }
    4. 预览图片
        ```
        private fun previewPicture() {
           if (imageItemList == null || imageItemList!!.size == 0) {
               ToastUtils.showShortToast(context, "图片格式有误，无法预览")
               return
           }
           val intentPreview = Intent(activity, ImagePreviewDelActivity::class.java)
           intentPreview.putExtra(ImagePicker.EXTRA_IMAGE_ITEMS, imageItemList)
           intentPreview.putExtra(ImagePicker.EXTRA_SELECTED_IMAGE_POSITION, 0)
           intentPreview.putExtra(ImagePicker.EXTRA_FROM_ITEMS, true)
           startActivityForResult(intentPreview, ImagePicker.REQUEST_CODE_PREVIEW)
       }
        ```
* xml中配置
```
<!--添加的列表-->
<androidx.recyclerview.widget.RecyclerView
    android:id="@+id/srvPeerPeople"
    android:layout_width="@dimen/dp_0"
    android:layout_height="wrap_content"
    android:layout_marginTop="@dimen/dp_16"
    android:overScrollMode="never"
    android:scrollbars="vertical"
    app:layout_constraintLeft_toLeftOf="parent"
    app:layout_constraintRight_toRightOf="parent"
    app:layout_constraintTop_toBottomOf="@id/tvAdd" />
```
