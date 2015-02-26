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

import Utils.DividerItemDecoration;
import recycler_handlers.InfoHolder;
import recycler_handlers.RITAOAdapter;


public class SettingsActivity extends ActionBarActivity {

    private Toolbar toolbar;
    private RecyclerView settings_capsules_rv;
    private InfoHolder infoHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        toolbar = (Toolbar) findViewById(R.id.app_bar);

        settings_capsules_rv = (RecyclerView) findViewById(R.id.settings_capsules_name_rv);
        setSupportActionBar(toolbar);

        infoHolder = new InfoHolder(InfoHolder.CONTAIN_ICON_TEXT_ABOUT);
        populateList();

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        settings_capsules_rv.setAdapter(new RITAOAdapter(infoHolder));
        settings_capsules_rv.setLayoutManager(new LinearLayoutManager(this));
        settings_capsules_rv.setItemAnimator(new DefaultItemAnimator());
        settings_capsules_rv.addItemDecoration(new DividerItemDecoration(this, null));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_settings, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == android.R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
        }

        return super.onOptionsItemSelected(item);
    }

    private void populateList() {
        int DEFAULT_ICON = R.mipmap.ic_launcher;
        /*this.infoHolder.add(DEFAULT_ICON, "Nomeclatura", false);
        this.infoHolder.add(DEFAULT_ICON, "Conexão", false);
        this.infoHolder.add(DEFAULT_ICON, "Sons", false);*/

        this.infoHolder.add("Título comum");
        this.infoHolder.add(DEFAULT_ICON,"","editText - dica", true);
        this.infoHolder.add(DEFAULT_ICON,"Seleção comum",false);
        this.infoHolder.add(DEFAULT_ICON,"editText preenchido", true);
        this.infoHolder.add(DEFAULT_ICON,"editText preenchido","e com dica", true);
        this.infoHolder.add(DEFAULT_ICON,"seleção comum"," e com texto embaixo", false);
    }
}
