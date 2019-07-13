package app.intelica.intelicalabs_controller.controller;


import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.content.pm.ActivityInfo;

import app.intelica.intelicalabs_controller.Util.bluetooth.BluetoothManager;
import app.intelica.intelicalabs_controller.Util.help.messages.BotSelectorHelpMessages;
import app.intelica.intelicalabs_controller.view.BluetoothDevicesList;
import app.intelica.intelicalabs_controller.R;
import app.intelica.intelicalabs_controller.Util.StaticMessage;
import app.intelica.intelicalabs_controller.Util.bluetooth.BluetoothConnection;
import app.intelica.intelicalabs_controller.Util.bluetooth.BluetoothConnector;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import uk.co.deanwild.materialshowcaseview.MaterialShowcaseSequence;
import uk.co.deanwild.materialshowcaseview.MaterialShowcaseView;
import uk.co.deanwild.materialshowcaseview.ShowcaseConfig;


public class BotSelector extends AppCompatActivity {

    private static final int REQUEST_CODE_BLUETOOTH_ON = 1313;
    private Set<BluetoothDevice> pairedDevices;
    private String selectedDeviceName = null;

    private ListView listView;

//TODO: convert MainActivityLayout into Landscape layout

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bot_selector);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        if ((BluetoothManager.isBluetoothSupported()) && !BluetoothManager.isBluetoothEnable()) {
            turnOnBluetooth();
        }

        WebView webView = (WebView) findViewById(R.id.webView);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);


        if (BluetoothConnection.getInstance().getBluetoothSocket() != null) {
            webView.loadUrl("file:///android_asset/connected.png");
            Toast.makeText(
                    this,
                    getResources().getString(R.string.bot_selector_connected_message),
                    Toast.LENGTH_SHORT
            ).show();
        } else {
            webView.loadUrl("file:///android_asset/bluemotion.gif");
        }

        this.setupUiListeners();
    }

    @Override
    protected void onStart() {
        super.onStart();

        if ((BluetoothManager.isBluetoothSupported()) && BluetoothManager.isBluetoothEnable()) {
            listPairedDevices();
        }
    }


    private void setupUiListeners() {

        listView = (ListView) findViewById(R.id.listViewPaired);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                List<BluetoothDevice> device2 = new ArrayList<BluetoothDevice>(pairedDevices);
                BluetoothDevice selectedDevice = device2.get(position);
                selectedDeviceName = selectedDevice.getName();

                Toast.makeText(
                        BotSelector.this,
                        getResources().getString(R.string.bot_selector_connecting_message),
                        Toast.LENGTH_SHORT
                ).show();
                BluetoothConnector connectThread = new BluetoothConnector(device2.get(position));
                connectThread.start();


            }
        });
    }


    private void turnOnBluetooth() {

        Intent requestBluetoothOn = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        this.startActivityForResult(requestBluetoothOn, REQUEST_CODE_BLUETOOTH_ON);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == REQUEST_CODE_BLUETOOTH_ON) {
            switch (resultCode) {
                case Activity.RESULT_CANCELED: {
                    Toast.makeText(BotSelector.this, StaticMessage.UN_CONNECTED, Toast.LENGTH_SHORT).show();
                    listPairedDevices();
                }
            }
        }

    }


    private void listPairedDevices() {
        if (BluetoothManager.isBluetoothEnable()) {
            pairedDevices = BluetoothAdapter.getDefaultAdapter().getBondedDevices();
            ArrayList<String> devices = new ArrayList<>();
            if (pairedDevices.size() > 0) {

                for (BluetoothDevice bt : pairedDevices) {
                    devices.add(bt.getName() + "\n" + bt.getAddress());
                }
                BluetoothDevicesList bluetoothDevicesList = new BluetoothDevicesList(BotSelector.this, R.layout.my_listview, devices);

                listView.setAdapter(bluetoothDevicesList);
            } else {
                Toast.makeText(BotSelector.this, StaticMessage.DV_NOT_FOUND, Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(BotSelector.this, StaticMessage.BT_NOT_ENABLE, Toast.LENGTH_LONG).show();
        }

    }


    public void help(View view){

        MaterialShowcaseSequence sequence = new MaterialShowcaseSequence(this);
        ShowcaseConfig config = new ShowcaseConfig();
        config.setDelay(500); // half second between each showcase view
        sequence.setConfig(config);

        sequence.addSequenceItem(
                new MaterialShowcaseView.Builder(this)
                        .setTarget(findViewById(R.id.listRobots))
                        .setDismissText("")
                        .setDismissOnTouch(true)
                        .setContentText(getResources().getText(R.string.bot_selector_tutorial_fill_robot_list))
                        .withRectangleShape()
                        .setTitleText(getResources().getText(R.string.bot_selector_tutorial_fill_robot_list_title))
                        .build()
        );
        sequence.addSequenceItem(
                new MaterialShowcaseView.Builder(this)
                        .setTarget(findViewById(R.id.deviceListTitle))
                        .setDismissText("")
                        .setDismissOnTouch(true)
                        .setContentText(getResources().getText(R.string.bot_selector_tutorial_robot_list))
                        .withRectangleShape()
                        .setTitleText(getResources().getText(R.string.bot_selector_tutorial_robot_list_title))
                        .build()
        );
        sequence.addSequenceItem(
                new MaterialShowcaseView.Builder(this)
                        .setTarget(findViewById(R.id.gameController))
                        .setDismissText("")
                        .setDismissOnTouch(true)
                        .setContentText(getResources().getText(R.string.bot_selector_tutorial_start_controller))
                        .withRectangleShape()
                        .setTitleText(getResources().getText(R.string.bot_selector_tutorial_start_controller_title))
                        .build()
        );
        sequence.addSequenceItem(
                new MaterialShowcaseView.Builder(this)
                        .setTarget(findViewById(R.id.disconnectRobot))
                        .setDismissText("")
                        .setDismissOnTouch(true)
                        .setContentText(getResources().getText(R.string.bot_selector_tutorial_disconnect))
                        .withRectangleShape()
                        .setTitleText(getResources().getText(R.string.bot_selector_tutorial_disconnect_title))
                        .build()
        );

        sequence.start();

    }


    public void listDevices(View view) {

        listPairedDevices();
    }

    public void disconnect(View view) {

        if (BluetoothConnection.getInstance().getBluetoothSocket() != null) {
            BluetoothConnector connectThread = new BluetoothConnector();
            connectThread.cancel();
        } else {
            Toast.makeText(BotSelector.this, StaticMessage.UN_CONNECTED, Toast.LENGTH_SHORT).show();

        }
    }

    public void startBotController(View view) {

        Intent controller = new Intent(BotSelector.this, BotController.class);
        controller.putExtra("deviceName", this.selectedDeviceName);
        startActivity(controller);
    }

}


