package com.andregloria.smartenvironment.utils;

import android.os.AsyncTask;
import android.support.v4.app.Fragment;

import com.andregloria.smartenvironment.ControlTab;
import com.andregloria.smartenvironment.MonitorTab;
import com.andregloria.smartenvironment.view.Sensor;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

/**
 * Created by ANDRE on 30/08/16.
 */
public class CurrentSensors  extends AsyncTask<Void, Void, Void> {

    private static final String LOGIN_URL = "http://smartenvironment.andregloria.com/api/getSensors.php";
    private List<Sensor> sensorsList;
    private MonitorTab monitor;
    private ControlTab control;
    private String user;
    private boolean isMonitor;

    public CurrentSensors(List<Sensor> movieAdapter, String user, Fragment fragment, boolean isMonitor){
        this.sensorsList =movieAdapter;
        if(isMonitor){
            this.monitor = (MonitorTab) fragment;
        } else {
            this.control = (ControlTab) fragment;
        }
        this.user = user;
        this.isMonitor = isMonitor;
    }


    @Override
    protected Void doInBackground(Void... params) {

        try {
            URL url = new URL(LOGIN_URL);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            String urlParameters = "user=" + user;
            connection.setRequestMethod("POST");
            connection.setRequestProperty("USER-AGENT", "Mozilla/5.0");
            connection.setRequestProperty("ACCEPT-LANGUAGE", "en-US,en;0.5");
            connection.setDoOutput(true);
            DataOutputStream dStream = new DataOutputStream(connection.getOutputStream());
            dStream.writeBytes(urlParameters);
            dStream.flush();
            dStream.close();

            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line = "";
            StringBuilder responseOutput = new StringBuilder();

            while ((line = br.readLine()) != null) {
                responseOutput.append(line);
            }
            br.close();

            String result = responseOutput.toString();
            System.out.println(result);

            //result = result.substring(1,result.length()-1); // remove wrapping "[" and "]"

            JSONArray sensorObject = new JSONArray(result);
            System.out.println(sensorObject.getJSONObject(0).getString("sensorName"));
            System.out.println(sensorObject.getJSONObject(1).getString("sensorName"));
            //String title = movieObject.getString("Title");
            for(int i = 0; i < sensorObject.length(); i++){
                JSONObject sensor = sensorObject.getJSONObject(i);
                sensorsList.add(new Sensor(i, sensor.getString("sensorName"), sensor.getString("sensorId"), sensor.getString("value")));
            }



            //sensorsList.add(new Sensor(title, coverUrl, redirectUrl, true));


        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        if(isMonitor){
            monitor.notifyAdapterOfDataChanged();
        } else {
            control.notifyAdapterOfDataChanged();
        }

    }

}
