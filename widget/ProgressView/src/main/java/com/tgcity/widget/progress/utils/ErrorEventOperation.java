package com.tgcity.widget.progress.utils;

import android.content.Context;
import android.content.Intent;
import android.os.Looper;

import com.tgcity.function.network.cache.ErrorMode;
import com.tgcity.function.network.retrofit.ApiException;
import com.tgcity.utils.IntentUtils;
import com.tgcity.utils.ToastUtils;

/**
 * @author TGCity
 * error工具类,用于区分是那种错误类型的操作并提供相应的回调给调用者
 */

public class ErrorEventOperation {

    /**
     * 判断是否为ApiException
     *
     * @param throwable throwable
     * @return ApiException
     */
    public static ApiException deposit(Throwable throwable) {
        return throwable instanceof ApiException ? (ApiException) throwable : null;
    }

    /**
     * 拿到ErrorMode
     *
     * @param throwable Throwable
     * @return ErrorMode
     */
    public static ErrorMode depositReturnErrorMode(Throwable throwable) {
        return throwable instanceof ApiException ? ((ApiException) throwable).errorMode : null;
    }

    /**********************************各种错误处理************************************************/
    public static void operation(Context context, Throwable throwable) {
        operation(context, false, depositReturnErrorMode(throwable));

    }

    public static void operation(Context context, ErrorMode errorMode) {
        operation(context, false, errorMode);

    }

    public static void operation(Context context, boolean isShowErrorMsg, Throwable throwable) {
        operation(context, isShowErrorMsg, depositReturnErrorMode(throwable));

    }

    public static void operation(Context context, Throwable throwable, OnErrorEventOperationCallBack onErrorEventOperationCallBack) {
        operation(context, depositReturnErrorMode(throwable), onErrorEventOperationCallBack);

    }

    public static void operation(Context context, boolean isShowErrorMsg, ErrorMode errorMode) {
        if (errorMode == null) {
            return;
        }
        if (isShowErrorMsg) {
            if (Looper.getMainLooper() == Looper.getMainLooper() && ErrorMode.API_VISUALIZATION_MESSAGE == errorMode) {
                ToastUtils.showShortToast(context, errorMode.getErrorTitle());
            }
        }
        operation(context, errorMode, null);
    }

    /**
     * 错误处理
     *
     * @param errorMode                     ErrorMode
     * @param onErrorEventOperationCallBack 暴露一个接口给调用者手动处理，如果为空，则视为自动处理
     */
    public static void operation(Context context, ErrorMode errorMode, OnErrorEventOperationCallBack onErrorEventOperationCallBack) {
        if (errorMode == null) {
            return;
        }
        if (ErrorMode.NO_AUTHORITY == errorMode) {
            if (onErrorEventOperationCallBack != null) {
                onErrorEventOperationCallBack.onNoAuthority();
            }

        } else if (ErrorMode.API_VISUALIZATION_MESSAGE == errorMode) {
            if (onErrorEventOperationCallBack != null) {
                onErrorEventOperationCallBack.onVisualizationMsg(errorMode.getErrorTitle());
            }

        } else if (ErrorMode.OVERLOAD == errorMode) {
            if (onErrorEventOperationCallBack != null) {
                onErrorEventOperationCallBack.onOverLoad();
            }
        } else if (ErrorMode.NO_CACHE == errorMode) {
            if (onErrorEventOperationCallBack != null) {
                onErrorEventOperationCallBack.onOther(errorMode);
            }
        } else if (ErrorMode.NO_NETWORK == errorMode) {
            if (onErrorEventOperationCallBack != null) {
                onErrorEventOperationCallBack.onNoNetWork();
            } else {
               context.startActivity(IntentUtils.getNetworkIntent().addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
            }
        } else if (ErrorMode.SIGNATURE_FAILURE_TIME == errorMode) {
            if (onErrorEventOperationCallBack != null) {
                onErrorEventOperationCallBack.onSignatureFailTime();
            } else {
                context.startActivity(IntentUtils.getDateIntent().addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
            }

        } else if (ErrorMode.SIGNATURE_FAILURE_SSL == errorMode) {
            if (onErrorEventOperationCallBack != null) {
                onErrorEventOperationCallBack.onOther(errorMode);
            }
        } else if (ErrorMode.CONNECT_TIME_OUT == errorMode) {
            if (onErrorEventOperationCallBack != null) {
                onErrorEventOperationCallBack.onTimeOut();
            }
        } else if (ErrorMode.UNKNOWN_HOST == errorMode) {
            if (onErrorEventOperationCallBack != null) {
                onErrorEventOperationCallBack.onOther(errorMode);
            }
        } else if (ErrorMode.DATA_FORMAT_ERROR == errorMode) {
            if (onErrorEventOperationCallBack != null) {
                onErrorEventOperationCallBack.onDataFormatError();
            }
        } else if (ErrorMode.TYPE_CAST_ERROR == errorMode) {
            if (onErrorEventOperationCallBack != null) {
                onErrorEventOperationCallBack.onCastError();
            }
        } else {
            if (onErrorEventOperationCallBack != null) {
                onErrorEventOperationCallBack.onOther(errorMode);
            }
        }
    }

    /**
     * 错误处理回调
     */
    public interface OnErrorEventOperationCallBack {

        /**
         * no authority
         */
        void onNoAuthority();

        /**
         * visual message
         *
         * @param msg message
         */
        void onVisualizationMsg(String msg);

        /**
         * over load
         */
        void onOverLoad();

        /**
         * no network
         */
        void onNoNetWork();

        /**
         * signature fail time
         */
        void onSignatureFailTime();

        /**
         * time out
         */
        void onTimeOut();

        /**
         * other
         *
         * @param errorMode1 ErrorMode
         */
        void onOther(ErrorMode errorMode1);

        /**
         * data format error
         */
        void onDataFormatError();

        /**
         * cast error
         */
        void onCastError();
    }
}
