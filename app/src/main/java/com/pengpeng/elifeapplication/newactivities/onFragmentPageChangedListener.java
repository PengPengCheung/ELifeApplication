package com.pengpeng.elifeapplication.newactivities;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import java.util.List;

/**
 * Created by pengpeng on 16-1-17.
 */
public class onFragmentPageChangedListener implements ViewPager.OnPageChangeListener {

    private int firstFromPage;
    private List<Fragment> fragmentList;

    public onFragmentPageChangedListener(List<Fragment> list, int firstFromPage) {
        this.firstFromPage = firstFromPage;
        this.fragmentList = list;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
//        if (fragmentList.get(position) instanceof NetworkPlayerFragment) {
//            Bundle bundle = ((ClassificationFragment) fragmentList.get(firstFromPage)).getBundle();
//            if (bundle != null) {
//                ((NetworkPlayerFragment) fragmentList.get(position)).update(bundle);
//            }
//        }


    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
