package android.intelica.intelicalabs_controller;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.nfc.Tag;
import android.os.Message;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;
import java.util.logging.Handler;

/**
 * Created by EmmanuelGonzalezLope on 7/16/2017.
 */

public class BluetoothManager {

public static boolean isBluetoothSupported(){
    return BluetoothAdapter.getDefaultAdapter() != null ? true : false;
}

    public static boolean isBluetoothEnable () {
    BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        if (bluetoothAdapter != null){
            return bluetoothAdapter.isEnabled(); // si el dispositivo cuenta con bluetooth pregunta esta hablitado?
        }
        return false; // sino revuelve un false
    }


}
