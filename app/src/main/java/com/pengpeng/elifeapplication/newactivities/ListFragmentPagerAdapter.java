package com.pengpeng.elifeapplication.newactivities;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by pengpeng on 16-1-15.
 */
public class ListFragmentPagerAdapter extends FragmentPagerAdapter{

    private List<Fragment> fragmentList;

    public ListFragmentPagerAdapter(FragmentManager fm, List<Fragment> list) {
        super(fm);
        this.fragmentList = list;
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    @Override
    public int getItemPosition(Object object) {

        return super.getItemPosition(object);
    }
}
