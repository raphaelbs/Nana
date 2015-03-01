package br.com.createlier.nana.nana;

import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import Utils.CapsuleHandler;
import Utils.DividerItemDecoration;
import recycler_handlers.InfoHolder;
import recycler_handlers.RITAOAdapter;


public class PillNamesActivity extends ActionBarActivity {

    private Toolbar toolbar;
    private TextView title;
    private RecyclerView recyclerView;
    private InfoHolder infoHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pillnames);

        toolbar = (Toolbar) findViewById(R.id.toolbar_app);
        title = (TextView) toolbar.findViewById(R.id.app_bar_dynamic_title);
        title.setText("Defina nomes");
        setSupportActionBar(toolbar);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        setSupportActionBar(toolbar);

        populateList();

        recyclerView.setAdapter(new RITAOAdapter(infoHolder));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        /*if (id == R.id.action_alarm_clock) {
            startActivity(new Intent(this, AlarmActivity.class));
        }
        if (id == R.id.action_settings) {
            startActivity(new Intent(this, SettingsActivity.class));
        }*/

        if (id == android.R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
        }

        return super.onOptionsItemSelected(item);
    }

    private void populateList() {
        infoHolder = new InfoHolder(this);
        int DEFAULT_ICON = R.mipmap.ic_launcher;
        for (int i=0; i < CapsuleHandler.getCapsulesSize(); i++) {
            infoHolder.addSelectionWithComplementAndIcon(
                    DEFAULT_ICON,
                    CapsuleHandler.getCapsuleName(i),
                    "Capsula "+i);
        }
    }
}
