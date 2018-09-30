package android.intelica.intelicalabs_controller.controller;

import android.bluetooth.BluetoothSocket;
import android.content.pm.ActivityInfo;
import android.intelica.intelicalabs_controller.R;
import android.intelica.intelicalabs_controller.Util.bluetooth.BluetoothConnection;
import android.intelica.intelicalabs_controller.Util.bluetooth.BluetoothOutput;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.larswerkman.holocolorpicker.ColorPicker;

import com.larswerkman.holocolorpicker.SVBar;

public class ColorMixer extends AppCompatActivity {

    public BluetoothSocket bluetoothSocket = null;
    private BluetoothOutput bluetoothOutput;

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

        setupColorPicker();
    }

    private void setupColorPicker(){

        final ColorPicker colorPicker = (ColorPicker) findViewById(R.id.picker);
        final SVBar svBar = (SVBar) findViewById(R.id.svbar);
        final TextView textColorValue = (TextView) findViewById(R.id.textColorValue);

        colorPicker.addSVBar(svBar);
        colorPicker.getColor();
        colorPicker.setShowOldCenterColor(false);

        colorPicker.setOnColorChangedListener(new ColorPicker.OnColorChangedListener() {
            @Override
            public void onColorChanged(int color) {

                int A = (color >> 24) & 0xff; // or color >>> 24
                int R = (color >> 16) & 0xff;
                int G = (color >> 8) & 0xff;
                int B = (color) & 0xff;
                textColorValue.setText("#RGB" + R + "," + G + "," + B + ",");
                if (bluetoothSocket != null) {
                    bluetoothOutput.write("#RGB" + R + "," + G + "," + B + "," + "\n");
                }


            }
        });
    }
}
