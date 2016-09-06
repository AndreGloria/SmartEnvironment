package com.andregloria.smartenvironment.view;

/**
 * Created by ANDRE on 02/09/16.
 */


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;
import android.widget.GridView;

import com.andregloria.smartenvironment.ActionsTab;
import com.andregloria.smartenvironment.ActuatorsTab;
import com.andregloria.smartenvironment.MonitorTab;
import com.andregloria.smartenvironment.R;

public class PagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;
    private GridView grdSensors;

    public PagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        Log.i("Teste", "switch");

        switch (position) {
            case 0:
                MonitorTab tab1 = new MonitorTab();
                return tab1;
            case 1:
                ActuatorsTab tab2 = new ActuatorsTab();
                return tab2;
            case 2:
                ActionsTab tab3 = new ActionsTab();
                return tab3;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
