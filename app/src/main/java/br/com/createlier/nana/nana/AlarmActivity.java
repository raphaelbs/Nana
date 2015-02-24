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
import android.widget.TextView;

import Utils.CapsuleHandler;
import Utils.DividerItemDecoration;
import recycler_handlers.AlarmRecyclerAdapter;
import recycler_handlers.PopulateAdapter;


public class AlarmActivity extends ActionBarActivity {

    private Toolbar toolbar;
    private ImageView add_pill;
    private RecyclerView alarm_list;
    private PopulateAdapter populateAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);
        CapsuleHandler.Fill();

        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

        populateAdapter = new PopulateAdapter();
        populateAdapter.populateAlarmAdapter();

        alarm_list = (RecyclerView) findViewById(R.id.alarm_recycler_view);
        alarm_list.setHasFixedSize(true);

        alarm_list.setAdapter(new AlarmRecyclerAdapter(populateAdapter.getAlarmAdapterList()));
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
}
