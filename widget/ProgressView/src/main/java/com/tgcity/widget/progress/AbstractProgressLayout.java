package com.tgcity.widget.progress;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tgcity.utils.StrongGradientButton;
import com.tgcity.utils.imageloader.ImageHelper;
import com.tgcity.widget.progressview.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author TGCity
 */
public abstract class AbstractProgressLayout extends RelativeLayout {

    private String TAG_LOADING = "ProgressLayout.TAG_LOADING";
    private String TAG_EMPTY = "ProgressLayout.TAG_EMPTY";
    private String TAG_ERROR = "ProgressLayout.TAG_ERROR";
    private String TAG_CUSTOM = "ProgressLayout.CUSTOM";

    String CONTENT = "type_content";
    String LOADING = "type_loading";
    String EMPTY = "type_empty";
    String ERROR = "type_error";
    String CUSTOM = "type_custom";

    LayoutInflater inflater;
    View view;
    LayoutParams layoutParams;
    Drawable currentBackground;

    List<View> contentViews = new ArrayList<>();

    RelativeLayout loadingStateRelativeLayout;

    RelativeLayout emptyStateRelativeLayout;
    ImageView emptyStateImageView;
    TextView emptyStateTitleTextView;
    TextView emptyStateContentTextView;


    RelativeLayout errorStateRelativeLayout;
    ImageView errorStateImageView;
    TextView errorStateTitleTextView;
    TextView errorStateContentTextView;
    StrongGradientButton errorStateButton;


    View customView;

    int loadingStateProgressBarWidth;
    int loadingStateProgressBarHeight;
    int loadingStateBackgroundColor;

    int emptyStateImageWidth;
    int emptyStateImageHeight;
    int emptyStateTitleTextSize;
    int emptyStateContentTextSize;
    int emptyStateTitleTextColor;
    int emptyStateContentTextColor;
    int emptyStateBackgroundColor;

    int errorStateImageWidth;
    int errorStateImageHeight;
    int errorStateTitleTextSize;
    int errorStateContentTextSize;
    int errorStateTitleTextColor;
    int errorStateContentTextColor;
    int errorStateButtonTextColor;
    int errorStateBackgroundColor;

    protected String state = CONTENT;
    private ImageView imageLoading;

    public void clear() {
        inflater = null;
        view = null;
        layoutParams = null;
        currentBackground = null;
        if (contentViews != null) {
            contentViews.clear();
        }
        contentViews = null;
        loadingStateRelativeLayout = null;
        emptyStateRelativeLayout = null;
        releaseImageViewResource(emptyStateImageView, this);
        emptyStateTitleTextView = null;
        emptyStateContentTextView = null;


        errorStateRelativeLayout = null;
        releaseImageViewResource(errorStateImageView, this);
        errorStateTitleTextView = null;
        errorStateContentTextView = null;
        errorStateButton = null;

        loadingStateProgressBarWidth = 0;
        loadingStateProgressBarHeight = 0;
        loadingStateBackgroundColor = 0;

        emptyStateImageWidth = 0;
        emptyStateImageHeight = 0;
        emptyStateTitleTextSize = 0;
        emptyStateContentTextSize = 0;
        emptyStateTitleTextColor = 0;
        emptyStateContentTextColor = 0;
        emptyStateBackgroundColor = 0;

        errorStateImageWidth = 0;
        errorStateImageHeight = 0;
        errorStateTitleTextSize = 0;
        errorStateContentTextSize = 0;
        errorStateTitleTextColor = 0;
        errorStateContentTextColor = 0;
        errorStateButtonTextColor = 0;
        errorStateBackgroundColor = 0;

        state = null;
        releaseImageViewResource(imageLoading, this);
    }


    public AbstractProgressLayout(Context context) {
        super(context);
    }

