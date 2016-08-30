package com.andregloria.smartenvironment.utils;

/**
 * Created by ANDRE on 10/05/16.
 */

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.widget.Toast;

import com.andregloria.smartenvironment.LoginActivity;
import com.andregloria.smartenvironment.MainActivity;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class TestLogin extends AsyncTask<String, String, String> {
    private static final String LOGIN_URL = "http://smartenvironment.andregloria.com/api/login.php";

    private String email, password;
    private Activity a=null;
    private ProgressDialog progressDialog =null;

    public TestLogin(Activity a, String email, String password){
        this.email=email;
        this.password=password;
        this.a=a;
    }

    /**
     * Before starting background thread Show Progress Dialog
     * */
    boolean failure = false;

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog =null;
        progressDialog = new ProgressDialog(a);
        progressDialog.setMessage("Attempting login...");
        progressDialog.setIndeterminate(false);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    @Override
    protected String doInBackground(String... args) {

        int success;
        String commit_password = password;
        //password = Utils.MD5(password);

        try {

            URL url = new URL(LOGIN_URL);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            String urlParameters = "user=" + email + "&pass=" + password;
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

            if (result.equals("true")) {
                //autologin
                SharedPreferences sharedPrefs = a.getSharedPreferences("login", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPrefs.edit();
                editor.putString("username",email);
                editor.putString("password",commit_password);
                editor.commit();
                //
                Intent i = new Intent(a, MainActivity.class);
                i.putExtra("access", true);
                a.finish();
                a.startActivity(i);
            } else {
                Intent i = new Intent(a.getApplicationContext(), LoginActivity.class);
                a.finish();
                a.startActivity(i);
            }


        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    /**
     * After completing background task Dismiss the progress dialog
     * **/
    protected void onPostExecute(String file_url) {
        // dismiss the dialog once product deleted
        if(progressDialog !=null) progressDialog.dismiss();
        if (file_url != null){
            Toast.makeText(a, file_url, Toast.LENGTH_LONG).show();
        }
    }

}

