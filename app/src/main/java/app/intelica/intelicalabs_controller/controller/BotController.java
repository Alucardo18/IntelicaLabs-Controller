package app.intelica.intelicalabs_controller.controller;

import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import app.intelica.intelicalabs_controller.R;

import app.intelica.intelicalabs_controller.Util.SettingKeysKt;
import app.intelica.intelicalabs_controller.Util.StaticMessage;
import app.intelica.intelicalabs_controller.Util.bluetooth.BluetoothConnection;
import app.intelica.intelicalabs_controller.Util.bluetooth.BluetoothOutput;
import app.intelica.intelicalabs_controller.Util.help.messages.BotControllerHelpMessages;
import android.preference.PreferenceManager;
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
    private TextView textViewMode;
    private Button battle;
    private Button line;
    private Button colorMixer;
    private Button honk;
    private Button ranger;

    private SharedPreferences controllerSettings;

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
            statusSwitch.setText(getIntent().getStringExtra("deviceName"));
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
        this.textViewMode = (TextView) findViewById(R.id.textViewMode);
        this.controllerSettings = PreferenceManager.getDefaultSharedPreferences(this);

        this.setupUiListeners();
    }

    private void setupUiListeners() {

        this.forwards.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (bluetoothSocket != null) {

                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        forwards.setBackgroundResource(R.drawable.arriba_pressed);
                        bluetoothOutput.write(
                                controllerSettings.getString(SettingKeysKt.getStartIndicator().getKey(), SettingKeysKt.getStartIndicator().getDefault()) +
                                        controllerSettings.getString(SettingKeysKt.getForward().getKey(), SettingKeysKt.getForward().getDefault()) +
                                        controllerSettings.getString(SettingKeysKt.getStopIndicator().getKey(), SettingKeysKt.getStopIndicator().getDefault()));
                    }
                    if (event.getAction() == MotionEvent.ACTION_UP) {
                        forwards.setBackgroundResource(R.drawable.arriba);
                        bluetoothOutput.write(
                                controllerSettings.getString(SettingKeysKt.getStartIndicator().getKey(), SettingKeysKt.getStartIndicator().getDefault()) +
                                        controllerSettings.getString(SettingKeysKt.getStop().getKey(), SettingKeysKt.getStop().getDefault()) +
                                        controllerSettings.getString(SettingKeysKt.getStopIndicator().getKey(), SettingKeysKt.getStopIndicator().getDefault()));

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
                        bluetoothOutput.write(
                                controllerSettings.getString(SettingKeysKt.getStartIndicator().getKey(), SettingKeysKt.getStartIndicator().getDefault()) +
                                        controllerSettings.getString(SettingKeysKt.getBackward().getKey(), SettingKeysKt.getBackward().getDefault()) +
                                        controllerSettings.getString(SettingKeysKt.getStopIndicator().getKey(), SettingKeysKt.getStopIndicator().getDefault()));

                    }
                    if (event.getAction() == MotionEvent.ACTION_UP) {
                        backwards.setBackgroundResource(R.drawable.abajo);
                        bluetoothOutput.write(
                                controllerSettings.getString(SettingKeysKt.getStartIndicator().getKey(), SettingKeysKt.getStartIndicator().getDefault()) +
                                        controllerSettings.getString(SettingKeysKt.getStop().getKey(), SettingKeysKt.getStop().getDefault()) +
                                        controllerSettings.getString(SettingKeysKt.getStopIndicator().getKey(), SettingKeysKt.getStopIndicator().getDefault()));

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
                        bluetoothOutput.write(
                                controllerSettings.getString(SettingKeysKt.getStartIndicator().getKey(), SettingKeysKt.getStartIndicator().getDefault()) +
                                        controllerSettings.getString(SettingKeysKt.getClockwise().getKey(), SettingKeysKt.getClockwise().getDefault()) +
                                        controllerSettings.getString(SettingKeysKt.getStopIndicator().getKey(), SettingKeysKt.getStopIndicator().getDefault()));
                    }
                    if (event.getAction() == MotionEvent.ACTION_UP) {
                        rightRotation.setBackgroundResource(R.drawable.derecha);
                        bluetoothOutput.write(
                                controllerSettings.getString(SettingKeysKt.getStartIndicator().getKey(), SettingKeysKt.getStartIndicator().getDefault()) +
                                        controllerSettings.getString(SettingKeysKt.getStop().getKey(), SettingKeysKt.getStop().getDefault()) +
                                        controllerSettings.getString(SettingKeysKt.getStopIndicator().getKey(), SettingKeysKt.getStopIndicator().getDefault()));
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
                        bluetoothOutput.write(
                                controllerSettings.getString(SettingKeysKt.getStartIndicator().getKey(), SettingKeysKt.getStartIndicator().getDefault()) +
                                        controllerSettings.getString(SettingKeysKt.getCounterClockwise().getKey(), SettingKeysKt.getCounterClockwise().getDefault()) +
                                        controllerSettings.getString(SettingKeysKt.getStopIndicator().getKey(), SettingKeysKt.getStopIndicator().getDefault()));

                    }
                    if (event.getAction() == MotionEvent.ACTION_UP) {
                        leftRotation.setBackgroundResource(R.drawable.izquierda);
                        bluetoothOutput.write(
                                controllerSettings.getString(SettingKeysKt.getStartIndicator().getKey(), SettingKeysKt.getStartIndicator().getDefault()) +
                                        controllerSettings.getString(SettingKeysKt.getStop().getKey(), SettingKeysKt.getStop().getDefault()) +
                                        controllerSettings.getString(SettingKeysKt.getStopIndicator().getKey(), SettingKeysKt.getStopIndicator().getDefault()));
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
    }

    public void back(View view) {

        finish();
    }

    public void battle(View view) {

        if (bluetoothSocket != null) {
            textViewMode.setText(StaticMessage.BATTLE);
            textViewMode.setTextColor(getColor(R.color.colorPrimary));
            bluetoothOutput.write(
                    controllerSettings.getString(SettingKeysKt.getStartIndicator().getKey(), SettingKeysKt.getStartIndicator().getDefault()) +
                            controllerSettings.getString(SettingKeysKt.getBattle().getKey(), SettingKeysKt.getBattle().getDefault()) +
                            controllerSettings.getString(SettingKeysKt.getStopIndicator().getKey(), SettingKeysKt.getStopIndicator().getDefault()));

            Toast.makeText(BotController.this, StaticMessage.BATTLE_ON, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(BotController.this, StaticMessage.UN_CONNECTED, Toast.LENGTH_SHORT).show();
        }
    }

    public void ranger(View view) {

        if (bluetoothSocket != null) {
            textViewMode.setText(StaticMessage.RANGER);
            textViewMode.setTextColor(getColor(R.color.colorPrimary));
            bluetoothOutput.write(
                    controllerSettings.getString(SettingKeysKt.getStartIndicator().getKey(), SettingKeysKt.getStartIndicator().getDefault()) +
                            controllerSettings.getString(SettingKeysKt.getRanger().getKey(), SettingKeysKt.getRanger().getDefault()) +
                            controllerSettings.getString(SettingKeysKt.getStopIndicator().getKey(), SettingKeysKt.getStopIndicator().getDefault()));

            Toast.makeText(BotController.this, StaticMessage.RANGER_ON, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(BotController.this, StaticMessage.UN_CONNECTED, Toast.LENGTH_SHORT).show();
        }
    }

    public void line(View view) {

        if (bluetoothSocket != null) {
            textViewMode.setText(StaticMessage.LINE);
            textViewMode.setTextColor(getColor(R.color.colorPrimary));
            bluetoothOutput.write(
                    controllerSettings.getString(SettingKeysKt.getStartIndicator().getKey(), SettingKeysKt.getStartIndicator().getDefault()) +
                            controllerSettings.getString(SettingKeysKt.getLine().getKey(), SettingKeysKt.getLine().getDefault()) +
                            controllerSettings.getString(SettingKeysKt.getStopIndicator().getKey(), SettingKeysKt.getStopIndicator().getDefault()));

            Toast.makeText(BotController.this, StaticMessage.LINE_ON, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(BotController.this, StaticMessage.UN_CONNECTED, Toast.LENGTH_SHORT).show();
        }
    }

    public void colorMixer(View view) {

        Intent color = new Intent(BotController.this, ColorMixer.class);
        startActivity(color);
    }

    public void honk(View view) {

        if (bluetoothSocket != null) {
            bluetoothOutput.write(
                    controllerSettings.getString(SettingKeysKt.getStartIndicator().getKey(), SettingKeysKt.getStartIndicator().getDefault()) +
                            controllerSettings.getString(SettingKeysKt.getHonk().getKey(), SettingKeysKt.getHonk().getDefault()) +
                            controllerSettings.getString(SettingKeysKt.getStopIndicator().getKey(), SettingKeysKt.getStopIndicator().getDefault()));


        } else {
            Toast.makeText(BotController.this, StaticMessage.UN_CONNECTED, Toast.LENGTH_SHORT).show();
        }
    }

    public void help(View view) {

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
                        .setTitleText("ADELANTE")
                        .build()
        );
        sequence.addSequenceItem(
                new MaterialShowcaseView.Builder(this)
                        .setTarget(this.backwards)
                        .setDismissText("")
                        .setDismissOnTouch(true)
                        .setContentText(BotControllerHelpMessages.BACKWARDS.toString())
                        .withRectangleShape()
                        .setTitleText("ATRAS")
                        .build()
        );
        sequence.addSequenceItem(
                new MaterialShowcaseView.Builder(this)
                        .setTarget(this.rightRotation)
                        .setDismissText("")
                        .setDismissOnTouch(true)
                        .setContentText(BotControllerHelpMessages.ROTATE_RIGHT.toString())
                        .withRectangleShape()
                        .setTitleText("DERECHA")
                        .build()
        );
        sequence.addSequenceItem(
                new MaterialShowcaseView.Builder(this)
                        .setTarget(this.leftRotation)
                        .setDismissText("")
                        .setDismissOnTouch(true)
                        .setContentText(BotControllerHelpMessages.ROTATE_LEFT.toString())
                        .withRectangleShape()
                        .setTitleText("IZQUIERDA")
                        .build()
        );
        sequence.addSequenceItem(
                new MaterialShowcaseView.Builder(this)
                        .setTarget(this.battle)
                        .setDismissText("")
                        .setDismissOnTouch(true)
                        .setContentText(BotControllerHelpMessages.BATTLE.toString())
                        .withRectangleShape()
                        .setTitleText("MODO BATALLA")
                        .build()
        );
        sequence.addSequenceItem(
                new MaterialShowcaseView.Builder(this)
                        .setTarget(this.line)
                        .setDismissText("")
                        .setDismissOnTouch(true)
                        .setContentText(BotControllerHelpMessages.LINE.toString())
                        .withRectangleShape()
                        .setTitleText("MODO SEGUIDOR")
                        .build()
        );
        sequence.addSequenceItem(
                new MaterialShowcaseView.Builder(this)
                        .setTarget(this.ranger)
                        .setDismissText("")
                        .setDismissOnTouch(true)
                        .setContentText(BotControllerHelpMessages.RANGER.toString())
                        .withRectangleShape()
                        .setTitleText("MODO EXPLORADOR")
                        .build()
        );
        sequence.addSequenceItem(
                new MaterialShowcaseView.Builder(this)
                        .setTarget(this.colorMixer)
                        .setDismissText("")
                        .setDismissOnTouch(true)
                        .setContentText(BotControllerHelpMessages.COLOR_MIXER.toString())
                        .withRectangleShape()
                        .setTitleText("COLOR MIXER")
                        .build()
        );
        sequence.addSequenceItem(
                new MaterialShowcaseView.Builder(this)
                        .setTarget(this.honk)
                        .setDismissText("")
                        .setDismissOnTouch(true)
                        .setContentText(BotControllerHelpMessages.HONK.toString())
                        .withRectangleShape()
                        .setTitleText("BUZZER")
                        .build()
        );

        sequence.start();

    }

    public void openSettings(View view) {

        Intent color = new Intent(BotController.this, ControllerSettings.class);
        startActivity(color);
    }
}