    public AbstractProgressLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public AbstractProgressLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs);
    }

    public int dpTpPx(float value) {
        DisplayMetrics dm = getResources().getDisplayMetrics();
        return (int) (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value, dm) + 0.5);
    }

    private void init(AttributeSet attrs) {
        inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.pv_AbstractProgressLayout);

        //Loading state attrs
        loadingStateProgressBarWidth =
                typedArray.getDimensionPixelSize(R.styleable.pv_AbstractProgressLayout_pv_loadingProgressBarWidth, dpTpPx(90));

        loadingStateProgressBarHeight =
                typedArray.getDimensionPixelSize(R.styleable.pv_AbstractProgressLayout_pv_loadingProgressBarHeight, dpTpPx(90));

        loadingStateBackgroundColor =
                typedArray.getColor(R.styleable.pv_AbstractProgressLayout_pv_loadingBackgroundColor, Color.TRANSPARENT);

        //Empty state attrs
        emptyStateImageWidth =
                typedArray.getDimensionPixelSize(R.styleable.pv_AbstractProgressLayout_pv_emptyImageWidth, dpTpPx(120));

        emptyStateImageHeight =
                typedArray.getDimensionPixelSize(R.styleable.pv_AbstractProgressLayout_pv_emptyImageHeight, dpTpPx(120));

        emptyStateTitleTextSize =
                typedArray.getDimensionPixelSize(R.styleable.pv_AbstractProgressLayout_pv_emptyTitleTextSize, 15);

        emptyStateContentTextSize =
                typedArray.getDimensionPixelSize(R.styleable.pv_AbstractProgressLayout_pv_emptyContentTextSize, 13);

        emptyStateTitleTextColor =
                typedArray.getColor(R.styleable.pv_AbstractProgressLayout_pv_emptyTitleTextColor, 0xff666666);

        emptyStateContentTextColor =
                typedArray.getColor(R.styleable.pv_AbstractProgressLayout_pv_emptyContentTextColor, 0xff999999);

        emptyStateBackgroundColor =
                typedArray.getColor(R.styleable.pv_AbstractProgressLayout_pv_emptyBackgroundColor, Color.TRANSPARENT);

        //Error state attrs
        errorStateImageWidth =
                typedArray.getDimensionPixelSize(R.styleable.pv_AbstractProgressLayout_pv_errorImageWidth, dpTpPx(120));

        errorStateImageHeight =
                typedArray.getDimensionPixelSize(R.styleable.pv_AbstractProgressLayout_pv_errorImageHeight, dpTpPx(120));

        errorStateTitleTextSize =
                typedArray.getDimensionPixelSize(R.styleable.pv_AbstractProgressLayout_pv_errorTitleTextSize, 15);

        errorStateContentTextSize =
                typedArray.getDimensionPixelSize(R.styleable.pv_AbstractProgressLayout_pv_errorContentTextSize, 13);

        errorStateTitleTextColor =
                typedArray.getColor(R.styleable.pv_AbstractProgressLayout_pv_errorTitleTextColor, 0xff666666);

        errorStateContentTextColor =
                typedArray.getColor(R.styleable.pv_AbstractProgressLayout_pv_errorContentTextColor, 0xff999999);

        errorStateButtonTextColor =
                typedArray.getColor(R.styleable.pv_AbstractProgressLayout_pv_errorButtonTextColor, 0xffFF6C4B);

        errorStateBackgroundColor =
                typedArray.getColor(R.styleable.pv_AbstractProgressLayout_pv_errorBackgroundColor, Color.TRANSPARENT);

        typedArray.recycle();

        currentBackground = this.getBackground();
    }

    @Override
    public void addView(@NonNull View child, int index, ViewGroup.LayoutParams params) {
        super.addView(child, index, params);
        if (child.getTag() == null || (!child.getTag().equals(TAG_LOADING) &&
                !child.getTag().equals(TAG_EMPTY) && !child.getTag().equals(TAG_ERROR) && !child.getTag().equals(TAG_CUSTOM))) {
            contentViews.add(child);
        }
    }


    public void showCustom() {
        switchState(CUSTOM, null, null, null, null, null, Collections.<Integer>emptyList());
    }

    /**
     * Hide all other states and showLruCache content
     */
    public void showContent() {
        switchState(CONTENT, null, null, null, null, null, Collections.<Integer>emptyList());
    }

    /**
     * Hide all other states and showLruCache content
     *
     * @param skipIds Ids of views not to showLruCache
     */
    public void showContent(List<Integer> skipIds) {
        switchState(CONTENT, null, null, null, null, null, skipIds);
    }

    /**
     * Hide content and showLruCache the progress bar
     */
    public void showLoading() {
        switchState(LOADING, null, null, null, null, null, Collections.<Integer>emptyList());
    }

    /**
     * Hide content and showLruCache the progress bar
     *
     * @param skipIds Ids of views to not hide
     */
    public void showLoading(List<Integer> skipIds) {
        switchState(LOADING, null, null, null, null, null, skipIds);
    }

    /**
     * Show empty view when there are not data to showLruCache
     *
     * @param emptyImageDrawable Drawable to showLruCache
     * @param emptyTextTitle     Title of the empty view to showLruCache
     * @param emptyTextContent   Content of the empty view to showLruCache
     */
    public void showEmpty(Drawable emptyImageDrawable, String emptyTextTitle, String emptyTextContent) {
        try {
            switchState(EMPTY, emptyImageDrawable, emptyTextTitle, emptyTextContent, null, null, Collections.<Integer>emptyList());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Show empty view when there are not data to showLruCache
     *
     * @param emptyImageDrawable Drawable to showLruCache
     * @param emptyTextTitle     Title of the empty view to showLruCache
     * @param emptyTextContent   Content of the empty view to showLruCache
     * @param skipIds            Ids of views to not hide
     */
    public void showEmpty(Drawable emptyImageDrawable, String emptyTextTitle, String emptyTextContent, List<Integer> skipIds) {
        switchState(EMPTY, emptyImageDrawable, emptyTextTitle, emptyTextContent, null, null, skipIds);
    }

    /**
     * Show error view with a button when something goes wrong and prompting the user to try again
     *
     * @param errorImageDrawable Drawable to showLruCache
     * @param errorTextTitle     Title of the error view to showLruCache
     * @param errorTextContent   Content of the error view to showLruCache
     * @param errorButtonText    Text on the error view button to showLruCache
     * @param onClickListener    Listener of the error view button
     */
    public void showError(Drawable errorImageDrawable, String errorTextTitle, String errorTextContent, String errorButtonText, OnClickListener onClickListener) {
         showError( errorImageDrawable,  errorTextTitle,  errorTextContent,  errorButtonText,  onClickListener,  null);
    }

    public void showError(Drawable errorImageDrawable, String errorTextTitle, String errorTextContent, String errorButtonText, OnClickListener onClickListener, OnProgressButtonCallBack onProgressButtonCallBack) {
        try {
            switchState(ERROR, errorImageDrawable, errorTextTitle, errorTextContent, errorButtonText, onClickListener, Collections.<Integer>emptyList());
            if (onProgressButtonCallBack != null && errorStateButton != null) {
                onProgressButtonCallBack.onCallComplete(errorStateButton);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Show error view with a button when something goes wrong and prompting the user to try again
     *
     * @param errorImageDrawable Drawable to showLruCache
     * @param errorTextTitle     Title of the error view to showLruCache
     * @param errorTextContent   Content of the error view to showLruCache
     * @param errorButtonText    Text on the error view button to showLruCache
     * @param onClickListener    Listener of the error view button
     * @param skipIds            Ids of views to not hide
     */
    public void showErrorSkip(Drawable errorImageDrawable, String errorTextTitle, String errorTextContent, String errorButtonText, OnClickListener onClickListener, List<Integer> skipIds) {
        switchState(ERROR, errorImageDrawable, errorTextTitle, errorTextContent, errorButtonText, onClickListener, skipIds);
    }

    /**
     * Get which state is set
     *
     * @return State
     */
    public String getState() {
        return state;
    }

    /**
     * Check if content is shown
     *
     * @return boolean
     */
    public boolean isContent() {
        return state.equals(CONTENT);
    }

    /**
     * Check if loading state is shown
     *
     * @return boolean
     */
    public boolean isLoading() {
        return state.equals(LOADING);
    }

    /**
     * Check if empty state is shown
     *
     * @return boolean
     */
    public boolean isEmpty() {
        return state.equals(EMPTY);
    }

    /**
     * Check if error state is shown
     *
     * @return boolean
     */
    public boolean isError() {
        return state.equals(ERROR);
    }

    private void switchState(String state, Drawable drawable, String errorText, String errorTextContent,
                             String errorButtonText, OnClickListener onClickListener, List<Integer> skipIds) {
        if (state == null) {
            return;
        }
        this.state = state;
        if (state.equals(CONTENT)) {
            //Hide all state views to display content
            hideLoadingView();
            hideEmptyView();
            hideErrorView();
            hideCustomView();
            setContentVisibility(true, skipIds);
        } else if (state.equals(LOADING)) {
            hideEmptyView();
            hideErrorView();
            setLoadingView();
            hideCustomView();
            setContentVisibility(false, skipIds);
        } else if (state.equals(EMPTY)) {
            hideLoadingView();
            hideErrorView();
            setEmptyView();
            hideCustomView();
            emptyStateImageView.setImageDrawable(drawable);
            emptyStateTitleTextView.setText(errorText);
            emptyStateContentTextView.setText(errorTextContent);
            setContentVisibility(false, skipIds);
        } else if (state.equals(ERROR)) {
            hideLoadingView();
            hideEmptyView();
            setErrorView();
            hideCustomView();
            errorStateImageView.setImageDrawable(drawable);
            errorStateTitleTextView.setText(errorText);
            errorStateContentTextView.setText(errorTextContent);
            errorStateButton.setText(errorButtonText);
            errorStateButton.setOnClickListener(onClickListener);
            setContentVisibility(false, skipIds);
        } else if (state.equals(CUSTOM)) {
            hideEmptyView();
            hideErrorView();
            hideLoadingView();
            setCustomView();
            setContentVisibility(false, skipIds);
        }
    }

    private void setLoadingView() {
        if (loadingStateRelativeLayout == null) {
            view = inflater.inflate(R.layout.pv_progress_loading_view, null);
            loadingStateRelativeLayout = view.findViewById(R.id.loadingStateRelativeLayout);
            loadingStateRelativeLayout.setTag(TAG_LOADING);

            imageLoading = view.findViewById(R.id.image_loading);

            ImageHelper.display(imageLoading, R.drawable.loading_dh);

            imageLoading.getLayoutParams().width = loadingStateProgressBarWidth;
            imageLoading.getLayoutParams().height = loadingStateProgressBarHeight;
            imageLoading.requestLayout();

            //Set background color if not TRANSPARENT
            if (loadingStateBackgroundColor != Color.TRANSPARENT) {
                this.setBackgroundColor(loadingStateBackgroundColor);
            }

            layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
            layoutParams.addRule(CENTER_IN_PARENT);

            addView(loadingStateRelativeLayout, layoutParams);
        } else {
            loadingStateRelativeLayout.setVisibility(VISIBLE);
        }
    }

    private void setEmptyView() {
        if (emptyStateRelativeLayout == null) {
            view = inflater.inflate(R.layout.pv_progress_empty_view, null);
            emptyStateRelativeLayout = view.findViewById(R.id.emptyStateRelativeLayout);
            emptyStateRelativeLayout.setTag(TAG_EMPTY);

            emptyStateImageView = view.findViewById(R.id.emptyStateImageView);
            emptyStateTitleTextView = view.findViewById(R.id.emptyStateTitleTextView);
            emptyStateContentTextView = view.findViewById(R.id.emptyStateContentTextView);

            //Set empty state image width and height
            emptyStateImageView.getLayoutParams().width = emptyStateImageWidth;
            emptyStateImageView.getLayoutParams().height = emptyStateImageHeight;
            emptyStateImageView.requestLayout();

            emptyStateTitleTextView.setTextSize(emptyStateTitleTextSize);
            emptyStateContentTextView.setTextSize(emptyStateContentTextSize);
            emptyStateTitleTextView.setTextColor(emptyStateTitleTextColor);
//            emptyStateContentTextView.setTextColor(emptyStateContentTextColor);

            //Set background color if not TRANSPARENT
            if (emptyStateBackgroundColor != Color.TRANSPARENT) {
                this.setBackgroundColor(emptyStateBackgroundColor);
            }

            layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
            layoutParams.addRule(CENTER_IN_PARENT);

            addView(emptyStateRelativeLayout, layoutParams);
        } else {
            emptyStateRelativeLayout.setVisibility(VISIBLE);
        }
    }

    private void setErrorView() {
        if (errorStateRelativeLayout == null) {
            view = inflater.inflate(R.layout.pv_progress_error_view, null);
            errorStateRelativeLayout = view.findViewById(R.id.errorStateRelativeLayout);
            errorStateRelativeLayout.setTag(TAG_ERROR);

            errorStateImageView = view.findViewById(R.id.errorStateImageView);
            errorStateTitleTextView = view.findViewById(R.id.errorStateTitleTextView);
            errorStateContentTextView = view.findViewById(R.id.errorStateContentTextView);
            errorStateButton = view.findViewById(R.id.errorStateButton);

            //Set error state image width and height
            errorStateImageView.getLayoutParams().width = errorStateImageWidth;
            errorStateImageView.getLayoutParams().height = errorStateImageHeight;
            errorStateImageView.requestLayout();

            errorStateTitleTextView.setTextSize(errorStateTitleTextSize);
            errorStateContentTextView.setTextSize(errorStateContentTextSize);
            errorStateTitleTextView.setTextColor(errorStateTitleTextColor);
            errorStateContentTextView.setTextColor(errorStateContentTextColor);
//            errorStateButton.setTextColor(errorStateButtonTextColor);

            //Set background color if not TRANSPARENT
            if (errorStateBackgroundColor != Color.TRANSPARENT) {
                this.setBackgroundColor(errorStateBackgroundColor);
            }

            layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
            layoutParams.addRule(CENTER_IN_PARENT);

            addView(errorStateRelativeLayout, layoutParams);
        } else {
            errorStateRelativeLayout.setVisibility(VISIBLE);
        }
    }

    private void setCustomView() {
        if (customView == null) {
            if (setLayout() == 0) {
                throw new RuntimeException("未实现布局，要想调用本方法，请继承并实现本类的抽象方法");
            }
            customView = inflater.inflate(setLayout(), null);
            customView.setTag(TAG_CUSTOM);
            bindView(customView);
            layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
            layoutParams.addRule(CENTER_IN_PARENT);
            addView(customView, layoutParams);
        } else {
            customView.setVisibility(VISIBLE);
        }
    }

    /**
     * set layout
     * @return layout id
     */
    protected abstract int setLayout();

    /**
     * bind view
     * @param view  view
     */
    protected abstract void bindView(View view);

    protected void setContentVisibility(boolean visible, List<Integer> skipIds) {
        for (View v : contentViews) {
            if (!skipIds.contains(v.getId())) {
                v.setVisibility(visible ? View.VISIBLE : View.GONE);
            }
        }
    }

    protected void hideCustomView() {
        if (customView != null) {
            customView.setVisibility(GONE);

            //Restore the background color if not TRANSPARENT
            if (loadingStateBackgroundColor != Color.TRANSPARENT) {
                this.setBackgroundDrawable(currentBackground);
            }
        }
    }

    protected void hideLoadingView() {
        if (loadingStateRelativeLayout != null) {
            loadingStateRelativeLayout.setVisibility(GONE);

            //Restore the background color if not TRANSPARENT
            if (loadingStateBackgroundColor != Color.TRANSPARENT) {
                this.setBackgroundDrawable(currentBackground);
            }
        }
    }

    public void hideEmptyView() {
        if (emptyStateRelativeLayout != null) {
            emptyStateRelativeLayout.setVisibility(GONE);

            //Restore the background color if not TRANSPARENT
            if (emptyStateBackgroundColor != Color.TRANSPARENT) {
                this.setBackgroundDrawable(currentBackground);
            }
        }
    }

    protected void hideErrorView() {
        if (errorStateRelativeLayout != null) {
            errorStateRelativeLayout.setVisibility(GONE);

            //Restore the background color if not TRANSPARENT
            if (errorStateBackgroundColor != Color.TRANSPARENT) {
                this.setBackgroundDrawable(currentBackground);
            }

        }
    }

    /**
     * 释放imageView中的图片
     *
     * @param imageView 要被释放的imageView
     * @param viewGroup 要被释放的imageView的父容器
     */
    private void releaseImageViewResource(ImageView imageView, ViewGroup viewGroup) {
        if (imageView == null) {
            return;
        }
        Drawable drawable = imageView.getDrawable();
        if (drawable instanceof BitmapDrawable) {

            if (viewGroup != null) {
                //将imageView从父容器移除，并将imag置为null
                viewGroup.removeView(imageView);
                imageView.clearColorFilter();
                imageView.setImageDrawable(null);
                imageView.setImageBitmap(null);
                imageView = null;
            }
            drawable.setCallback(null);
        }
    }

    public interface OnProgressButtonCallBack {
        void onCallComplete(TextView errorButton);
    }
}