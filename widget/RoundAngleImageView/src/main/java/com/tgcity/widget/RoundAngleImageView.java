package com.tgcity.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * @author TGCity
 * @description 圆形的ImageView控件
 * <pre>
 *     <code>
 *         1.xml file
 *         <com.tgcity.base.widget.TwoTextViewLayout
 *                         android:id="@+id/ttv_riverName"
 *                         android:layout_width="match_parent"
 *                         android:layout_height="@dimen/dp_45"
 *                         android:layout_marginLeft="@dimen/dp_6"
 *                         app:dt_isShowLine="true"
 *                         app:dt_leftTitleCol="@color/color_ff000000"
 *                         app:dt_leftTitleStr="问题河道"
 *                         app:dt_rightTitleCol="@color/color_a6000000" />
 *
 *         2.class file
 *         tv.setRightContent("xxx");
 *         tv.setContentSize(14);
 *         tv.setPaddingWidth(0);
 *     </code>
 * </pre>
 */
public class RoundAngleImageView extends ImageView {
    private Paint paint;
    private Paint paintBorder;
    private Bitmap mSrcBitmap;
    private float mRadius;
    private boolean mIsCircle;
    private boolean mHasBroader;
    private float mBroaderWidth;
    private int mBroaderColor;

    public RoundAngleImageView(final Context context) {
        this(context, null);
    }

    public RoundAngleImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.ri_RoundAngleImageViewStyle);
        mRadius = ta.getDimension(R.styleable.ri_RoundAngleImageViewStyle_radius, 0);
        mIsCircle = ta.getBoolean(R.styleable.ri_RoundAngleImageViewStyle_circle, false);
        mHasBroader = ta.getBoolean(R.styleable.ri_RoundAngleImageViewStyle_hasbroader, false);
        mBroaderWidth = ta.getDimension(R.styleable.ri_RoundAngleImageViewStyle_broaderwidth, 5);
        mBroaderColor = ta.getColor(R.styleable.ri_RoundAngleImageViewStyle_broadercolor, Color.WHITE);
        ta.recycle();
        paint = new Paint();
        paint.setAntiAlias(true);
        paintBorder = new Paint();
        paintBorder.setAntiAlias(true);
    }

    @Override
    public void onDraw(Canvas canvas) {
        int width = canvas.getWidth() - getPaddingLeft() - getPaddingRight();
        int height = canvas.getHeight() - getPaddingTop() - getPaddingBottom();
        Bitmap image = drawableToBitmap(getDrawable(), width, height);
        if (image != null) {
            if (mIsCircle) {
                Bitmap reSizeImage = reSizeImageC(image, (int) (width * 1.2), (int) (height * 1.2));
                canvas.drawBitmap(createOutCircleImage(width, height), getPaddingLeft(), getPaddingTop(), null);
                canvas.drawBitmap(createWhiteCircleImage(width, height), getPaddingLeft(), getPaddingTop(), null);
                canvas.drawBitmap(createCircleImage(reSizeImage, width, height), getPaddingLeft(), getPaddingTop(),
                        null);
            } else {
                Bitmap reSizeImage = reSizeImage(image, width, height);
                if (mHasBroader) {
                    canvas.drawBitmap(createOutRoundImage(width, height), getPaddingLeft(), getPaddingTop(), null);
                }
                canvas.drawBitmap(createRoundImage(reSizeImage, width, height), getPaddingLeft(), getPaddingTop(),
                        null);
            }
        }
    }

    private Bitmap createRoundImage(Bitmap source, int width, int height) {
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        Bitmap target = Bitmap.createBitmap(width, height, Config.ARGB_8888);
        Canvas canvas = new Canvas(target);
        if (mHasBroader) {
            RectF rect = new RectF(mBroaderWidth, mBroaderWidth, width - mBroaderWidth, height - mBroaderWidth);
            paint.setColor(Color.WHITE);
            canvas.drawRoundRect(rect, mRadius, mRadius, paint);
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
            canvas.drawBitmap(source, mBroaderWidth, mBroaderWidth, paint);
            return target;
        } else {
            RectF rect = new RectF(mBroaderWidth, mBroaderWidth, width, height);
            paint.setColor(Color.WHITE);
            canvas.drawRoundRect(rect, mRadius, mRadius, paint);
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
            canvas.drawBitmap(source, 0, 0, paint);
            return target;
        }
    }

    private Bitmap createOutRoundImage(int width, int height) {
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        Bitmap target = Bitmap.createBitmap(width, height, Config.ARGB_8888);
        Canvas canvas = new Canvas(target);
        RectF rect = new RectF(0, 0, width, height);
        paint.setColor(mBroaderColor);
        canvas.drawRoundRect(rect, mRadius, mRadius, paint);
        return target;
    }

    private Bitmap createWhiteCircleImage(int width, int height) {
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        Bitmap target = Bitmap.createBitmap(width, height, Config.ARGB_8888);
        Canvas canvas = new Canvas(target);
        paint.setColor(Color.WHITE);
        canvas.drawCircle(width / 2, height / 2, Math.min(width, height) / 2 - mBroaderWidth, paint);
        return target;
    }

    private Bitmap createCircleImage(Bitmap source, int width, int height) {
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        Bitmap target = Bitmap.createBitmap(width, height, Config.ARGB_8888);
        Canvas canvas = new Canvas(target);
        canvas.drawCircle(width / 2, height / 2, Math.min(width, height) / 2 - mBroaderWidth, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(source, (width - source.getHeight()) / 2, (height - source.getHeight()) / 2, paint);
        return target;
    }

    private Bitmap createOutCircleImage(int width, int height) {
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        Bitmap target = Bitmap.createBitmap(width, height, Config.ARGB_8888);
        Canvas canvas = new Canvas(target);
        paint.setColor(mBroaderColor);
        canvas.drawCircle(width / 2, height / 2, Math.min(width, height) / 2, paint);
        return target;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(width, height);
    }

    private Bitmap drawableToBitmap(Drawable drawable, int width, int height) {
        if (drawable == null) {
            if (mSrcBitmap != null) {
                return mSrcBitmap;
            } else {
                return null;
            }
        } else if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }
        Bitmap bitmap = Bitmap.createBitmap(width, height, Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    private Bitmap reSizeImage(Bitmap bitmap, int newWidth, int newHeight) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        Matrix matrix = new Matrix();

        matrix.postScale(scaleWidth, scaleHeight);
        return Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
    }

    private Bitmap reSizeImageC(Bitmap bitmap, int newWidth, int newHeight) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int x = (newWidth - width) / 2;
        int y = (newHeight - height) / 2;
        if (x > 0 && y > 0) {
            return Bitmap.createBitmap(bitmap, 0, 0, width, height, null, true);
        }

        float scale = 1;

        if (width > height) {
            scale = ((float) newWidth) / width;
        } else {
            scale = ((float) newHeight) / height;
        }
        Matrix matrix = new Matrix();
        matrix.postScale(scale, scale);
        return Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
    }

    public void setBoraderWidth(int broaderWidth) {
        this.mBroaderWidth = broaderWidth;
    }

    public void setBroaderColor(int broaderColor) {
        this.mBroaderColor = broaderColor;
    }
}
