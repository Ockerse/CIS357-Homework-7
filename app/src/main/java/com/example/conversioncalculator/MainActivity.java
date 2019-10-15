package com.example.conversioncalculator;

import androidx.annotation.CallSuper;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.app.Activity;
import android.view.MenuItem;
import android.view.Menu;
import android.view.View;
import android.content.Intent;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;


public class MainActivity extends AppCompatActivity {
    public static int mode = 0;
    public static final int VICE_SELECTION = 1;
    public TextView fromValue, toValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText toField = (EditText) findViewById(R.id.toTextField);
        EditText fromField = (EditText) findViewById(R.id.fromTextField);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        TextView toLabel = (TextView) findViewById(R.id.toLabel);
        TextView fromLabel = (TextView) findViewById(R.id.fromLabel);
        TextView titleLabel = (TextView) findViewById(R.id.titleLabel);

         fromValue = (TextView) findViewById(R.id.fromValue);
         toValue = (TextView) findViewById(R.id.toValue);

        Button clearButton = (Button) findViewById(R.id.clearButton);
        Button modeButton = (Button) findViewById(R.id.modeButton);
        Button calculateButton = (Button) findViewById(R.id.calculateButton);

        //toLabel.setText("Meters");
        //fromLabel.setText("Yards");
        toValue.setText("Yards");
        fromValue.setText("Yards");



        clearButton.setOnClickListener((View v) -> {
            hideKeyboard();
            toField.setText("");
            fromField.setText("");
        });

        modeButton.setOnClickListener((View v) -> {
            hideKeyboard();
            toField.setText("");
            fromField.setText("");
            if (mode == 0) {
                titleLabel.setText("Volume Converter");
                fromField.setHint("Enter From Value");
                toField.setHint("Enter To Value");
                fromValue.setText("Liters");
                toValue.setText("Liters");
                //toLabel.setText("Gallons");
                //fromLabel.setText("Liters");
                mode++;
            } else {
                titleLabel.setText("Length Converter");
                fromField.setHint("Enter From Value");
                toField.setHint("Enter To Value");
                fromValue.setText("Yards");
                toValue.setText("Yards");
                //toLabel.setText("Meters");
                //fromLabel.setText("Yards");
                mode--;
            }



        });

        calculateButton.setOnClickListener((View v) -> {
            hideKeyboard();
            if(!fromField.getText().toString().equals("") && toField.getText().toString().equals("")){
                if(mode == 0) {
                    double top = Double.parseDouble(fromField.getText().toString());
                    UnitsConverter.LengthUnits topLabel = UnitsConverter.LengthUnits.valueOf(fromValue.getText().toString());
                    UnitsConverter.LengthUnits bottomLabel = UnitsConverter.LengthUnits.valueOf(toValue.getText().toString());
                    double lengthConv = UnitsConverter.convert(top, topLabel, bottomLabel);
                    toField.setText(""+lengthConv);
                }
                if(mode == 1) {
                    double top = Double.parseDouble(fromField.getText().toString());
                    UnitsConverter.VolumeUnits topLabel2 = UnitsConverter.VolumeUnits.valueOf(fromValue.getText().toString());
                    UnitsConverter.VolumeUnits bottomLabel2 = UnitsConverter.VolumeUnits.valueOf(toValue.getText().toString());
                    double lengthConv = UnitsConverter.convert(top, topLabel2, bottomLabel2);
                    toField.setText(""+lengthConv);
                }

            }
            if(fromField.getText().toString().equals("") && !toField.getText().toString().equals("")) {
                if (mode == 0) {
                    double bottom = Double.parseDouble(toField.getText().toString());
                    UnitsConverter.LengthUnits topLabel = UnitsConverter.LengthUnits.valueOf(fromValue.getText().toString());
                    UnitsConverter.LengthUnits bottomLabel = UnitsConverter.LengthUnits.valueOf(toValue.getText().toString());
                    double lengthConv = UnitsConverter.convert(bottom, bottomLabel, topLabel);
                    fromField.setText("" + lengthConv);
                }
                if (mode == 1) {
                    double bottom = Double.parseDouble(toField.getText().toString());
                    UnitsConverter.VolumeUnits topLabel2 = UnitsConverter.VolumeUnits.valueOf(fromValue.getText().toString());
                    UnitsConverter.VolumeUnits bottomLabel2 = UnitsConverter.VolumeUnits.valueOf(toValue.getText().toString());
                    double lengthConv = UnitsConverter.convert(bottom, bottomLabel2, topLabel2);
                    fromField.setText("" + lengthConv);
                }
            }

            fromField.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View view, boolean hasFocus) {
                    if (hasFocus) {
                        toField.setText("");
                    }
                    if (!hasFocus) {
                        hideKeyboard();
                        //hide keyboard
                    }
                }
            });
            toField.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View view, boolean hasFocus) {
                    if (hasFocus) {
                        fromField.setText("");
                    }
                    if (!hasFocus) {
                        hideKeyboard();
                        // hide keyboard
                    }
                }
            });

        });
    }

    public void hideKeyboard() {
        if(getCurrentFocus() != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }

    }

@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
}

@Override
    public boolean onOptionsItemSelected(MenuItem item) {
    if (item.getItemId() == R.id.action_settings) {
        Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
        intent.putExtra("to", toValue.getText());
        intent.putExtra("from", fromValue.getText());
        startActivityForResult(intent, VICE_SELECTION);
        //finish();
        return true;
    }
    return false;

}

@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (requestCode == VICE_SELECTION) {
        toValue.setText(data.getStringExtra("to"));
        fromValue.setText(data.getStringExtra("from"));
    }
}


}
