package com.andregloria.smartenvironment.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.LocationManager;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.andregloria.smartenvironment.MainActivity;

public class DrawerItemListener implements ListView.OnItemClickListener{

    Activity activity;
    DrawerLayout mDrawerLayout;

    public DrawerItemListener(Activity activity, DrawerLayout mDrawerLayout) {
        this.activity = activity;
        this.mDrawerLayout=mDrawerLayout;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if(position==0) {

        }else if (position==1){


        }else if(position==2){

        }else if (position==3){

        }else if (position==4){

        }else if(position==5){

        }
        mDrawerLayout.closeDrawers();

    }
}
