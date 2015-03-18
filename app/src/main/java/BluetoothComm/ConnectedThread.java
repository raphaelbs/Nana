package BluetoothComm;

import android.bluetooth.BluetoothSocket;
import android.os.Handler;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by dede on 15/03/2015.
 */
//create new class for connect thread
public class ConnectedThread extends Thread {
    private final InputStream mmInStream;
    private final OutputStream mmOutStream;
    byte[] readBuffer;
    private Handler bluetoothIn;
    private int handlerState;
    private ConnectedThread connThread;
    private volatile boolean stopWorker;
    private Handler handler;
    private byte delimiter;
    private int readBufferPosition;

    //creation of the connect thread
    public ConnectedThread(BluetoothSocket socket, Handler bluetoothIn, final int handlerState) {
        InputStream tmpIn = null;
        OutputStream tmpOut = null;
        this.bluetoothIn = bluetoothIn;
        this.handlerState = handlerState;
        connThread = this;
        handler = new Handler();
        delimiter = 10; //This is the ASCII code for a newline character

        stopWorker = false;
        readBufferPosition = 0;
        readBuffer = new byte[1024];

        try {
            //Create I/O streams for connection
            tmpIn = socket.getInputStream();
            tmpOut = socket.getOutputStream();
        } catch (IOException e) {
        }

        mmInStream = tmpIn;
        mmOutStream = tmpOut;
    }

    public void setStopWorker(boolean stopWorker) {
        this.stopWorker = stopWorker;
    }

    public void run() {
        while (!Thread.currentThread().isInterrupted() && !stopWorker) {
            try {
                int bytesAvailable = mmInStream.available();
                if (bytesAvailable > 0) {
                    byte[] packetBytes = new byte[bytesAvailable];
                    mmInStream.read(packetBytes);
                    for (int i = 0; i < bytesAvailable; i++) {
                        byte b = packetBytes[i];
                        if (b == delimiter) {
                            byte[] encodedBytes = new byte[readBufferPosition];
                            System.arraycopy(readBuffer, 0, encodedBytes, 0, encodedBytes.length);
                            final String data = new String(encodedBytes, "US-ASCII");
                            readBufferPosition = 0;

                            /*handler.post(new Runnable()
                            {
                                public void run()
                                {
                                    myLabel.setText(data);
                                }
                            });*/
                            bluetoothIn.obtainMessage(handlerState, encodedBytes.length, -1, data).sendToTarget();
                        } else {
                            readBuffer[readBufferPosition++] = b;
                        }
                    }
                }
            } catch (IOException ex) {
                stopWorker = true;
            }
        }
    }

    //write method
    public void write(String input) {
        byte[] msgBuffer = input.getBytes();
        try {
            mmOutStream.write(msgBuffer);
        } catch (IOException e) {
        }
    }
}