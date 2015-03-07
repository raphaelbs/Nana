package Utils;

import android.app.Activity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import br.com.createlier.nana.nana.R;
import recycler_handlers.InfoHolder;
import recycler_handlers.RITAOAdapter;

/**
 * To correct use this class, you need to feed the @defineParentView and @defineActivity.
 * Created by dede on 06/03/2015.
 */
public class PillSelectorManager {
    private ViewGroup mParent;
    private TextView mAboutText;
    private Activity activity;

    /**
     * Defines the ViewGroup that are holding the @dialog_add_pill_endl.
     *
     * @param v
     * @return
     */
    final public PillSelectorManager defineParentView(ViewGroup v) {
        mParent = v;
        return this;
    }

    /**
     * Define in witch @Activity we are working on.
     *
     * @param activity
     */
    final public PillSelectorManager defineActivity(Activity activity) {
        this.activity = activity;
        return this;
    }

    final public void build() {
        final RecyclerView recyclerView = (RecyclerView) mParent.findViewById(R.id.recycler_view_end);
        mAboutText = (TextView) mParent.findViewById(R.id.fpsAboutText);

        recyclerView.setAdapter(new RITAOAdapter(populateList()));
        recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    private InfoHolder populateList() {
        InfoHolder mInfoHolder = new InfoHolder(activity);
        for (int i = 0; i < 7; i++) {
            final int ite = i;
            mInfoHolder.addSelectionWithIcon(
                    R.drawable.ic_launcher,
                    CapsuleHandler.getCapsuleName(i)
            ).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    addOn(ite);
                }
            });
        }
        return mInfoHolder;
    }

    final private void addOn(final int position) {
        if (mAboutText.getText().equals(""))
            mAboutText.setText(CapsuleHandler.getCapsuleName(position));
        else
            mAboutText.setText(
                    mAboutText.getText().toString() +
                            ", " + CapsuleHandler.getCapsuleName(position));
    }
}
