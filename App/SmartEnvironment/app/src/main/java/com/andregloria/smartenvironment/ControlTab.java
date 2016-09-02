package com.andregloria.smartenvironment;

/**
 * Created by ANDRE on 02/09/16.
 */

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.andregloria.smartenvironment.utils.CheckConnection;
import com.andregloria.smartenvironment.utils.CurrentSensors;
import com.andregloria.smartenvironment.view.Sensor;
import com.andregloria.smartenvironment.view.SliderAdapterGrid;

import java.util.ArrayList;

public class ControlTab extends Fragment {

    protected GridView sliderSensors;
    protected SliderAdapterGrid adapterGrid;
    private ArrayList<Sensor> lcSensors;
    private String user;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        SharedPreferences sharedPrefs = this.getActivity().getSharedPreferences("login", Context.MODE_PRIVATE);
        if(sharedPrefs.contains("username")){
            //new TestLogin(LoginActivity.this, sharedPrefs.getString("username",""), sharedPrefs.getString("password","")).execute();
            user = sharedPrefs.getString("username","");
        }

        View rootView = inflater.inflate(R.layout.control_tab_fragment, container, false);

        sliderSensors = (GridView) rootView.findViewById(R.id.sliderSensors);

        addToGridView();

        return rootView;
    }

    public void addToGridView(){
        if(lcSensors == null) {
            lcSensors = new ArrayList<Sensor>();
            if (new CheckConnection(this.getActivity()).isConnected()) {
                new CurrentSensors(lcSensors, user, this, false).execute();
            }
        }
        System.out.println(lcSensors.size());
        sliderSensors.setNumColumns(lcSensors.size());

        adapterGrid = new SliderAdapterGrid(this.getActivity(),lcSensors);
        sliderSensors.setAdapter(adapterGrid);
        System.out.println(lcSensors.size());

    }

    public void notifyAdapterOfDataChanged(){
        sliderSensors.setNumColumns(lcSensors.size());
        adapterGrid.notifyDataSetChanged();
    }
}
