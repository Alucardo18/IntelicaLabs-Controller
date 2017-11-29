package android.intelica.intelicalabs_controller;

import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class BotController extends AppCompatActivity {

    private Button adelante;
    private Button atras;
    private Button izquierda;
    private Button derecha;
    private Button colorMixer;

    private BluetoothSocket bluetoothSocket = null;


//Full screen settings



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bot_controller);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        adelante = (Button) findViewById(R.id.buttonAdelante);
        atras = (Button) findViewById(R.id.buttonAtras);
        izquierda = (Button) findViewById(R.id.buttonIzquierda);
        derecha = (Button) findViewById(R.id.buttonDerecha);
        colorMixer = (Button) findViewById(R.id.buttonColorMixer);

            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.hide();
                    }

        bluetoothSocket = MainActivity.globalSocket;


//ADELANTE
    adelante.setOnTouchListener(new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (bluetoothSocket !=null){

                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    MainActivity.mConnectedThread.write("%FORWARD"+"\n");
                }
                if(event.getAction() == MotionEvent.ACTION_UP){
                    MainActivity.mConnectedThread.write("%STOP"+"\n");
                }

            }else {
                Toast.makeText(BotController.this,"CONNECTE SU ROBOT",Toast.LENGTH_SHORT).show();
            }

            return true;
        }
    });
//ATRAS
       atras.setOnTouchListener(new View.OnTouchListener() {
           @Override
           public boolean onTouch(View v, MotionEvent event) {
               if (bluetoothSocket !=null){

                   if(event.getAction() == MotionEvent.ACTION_DOWN){
                       MainActivity.mConnectedThread.write("%BACKWARD" + "\n");
                   }
                   if(event.getAction() == MotionEvent.ACTION_UP){
                       MainActivity.mConnectedThread.write("%STOP" + "\n");
                   }

               }else {
                   Toast.makeText(BotController.this,"CONNECTE SU ROBOT",Toast.LENGTH_SHORT).show();
               }
               return true;
           }
       });

//DERECHA
        derecha.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (bluetoothSocket !=null){

                    if(event.getAction() == MotionEvent.ACTION_DOWN){
                        MainActivity.mConnectedThread.write("%RIGHT"+ "\n");
                    }
                    if(event.getAction() == MotionEvent.ACTION_UP){
                        MainActivity.mConnectedThread.write("%STOP"+"\n");
                    }

                }else {
                    Toast.makeText(BotController.this,"CONNECTE SU ROBOT",Toast.LENGTH_SHORT).show();
                }
                return true;
            }
        });

//IZUIERDA o
        izquierda.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (bluetoothSocket !=null){

                    if(event.getAction() == MotionEvent.ACTION_DOWN){
                        MainActivity.mConnectedThread.write("%LEFT" + "\n");
                    }
                    if(event.getAction() == MotionEvent.ACTION_UP){
                        MainActivity.mConnectedThread.write("%STOP" + "\n");
                    }

                }else {
                    Toast.makeText(BotController.this,"CONNECTE SU ROBOT",Toast.LENGTH_SHORT).show();
                }
                return true;
            }
        });
        colorMixer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent color = new Intent(BotController.this,ColorMixer.class);
                startActivity(color);
            }
        });
    }







}
