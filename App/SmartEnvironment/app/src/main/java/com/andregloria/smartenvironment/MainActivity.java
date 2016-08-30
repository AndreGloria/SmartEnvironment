package com.andregloria.smartenvironment;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;

import com.andregloria.smartenvironment.utils.CheckConnection;
import com.andregloria.smartenvironment.utils.CurrentSensors;
import com.andregloria.smartenvironment.utils.DrawerItemListener;
import com.andregloria.smartenvironment.view.Sensor;
import com.andregloria.smartenvironment.view.SensorAdapterGrid;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    protected ListView mDrawerList;
    protected GridView grdSensors;
    protected SensorAdapterGrid adapterGrid;
    private ArrayList<Sensor> lcSensors;
    private String user;

    Toolbar toolBar;
    String[] drawerOptions;


    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences sharedPrefs = getSharedPreferences("login", Context.MODE_PRIVATE);
        if(sharedPrefs.contains("username")){
            //new TestLogin(LoginActivity.this, sharedPrefs.getString("username",""), sharedPrefs.getString("password","")).execute();
            user = sharedPrefs.getString("username","");
        }

        //Sidebar menu
        toolBar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        drawerOptions = getResources().getStringArray(R.array.side_bar_option);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        mDrawerList.setAdapter(new ArrayAdapter<>(getApplicationContext(), R.layout.drawer_list_item, drawerOptions));
        mDrawerList.setOnItemClickListener(new DrawerItemListener(this, mDrawerLayout));

        mDrawerToggle = new ActionBarDrawerToggle(
                this,
                mDrawerLayout,
                toolBar,
                R.string.drawer_open,
                R.string.drawer_close
        ) {
            public void onDrawerClosed(View view) {

                invalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {

                invalidateOptionsMenu();
            }
        };
        mDrawerLayout.addDrawerListener(mDrawerToggle);
        //Sidebar menu end

        //Grid start
        grdSensors = (GridView) findViewById(R.id.grdSensors);

        addToGridView();

        //


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void addToGridView(){
        if(lcSensors ==null) {
            lcSensors= new ArrayList<Sensor>();
            if (new CheckConnection(MainActivity.this).isConnected()) {
                new CurrentSensors(lcSensors, user, this).execute();
            }
        }

        adapterGrid = new SensorAdapterGrid(MainActivity.this,lcSensors);
        grdSensors.setAdapter(adapterGrid);

        mDrawerList.setAdapter(new ArrayAdapter<>(getApplicationContext(), R.layout.drawer_list_item, drawerOptions));

    }
}
