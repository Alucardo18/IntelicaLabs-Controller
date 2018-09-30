package android.intelica.intelicalabs_controller.Util.bluetooth;

import android.bluetooth.BluetoothSocket;
import android.intelica.intelicalabs_controller.MainActivity;

public class BluetoothConnection {
    private static final BluetoothConnection instance = new BluetoothConnection();

    private BluetoothSocket bluetoothSocket = null;
    private BluetoothOutput bluetoothOutput;

    public static BluetoothConnection getInstance() {
        return instance;
    }

    private BluetoothConnection() {
    }

    public BluetoothSocket getBluetoothSocket() {
        return bluetoothSocket;
    }

    public void setBluetoothSocket(BluetoothSocket bluetoothSocket) {
        this.bluetoothSocket = bluetoothSocket;
    }

    public BluetoothOutput getBluetoothOutput() {
        return bluetoothOutput;
    }

    public void setBluetoothOutput(BluetoothOutput bluetoothOutput) {
        this.bluetoothOutput = bluetoothOutput;
    }
}
