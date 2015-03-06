package Utils;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import br.com.createlier.nana.nana.R;
import recycler_handlers.InfoData;
import recycler_handlers.InfoHolder;

/**
 * To correct use this class, you need to feed the @defineParentView and @defineActivity.
 * Created by dede on 06/03/2015.
 */
public class PillSelectorManager extends Nana {
    private ViewGroup mParent;
    private TextView mAboutText;
    private TextView mNextButton;
    private InfoData infoData;
    private TextView mTimeHolder;

    /**
     * Defines the ViewGroup that are holding the @fragment_pill_selector.xml.
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
        onCreate(activity);
        return this;
    }

    final public void build() {
        setPrivateDatabase("AlarmDatabase");

        final RecyclerView recyclerView = (RecyclerView) mParent.findViewById(R.id.recycler_view);
        mAboutText = (TextView) mParent.findViewById(R.id.fpsAboutText);
        mNextButton = (TextView) mParent.findViewById(R.id.nextScreen);
        mNextButton.setText(R.string.material_dialog_finish);
        mTimeHolder = (TextView) mParent.findViewById(R.id.timeTextHolder);

        mAboutText.setText("");
        setRecyclerView(recyclerView);
        setDecorationInRecyclerView(true);

        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int nhour = Integer.parseInt(mTimeHolder.getText().toString().charAt(0) + "" + mTimeHolder.getText().toString().charAt(1));
                infoData = new InfoData(
                        Utils.chooseResourcesFromTime(nhour),
                        mTimeHolder.getText().toString(),
                        mAboutText.getText().toString(),
                        false
                );
            }
        });
    }

    public InfoData getInfoData() {
        return infoData;
    }

    @Override
    protected void populateList(InfoHolder infoHolder) {
        for (int i = 0; i < 7; i++) {
            final int ite = i;
            infoHolder.addSelectionWithIcon(
                    R.drawable.ic_launcher,
                    CapsuleHandler.getCapsuleName(i)
            ).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    addOn(ite);
                }
            });
        }

    }

    @Override
    protected void manageDatabase() {
        CapsuleHandler.newList();
        for (int i = 0; i < 7; i++)
            CapsuleHandler.setCapsule(i,
                    getFromDatabase(CapsuleHandler.getKey(i)));

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
