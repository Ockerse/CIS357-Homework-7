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

import java.util.ArrayList;

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

        // retrieve input params
        Intent intent = getIntent();
        //mode = intent.getStringExtra("mode");
        fromSelection = intent.getStringExtra("fromUnits");
        toSelection = intent.getStringExtra("toUnits");

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra("toUnits", toSelection);
                intent.putExtra("fromUnits", fromSelection);
                setResult(MainActivity.VICE_SELECTION, intent);

                finish();
            }
        });


        ArrayList<String> vals = new ArrayList<String>();
        int selection = 0;


        ArrayAdapter<CharSequence> adapter;

        if(mode == 0) {
            for (UnitsConverter.LengthUnits unit : UnitsConverter.LengthUnits.values()) {
                vals.add(unit.toString());
            }
        } else {
            for (UnitsConverter.VolumeUnits unit : UnitsConverter.VolumeUnits.values()) {
                vals.add(unit.toString());
            }
        }

        ArrayAdapter<String> unitAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item, vals);

        Spinner fromSpinner = findViewById(R.id.settings_from);
        fromSpinner.setAdapter(unitAdapter);

        fromSpinner.setSelection(0);

        fromSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                fromSelection = (String) adapterView.getItemAtPosition(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        Spinner toSpinner = findViewById(R.id.settings_to);
        toSpinner.setAdapter(unitAdapter);
        toSpinner.setSelection(0);

        toSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                toSelection = (String) adapterView.getItemAtPosition(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        // set initial val of spinners.
        for(int i=0; i<vals.size(); i++) {
            if (fromSelection.intern() == vals.get(i)) {
                fromSpinner.setSelection(i);
            }
            if (toSelection.intern() == vals.get(i)) {
                toSpinner.setSelection(i);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == VICE_SELECTION) {
            toSelection = (data.getStringExtra("toUnits"));
            fromSelection = (data.getStringExtra("fromUnits"));
        }
    }

}
