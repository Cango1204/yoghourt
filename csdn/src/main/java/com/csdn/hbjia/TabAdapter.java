package com.csdn.hbjia;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by hbjia on 2015/1/21.
 */
public class TabAdapter extends FragmentPagerAdapter{

    public static final String [] TITLES = new String[]{"业界", "移动", "研发", "程序员杂志", "云计算"};

    public TabAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
//        MainFragment fragment = new MainFragment(position + 1);
        MainFragment fragment = MainFragment.getInstance(position + 1);
        return fragment;
    }

    @Override
    public int getCount() {
        return TITLES.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return TITLES[position];
    }
}
