package br.com.createlier.nana.nana;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.TimePicker;

import com.afollestad.materialdialogs.MaterialDialog;
import com.software.shell.fab.ActionButton;

import java.util.Calendar;

import Utils.CapsuleHandler;
import Utils.NanaActivity;
import recycler_handlers.InfoHolder;


public class AlarmActivity extends NanaActivity {

    private Toolbar toolbar;
    private ActionButton actionButton;
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);

        toolbar = (Toolbar) findViewById(R.id.toolbar_app);
        setSupportActionBar(toolbar);
        setPrivateDatabase("AlarmDatabase");

        mRecyclerView = (RecyclerView) findViewById(R.id.alarm_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        setRecyclerView(mRecyclerView);

        actionButton = (ActionButton) findViewById(R.id.button_add_pill);

        final MaterialDialog md = new MaterialDialog.Builder(this)
                .customView(R.layout.dialog_add_pill, true)
                .dismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        actionButton.startAnimation(actionButton.getShowAnimation());
                        actionButton.show();
                    }
                })
                .build();

        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actionButton.startAnimation(actionButton.getHideAnimation());
                actionButton.hide();
                md.show();

                final int MINUTES_INTERVAL = 5;

                TimePicker timePicker = (TimePicker) md.findViewById(R.id.timePicker);
                timePicker.setIs24HourView(true);
                timePicker.setCurrentHour(Calendar.getInstance().get(Calendar.HOUR_OF_DAY));

                NumberPicker minutes = (NumberPicker) ((ViewGroup) timePicker.getChildAt(0)).getChildAt(1);
                minutes.setMaxValue(3);
                minutes.setMinValue(0);
                minutes.setWrapSelectorWheel(true);
                minutes.setDisplayedValues(returnMinutes(MINUTES_INTERVAL));

                timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
                    @Override
                    public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                        ((TextView) findViewById(R.id.timeTextHolder)).setText(hourOfDay + ":" + String.format("%02d", minute * MINUTES_INTERVAL));
                    }
                });
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
        CapsuleHandler.setCapsule(0,
                getFromDatabase(R.string.shared_pill_name_1));
        CapsuleHandler.setCapsule(1,
                getFromDatabase(R.string.shared_pill_name_2));
        CapsuleHandler.setCapsule(2,
                getFromDatabase(R.string.shared_pill_name_3));
        CapsuleHandler.setCapsule(3,
                getFromDatabase(R.string.shared_pill_name_4));
        CapsuleHandler.setCapsule(4,
                getFromDatabase(R.string.shared_pill_name_5));
        CapsuleHandler.setCapsule(5,
                getFromDatabase(R.string.shared_pill_name_6));
        CapsuleHandler.setCapsule(6,
                getFromDatabase(R.string.shared_pill_name_7));
    }

    @Override
    public void populateList(InfoHolder infoHolder) {
        infoHolder.addAlarmSelector(7, 30, new int[]{0});
        infoHolder.addAlarmSelector(8, 30, new int[]{1, 2, 3});
        infoHolder.addAlarmSelector(12, 00, new int[]{4});
        infoHolder.addAlarmSelector(20, 30, new int[]{1, 5, 6});
    }

    /**
     * Generate a array of minutes to feed into the TimePicker.
     * If the 60 % interval != 0, then the interval is set to 1.
     *
     * @param interval
     * @return
     */
    private String[] returnMinutes(int interval) {
        String[] array = {};
        if (interval > 0 && interval < 60 && 60 % interval == 0)
            array = new String[60 / interval];
        else {
            array = new String[60];
            interval = 1;
        }
        for (int i = 0; i < array.length; i++)
            array[i] = (i * interval) + "";
        return array;
    }
}
