package Utils;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.afollestad.materialdialogs.MaterialDialog;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;

import br.com.createlier.nana.nana.R;

/**
 * Created by dede on 15/03/2015.
 */
public class BTHandler {
    private static final UUID MY_UUID =
            UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    final int handlerState = 0;
    Handler bluetoothIn;
    private BluetoothAdapter BA;
    private Set<BluetoothDevice> pairedDevices;
    private Activity activity;
    private BluetoothSocket btSocket = null;
    private String address;
    private OutputStream outStream = null;
    private boolean isConnected;
    private MenuItem menuItem;
    private ConnectedThread mConnectedThread;

    public BTHandler(Activity activity) {
        this.activity = activity;
        BA = BluetoothAdapter.getDefaultAdapter();

        bluetoothIn = new Handler() {
            public void handleMessage(android.os.Message msg) {
                if (msg.what == handlerState) {                                     //if message is what we want
                    String readMessage = (String) msg.obj;
                    Log.d("MESSAGE", readMessage);
                }
            }
        };

        checkBTState();
    }

    private void checkBTState() {
        if (!BA.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            activity.startActivityForResult(enableBtIntent, 1);
        }
    }

    public void setMenuItem(MenuItem menuItem) {
        this.menuItem = menuItem;
    }

    public boolean isConnected() {
        return isConnected;
    }

    public void showList() {
        if (isConnected()) {

            final ArrayList<String> arrayList = new ArrayList<String>();
            arrayList.add("Download configurações");
            arrayList.add("Upload configurações");
            arrayList.add("Desconectar");

            MaterialDialog pairedDialog = new MaterialDialog.Builder(activity)
                    .title(R.string.bluetooth_dialog_connected_tilte)
                    .positiveText(R.string.material_dialog_confirm)
                    .items(arrayList.toArray(new CharSequence[arrayList.size()]))
                    .itemsCallbackSingleChoice(1, new MaterialDialog.ListCallback() {
                        @Override
                        public void onSelection(MaterialDialog materialDialog, View view, int i, CharSequence charSequence) {
                            if (i == 0) {
                                mConnectedThread.write("x.");
                            }
                        }
                    })
                    .build();
            pairedDialog.show();

        } else {
            menuItem.setIcon(R.mipmap.ic_bluetooth_searching);

            pairedDevices = BA.getBondedDevices();

            final ArrayList<String> arrayList = new ArrayList<String>();
            for (BluetoothDevice bt : pairedDevices)
                arrayList.add(bt.getName());

            MaterialDialog pairedDialog = new MaterialDialog.Builder(activity)
                    .title(R.string.bluetooth_dialog_tilte)
                    .positiveText(R.string.material_dialog_connect)
                    .items(arrayList.toArray(new CharSequence[arrayList.size()]))
                    .itemsCallbackSingleChoice(1, new MaterialDialog.ListCallback() {
                        @Override
                        public void onSelection(MaterialDialog materialDialog, View view, int i, CharSequence charSequence) {
                            for (BluetoothDevice bt : pairedDevices)
                                if (bt.getName().equals(arrayList.get(i)))
                                    address = bt.getAddress();
                            resumeComm();
                        }
                    })
                    .build();
            pairedDialog.show();
        }
    }


    public void resumeComm() {
        checkBTState();
        menuItem.setIcon(R.mipmap.ic_bluetooth_searching);
        // Set up a pointer to the remote node using it's address.
        BluetoothDevice device = BA.getRemoteDevice(address);

        // Two things are needed to make a connection:
        //   A MAC address, which we got above.
        //   A Service ID or UUID.  In this case we are using the
        //     UUID for SPP.
        try {
            btSocket = device.createRfcommSocketToServiceRecord(MY_UUID);
        } catch (IOException e) {
        }

        // Discovery is resource intensive.  Make sure it isn't going on
        // when you attempt to connect and pass your message.
        BA.cancelDiscovery();

        // Establish the connection.  This will block until it connects.
        try {
            btSocket.connect();
            isConnected = true;
            menuItem.setIcon(R.mipmap.ic_bluetooth_connected);
        } catch (IOException e) {
            try {
                btSocket.close();
                menuItem.setIcon(R.mipmap.ic_bluetooth_disabled);
                isConnected = false;
            } catch (IOException e2) {
            }
        }

        // Create a data stream so we can talk to server.

        mConnectedThread = new ConnectedThread(btSocket);
        mConnectedThread.start();
    }

    public void pauseComm() {
        if (outStream != null) {
            try {
                outStream.flush();
            } catch (IOException e) {
            }
        }

        try {
            btSocket.close();
            menuItem.setIcon(R.mipmap.ic_bluetooth_disabled);
        } catch (IOException e2) {
        }
    }


    //create new class for connect thread
    private class ConnectedThread extends Thread {
        private final InputStream mmInStream;
        private final OutputStream mmOutStream;

        //creation of the connect thread
        public ConnectedThread(BluetoothSocket socket) {
            InputStream tmpIn = null;
            OutputStream tmpOut = null;

            try {
                //Create I/O streams for connection
                tmpIn = socket.getInputStream();
                tmpOut = socket.getOutputStream();
            } catch (IOException e) {
            }

            mmInStream = tmpIn;
            mmOutStream = tmpOut;
        }

        public void run() {
            byte[] buffer = new byte[256];
            int bytes;

            // Keep looping to listen for received messages
            while (true) {
                try {
                    bytes = mmInStream.read(buffer);            //read bytes from input buffer
                    String readMessage = new String(buffer, 0, bytes);
                    // Send the obtained bytes to the UI Activity via handler
                    bluetoothIn.obtainMessage(handlerState, bytes, -1, readMessage).sendToTarget();
                } catch (IOException e) {
                    break;
                }
            }
        }

        //write method
        public void write(String input) {
            byte[] msgBuffer = input.getBytes();           //converts entered String into bytes
            try {
                mmOutStream.write(msgBuffer);                //write bytes over BT connection via outstream
            } catch (IOException e) {
            }
        }
    }
}
