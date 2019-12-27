package com.tgcity.profession.mvp.view.fragment;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.tgcity.function.fragment.BaseMemoryFragment;
import com.tgcity.profession.mvp.model.OnPresenterTaskCallBack;
import com.tgcity.profession.mvp.present.CommonPresenter;


/**
 * MVP Fragment
 * Created by Administrator on 2018/7/23.
 */

public abstract class BaseMVPFragment<V, P extends CommonPresenter<V>> extends BaseMemoryFragment {
    public P presenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (presenter == null) {
            presenter = createPresenter();
        }
        if (presenter != null) {
            presenter.attachView((V) this);
            presenter.bindLifecycle(this.bindToLifecycle());
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (presenter != null) {
            presenter.detachView();
        }
    }

    /**
     * 创建Presenter
     *
     * @return
     */
    protected abstract P createPresenter();

    /**
     * 执行Presenter层的任务，这个方法出现的目的在于为了防止调用P层时出现空指针导致应用崩溃。
     * 举列：页面关闭后但网络未停止，当网络任务执行完毕后再调用presenter的时候出现空指针，
     * 故首先判空，将Presenter层的任务放到后面，以保证程序的绝对稳定
     */
    public void presenterTask(OnPresenterTaskCallBack<P> onPresenterTaskCallBack) {
        if (onPresenterTaskCallBack != null) {
            if (presenter != null) {
                onPresenterTaskCallBack.onPresenterTask(presenter);
            }
        }
    }
}
