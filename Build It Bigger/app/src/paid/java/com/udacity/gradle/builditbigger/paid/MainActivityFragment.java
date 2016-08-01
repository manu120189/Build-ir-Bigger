package com.udacity.gradle.builditbigger.paid;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.udacity.gradle.builditbigger.EndpointsAsyncTask;
import com.udacity.gradle.builditbigger.R;


public class MainActivityFragment extends Fragment {

    Button button;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);
        return root;
    }

    @Override
    public void onStart(){
        super.onStart();


        button = (Button) getActivity().findViewById(R.id.tell_joke_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                processJokeRequest();
            }
        });


    }

    private void processJokeRequest() {
        ProgressDialog progress = new ProgressDialog(getActivity());
        progress.setTitle(getResources().getString(R.string.wait));
        progress.setMessage(getResources().getString(R.string.wait_message));
        progress.show();
        EndpointsAsyncTask endpointsAsyncTask = new EndpointsAsyncTask();
        endpointsAsyncTask.setProgressDialog(progress);
        endpointsAsyncTask.execute(new Pair<Context, String>(getActivity(), ""));
    }

}
