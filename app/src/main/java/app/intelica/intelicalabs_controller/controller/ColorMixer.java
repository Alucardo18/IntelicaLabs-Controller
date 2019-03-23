package app.intelica.intelicalabs_controller.controller;

import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import app.intelica.intelicalabs_controller.R;
import app.intelica.intelicalabs_controller.Util.bluetooth.BluetoothConnection;
import app.intelica.intelicalabs_controller.Util.bluetooth.BluetoothOutput;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.larswerkman.holocolorpicker.ColorPicker;

import com.larswerkman.holocolorpicker.SVBar;

public class ColorMixer extends AppCompatActivity {

    public BluetoothSocket bluetoothSocket = null;
    private BluetoothOutput bluetoothOutput;
    private ColorPicker colorPicker;
    private SVBar svBar;
    private TextView textColorValue;
    private SharedPreferences sharedPref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color_mixer);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        bluetoothSocket = BluetoothConnection.getInstance().getBluetoothSocket();
        this.bluetoothOutput = BluetoothConnection.getInstance().getBluetoothOutput();
        this.colorPicker = (ColorPicker) findViewById(R.id.picker);
        this.svBar = (SVBar) findViewById(R.id.svbar);
        this.textColorValue = (TextView) findViewById(R.id.textColorValue);

        this.setupColorPicker();
    }

    @Override
    protected void onStart() {

        super.onStart();

        this.sharedPref = this.getSharedPreferences(
                getString(R.string.color_preferences), Context.MODE_PRIVATE);

        int color = sharedPref.getInt("color", 0);
        this.changeColorOnRobot(color);
        this.showStoredColor(color);
    }

    public void back(View view){
        finish();
    }

    private void setupColorPicker(){

        this.colorPicker.addSVBar(svBar);
        this.colorPicker.getColor();
        this.colorPicker.setShowOldCenterColor(false);

        this.colorPicker.setOnColorChangedListener(new ColorPicker.OnColorChangedListener() {
            @Override
            public void onColorChanged(int color) {

                saveColor(color);
                changeColorOnRobot(color);
            }
        });
    }

    private void showStoredColor(int color) {

        this.colorPicker.setColor(color);
    }

    private void saveColor(int color) {

        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt("color", color);
        editor.apply();
    }

    private void changeColorOnRobot(int color) {

        int A = (color >> 24) & 0xff; // or color >>> 24
        int R = (color >> 16) & 0xff;
        int G = (color >> 8) & 0xff;
        int B = (color) & 0xff;
        textColorValue.setText("Valor enviado: #RGB" + R + "," + G + "," + B + ",\\n");
        if (bluetoothSocket != null) {
            bluetoothOutput.write("#RGB" + R + "," + G + "," + B + "," + "\n");
        }
    }
}
