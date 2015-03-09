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

import recycler_handlers.InfoHolder;
import recycler_handlers.RITAOAdapter;


public class ConnectionActivity extends ActionBarActivity {

    private Toolbar toolbar;
    private TextView title;
    private RecyclerView recyclerView;
    private InfoHolder infoHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connection);

        toolbar = (Toolbar) findViewById(R.id.toolbar_app);
        title = (TextView) toolbar.findViewById(R.id.app_bar_dynamic_title);
        title.setText("Conexão");
        setSupportActionBar(toolbar);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        setSupportActionBar(toolbar);

        populateList();

        recyclerView.setAdapter(new RITAOAdapter(infoHolder, this));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_conection, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
        }

        return super.onOptionsItemSelected(item);
    }

    private void populateList() {
        infoHolder = new InfoHolder(this);

        infoHolder.addSelectionWithComplementAndIcon(
                R.mipmap.ic_file_upload,
                R.string.connection_att_item,
                R.string.connection_att_item_desc);
        infoHolder.addSelectionWithComplementAndIcon(
                R.mipmap.ic_file_download,
                R.string.connection_up_item,
                R.string.connection_up_item_desc);
    }
}
