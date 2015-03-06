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
 * Created by dede on 06/03/2015.
 */
abstract class Nana {

    private SharedPreferences mSharedPreferences;
    private InfoHolder mInfoHolder;
    private RecyclerView mRecyclerView;
    private DividerItemDecoration mDividerItemDecoration;
    private SharedPreferences.Editor mEditor;
    private Activity mActivity;

    abstract protected void populateList(InfoHolder infoHolder);

    abstract protected void manageDatabase();

    final protected void onCreate(Activity activity) {
        mActivity = activity;
    }

    final protected void setPrivateDatabase(String name) {
        this.mInfoHolder = new InfoHolder(mActivity);
        this.mSharedPreferences = mActivity.getSharedPreferences(name, Context.MODE_PRIVATE);
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
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    final protected void notifyAdapterItemChanged(int position) {
        mRecyclerView.getAdapter().notifyItemChanged(position);
    }

    final protected void setDecorationInRecyclerView(boolean enabled) {
        mDividerItemDecoration = new DividerItemDecoration(
                mActivity.getResources().getDrawable(R.drawable.abc_list_divider_mtrl_alpha));
        if (enabled)
            mRecyclerView.addItemDecoration(mDividerItemDecoration);
        else
            mRecyclerView.removeItemDecoration(mDividerItemDecoration);
    }

    final protected String getFromDatabase(final int key, final int defaultValue) {
        return mSharedPreferences.getString(
                mActivity.getResources().getString(key), mActivity.getResources().getString(defaultValue));
    }

    final protected String getFromDatabase(final int key) {
        return mSharedPreferences.getString(
                mActivity.getResources().getString(key), mActivity.getResources().getString(key));
    }

    final protected String getFromDatabase(final int key, final String defaultValue) {
        return mSharedPreferences.getString(
                mActivity.getResources().getString(key), defaultValue);
    }

    final protected void setInDatabase(final int key, final String value) {
        mEditor.putString(mActivity.getResources().getString(key), value);
        mEditor.commit();
    }

    final protected void setInDatabase(final String key, final String value) {
        mEditor.putString(key, value);
        mEditor.commit();
    }

}
