package com.example.conversioncalculator;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import static com.example.conversioncalculator.MainActivity.mode;

public class SettingsActivity extends AppCompatActivity {
    public static final int VICE_SELECTION = 1;
    public String toSelection = "";
    public String fromSelection = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra("to", toSelection);
                intent.putExtra("from", fromSelection);
                setResult(MainActivity.VICE_SELECTION, intent);

                finish();
            }
        });

        Spinner fromSpinner = (Spinner) findViewById(R.id.settings_from);
        Spinner toSpinner = (Spinner) findViewById(R.id.settings_to);
        ArrayAdapter<CharSequence> adapter;

        if(mode == 0) {
            adapter = ArrayAdapter.createFromResource(this, R.array.length_units, android.R.layout.simple_spinner_item);
        }
        else {
            adapter = ArrayAdapter.createFromResource(this, R.array.volume_units, android.R.layout.simple_spinner_item);
        }

        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        toSpinner.setAdapter(adapter);
        fromSpinner.setAdapter(adapter);

        fromSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               fromSelection = (String) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        toSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                toSelection = (String) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == VICE_SELECTION) {
            toSelection = (data.getStringExtra("to"));
            fromSelection = (data.getStringExtra("from"));
        }
    }

}
