package com.tgcity.profession.imageselector;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.ListPopupWindow;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;

import com.tgcity.profession.imageselector.adapter.FolderAdapter;
import com.tgcity.profession.imageselector.adapter.ImageGridAdapter;
import com.tgcity.profession.imageselector.bean.FolderBean;
import com.tgcity.profession.imageselector.bean.ImageBean;
import com.tgcity.profession.multiimageselector.R;
import com.tgcity.utils.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Multi image selector Fragment
 * Created by Nereo on 2015/4/7.
 * Updated by nereo on 2016/5/18.
 */
public class MultiImageSelectorFragment extends Fragment {

    public static final String TAG = "MultiImageSelectorFragment";

    private static final int REQUEST_STORAGE_WRITE_ACCESS_PERMISSION = 110;
    private static final int REQUEST_CAMERA = 100;

    private static final String KEY_TEMP_FILE = "key_temp_file";

    /**
     * Single choice
     */
    private static final int MODE_SINGLE = 0;

    /**
     * Multi choice
     */
    private static final int MODE_MULTI = 1;

    /**
     * Max image size，int，
     */
    static final String EXTRA_SELECT_COUNT = "max_select_count";
    /**
     * Select mode，{@link #MODE_MULTI} by default
     */
    static final String EXTRA_SELECT_MODE = "select_count_mode";
    /**
     * Whether show camera，true by default
     */
    static final String EXTRA_SHOW_CAMERA = "show_camera";
    /**
     * Original data set
     */
    private static final String EXTRA_DEFAULT_SELECTED_LIST = "default_list";
    /**
     * 0 显示照相机  1 显示图片  2 显示照相机和图片
     */
    static final String EXTRA_OPEN_STYLE = "open_style";

    /**
     * loaders
     */
    private static final int LOADER_ALL = 0;
    private static final int LOADER_CATEGORY = 1;

    /**
     * image result data set
     */
    private ArrayList<String> resultList = new ArrayList<>();

    /**
     * folder result data set
     */
    private ArrayList<FolderBean> mResultFolderBean = new ArrayList<>();

    private GridView mGridView;
    private Callback mCallback;

    private ImageGridAdapter mImageAdapter;
    private FolderAdapter mFolderAdapter;

    private ListPopupWindow mFolderPopupWindow;

    private TextView mCategoryText;
    private View mPopupAnchorView;

    private boolean hasFolderGened = false;

