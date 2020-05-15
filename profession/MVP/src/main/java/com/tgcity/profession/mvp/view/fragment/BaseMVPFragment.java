package com.tgcity.profession.mvp.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LifecycleOwner;

import com.tgcity.function.fragment.BaseMemoryFragment;
import com.tgcity.profession.mvp.model.OnPresenterTaskCallBack;
import com.tgcity.profession.mvp.present.BasePresenterImpl;


/**
 * MVP Fragment
 * Created by Administrator on 2018/7/23.
 */

public abstract class BaseMVPFragment<V, P extends BasePresenterImpl<V>> extends BaseMemoryFragment {
    public P presenter;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        if (presenter == null) {
            presenter = createPresenter();
        }
        if (presenter != null) {
            presenter.attachView((V) this);
        }
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onDestroy(LifecycleOwner owner) {
        super.onDestroy(owner);

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
