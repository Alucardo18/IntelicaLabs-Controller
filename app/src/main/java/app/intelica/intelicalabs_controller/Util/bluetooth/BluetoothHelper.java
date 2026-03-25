package app.intelica.intelicalabs_controller.Util.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.Context;

/**
 * Created by EmmanuelGonzalezLope on 7/16/2017.
 * Updated to BluetoothHelper to avoid conflicts with android.bluetooth.BluetoothManager.
 */
public class BluetoothHelper {

    public static BluetoothAdapter getAdapter(Context context) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S) {
            BluetoothManager bluetoothManager = context.getSystemService(BluetoothManager.class);
            return bluetoothManager != null ? bluetoothManager.getAdapter() : null;
        } else {
            return BluetoothAdapter.getDefaultAdapter();
        }
    }

    public static boolean isBluetoothSupported(Context context) {
        return getAdapter(context) != null;
    }

    public static boolean isBluetoothEnable(Context context) {
        BluetoothAdapter bluetoothAdapter = getAdapter(context);
        return bluetoothAdapter != null && bluetoothAdapter.isEnabled();
    }
}
