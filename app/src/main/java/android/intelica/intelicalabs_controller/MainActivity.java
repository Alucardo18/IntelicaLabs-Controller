package android.intelica.intelicalabs_controller;


import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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

private static final int REQUEST_CODE_BLUETOOTH_ON =1313;
private static final int BLUETOOTH_DISCOVERABLE_DURATION = 250;
private Button conectar;
private Button desconectar;
private Button controller;
private BluetoothDevice globalDevice;
public static BluetoothSocket globalSocket = null;
public static ConnectedThread mConnectedThread;
ListView listView;
Set<BluetoothDevice> pariedDevices;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.action_intelica);


        if ((BluetoothManager.isBluetoothSupported())&& !BluetoothManager.isBluetoothEnable()){
                    turnOnBluetooth();
        }
    listView = (ListView) findViewById(R.id.listViewPaired);
    conectar = (Button) findViewById(R.id.buttonConnect);
    desconectar = (Button) findViewById(R.id.buttonRelease);
    controller = (Button) findViewById(R.id.gameController);
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

                if (globalSocket != null) {
                    ConnectThread connectThread = new ConnectThread(globalDevice);
                    connectThread.cancel();
                }else {
                    Toast.makeText(MainActivity.this,"AUN NO TE HAS COENCTADO A TU ROBOT",Toast.LENGTH_LONG).show();
                }
            }
        });


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                List<BluetoothDevice> device2 = new ArrayList<BluetoothDevice>(pariedDevices);

                Toast.makeText(MainActivity.this, "Conectando..." , Toast.LENGTH_SHORT).show();
                ConnectThread connectThread = new ConnectThread(device2.get(position));
                connectThread.start();


            }
        });


// ROBO CONTROLLER
        controller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent controller = new Intent(MainActivity.this,BotController.class);
                startActivity(controller);

            }
        });





    }







    ///crear un alert para el USER ACTION REQUEST
    private void turnOnBluetooth(){
            //intent para habilitr el bluetooth mediante un ALERT ACTION_REQUEST
        Intent requestBluetoothOn =  new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        //requestBluetoothOn.setAction(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        //requestBluetoothOn.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION,BLUETOOTH_DISCOVERABLE_DURATION);
        this.startActivityForResult(requestBluetoothOn,REQUEST_CODE_BLUETOOTH_ON);

    }

    /// RESULTADO DEL ALERT
    @Override
    protected void onActivityResult (int requestCode, int resultCode, Intent data){

        if (requestCode == REQUEST_CODE_BLUETOOTH_ON){
            switch (resultCode){
                case Activity.RESULT_OK:{
                    /// TURN ON BLUETOOTH
                }
                break;
                case Activity.RESULT_CANCELED:{
                    Toast.makeText(MainActivity.this,"HABILITE EL BLUETOOTH PARA CONECTAR SU ROBOT",Toast.LENGTH_LONG).show();
                }
            }
        }

    }

    ////obtener los dispotivos apareados y desplegarlos en un listView
    private void listPariedDevices (){
        if (BluetoothManager.isBluetoothEnable()) {
            pariedDevices = BluetoothAdapter.getDefaultAdapter().getBondedDevices();
            ArrayList<String> devices = new ArrayList<>();
            if (pariedDevices.size()>0){

                for (BluetoothDevice bt : pariedDevices) {
                    devices.add(bt.getName() + "\n" + bt.getAddress());
                }

                ArrayAdapter arrayAdapter = new ArrayAdapter(MainActivity.this, android.R.layout.simple_list_item_1, devices);
                listView.setAdapter(arrayAdapter);
             } else {
                Toast.makeText(MainActivity.this, "No paired devices were found", Toast.LENGTH_LONG).show();
                }
        }
        else {
        Toast.makeText(MainActivity.this,"Hablite el bluetooth primero", Toast.LENGTH_LONG).show();
        }

    }




    /////////////CONECT THREAD
        private class ConnectThread extends Thread{

        private  BluetoothSocket mmSocket;
        private final BluetoothDevice mmDevice;
        private final UUID myUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
        private BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

            public ConnectThread(BluetoothDevice device) {
                // Use a temporary object that is later assigned to mmSocket,
                // because mmSocket is final
                BluetoothSocket tmp = null;
                mmDevice = device;
               // mmDevice.getAddress();
                // Get a BluetoothSocket to connect with the given BluetoothDevice
                try {
                    // MY_UUID is the app's UUID string, also used by the server code
                    tmp = device.createRfcommSocketToServiceRecord(myUUID);
                } catch (IOException e) { }
                mmSocket = tmp;
            }

            public void run() {
                // Cancel discovery because it will slow down the connection
                mBluetoothAdapter.cancelDiscovery();

                try {
                    // Connect the device through the socket. This will block
                    // until it succeeds or throws an exception
                   // Thread.sleep(1000);
                    mmSocket.connect();
                } catch (IOException e){
                    try {
                        mmSocket.close();
                    }catch (IOException close){
                        // Unable to connect; close the socket and get out
                    }
                    // Do work to manage the connection (in a separate thread)
                    return;
                }
                globalDevice = mmDevice;
                globalSocket = mmSocket;
                mConnectedThread = new ConnectedThread(globalSocket);
                mConnectedThread.start();
            }

            /** Will cancel an in-progress connection, and close the socket */
            public void cancel() {
                try {
                    globalSocket.close();
                } catch (IOException e) { }
            }

        }

 // METODO DE ENVIO DE DATOS
 public class ConnectedThread extends Thread {
     private final BluetoothSocket mmSocket;
     private final InputStream mmInStream;
     private final OutputStream mmOutStream;
     public ConnectedThread(BluetoothSocket socket) {
         mmSocket = socket;
         InputStream tmpIn = null;
         OutputStream tmpOut = null;
         try {
             tmpIn = socket.getInputStream();
             tmpOut = socket.getOutputStream();
         } catch (IOException e) { }
         mmInStream = tmpIn;
         mmOutStream = tmpOut;
     }
     public void run() {
         byte[] buffer = new byte[1024];
         int begin = 0;
         int bytes = 0;
         while (true) {
             try {
                 bytes += mmInStream.read(buffer, bytes, buffer.length - bytes);
                 for(int i = begin; i < bytes; i++) {
                     if(buffer[i] == "#".getBytes()[0]) {
                         mHandler.obtainMessage(1, begin, i, buffer).sendToTarget();
                         begin = i + 1;
                         if(i == bytes - 1) {
                             bytes = 0;
                             begin = 0;
                         }
                     }
                 }
             } catch (IOException e) {
                 break;
             }
         }
     }
     public void write(String input) {
         //byte[] bytes = input.getBytes();
         try {
             mmOutStream.write(input.getBytes());
         } catch (IOException e) { }
     }
     public void cancel() {
         try {
             mmSocket.close();
         } catch (IOException e) { }
     }


 }


    ////HANDLER EXCEPTION DE ENVIO DE DATOS
    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            byte[] writeBuf = (byte[]) msg.obj;
            int begin = (int)msg.arg1;
            int end = (int)msg.arg2;

            switch(msg.what) {
                case 1:
                    String writeMessage = new String(writeBuf);
                    writeMessage = writeMessage.substring(begin, end);
                    break;

            }
        }
    };





}
