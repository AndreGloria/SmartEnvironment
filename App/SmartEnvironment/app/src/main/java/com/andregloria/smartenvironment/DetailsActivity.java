package com.andregloria.smartenvironment;

import android.net.Uri;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.andregloria.smartenvironment.view.Sensor;

/**
 * Created by ANDRE on 05/09/16.
 */

public class DetailsActivity extends AppCompatActivity {

    protected TextView tvNome, tvID, tvValue;
    protected ImageView ivImg;
    protected long id;
    protected Sensor sensor;
    protected Toolbar toolBar;

    protected static int RESULT_NOT_CHANGED = 3;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_details, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                onBackPressed();
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        toolBar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        tvNome = (TextView) findViewById(R.id.tv_sensorName);
        tvID = (TextView) findViewById(R.id.tv_sensorID);
        tvValue = (TextView) findViewById(R.id.tv_sensorValue);
        ivImg = (ImageView) findViewById(R.id.iv_sensorImg);

        Intent i = getIntent();
        sensor = i.getParcelableExtra("sensor");

        tvNome.setText(sensor.getSensorName());
        tvID.setText(sensor.getSensorId());
        tvValue.setText("Current Value: " + sensor.getValue());

        /*if(movie.getImageCover()==null){
            String cover = i.getStringExtra("cover");
            new DownloadImageTask(ivCover,movie).execute(cover);
        }
        else
            ivCover.setImageBitmap(movie.getImageCover());*/

        setResult(RESULT_NOT_CHANGED);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}

