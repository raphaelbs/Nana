package BluetoothComm;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;

import Utils.DatabaseAlarms;
import Utils.Utils;
import br.com.createlier.nana.nana.R;

/**
 * Created by dede on 15/03/2015.
 */
public class BTHandler {
    private static final UUID MY_UUID =
            UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
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
        checkBTState();
    }

    public void setBluetoothIn(Handler bluetoothIn) {
        this.bluetoothIn = bluetoothIn;
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

            //activity.startActivity(new Intent(activity, ConnectionActivity.class));

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
                                DatabaseAlarms db = new DatabaseAlarms(activity);
                                db.dropTable();
                                db.close();
                                mConnectedThread.write("x.");
                                //((NanaActivity)activity).addOnInfoHolder();
                            }
                            if (i == 1) {
                                //mConnectedThread.write("x.");
                                //((NanaActivity)activity).addOnInfoHolder();
                            }
                            if (i == 2) {
                                pauseComm();
                                isConnected = false;
                                //((NanaActivity)activity).addOnInfoHolder();
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
                    .dismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialog) {
                            if (!isConnected) {
                                setIcon(ICON.disabled);
                            }
                        }
                    })
                    .itemsCallbackSingleChoice(1, new MaterialDialog.ListCallback() {
                        @Override
                        public void onSelection(MaterialDialog materialDialog, View view, int i, CharSequence charSequence) {
                            try {
                                for (BluetoothDevice bt : pairedDevices)
                                    if (bt.getName().equals(arrayList.get(i)))
                                        address = bt.getAddress();
                                resumeComm();
                            } catch (Exception e) {
                                Toast.makeText(activity, R.string.bluetooth_find_none, Toast.LENGTH_SHORT)
                                        .show();
                            }
                        }
                    })
                    .build();
            pairedDialog.show();
        }
    }

    public void resumeComm() {
        checkBTState();
        setIcon(ICON.searching);
        BA.cancelDiscovery();
        BluetoothDevice device = BA.getRemoteDevice(address);
        try {
            btSocket = device.createRfcommSocketToServiceRecord(MY_UUID);
        } catch (IOException e) {
        }
        try {
            btSocket.connect();
            isConnected = true;
            setIcon(ICON.enabled);
        } catch (IOException e) {
            try {
                btSocket.close();
                setIcon(ICON.disabled);
                Toast.makeText(activity, R.string.bluetooth_error_onconnect, Toast.LENGTH_SHORT)
                        .show();
                isConnected = false;
            } catch (IOException e2) {
            }
        }
        if (bluetoothIn == null) {
            Toast.makeText(activity, R.string.bluetooth_handler_null, Toast.LENGTH_SHORT)
                    .show();
        } else {
            mConnectedThread = new ConnectedThread(btSocket, bluetoothIn, 0);
            mConnectedThread.start();
        }
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
            setIcon(ICON.disabled);
        } catch (IOException e2) {
        }
    }

    public void addAlarm(String hour, String capsules) {
        if (isConnected) {
            String msg = "al" + Utils.encodeHour(hour) + capsules;
            mConnectedThread.write("b" + msg.length() + ".");
            mConnectedThread.write("al" + Utils.encodeHour(hour) + capsules);
        }
    }

    public void setIcon(ICON icon) {
        switch (icon) {
            case disabled:
                menuItem.setIcon(R.mipmap.ic_bluetooth_disabled);
                break;
            case enabled:
                menuItem.setIcon(R.mipmap.ic_bluetooth_connected);
                break;
            case searching:
                menuItem.setIcon(R.mipmap.ic_bluetooth_searching);
                break;
        }
    }


    public enum ICON {
        disabled,
        enabled,
        searching;
    }

}
