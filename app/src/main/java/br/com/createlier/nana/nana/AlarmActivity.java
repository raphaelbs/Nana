package br.com.createlier.nana.nana;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.software.shell.fab.ActionButton;

import Utils.CapsuleHandler;
import Utils.DividerItemDecoration;
import recycler_handlers.InfoHolder;
import recycler_handlers.RITAOAdapter;


public class AlarmActivity extends ActionBarActivity {

    private Toolbar toolbar;
    private ActionButton actionButton;
    private RecyclerView alarm_list;
    private InfoHolder infoHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);
        CapsuleHandler.Fill();

        toolbar = (Toolbar) findViewById(R.id.toolbar_app);
        setSupportActionBar(toolbar);

        populateList();

        alarm_list = (RecyclerView) findViewById(R.id.alarm_recycler_view);
        alarm_list.setHasFixedSize(true);

        alarm_list.setAdapter(new RITAOAdapter(infoHolder));
        alarm_list.setLayoutManager(new LinearLayoutManager(this));
        alarm_list.setItemAnimator(new DefaultItemAnimator());

        actionButton = (ActionButton) findViewById(R.id.button_add_pill);

        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actionButton.startAnimation(actionButton.getHideAnimation());
                startActivity(new Intent(getApplicationContext(), PillActivity.class));
            }
        });
    }

    @Override
    protected void onResume() {
        actionButton.startAnimation(actionButton.getShowAnimation());
        super.onResume();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_alarm, menu);
        menu.getItem(0).setIcon(R.mipmap.ic_mode_edit);
        menu.getItem(1).setIcon(R.mipmap.ic_bluetooth);
        menu.getItem(2).setIcon(R.mipmap.ic_volume_up);
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

    private void populateList() {
        infoHolder = new InfoHolder(this);
        addOnInfoHolder(7, 30, new int[]{0});
        addOnInfoHolder(8, 30, new int[]{1, 2, 3});
        addOnInfoHolder(12, 00, new int[]{4});
        addOnInfoHolder(20, 30, new int[]{1, 5, 6});
    }

    private void addOnInfoHolder(int hour, int min, int[] capsules){
        String time = String.format("%02d", hour) + ":" + String.format("%02d", min);
        infoHolder.addAlarmSelector(chooseResourcesFromTime(hour), time, capsules);
    }

    private int chooseResourcesFromTime(int hour){
        if(hour >= 0 && hour < 2)
            return R.mipmap.ic_brightness_1;
        if(hour >= 2 && hour < 4)
            return R.mipmap.ic_brightness_4;
        if(hour >= 4 && hour < 6)
            return R.mipmap.ic_brightness_5;
        if(hour >= 6 && hour < 10)
            return R.mipmap.ic_brightness_6;
        if(hour >= 10 && hour < 16)
            return R.mipmap.ic_brightness_7;
        if(hour >= 16 && hour < 19)
            return R.mipmap.ic_brightness_4;
        if(hour >= 19 && hour < 22)
            return R.mipmap.ic_brightness_2;
        if(hour >= 22 && hour < 24)
            return R.mipmap.ic_brightness_1;
        return 0;
    }
}
