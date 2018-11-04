package android.intelica.intelicalabs_controller.controller;

import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.intelica.intelicalabs_controller.R;
import android.intelica.intelicalabs_controller.Util.StaticMessage;
import android.intelica.intelicalabs_controller.Util.bluetooth.BluetoothConnection;
import android.intelica.intelicalabs_controller.Util.bluetooth.BluetoothOutput;
import android.intelica.intelicalabs_controller.Util.help.messages.BotControllerHelpMessages;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import uk.co.deanwild.materialshowcaseview.MaterialShowcaseSequence;
import uk.co.deanwild.materialshowcaseview.MaterialShowcaseView;
import uk.co.deanwild.materialshowcaseview.ShowcaseConfig;

public class BotController extends AppCompatActivity {


    private BluetoothSocket bluetoothSocket;
    private BluetoothOutput bluetoothOutput;
    private Button forwards;
    private Button backwards;
    private Button rightRotation;
    private Button leftRotation;
    private Button battle;
    private Button line;
    private Button colorMixer;
    private Button honk;
    private Button ranger;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bot_controller);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        bluetoothSocket = BluetoothConnection.getInstance().getBluetoothSocket();
        this.bluetoothOutput = BluetoothConnection.getInstance().getBluetoothOutput();

        Switch statusSwitch = (Switch) findViewById(R.id.statusSwitch);
        statusSwitch.setClickable(false);
        if (bluetoothSocket != null) {
            statusSwitch.setChecked(true);
            statusSwitch.setText("ON");
        } else {
            statusSwitch.setChecked(false);
            statusSwitch.setText("OFF");
        }

        this.forwards = (Button) findViewById(R.id.buttonAdelante);
        this.backwards = (Button) findViewById(R.id.buttonAtras);
        this.rightRotation = (Button) findViewById(R.id.buttonDerecha);
        this.leftRotation = (Button) findViewById(R.id.buttonIzquierda);
        this.battle = (Button) findViewById(R.id.buttonBattle);
        this.line = (Button) findViewById(R.id.buttonLine);
        this.colorMixer = (Button) findViewById(R.id.buttonColorMixer);
        this.honk = (Button) findViewById(R.id.buttonHonk);
        this.ranger = (Button) findViewById(R.id.buttonRanger);

        this.setupUiListeners();
    }

    private void setupUiListeners(){

        this.forwards.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (bluetoothSocket != null) {

                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        forwards.setBackgroundResource(R.drawable.arriba_pressed);
                        bluetoothOutput.write("%FORWARD" + "\n");
                    }
                    if (event.getAction() == MotionEvent.ACTION_UP) {
                        forwards.setBackgroundResource(R.drawable.arriba);
                        bluetoothOutput.write("%STOP" + "\n");
                    }
                } else {
                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        forwards.setBackgroundResource(R.drawable.arriba_pressed);
                    }
                    if (event.getAction() == MotionEvent.ACTION_UP) {
                        forwards.setBackgroundResource(R.drawable.arriba);

                    }
                }

                return true;
            }
        });

        this.backwards.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (bluetoothSocket != null) {

                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        backwards.setBackgroundResource(R.drawable.abajo_pressed);
                        bluetoothOutput.write("%BACKWARD" + "\n");
                    }
                    if (event.getAction() == MotionEvent.ACTION_UP) {
                        backwards.setBackgroundResource(R.drawable.abajo);
                        bluetoothOutput.write("%STOP" + "\n");
                    }

                } else {
                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        backwards.setBackgroundResource(R.drawable.abajo_pressed);
                    }
                    if (event.getAction() == MotionEvent.ACTION_UP) {
                        backwards.setBackgroundResource(R.drawable.abajo);
                    }
                }
                return true;
            }
        });

        this.rightRotation.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (bluetoothSocket != null) {

                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        rightRotation.setBackgroundResource(R.drawable.derecha_pressed);
                        bluetoothOutput.write("%RIGHT" + "\n");
                    }
                    if (event.getAction() == MotionEvent.ACTION_UP) {
                        rightRotation.setBackgroundResource(R.drawable.derecha);
                        bluetoothOutput.write("%STOP" + "\n");
                    }

                } else {
                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        rightRotation.setBackgroundResource(R.drawable.derecha_pressed);
                    }
                    if (event.getAction() == MotionEvent.ACTION_UP) {
                        rightRotation.setBackgroundResource(R.drawable.derecha);
                    }
                }
                return true;
            }
        });

        this.leftRotation.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (bluetoothSocket != null) {

                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        leftRotation.setBackgroundResource(R.drawable.izquierda_pressed);
                        bluetoothOutput.write("%LEFT" + "\n");
                    }
                    if (event.getAction() == MotionEvent.ACTION_UP) {
                        leftRotation.setBackgroundResource(R.drawable.izquierda);
                        bluetoothOutput.write("%STOP" + "\n");
                    }

                } else {
                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        leftRotation.setBackgroundResource(R.drawable.izquierda_pressed);

                    }
                    if (event.getAction() == MotionEvent.ACTION_UP) {
                        leftRotation.setBackgroundResource(R.drawable.izquierda);

                    }
                }
                return true;
            }
        });

        //TODO: CHANGE TOAST MESSAGE FOR AN ALERTDIALOG BOX INCLUDE IMAGE OF BATTLE/ RANGER / FOLLOWER
        final TextView textViewMode = (TextView) findViewById(R.id.textViewMode);

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

        this.ranger.setOnClickListener(new View.OnClickListener() {
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

        this.colorMixer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent color = new Intent(BotController.this, ColorMixer.class);
                startActivity(color);
            }
        });

        this.honk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (bluetoothSocket != null) {
                    bluetoothOutput.write("%HONK" + "\n");

                } else {
                    Toast.makeText(BotController.this, StaticMessage.UN_CONNECTED, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void back(View view){

        finish();
    }

    public void help(View view){

        MaterialShowcaseSequence sequence = new MaterialShowcaseSequence(this);
        ShowcaseConfig config = new ShowcaseConfig();
        config.setDelay(500); // half second between each showcase view
        sequence.setConfig(config);

        sequence.addSequenceItem(
                new MaterialShowcaseView.Builder(this)
                        .setTarget(this.forwards)
                        .setDismissText("")
                        .setDismissOnTouch(true)
                        .setContentText(BotControllerHelpMessages.FORWARDS.toString())
                        .withRectangleShape()
                        .build()
        );
        sequence.addSequenceItem(
                new MaterialShowcaseView.Builder(this)
                        .setTarget(this.backwards)
                        .setDismissText("")
                        .setDismissOnTouch(true)
                        .setContentText(BotControllerHelpMessages.BACKWARDS.toString())
                        .withRectangleShape()
                        .build()
        );
        sequence.addSequenceItem(
                new MaterialShowcaseView.Builder(this)
                        .setTarget(this.rightRotation)
                        .setDismissText("")
                        .setDismissOnTouch(true)
                        .setContentText(BotControllerHelpMessages.ROTATE_RIGHT.toString())
                        .withRectangleShape()
                        .build()
        );
        sequence.addSequenceItem(
                new MaterialShowcaseView.Builder(this)
                        .setTarget(this.leftRotation)
                        .setDismissText("")
                        .setDismissOnTouch(true)
                        .setContentText(BotControllerHelpMessages.ROTATE_LEFT.toString())
                        .withRectangleShape()
                        .build()
        );
        sequence.addSequenceItem(
                new MaterialShowcaseView.Builder(this)
                        .setTarget(this.battle)
                        .setDismissText("")
                        .setDismissOnTouch(true)
                        .setContentText(BotControllerHelpMessages.BATTLE.toString())
                        .withRectangleShape()
                        .build()
        );
        sequence.addSequenceItem(
                new MaterialShowcaseView.Builder(this)
                        .setTarget(this.line)
                        .setDismissText("")
                        .setDismissOnTouch(true)
                        .setContentText(BotControllerHelpMessages.LINE.toString())
                        .withRectangleShape()
                        .build()
        );
        sequence.addSequenceItem(
                new MaterialShowcaseView.Builder(this)
                        .setTarget(this.ranger)
                        .setDismissText("")
                        .setDismissOnTouch(true)
                        .setContentText(BotControllerHelpMessages.RANGER.toString())
                        .withRectangleShape()
                        .build()
        );
        sequence.addSequenceItem(
                new MaterialShowcaseView.Builder(this)
                        .setTarget(this.colorMixer)
                        .setDismissText("")
                        .setDismissOnTouch(true)
                        .setContentText(BotControllerHelpMessages.COLOR_MIXER.toString())
                        .withRectangleShape()
                        .build()
        );
        sequence.addSequenceItem(
                new MaterialShowcaseView.Builder(this)
                        .setTarget(this.honk)
                        .setDismissText("")
                        .setDismissOnTouch(true)
                        .setContentText(BotControllerHelpMessages.HONK.toString())
                        .withRectangleShape()
                        .build()
        );

        sequence.start();

    }
}
