package br.com.createlier.nana.nana;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;

import Utils.CapsuleHandler;
import Utils.DividerItemDecoration;
import Utils.NanaActivity;
import recycler_handlers.InfoHolder;
import recycler_handlers.RITAOAdapter;


public class PillNamesActivity extends NanaActivity {

    private Toolbar toolbar;
    private TextView title;
    private RecyclerView mRecyclerView;

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

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        setSupportActionBar(toolbar);
        setPrivateDatabase("AlarmDatabase");
        setRecyclerView(mRecyclerView);
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


    @Override
    public void populateList(final InfoHolder infoHolder) {
        int DEFAULT_ICON = R.mipmap.ic_launcher;

        for (int i = 0; i < CapsuleHandler.getCapsulesSize(); i++) {

            final String capsuleName = String.format(getString(R.string.pillname_capsule_mask), i+1);

            final MaterialDialog md = new MaterialDialog.Builder(this)
                    .title(capsuleName)
                    .customView(R.layout.editable_dialog, true)
                    .positiveText(R.string.material_dialog_ok)
                    .negativeText(R.string.material_dialog_cancel)
                    .callback(new MaterialDialog.ButtonCallback() {
                        @Override
                        public void onPositive(MaterialDialog dialog) {
                            EditText field = (EditText) dialog.getCustomView().findViewById(R.id.ed_text);
                            int capsuleNumber = ((int)capsuleName.charAt(capsuleName.length()-1))-49;
                            updateData(capsuleNumber, field.getText().toString(), infoHolder);
                        }
                    }).build();

            infoHolder.addSelectionWithComplementAndIcon(
                    DEFAULT_ICON,
                    CapsuleHandler.getCapsuleName(i),
                    capsuleName)
                    .setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            md.show();
                            EditText field = (EditText) md.getCustomView().findViewById(R.id.ed_text);
                            int capsuleNumber = ((int)capsuleName.charAt(capsuleName.length()-1))-49;
                            field.setText(CapsuleHandler.getCapsuleName(capsuleNumber));
                        }
                    });
        }
    }

    @Override
    public void manageDatabase() {
    }

    final private void updateData(int position, String value, InfoHolder infoHolder){
        setInDatabase(CapsuleHandler.getKey(position),value);
        CapsuleHandler.setCapsule(position,value);
        infoHolder.setTextInfoData(position,value);
        notifyAdapterItemChanged(position);
    }
}
