package app.intelica.intelicalabs_controller.controller;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import app.intelica.intelicalabs_controller.R;
import app.intelica.intelicalabs_controller.Util.StaticMessage;
import app.intelica.intelicalabs_controller.Util.bluetooth.BluetoothConnection;
import app.intelica.intelicalabs_controller.Util.bluetooth.BluetoothConnector;
import app.intelica.intelicalabs_controller.Util.bluetooth.BluetoothHelper;
import app.intelica.intelicalabs_controller.view.BluetoothDevicesList;
import uk.co.deanwild.materialshowcaseview.MaterialShowcaseSequence;
import uk.co.deanwild.materialshowcaseview.MaterialShowcaseView;
import uk.co.deanwild.materialshowcaseview.ShowcaseConfig;

public class BotSelector extends AppCompatActivity {

    private Set<BluetoothDevice> pairedDevices;
    private String selectedDeviceName = null;
    private ListView listView;

    private final ActivityResultLauncher<Intent> bluetoothEnableLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_CANCELED) {
                    Toast.makeText(BotSelector.this, StaticMessage.UN_CONNECTED, Toast.LENGTH_SHORT).show();
                }
                listPairedDevices();
            }
    );

    private final ActivityResultLauncher<String[]> permissionLauncher = registerForActivityResult(
            new ActivityResultContracts.RequestMultiplePermissions(),
            result -> {
                Boolean bluetoothConnect = result.getOrDefault(android.Manifest.permission.BLUETOOTH_CONNECT, false);
                if (Boolean.TRUE.equals(bluetoothConnect)) {
                    checkBluetoothState();
                } else {
                    Toast.makeText(this, "Bluetooth permissions are required", Toast.LENGTH_SHORT).show();
                }
            }
    );

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

        checkPermissionsAndBluetooth();

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

    private void checkPermissionsAndBluetooth() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S) {
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.BLUETOOTH_CONNECT) != android.content.pm.PackageManager.PERMISSION_GRANTED) {
                permissionLauncher.launch(new String[]{android.Manifest.permission.BLUETOOTH_CONNECT, android.Manifest.permission.BLUETOOTH_SCAN});
                return;
            }
        }
        checkBluetoothState();
    }

    private void checkBluetoothState() {
        if (BluetoothHelper.isBluetoothSupported(this) && !BluetoothHelper.isBluetoothEnable(this)) {
            turnOnBluetooth();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (BluetoothHelper.isBluetoothSupported(this) && BluetoothHelper.isBluetoothEnable(this)) {
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
                if (ActivityCompat.checkSelfPermission(BotSelector.this, android.Manifest.permission.BLUETOOTH_CONNECT) != android.content.pm.PackageManager.PERMISSION_GRANTED) {
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S) {
                        return;
                    }
                }
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
        bluetoothEnableLauncher.launch(requestBluetoothOn);
    }

    private void listPairedDevices() {
        if (BluetoothHelper.isBluetoothEnable(this)) {
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.BLUETOOTH_CONNECT) != android.content.pm.PackageManager.PERMISSION_GRANTED) {
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S) {
                    return;
                }
            }
            pairedDevices = BluetoothHelper.getAdapter(this).getBondedDevices();
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
        config.setDelay(500); 
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
