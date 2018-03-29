package android.intelica.intelicalabs_controller;

import android.bluetooth.BluetoothSocket;
import android.content.pm.ActivityInfo;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.larswerkman.holocolorpicker.ColorPicker;
import com.larswerkman.holocolorpicker.OpacityBar;

public class ColorMixer extends AppCompatActivity {

ColorPicker colorPicker;
OpacityBar opacityBar;
TextView textColorValue;

    public BluetoothSocket bluetoothSocket=null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color_mixer);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
//HIDE ACTIONBAR
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

    bluetoothSocket = MainActivity.globalSocket;

    colorPicker = (ColorPicker) findViewById(R.id.picker);
    opacityBar = (OpacityBar) findViewById(R.id.opacitybar);
    textColorValue = (TextView) findViewById(R.id.textColorValue);

    colorPicker.addOpacityBar(opacityBar);
    colorPicker.getColor();
    colorPicker.setShowOldCenterColor(false);

        colorPicker.setOnColorChangedListener(new ColorPicker.OnColorChangedListener() {
            @Override
            public void onColorChanged(int color) {
                int A = (color >> 24) & 0xff; // or color >>> 24
                int R = (color >> 16) & 0xff;
                int G = (color >>  8) & 0xff;
                int B = (color      ) & 0xff;
                textColorValue.setText("RED="+ R +" GREEN="+ G + " BLUE="+B+ "Alpha" + A);


            }
        });

    }
}
