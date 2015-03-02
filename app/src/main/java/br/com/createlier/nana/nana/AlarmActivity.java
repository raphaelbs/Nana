package br.com.createlier.nana.nana;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.software.shell.fab.ActionButton;

import Utils.CapsuleHandler;
import Utils.NanaActivity;
import recycler_handlers.InfoHolder;


public class AlarmActivity extends NanaActivity{

    private Toolbar toolbar;
    private ActionButton actionButton;
    private RecyclerView alarm_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);

        toolbar = (Toolbar) findViewById(R.id.toolbar_app);
        setSupportActionBar(toolbar);
        setPrivateDatabase(this);

        alarm_list = (RecyclerView) findViewById(R.id.alarm_recycler_view);
        alarm_list.setHasFixedSize(true);
        setRecyclerView(alarm_list);

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
    public void manageDatabase(SharedPreferences sharedPreferences, SharedPreferences.Editor editor) {
        CapsuleHandler.Fill(getResources(),sharedPreferences);
        editor.putString(getResources().getString(R.string.spk_pill1),"Omeprazol");
        editor.commit();
    }

    @Override
    public void populateList(InfoHolder infoHolder, SharedPreferences sharedPreferences) {
        infoHolder.addAlarmSelector(7, 30, new int[]{0});
        infoHolder.addAlarmSelector(8, 30, new int[]{1, 2, 3});
        infoHolder.addAlarmSelector(12, 00, new int[]{4});
        infoHolder.addAlarmSelector(20, 30, new int[]{1, 5, 6});
    }
}
