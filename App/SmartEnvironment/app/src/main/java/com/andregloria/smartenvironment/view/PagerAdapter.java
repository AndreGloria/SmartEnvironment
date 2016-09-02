package com.andregloria.smartenvironment.view;

/**
 * Created by ANDRE on 02/09/16.
 */


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.andregloria.smartenvironment.ControlTab;
import com.andregloria.smartenvironment.MonitorTab;

public class PagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public PagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                MonitorTab tab1 = new MonitorTab();
                return tab1;
            case 1:
                ControlTab tab2 = new ControlTab();
                return tab2;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
