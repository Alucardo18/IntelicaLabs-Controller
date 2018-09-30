package android.intelica.intelicalabs_controller;


import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.intelica.intelicalabs_controller.Util.StaticMessage;
import android.intelica.intelicalabs_controller.Util.bluetooth.BluetoothConnection;
import android.intelica.intelicalabs_controller.Util.bluetooth.BluetoothConnector;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;


public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_BLUETOOTH_ON = 1313;
    private static final int BLUETOOTH_DISCOVERABLE_DURATION = 250;
    private Button conectar;
    private Button desconectar;
    private Button controller;
    private WebView webView;
    ListView listView;
    Set<BluetoothDevice> pariedDevices;

//TODO: convert MainActivityLayout into Landscape layout

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        // getSupportActionBar().setDisplayShowHomeEnabled(true);
        //getSupportActionBar().setIcon(R.mipmap.action_intelica);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        if ((BluetoothManager.isBluetoothSupported()) && !BluetoothManager.isBluetoothEnable()) {
            turnOnBluetooth();
        }
        listView = (ListView) findViewById(R.id.listViewPaired);
        conectar = (Button) findViewById(R.id.buttonConnect);
        desconectar = (Button) findViewById(R.id.buttonRelease);
        controller = (Button) findViewById(R.id.gameController);
        webView = (WebView) findViewById(R.id.webView);

        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);


        if (BluetoothConnection.getInstance().getBluetoothSocket() != null) {
            webView.loadUrl("file:///android_asset/connected.png");
            Toast.makeText(this, "ROBOT CONNECTED", Toast.LENGTH_SHORT).show();
        } else {
            webView.loadUrl("file:///android_asset/bluemotion.gif");
        }

        ////////////////VIEWS
// CONECTAR
        conectar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listPariedDevices();
            }
        });


// DESCONECTAR
        desconectar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (BluetoothConnection.getInstance().getBluetoothSocket() != null) {
                    BluetoothConnector connectThread = new BluetoothConnector();
                    connectThread.cancel();
                } else {
                    Toast.makeText(MainActivity.this, StaticMessage.UN_CONNECTED, Toast.LENGTH_SHORT).show();

                }
            }
        });


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                List<BluetoothDevice> device2 = new ArrayList<BluetoothDevice>(pariedDevices);

                Toast.makeText(MainActivity.this, "Conectando...", Toast.LENGTH_SHORT).show();
                BluetoothConnector connectThread = new BluetoothConnector(device2.get(position));
                connectThread.start();


            }
        });


// ROBO CONTROLLER
        controller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent controller = new Intent(MainActivity.this, BotController.class);
                startActivity(controller);

            }
        });

    }//// FIN ON CREATE

    ///crear un alert para el USER ACTION REQUEST
    private void turnOnBluetooth() {
        //intent para habilitr el bluetooth mediante un ALERT ACTION_REQUEST
        Intent requestBluetoothOn = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        //requestBluetoothOn.setAction(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        //requestBluetoothOn.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION,BLUETOOTH_DISCOVERABLE_DURATION);
        this.startActivityForResult(requestBluetoothOn, REQUEST_CODE_BLUETOOTH_ON);

    }

    /// RESULTADO DEL ALERT
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == REQUEST_CODE_BLUETOOTH_ON) {
            switch (resultCode) {
                case Activity.RESULT_OK: {
                    /// TURN ON BLUETOOTH
                }
                break;
                case Activity.RESULT_CANCELED: {
                    Toast.makeText(MainActivity.this, StaticMessage.UN_CONNECTED, Toast.LENGTH_SHORT).show();
                }
            }
        }

    }

    ////obtener los dispotivos apareados y desplegarlos en un listView
    private void listPariedDevices() {
        if (BluetoothManager.isBluetoothEnable()) {
            pariedDevices = BluetoothAdapter.getDefaultAdapter().getBondedDevices();
            ArrayList<String> devices = new ArrayList<>();
            if (pariedDevices.size() > 0) {

                for (BluetoothDevice bt : pariedDevices) {
                    devices.add(bt.getName() + "\n" + bt.getAddress());
                }
                MyAdapter myAdapter = new MyAdapter(MainActivity.this, R.layout.my_listview, devices);

                //ArrayAdapter arrayAdapter = new ArrayAdapter(MainActivity.this, android.R.layout.simple_list_item_1, devices);
                listView.setAdapter(myAdapter);
            } else {
                Toast.makeText(MainActivity.this, StaticMessage.DV_NOT_FOUND, Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(MainActivity.this, StaticMessage.BT_NOT_ENABLE, Toast.LENGTH_LONG).show();
        }

    }

}


