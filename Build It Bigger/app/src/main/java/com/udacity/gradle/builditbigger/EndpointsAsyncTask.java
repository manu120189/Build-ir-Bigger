package com.udacity.gradle.builditbigger;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.util.Pair;

import com.builditbigger.myapplication.backend.myApi.MyApi;
import com.example.predator.jokelibandroid.JokeDisplayActivity;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;

import java.io.IOException;

public class EndpointsAsyncTask extends AsyncTask<Pair<Context, String>, Void, String> {
    public static final String DOMAIN_JOKE_ENDPOINT = "192.168.0.103";
    public static final int PORT_JOKE_ENDPOINT = 8080;
    private MyApi myApiService = null;
    private Context context;
    public static String result;
    private ProgressDialog progressDialog;

    public ProgressDialog getProgressDialog() {
        return progressDialog;
    }

    public void setProgressDialog(ProgressDialog progressDialog) {
        this.progressDialog = progressDialog;
    }

    public MyApi getMyApiService() {
        return myApiService;
    }

    public EndpointsAsyncTask setMyApiService(MyApi myApiService) {
        this.myApiService = myApiService;
        return this;
    }

    @Override
    protected String doInBackground(Pair<Context, String>... params) {
        if(myApiService == null) {  // Only do this once
            MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    // options for running against local devappserver
                    // - 10.0.2.2 is localhost's IP address in Android emulator
                    // - turn off compression when running against local devappserver
                    .setRootUrl("http://" + DOMAIN_JOKE_ENDPOINT + ":" + PORT_JOKE_ENDPOINT + "/_ah/api/")
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                            abstractGoogleClientRequest.setDisableGZipContent(true);
                        }
                    });
            // end options for devappserver

            myApiService = builder.build();
        }

        context = params[0].first;

        try {
            return myApiService.getJoke().execute().getData();
        } catch (IOException e) {
            e.printStackTrace();
            return "An unexpected error occurred :(";
        }
    }

    @Override
    protected void onPostExecute(String joke) {
        result = joke;
        if (context != null){
            if (progressDialog != null){
                // To dismiss the dialog
                progressDialog.dismiss();
            }
            Intent intent = new Intent(context, JokeDisplayActivity.class);
            intent.putExtra(JokeDisplayActivity.JOKE_EXTRA, joke);
            context.startActivity(intent);
        }
    }

}