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
import android.widget.ImageView;

import Utils.CapsuleHandler;
import Utils.DividerItemDecoration;
import recycler_handlers.InfoHolder;
import recycler_handlers.RITAOAdapter;


public class AlarmActivity extends ActionBarActivity {

    private Toolbar toolbar;
    private ImageView add_pill;
    private RecyclerView alarm_list;
    private InfoHolder infoHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);
        CapsuleHandler.Fill();

        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

        infoHolder = new InfoHolder(InfoHolder.CONTAIN_ICON_TEXT_ABOUT);
        populateList();

        alarm_list = (RecyclerView) findViewById(R.id.alarm_recycler_view);
        alarm_list.setHasFixedSize(true);

        alarm_list.setAdapter(new RITAOAdapter(infoHolder));
        alarm_list.setLayoutManager(new LinearLayoutManager(this));
        alarm_list.setItemAnimator(new DefaultItemAnimator());
        alarm_list.addItemDecoration(new DividerItemDecoration(this, null));

        add_pill = (ImageView) findViewById(R.id.button_add_pill);
        add_pill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), PillActivity.class));
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
        if (id == R.id.action_settings)
            startActivity(new Intent(this, SettingsActivity.class));

        return super.onOptionsItemSelected(item);

    }

    private void populateList() {
        addOnInfoHolder(7, 30, new int[]{0});
        addOnInfoHolder(8, 30, new int[]{1, 2, 3});
        addOnInfoHolder(12, 00, new int[]{4});
        addOnInfoHolder(20, 30, new int[]{1, 5, 6});
    }

    private void addOnInfoHolder(int hour, int min, int[] capsules){
        String time = String.format("%02d", hour) + ":" + String.format("%02d", min);
        this.infoHolder.add(chooseResourcesFromTime(hour), time, capsules);
    }

    private int chooseResourcesFromTime(int hour){
        if(hour >= 0 && hour < 4)
            return R.mipmap.midnight;
        if(hour >= 4 && hour < 6)
            return R.mipmap.night;
        if(hour >= 6 && hour < 11)
            return R.mipmap.sunrise;
        if(hour >= 10 && hour < 16)
            return R.mipmap.midday;
        if(hour >= 16 && hour < 19)
            return R.mipmap.afternoon;
        if(hour >= 19 && hour < 22)
            return R.mipmap.night;
        if(hour >= 22 && hour < 24)
            return R.mipmap.midnight;
        return 0;
    }
}
