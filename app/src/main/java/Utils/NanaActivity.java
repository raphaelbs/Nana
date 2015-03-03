package Utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import br.com.createlier.nana.nana.R;
import recycler_handlers.InfoHolder;
import recycler_handlers.RITAOAdapter;

/**
 * Created by dede on 01/03/2015.
 */
abstract public class NanaActivity extends ActionBarActivity {

    private SharedPreferences mSharedPreferences;
    private InfoHolder mInfoHolder;
    private Activity mActivity;
    private RecyclerView mRecyclerView;
    private DividerItemDecoration mDividerItemDecoration;
    private SharedPreferences.Editor mEditor;

    abstract public void populateList(InfoHolder infoHolder);

    abstract public void manageDatabase();

    /**
     * Call this method only in the mainActivity Class.
     * @param activity
     */
    public void setPrivateDatabase(String name, Activity activity) {
        this.mActivity = activity;
        this.mInfoHolder = new InfoHolder(activity);
        this.mSharedPreferences = activity.getSharedPreferences(name, Context.MODE_PRIVATE);
        this.mEditor = this.mSharedPreferences.edit();
    }

    public void setRecyclerView(RecyclerView recyclerView) {
        manageDatabase();
        mEditor.commit();
        populateList(this.mInfoHolder);
        mRecyclerView = recyclerView;
        recyclerView.setAdapter(new RITAOAdapter(this.mInfoHolder));
        recyclerView.setLayoutManager(new LinearLayoutManager(this.mActivity));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    public void setDecorationInRecyclerView(boolean enabled) {
        mDividerItemDecoration = new DividerItemDecoration(
                mActivity.getResources().getDrawable(R.drawable.abc_list_divider_mtrl_alpha));
        if (enabled)
            mRecyclerView.addItemDecoration(mDividerItemDecoration);
        else
            mRecyclerView.removeItemDecoration(mDividerItemDecoration);
    }

    public String getFromDatabase(final int key, final int defaultValue){
        return mSharedPreferences.getString(
                mActivity.getResources().getString(key), mActivity.getResources().getString(defaultValue));
    }

    public String getFromDatabase(final int key){
        return mSharedPreferences.getString(
                mActivity.getResources().getString(key), mActivity.getResources().getString(key));
    }

    public String getFromDatabase(final int key, final String defaultValue){
        return mSharedPreferences.getString(
                mActivity.getResources().getString(key), defaultValue);
    }

    public void setInDatabase(final int key, final String value){
        mEditor.putString(mActivity.getResources().getString(key),value);
    }

    public void setInDatabase(final String key, final String value){
        mEditor.putString(key,value);
    }

}
