package com.andregloria.smartenvironment;

/**
 * Created by ANDRE on 02/09/16.
 */

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Movie;
import android.support.v4.app.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.andregloria.smartenvironment.utils.CheckConnection;
import com.andregloria.smartenvironment.utils.CurrentSensors;
import com.andregloria.smartenvironment.view.Sensor;
import com.andregloria.smartenvironment.view.SensorAdapterGrid;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class MonitorTab extends Fragment implements AdapterView.OnItemClickListener{

    protected GridView grdSensors;
    protected SensorAdapterGrid adapterGrid;
    private ArrayList<Sensor> lcSensors;
    private String user;
    private View rootView;

    protected static int DETAILS = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.i("Monitor", "passou");

        rootView = inflater.inflate(R.layout.monitor_tab_fragment, container, false);

        SharedPreferences sharedPrefs = this.getActivity().getSharedPreferences("login", Context.MODE_PRIVATE);
        if(sharedPrefs.contains("username")){
            //new TestLogin(LoginActivity.this, sharedPrefs.getString("username",""), sharedPrefs.getString("password","")).execute();
            user = sharedPrefs.getString("username","");
        }

        return rootView;
    }

    public void addToGridView(){
        if(lcSensors == null) {
            lcSensors = new ArrayList<Sensor>();
            if (new CheckConnection(this.getActivity()).isConnected()) {
                new CurrentSensors(lcSensors, user, this, true).execute();
            }
        }

        adapterGrid = new SensorAdapterGrid(this.getActivity(),lcSensors);
        grdSensors.setAdapter(adapterGrid);
        Log.i("Test", "Passou");
    }

    public void notifyAdapterOfDataChanged(){
        adapterGrid.notifyDataSetChanged();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Grid start
        grdSensors = (GridView) view.findViewById(R.id.grdSensors);

        addToGridView();
        grdSensors.bringToFront();

        grdSensors.setOnItemClickListener(this);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.i("Sensor", "s");
        Context ctx = rootView.getContext().getApplicationContext();
        Intent i = new Intent(ctx, DetailsActivity.class);

        //Sensor s = (Sensor) lcSensors.getItemAtPosition(position);
        Sensor s = (Sensor) lcSensors.get(position);
        Log.i("Sensor", s.getSensorName());
        i.putExtra("sensor", s);
        startActivityForResult(i, DETAILS);
    }
}