    private File mTmpFile;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            mCallback = (Callback) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException("The Activity must implement MultiImageSelectorFragment.Callback interface...");
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.mis_fragment_multi_image, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments().getInt(EXTRA_OPEN_STYLE) == 0) {
            showCameraAction();
            return;
        }
        final int mode = selectMode();
        if (mode == MODE_MULTI) {
            ArrayList<String> tmp = getArguments().getStringArrayList(EXTRA_DEFAULT_SELECTED_LIST);
            if (tmp != null && tmp.size() > 0) {
                resultList = tmp;
            }
        }
        mImageAdapter = new ImageGridAdapter(getActivity(), showCamera(), 3);
        mImageAdapter.showSelectIndicator(mode == MODE_MULTI);

        mPopupAnchorView = view.findViewById(R.id.footer);

        mCategoryText = view.findViewById(R.id.category_btn);
        mCategoryText.setText(R.string.mis_folder_all);
        mCategoryText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (mFolderPopupWindow == null) {
                    createPopupFolderList();
                }

                if (mFolderPopupWindow.isShowing()) {
                    mFolderPopupWindow.dismiss();
                } else {
                    mFolderPopupWindow.show();
                    int index = mFolderAdapter.getSelectIndex();
                    index = index == 0 ? index : index - 1;
                    mFolderPopupWindow.getListView().setSelection(index);
                }
            }
        });

        mGridView = view.findViewById(R.id.grid);
        mGridView.setAdapter(mImageAdapter);
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (mImageAdapter.isShowCamera()) {
                    if (i == 0) {
                        showCameraAction();
                    } else {
                        ImageBean imageBean = (ImageBean) adapterView.getAdapter().getItem(i);
                        selectImageFromGrid(imageBean, mode);
                    }
                } else {
                    ImageBean imageBean = (ImageBean) adapterView.getAdapter().getItem(i);
                    selectImageFromGrid(imageBean, mode);
                }
            }
        });
        mGridView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                /*if (scrollState == SCROLL_STATE_FLING) {
                    Picasso.with(view.getContext()).pauseTag(TAG);
                } else {
                    Picasso.with(view.getContext()).resumeTag(TAG);
                }*/
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });

        mFolderAdapter = new FolderAdapter(getActivity());
    }

    /**
     * Create popup ListView
     */
    private void createPopupFolderList() {
        WindowManager wm = (WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);
        int width = point.x;
        int height = (int) (point.y * (4.5f / 8.0f));
        mFolderPopupWindow = new ListPopupWindow(getActivity());
        mFolderPopupWindow.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        mFolderPopupWindow.setAdapter(mFolderAdapter);
        mFolderPopupWindow.setContentWidth(width);
        mFolderPopupWindow.setWidth(width);
        mFolderPopupWindow.setHeight(height);
        mFolderPopupWindow.setAnchorView(mPopupAnchorView);
        mFolderPopupWindow.setModal(true);
        mFolderPopupWindow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                mFolderAdapter.setSelectIndex(i);

                final int index = i;
                final AdapterView v = adapterView;

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mFolderPopupWindow.dismiss();

                        if (index == 0) {
                            getActivity().getSupportLoaderManager().restartLoader(LOADER_ALL, null, mLoaderCallback);
                            mCategoryText.setText(R.string.mis_folder_all);
                            if (showCamera()) {
                                mImageAdapter.setShowCamera(true);
                            } else {
                                mImageAdapter.setShowCamera(false);
                            }
                        } else {
                            FolderBean folderBean = (FolderBean) v.getAdapter().getItem(index);
                            if (null != folderBean) {
                                mImageAdapter.setData(folderBean.getImageBeans());
                                mCategoryText.setText(folderBean.getName());
                                if (resultList != null && resultList.size() > 0) {
                                    mImageAdapter.setDefaultSelected(resultList);
                                }
                            }
                            mImageAdapter.setShowCamera(false);
                        }

                        mGridView.smoothScrollToPosition(0);
                    }
                }, 100);

            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(KEY_TEMP_FILE, mTmpFile);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (savedInstanceState != null) {
            mTmpFile = (File) savedInstanceState.getSerializable(KEY_TEMP_FILE);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // 拍照模式下 不加载
        if (getArguments().getInt(EXTRA_OPEN_STYLE) == 0) {
            return;
        }
        // load image data
        getActivity().getSupportLoaderManager().initLoader(LOADER_ALL, null, mLoaderCallback);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CAMERA) {
            if (resultCode == Activity.RESULT_OK) {
                if (mTmpFile != null) {
                    if (mCallback != null) {
                        mCallback.onCameraShot(mTmpFile);
                    }
                }
            } else {
                // delete tmp file
                while (mTmpFile != null && mTmpFile.exists()) {
                    boolean success = mTmpFile.delete();
                    if (success) {
                        mTmpFile = null;
                    }
                }

                getActivity().finish();
            }
        }
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        if (mFolderPopupWindow != null) {
            if (mFolderPopupWindow.isShowing()) {
                mFolderPopupWindow.dismiss();
            }
        }
        super.onConfigurationChanged(newConfig);
    }

    /**
     * Open camera
     */
    private void showCameraAction() {
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermission(getString(R.string.mis_permission_rationale_write_storage));
        } else {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
                try {
                    mTmpFile = FileUtils.createTmpFile(getActivity());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (mTmpFile != null && mTmpFile.exists()) {
                    Uri mImageUri;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        ///7.0以上要通过FileProvider将File转化为Uri
                        mImageUri = FileProvider.getUriForFile(getActivity(), getActivity().getPackageName() + ".fileprovider", mTmpFile);
                    } else {
                        //7.0以下则直接使用Uri的fromFile方法将File转化为Uri
                        mImageUri = Uri.fromFile(mTmpFile);
                    }

                    intent.putExtra(MediaStore.EXTRA_OUTPUT, mImageUri);
                    startActivityForResult(intent, REQUEST_CAMERA);
                } else {
                    Toast.makeText(getActivity(), R.string.mis_error_image_not_exist, Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getActivity(), R.string.mis_msg_no_camera, Toast.LENGTH_SHORT).show();
            }

        }
    }

    private void requestPermission(String rationale) {
        if (shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            new AlertDialog.Builder(getContext())
                    .setTitle(R.string.mis_permission_dialog_title)
                    .setMessage(rationale)
                    .setPositiveButton(R.string.mis_permission_dialog_ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, MultiImageSelectorFragment.REQUEST_STORAGE_WRITE_ACCESS_PERMISSION);
                        }
                    })
                    .setNegativeButton(R.string.mis_permission_dialog_cancel, null)
                    .create().show();
        } else {
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, MultiImageSelectorFragment.REQUEST_STORAGE_WRITE_ACCESS_PERMISSION);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_STORAGE_WRITE_ACCESS_PERMISSION) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                showCameraAction();
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    /**
     * notify callback
     *
     * @param imageBean imageBean data
     */
    private void selectImageFromGrid(ImageBean imageBean, int mode) {
        if (imageBean != null) {
            if (mode == MODE_MULTI) {
                if (resultList.contains(imageBean.getPath())) {
                    resultList.remove(imageBean.getPath());
                    if (mCallback != null) {
                        mCallback.onImageUnselected(imageBean.getPath());
                    }
                } else {
                    if (selectImageCount() == resultList.size()) {
                        Toast.makeText(getActivity(), R.string.mis_msg_amount_limit, Toast.LENGTH_SHORT).show();
                        return;
                    }
                    resultList.add(imageBean.getPath());
                    if (mCallback != null) {
                        mCallback.onImageSelected(imageBean.getPath());
                    }
                }
                mImageAdapter.select(imageBean);
            } else if (mode == MODE_SINGLE) {
                if (mCallback != null) {
                    mCallback.onSingleImageSelected(imageBean.getPath());
                }
            }
        }
    }

    private LoaderManager.LoaderCallbacks<Cursor> mLoaderCallback = new LoaderManager.LoaderCallbacks<Cursor>() {

        private final String[] IMAGE_PROJECTION = {
                MediaStore.Images.Media.DATA,
                MediaStore.Images.Media.DISPLAY_NAME,
                MediaStore.Images.Media.DATE_ADDED,
                MediaStore.Images.Media.MIME_TYPE,
                MediaStore.Images.Media.SIZE,
                MediaStore.Images.Media._ID};

        @Override
        public Loader<Cursor> onCreateLoader(int id, Bundle args) {
            CursorLoader cursorLoader = null;
            if (id == LOADER_ALL) {
                cursorLoader = new CursorLoader(getActivity(),
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, IMAGE_PROJECTION,
                        IMAGE_PROJECTION[4] + ">0 AND " + IMAGE_PROJECTION[3] + "=? OR " + IMAGE_PROJECTION[3] + "=? ",
                        new String[]{"image/jpeg", "image/png"}, IMAGE_PROJECTION[2] + " DESC");
            } else if (id == LOADER_CATEGORY) {
                cursorLoader = new CursorLoader(getActivity(),
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, IMAGE_PROJECTION,
                        IMAGE_PROJECTION[4] + ">0 AND " + IMAGE_PROJECTION[0] + " like '%" + args.getString("path") + "%'",
                        null, IMAGE_PROJECTION[2] + " DESC");
            }
            return cursorLoader;
        }

        private boolean fileExist(String path) {
            if (!TextUtils.isEmpty(path)) {
                return new File(path).exists();
            }
            return false;
        }

        @Override
        public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
            if (data != null) {
                if (data.getCount() > 0) {
                    List<ImageBean> imageBeans = new ArrayList<>();
                    data.moveToFirst();
                    do {
                        String path = data.getString(data.getColumnIndexOrThrow(IMAGE_PROJECTION[0]));
                        String name = data.getString(data.getColumnIndexOrThrow(IMAGE_PROJECTION[1]));
                        long dateTime = data.getLong(data.getColumnIndexOrThrow(IMAGE_PROJECTION[2]));
                        if (!fileExist(path)) {
                            continue;
                        }
                        ImageBean imageBean = null;
                        if (!TextUtils.isEmpty(name)) {
                            imageBean = new ImageBean(path, name, dateTime);
                            imageBeans.add(imageBean);
                        }
                        if (!hasFolderGened) {
                            // get all folder data
                            File folderFile = new File(path).getParentFile();
                            if (folderFile != null && folderFile.exists()) {
                                String fp = folderFile.getAbsolutePath();
                                FolderBean folderByPath = getFolderByPath(fp);
                                if (folderByPath == null) {
                                    FolderBean folderBean = new FolderBean();
                                    folderBean.setName(folderFile.getName());
                                    folderBean.setPath(fp);
                                    folderBean.setCover(imageBean);
                                    List<ImageBean> imageBeanList = new ArrayList<>();
                                    imageBeanList.add(imageBean);
                                    folderBean.setImageBeans(imageBeanList);
                                    mResultFolderBean.add(folderBean);
                                } else {
                                    folderByPath.getImageBeans().add(imageBean);
                                }
                            }
                        }

                    } while (data.moveToNext());

                    mImageAdapter.setData(imageBeans);
                    if (resultList != null && resultList.size() > 0) {
                        mImageAdapter.setDefaultSelected(resultList);
                    }
                    if (!hasFolderGened) {
                        mFolderAdapter.setData(mResultFolderBean);
                        hasFolderGened = true;
                    }
                }
            }
        }

        @Override
        public void onLoaderReset(Loader<Cursor> loader) {

        }
    };

    private FolderBean getFolderByPath(String path) {
        if (mResultFolderBean != null) {
            for (FolderBean folderBean : mResultFolderBean) {
                if (TextUtils.equals(folderBean.getPath(), path)) {
                    return folderBean;
                }
            }
        }
        return null;
    }

    private boolean showCamera() {
        return getArguments() == null || getArguments().getBoolean(EXTRA_SHOW_CAMERA, true);
    }

    private int selectMode() {
        return getArguments() == null ? MODE_MULTI : getArguments().getInt(EXTRA_SELECT_MODE);
    }

    private int selectImageCount() {
        return getArguments() == null ? MultiImageSelectorActivity.mDefaultCount : getArguments().getInt(EXTRA_SELECT_COUNT);
    }

    /**
     * Callback for host activity
     */
    public interface Callback {
        void onSingleImageSelected(String path);

        void onImageSelected(String path);

        void onImageUnselected(String path);

        void onCameraShot(File imageFile);
    }
}
