package com.example.home.swipetab.fragment;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import java.util.Arrays;
import java.util.List;

public class MyPagerAdapter extends FragmentStatePagerAdapter {
    List<Fragment> list;
    private Context mContext;

    public MyPagerAdapter(FragmentManager fm , Context mContext){
        super(fm);
        this.mContext = mContext;
        list = Arrays.asList( new Tab1(mContext),new Tab2(mContext),new Tab3(),new Tab4());
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0: return list.get(position);
            case 1: return list.get(position);
            case 2: return list.get(position);
            case 3: return list.get(position);
        }
        return null;
    }

    @Override
    public int getCount() { return list.size();}

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0: return "Tab 처음이야";
            case 1: return "Tab 2";
            case 2: return "Tab 3";
            case 3: return "Tab 4";
            default: return null;
        }
    }
}
