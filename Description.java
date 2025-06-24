package com.example.weatherapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

public class Description extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);

        TextView title = findViewById(R.id.header);
        TextView description = findViewById(R.id.description);

        Bundle getData = getIntent().getExtras();
        String header = getData.getString("Title");
        String summary = getData.getString("Description");

        title.setText(header);
        description.setText(Html.fromHtml(summary));

        // description.setMovementMethod(new ScrollingMovementMethod());
    }
}