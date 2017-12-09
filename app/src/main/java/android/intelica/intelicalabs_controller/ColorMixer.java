package android.intelica.intelicalabs_controller;

import android.bluetooth.BluetoothSocket;
import android.content.pm.ActivityInfo;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Toast;

public class ColorMixer extends AppCompatActivity {

    private SeekBar redSeekBar;
    private SeekBar greenSeekBar;
    private SeekBar blueSeekBar;
    public BluetoothSocket bluetoothSocket=null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color_mixer);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

//casting
        redSeekBar = (SeekBar) findViewById(R.id.seekBarRed);
        greenSeekBar = (SeekBar) findViewById(R.id.seekBarGreen);
        blueSeekBar = (SeekBar) findViewById(R.id.seekBarBlue);
        redSeekBar.setMax(200);
        greenSeekBar.setMax(200);
        blueSeekBar.setMax(200);


//HIDE ACTIONBAR
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

    bluetoothSocket = MainActivity.globalSocket;

///SEEKBARS
        redSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (bluetoothSocket != null) {
                    MainActivity.mConnectedThread.write("#R" + progress + "\n");
                }
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                if (bluetoothSocket == null){
                    Toast.makeText(ColorMixer.this, "CONECTE SU ROBOT PRIMERO", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        greenSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (bluetoothSocket != null) {
                    MainActivity.mConnectedThread.write("#G" + progress + "\n");
                }
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                if (bluetoothSocket == null){
                    Toast.makeText(ColorMixer.this, "CONECTE SU ROBOT PRIMERO", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        blueSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (bluetoothSocket != null) {
                    MainActivity.mConnectedThread.write("#B" + progress + "\n");
                }
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                if (bluetoothSocket == null){
                    Toast.makeText(ColorMixer.this, "CONECTE SU ROBOT PRIMERO", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
    }
}
