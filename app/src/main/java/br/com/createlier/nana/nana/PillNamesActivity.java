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
import android.view.View;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;

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
        title.setText(R.string.pillname_activity);
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
            final String capsuleName = String.format(getString(R.string.pillname_capsule_mask), i);
            final MaterialDialog.Builder md = new MaterialDialog.Builder(this)
                    .title(capsuleName)
                    .content(CapsuleHandler.getCapsuleName(i))
                    .positiveText(R.string.material_dialog_ok)
                    .negativeText(R.string.material_dialog_cancel);
            infoHolder.addSelectionWithComplementAndIcon(
                    DEFAULT_ICON,
                    CapsuleHandler.getCapsuleName(i),
                    capsuleName)
            .setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    md.show();
                }
            });
        }
    }
}
