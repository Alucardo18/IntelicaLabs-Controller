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
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class BotController extends AppCompatActivity {



public BluetoothSocket bluetoothSocket = null;  //Bluettoh soocket object


//Full screen settings
//TODO: CREATE A NEW CLASS TO SET ALL GENERAL MESSAGES INTO A STATIC VARIABLES


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bot_controller);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
//casstting views
        Button adelante = (Button) findViewById(R.id.buttonAdelante);
        Button atras = (Button) findViewById(R.id.buttonAtras);
        Button izquierda = (Button) findViewById(R.id.buttonIzquierda);
        Button derecha = (Button) findViewById(R.id.buttonDerecha);
        Button colorMixer = (Button) findViewById(R.id.buttonColorMixer);
        Button battle = (Button) findViewById(R.id.buttonBattle);
        Button ranger = (Button) findViewById(R.id.buttonRanger);
        Button line = (Button) findViewById(R.id.buttonLine);
        Button honk = (Button) findViewById(R.id.buttonHonk);
        Switch statusSwitch = (Switch) findViewById(R.id.statusSwitch);
        final TextView textViewMode = (TextView) findViewById(R.id.textViewMode);

        ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.hide();
                    }

        bluetoothSocket = MainActivity.globalSocket;
        //CHECAR EL STATUS DE CONECCCION DEL ROBOT
        statusSwitch.setClickable(false);
        if (bluetoothSocket != null){
            statusSwitch.setChecked(true);
            statusSwitch.setText("ON");
        } else {
            statusSwitch.setChecked(false);
            statusSwitch.setText("OFF");
        }

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

//IZUIERDA
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
        //BOTONES DE FUNCIONALIDAD ESPECIAL
        battle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(bluetoothSocket != null ){
                    textViewMode.setText("BATTLE MODE");
                    textViewMode.setTextColor(getColor(R.color.colorPrimary));
                    MainActivity.mConnectedThread.write("%BATTLE" + "\n");
                    Toast.makeText(BotController.this,"BATTLE MODE ACTIVATED",Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(BotController.this, "CONECTE SU ROBOT PRIMERO", Toast.LENGTH_SHORT).show();
                }


            }
        });
        ranger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (bluetoothSocket != null){
                    textViewMode.setText("RANGER MODE");
                    textViewMode.setTextColor(getColor(R.color.colorPrimary));
                    MainActivity.mConnectedThread.write("%RANGER" + "\n");
                    Toast.makeText(BotController.this,"BATTLE MODE ACTIVATED",Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(BotController.this, "CONECTE SU ROBOT PRIMERO", Toast.LENGTH_SHORT).show();
                }

            }
        });
        line.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (bluetoothSocket != null){
                    textViewMode.setText("LINE MODE");
                    textViewMode.setTextColor(getColor(R.color.colorPrimary));
                    MainActivity.mConnectedThread.write("%LINE" + "\n");
                    Toast.makeText(BotController.this,"LINE MODE ACTIVATED",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(BotController.this, "CONECTE SU ROBOT PRIMERO", Toast.LENGTH_SHORT).show();
                }
            }
        });
        honk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (bluetoothSocket != null){
                    MainActivity.mConnectedThread.write("%HONK" + "\n");

                }else{
                    Toast.makeText(BotController.this, "CONECTE SU ROBOT PRIMERO", Toast.LENGTH_SHORT).show();
                }
            }
        });


//oncreate semicolum
    }
}
