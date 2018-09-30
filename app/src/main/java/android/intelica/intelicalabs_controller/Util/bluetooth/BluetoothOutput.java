package android.intelica.intelicalabs_controller.Util.bluetooth;

import android.bluetooth.BluetoothSocket;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

// METODO DE ENVIO DE DATOS
public class BluetoothOutput extends Thread {
    private final BluetoothSocket mmSocket;
    private final InputStream mmInStream;
    private final OutputStream mmOutStream;

    ////HANDLER EXCEPTION DE ENVIO DE DATOS
    Handler mHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            byte[] writeBuf = (byte[]) msg.obj;
            int begin = (int) msg.arg1;
            int end = (int) msg.arg2;

            switch (msg.what) {
                case 1:
                    String writeMessage = new String(writeBuf);
                    writeMessage = writeMessage.substring(begin, end);
                    break;

            }
        }
    };

    public BluetoothOutput(BluetoothSocket socket) {
        mmSocket = socket;
        InputStream tmpIn = null;
        OutputStream tmpOut = null;
        try {
            tmpIn = socket.getInputStream();
            tmpOut = socket.getOutputStream();
        } catch (IOException e) {
        }
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
                for (int i = begin; i < bytes; i++) {
                    if (buffer[i] == "#".getBytes()[0]) {
                        mHandler.obtainMessage(1, begin, i, buffer).sendToTarget();
                        begin = i + 1;
                        if (i == bytes - 1) {
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
        } catch (IOException e) {
        }
    }

    public void cancel() {
        try {
            mmSocket.close();
        } catch (IOException e) {
        }
    }


}
