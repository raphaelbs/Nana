package br.com.createlier.nana.nana;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.software.shell.fab.ActionButton;

import Utils.CapsuleHandler;
import Utils.NanaActivity;
import Utils.PillSelectorManager;
import Utils.TimePickerManager;
import recycler_handlers.InfoHolder;


public class AlarmActivity extends NanaActivity {

    private Toolbar toolbar;
    private ActionButton actionButton;
    private RecyclerView mRecyclerView;
    private PillSelectorManager psm;
    private MaterialDialog md;

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

        actionButton = (ActionButton) findViewById(R.id.button_add_pill);

        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actionButton.startAnimation(actionButton.getHideAnimation());
                actionButton.hide();
                md.show();

                final TextView nextButton = (TextView) md.findViewById(R.id.nextScreen);
                nextButton.setText(R.string.material_dialog_next);

                final RelativeLayout parent = (RelativeLayout) md.findViewById(R.id.contentHolder);
                parent.removeAllViews();
                getLayoutInflater().inflate(R.layout.fragment_time_picker, parent);

                final RelativeLayout supremeParent = (RelativeLayout) md.findViewById(R.id.supremeParent);
                TimePickerManager timePickerManager = new TimePickerManager(supremeParent, 5);
                timePickerManager.build();

                nextButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        parent.removeAllViews();
                        getLayoutInflater().inflate(R.layout.fragment_pill_selector, parent);

                        psm = new PillSelectorManager();
                        psm.defineParentView(supremeParent).defineActivity(main);
                        psm.build();
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
        for (int i = 0; i < 7; i++)
            CapsuleHandler.setCapsule(i,
                    getFromDatabase(CapsuleHandler.getKey(i)));
    }

    @Override
    public void populateList(final InfoHolder infoHolder) {
        infoHolder.addAlarmSelector(7, 30, new int[]{0});
        infoHolder.addAlarmSelector(8, 30, new int[]{1, 2, 3});
        infoHolder.addAlarmSelector(12, 00, new int[]{4});
        infoHolder.addAlarmSelector(20, 30, new int[]{1, 5, 6});

        md = new MaterialDialog.Builder(this)
                .customView(R.layout.dialog_add_pill, true)
                .dismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        actionButton.startAnimation(actionButton.getShowAnimation());
                        actionButton.show();

                        try {
                            infoHolder.addInfoData(psm.getInfoData());
                        } catch (Exception e) {
                            Log.e("ERROR", e + "");
                        }
                    }
                })
                .build();
    }
}
