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
    private RecyclerView mRecyclerView;
    private DividerItemDecoration mDividerItemDecoration;
    private SharedPreferences.Editor mEditor;

    abstract protected void populateList(InfoHolder infoHolder);

    abstract protected void manageDatabase();

    final protected void setPrivateDatabase(String name) {
        this.mInfoHolder = new InfoHolder(this);
        this.mSharedPreferences = this.getSharedPreferences(name, Context.MODE_PRIVATE);
        this.mEditor = this.mSharedPreferences.edit();
    }

    /**
     * Bind the @mRecyclerView with an new InfoHolder.
     * Do not forget to assign the @mRecyclerView with a valid inflated RecyclerView.
     */
    final protected void setRecyclerView(RecyclerView recyclerView) {
        manageDatabase();
        populateList(this.mInfoHolder);
        mRecyclerView = recyclerView;
        mRecyclerView.setAdapter(new RITAOAdapter(this.mInfoHolder));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    final protected void notifyAdapterItemChanged(int position){
        mRecyclerView.getAdapter().notifyItemChanged(position);
    }

    final protected void setDecorationInRecyclerView(boolean enabled) {
        mDividerItemDecoration = new DividerItemDecoration(
                this.getResources().getDrawable(R.drawable.abc_list_divider_mtrl_alpha));
        if (enabled)
            mRecyclerView.addItemDecoration(mDividerItemDecoration);
        else
            mRecyclerView.removeItemDecoration(mDividerItemDecoration);
    }

    final protected String getFromDatabase(final int key, final int defaultValue){
        return mSharedPreferences.getString(
                this.getResources().getString(key), this.getResources().getString(defaultValue));
    }

    final protected String getFromDatabase(final int key){
        return mSharedPreferences.getString(
                this.getResources().getString(key), this.getResources().getString(key));
    }

    final protected String getFromDatabase(final int key, final String defaultValue){
        return mSharedPreferences.getString(
                this.getResources().getString(key), defaultValue);
    }

    final protected void setInDatabase(final int key, final String value){
        mEditor.putString(this.getResources().getString(key),value);
        mEditor.commit();
    }

    final protected void setInDatabase(final String key, final String value){
        mEditor.putString(key,value);
        mEditor.commit();
    }

}
