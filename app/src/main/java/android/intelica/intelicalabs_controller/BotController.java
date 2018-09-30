package android.intelica.intelicalabs_controller;

import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.intelica.intelicalabs_controller.Util.StaticMessage;
import android.intelica.intelicalabs_controller.Util.bluetooth.BluetoothConnection;
import android.intelica.intelicalabs_controller.Util.bluetooth.BluetoothOutput;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class BotController extends AppCompatActivity {


    private BluetoothSocket bluetoothSocket = null;  //Bluettoh soocket object
    private BluetoothOutput bluetoothOutput;


//Full screen settings


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bot_controller);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
//casstting views
        final Button adelante = (Button) findViewById(R.id.buttonAdelante);
        final Button atras = (Button) findViewById(R.id.buttonAtras);
        final Button izquierda = (Button) findViewById(R.id.buttonIzquierda);
        final Button derecha = (Button) findViewById(R.id.buttonDerecha);
        Button colorMixer = (Button) findViewById(R.id.buttonColorMixer);
        Button battle = (Button) findViewById(R.id.buttonBattle);
        Button ranger = (Button) findViewById(R.id.buttonRanger);
        Button line = (Button) findViewById(R.id.buttonLine);
        Button honk = (Button) findViewById(R.id.buttonHonk);
        Switch statusSwitch = (Switch) findViewById(R.id.statusSwitch);
        final TextView textViewMode = (TextView) findViewById(R.id.textViewMode);


        bluetoothSocket = BluetoothConnection.getInstance().getBluetoothSocket();
        this.bluetoothOutput = BluetoothConnection.getInstance().getBluetoothOutput();
        //CHECAR EL STATUS DE CONECCCION DEL ROBOT
        statusSwitch.setClickable(false);
        if (bluetoothSocket != null) {
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
                if (bluetoothSocket != null) {

                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        adelante.setBackgroundResource(R.drawable.arriba_pressed);
                        bluetoothOutput.write("%FORWARD" + "\n");
                    }
                    if (event.getAction() == MotionEvent.ACTION_UP) {
                        adelante.setBackgroundResource(R.drawable.arriba);
                        bluetoothOutput.write("%STOP" + "\n");
                    }
                } else {
                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        adelante.setBackgroundResource(R.drawable.arriba_pressed);
                    }
                    if (event.getAction() == MotionEvent.ACTION_UP) {
                        adelante.setBackgroundResource(R.drawable.arriba);

                    }
                }

                return true;
            }
        });
//ATRAS
        atras.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (bluetoothSocket != null) {

                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        atras.setBackgroundResource(R.drawable.abajo_pressed);
                        bluetoothOutput.write("%BACKWARD" + "\n");
                    }
                    if (event.getAction() == MotionEvent.ACTION_UP) {
                        atras.setBackgroundResource(R.drawable.abajo);
                        bluetoothOutput.write("%STOP" + "\n");
                    }

                } else {
                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        atras.setBackgroundResource(R.drawable.abajo_pressed);
                    }
                    if (event.getAction() == MotionEvent.ACTION_UP) {
                        atras.setBackgroundResource(R.drawable.abajo);
                    }
                }
                return true;
            }
        });

//DERECHA
        derecha.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (bluetoothSocket != null) {

                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        derecha.setBackgroundResource(R.drawable.derecha_pressed);
                        bluetoothOutput.write("%RIGHT" + "\n");
                    }
                    if (event.getAction() == MotionEvent.ACTION_UP) {
                        derecha.setBackgroundResource(R.drawable.derecha);
                        bluetoothOutput.write("%STOP" + "\n");
                    }

                } else {
                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        derecha.setBackgroundResource(R.drawable.derecha_pressed);
                    }
                    if (event.getAction() == MotionEvent.ACTION_UP) {
                        derecha.setBackgroundResource(R.drawable.derecha);
                    }
                }
                return true;
            }
        });

//IZUIERDA
        izquierda.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (bluetoothSocket != null) {

                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        izquierda.setBackgroundResource(R.drawable.izquierda_pressed);
                        bluetoothOutput.write("%LEFT" + "\n");
                    }
                    if (event.getAction() == MotionEvent.ACTION_UP) {
                        izquierda.setBackgroundResource(R.drawable.izquierda);
                        bluetoothOutput.write("%STOP" + "\n");
                    }

                } else {
                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        izquierda.setBackgroundResource(R.drawable.izquierda_pressed);

                    }
                    if (event.getAction() == MotionEvent.ACTION_UP) {
                        izquierda.setBackgroundResource(R.drawable.izquierda);

                    }
                }
                return true;
            }
        });
        colorMixer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent color = new Intent(BotController.this, ColorMixer.class);
                startActivity(color);
            }
        });
        //BOTONES DE FUNCIONALIDAD ESPECIAL
        //TODO: CHANGE TOAST MESSAGE FOR AN ALERTDIALOG BOX INCLUDE IMAGE OF BATTLE/ RANGER / FOLLOWER
        battle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (bluetoothSocket != null) {
                    textViewMode.setText(StaticMessage.BATTLE);
                    textViewMode.setTextColor(getColor(R.color.colorPrimary));
                    bluetoothOutput.write("%BATTLE" + "\n");
                    Toast.makeText(BotController.this, StaticMessage.BATTLE_ON, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(BotController.this, StaticMessage.UN_CONNECTED, Toast.LENGTH_SHORT).show();
                }


            }
        });
        ranger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (bluetoothSocket != null) {
                    textViewMode.setText(StaticMessage.RANGER);
                    textViewMode.setTextColor(getColor(R.color.colorPrimary));
                    bluetoothOutput.write("%RANGER" + "\n");
                    Toast.makeText(BotController.this, StaticMessage.RANGER_ON, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(BotController.this, StaticMessage.UN_CONNECTED, Toast.LENGTH_SHORT).show();
                }

            }
        });
        line.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (bluetoothSocket != null) {
                    textViewMode.setText(StaticMessage.LINE);
                    textViewMode.setTextColor(getColor(R.color.colorPrimary));
                    bluetoothOutput.write("%LINE" + "\n");
                    Toast.makeText(BotController.this, StaticMessage.LINE_ON, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(BotController.this, StaticMessage.UN_CONNECTED, Toast.LENGTH_SHORT).show();
                }
            }
        });
        honk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (bluetoothSocket != null) {
                    bluetoothOutput.write("%HONK" + "\n");

                } else {
                    Toast.makeText(BotController.this, StaticMessage.UN_CONNECTED, Toast.LENGTH_SHORT).show();
                }
            }
        });


//oncreate semicolum
    }
}
