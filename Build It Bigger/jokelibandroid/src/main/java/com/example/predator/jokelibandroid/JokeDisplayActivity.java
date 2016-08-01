package com.example.predator.jokelibandroid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

public class JokeDisplayActivity extends AppCompatActivity {
    public static final String JOKE_EXTRA = "JOKE";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joke_display);

        Intent intent = getIntent();
        String joke = intent.getStringExtra(JOKE_EXTRA);

        TextView textView = (TextView)findViewById(R.id.jokeTextView);
        textView.setText(joke);

        Button goBack = (Button) findViewById(R.id.go_back_button);
        goBack.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

}
