package Utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import br.com.createlier.nana.nana.R;
import recycler_handlers.InfoHolder;
import recycler_handlers.RITAOAdapter;

/**
 * Created by dede on 01/03/2015.
 */
abstract public class PrivateDatabase {

    private SharedPreferences sharedPreferences;
    private InfoHolder infoHolder;
    private RecyclerView recyclerView;
    private Activity activity;
    private final DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(
            activity.getResources().getDrawable(R.drawable.abc_list_divider_mtrl_alpha));

    abstract public void populateList(InfoHolder infoHolder,
                                      SharedPreferences sharedPreferences);

    public void setPrivateDatabase(Activity activity) {
        this.activity = activity;
        this.infoHolder = new InfoHolder(activity);
        this.sharedPreferences = activity.getPreferences(Context.MODE_PRIVATE);
    }

    public void setRecyclerView(RecyclerView recyclerView) {
        recyclerView.setAdapter(new RITAOAdapter(infoHolder));
        recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    public void setDecorationInRecyclerView(boolean enabled) {
        if (enabled)
            recyclerView.addItemDecoration(dividerItemDecoration);
        else
            recyclerView.removeItemDecoration(dividerItemDecoration);
    }

}
