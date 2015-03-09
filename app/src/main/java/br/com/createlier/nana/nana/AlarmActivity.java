package br.com.createlier.nana.nana;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.software.shell.fab.ActionButton;

import Utils.CapsuleHandler;
import Utils.DatabaseAlarms;
import Utils.NanaActivity;
import Utils.PillSelectorManager;
import Utils.TimePickerManager;
import Utils.Utils;
import recycler_handlers.InfoData;
import recycler_handlers.InfoHolder;


public class AlarmActivity extends NanaActivity {

    private static String hour;
    private Toolbar toolbar;
    private ActionButton actionButton;
    private RecyclerView mRecyclerView;
    private MaterialDialog mdBegin, mdEnd;

    private DatabaseAlarms db;
    private PillSelectorManager psm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);

        toolbar = (Toolbar) findViewById(R.id.toolbar_app);
        setSupportActionBar(toolbar);
        setPrivateDatabase("AlarmDatabase");

        final Activity main = this;

        mRecyclerView = (RecyclerView) findViewById(R.id.alarm_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        setRecyclerView(mRecyclerView);


        mdEnd = new MaterialDialog.Builder(this)
                .customView(R.layout.dialog_add_pill_end, true)
                .positiveText(R.string.material_dialog_finish)
                .negativeText(R.string.material_dialog_cancel)
                .callback(new MaterialDialog.ButtonCallback() {
                    @Override
                    public void onPositive(MaterialDialog dialog) {
                        final TextView timeHolder = (TextView) dialog.findViewById(R.id.timeTextHolder);

                        int nhour = Integer.parseInt(hour.charAt(0) + "" + hour.charAt(1));

                        InfoData infoData = new InfoData(
                                Utils.chooseResFromTime(nhour),
                                hour,
                                ((TextView) dialog.findViewById(R.id.fpsAboutText)).getText().toString(),
                                false
                        );
                        infoData.setDeletable(true);
                        addOnInfoHolder(infoData);
                        db.addAlarm(infoData);
                    }
                })
                .dismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        actionButton.startAnimation(actionButton.getShowAnimation());
                        actionButton.show();
                    }
                })
                .build();


        mdBegin = new MaterialDialog.Builder(this)
                .customView(R.layout.dialog_add_pill, true)
                .positiveText(R.string.material_dialog_next)
                .negativeText(R.string.material_dialog_cancel)
                .dismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        actionButton.startAnimation(actionButton.getShowAnimation());
                        actionButton.show();
                    }
                })
                .callback(new MaterialDialog.ButtonCallback() {
                    @Override
                    public void onPositive(MaterialDialog dialog) {
                        hour = ((TextView) dialog.findViewById(R.id.timeTextHolder)).getText().toString();
                        mdEnd.show();
                        final TextView timeHolder = (TextView) mdEnd.findViewById(R.id.timeTextHolder);
                        timeHolder.setText(hour);

                        psm = new PillSelectorManager(
                                (ViewGroup) mdEnd.findViewById(R.id.supremeParent)
                        );
                        psm.build();
                    }
                })
                .build();


        actionButton = (ActionButton) findViewById(R.id.button_add_pill);

        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actionButton.startAnimation(actionButton.getHideAnimation());
                actionButton.hide();
                mdBegin.show();
                TimePickerManager timePickerManager = new TimePickerManager(
                        (ViewGroup) mdBegin.findViewById(R.id.supremeParent), 5);
                timePickerManager.build();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_alarm, menu);

        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.settings_menu_name)
            startActivity(new Intent(this, PillNamesActivity.class));
        if (id == R.id.settings_menu_conneciton)
            startActivity(new Intent(this, ConnectionActivity.class));
        if (id == R.id.settings_menu_songs)
            startActivity(new Intent(this, SongsActivity.class));

        return super.onOptionsItemSelected(item);

    }

    @Override
    public void manageDatabase() {
        CapsuleHandler.newList();
        for (int i = 0; i < 7; i++)
            CapsuleHandler.setCapsule(i,
                    getFromDatabase(CapsuleHandler.getKey(i)));
    }

    @Override
    public void populateList(final InfoHolder infoHolder) {
        db = new DatabaseAlarms(getApplicationContext());

        for (InfoData id : db.getAlarms())
            infoHolder.addInfoData(id.setDeletable(true));

        db.close();
    }
}
