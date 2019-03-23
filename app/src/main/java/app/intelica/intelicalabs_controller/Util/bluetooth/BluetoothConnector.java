package app.intelica.intelicalabs_controller.Util.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;

import java.io.IOException;
import java.util.UUID;

/////////////CONECT THREAD
public class BluetoothConnector extends Thread {

    private BluetoothSocket mmSocket;
    private final UUID myUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    private BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

    public BluetoothConnector() {
    }

    public BluetoothConnector(BluetoothDevice device) {
        // Use a temporary object that is later assigned to mmSocket,
        // because mmSocket is final
        BluetoothSocket tmp = null;
        // mmDevice.getAddress();
        // Get a BluetoothSocket to connect with the given BluetoothDevice
        try {
            // MY_UUID is the app's UUID string, also used by the server code
            tmp = device.createRfcommSocketToServiceRecord(myUUID);
        } catch (IOException e) {
        }
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
        } catch (IOException e) {
            try {
                mmSocket.close();
            } catch (IOException close) {
                // Unable to connect; close the socket and get out
            }
            // Do work to manage the connection (in a separate thread)
            return;
        }

        BluetoothOutput bluetoothOutput = new BluetoothOutput(mmSocket);
        bluetoothOutput.start();
        BluetoothConnection.getInstance().setBluetoothSocket(mmSocket);
        BluetoothConnection.getInstance().setBluetoothOutput(bluetoothOutput);
    }

    /**
     * Will cancel an in-progress connection, and close the socket
     */
    public void cancel() {
        try {
            BluetoothConnection.getInstance().getBluetoothSocket().close();
            BluetoothConnection.getInstance().setBluetoothSocket(null);
        } catch (IOException e) {
        }
    }

}
