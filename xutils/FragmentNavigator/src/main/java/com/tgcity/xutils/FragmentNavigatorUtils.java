package com.tgcity.xutils;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.List;

/**
 * @author tgcity
 * @description Fragment指示器
 */
public class FragmentNavigatorUtils {

    private FragmentManager fm;
    private int containerId;
    private String mSimpleName;

    public FragmentNavigatorUtils(FragmentManager fm, int id) {
        this.fm = fm;
        this.containerId = id;
    }

    /**
     * 显示指定的Fragment
     *
     * @param fragment Fragment
     * @param tag      String
     */
    public void navigateTo(Fragment fragment, String tag) {
        List<Fragment> fragments = fm.getFragments();
        int i = 0;
        for (; i < fragments.size(); i++) {
            if (fragments.get(i) == null) {
                break;
            }
        }
        if (i > 0) {
            fragments.get(i - 1).setUserVisibleHint(false);
        }
        FragmentTransaction ft = fm.beginTransaction();
        fragment.setUserVisibleHint(true);
        ft.add(containerId, fragment, tag);
        ft.addToBackStack(tag);
        ft.commit();
    }

    /**
     * 返回上一个Fragment
     */
    public void navigateBack() {
        List<Fragment> fragments = fm.getFragments();
        int i = 0;
        for (; i < fragments.size(); i++) {
            if (fragments.get(i) == null) {
                break;
            }
        }

        if (i > 1) {
            fragments.get(i - 1).setUserVisibleHint(false);
            fragments.get(i - 2).setUserVisibleHint(true);
        }
        fm.popBackStackImmediate();
    }

    public void navigateToSelf() {
        List<Fragment> fragments = fm.getFragments();
        for (int i = 0; i < fragments.size(); i++) {
            if (i != (fragments.size() - 1)) {
                fragments.get(i).setUserVisibleHint(false);
            }
        }
    }

    /**
     * 获取当前的Fragment标识
     *
     * @return String
     */
    public String getCurrentFragmentTag() {
        List<Fragment> fragments = fm.getFragments();
        int i = 0;
        for (; i < fragments.size(); i++) {
            if (fragments.get(i) == null) {
                break;
            }
        }
        if (i > 0) {
            return fragments.get(i - 1).getClass().getSimpleName();
        }
        return mSimpleName;
    }

    /**
     * 设置初始Fragment路径
     * 样式 class.getSimpleName()
     *
     * @param simpleName String
     */
    public void setCurrentFragmentTag(String simpleName) {
        mSimpleName = simpleName;
    }
}
