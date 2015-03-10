package Utils;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;

import java.util.ArrayList;

import br.com.createlier.nana.nana.R;

/**
 * To correct use this class, you need to feed the @defineParentView and @defineActivity.
 * Created by dede on 06/03/2015.
 */
public class PillSelectorManager {
    private static int nVal;
    private ViewGroup mParent;
    private TextView mAboutText;
    private ImageView mAdd, mRemove;
    private NumberPicker mNumberPicker;
    private ArrayList<Integer> capsulesHolder;

    public PillSelectorManager(ViewGroup parent) {
        mParent = parent;
        mAboutText = (TextView) parent.findViewById(R.id.fpsAboutText);
        mNumberPicker = (NumberPicker) parent.findViewById(R.id.fpsHolderList);
        mAdd = (ImageView) parent.findViewById(R.id.fpsAdd);
        mRemove = (ImageView) parent.findViewById(R.id.fpsRemove);
        mAboutText.setText("");
        capsulesHolder = new ArrayList<Integer>();
        nVal = 0;
    }

    final public void build() {
        String[] capsules = CapsuleHandler.getCapsules().toArray(
                new String[CapsuleHandler.getCapsules().size()]);
        mNumberPicker.setMaxValue(capsules.length - 1);
        mNumberPicker.setMinValue(0);
        mNumberPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        mNumberPicker.setWrapSelectorWheel(true);
        mNumberPicker.setDisplayedValues(capsules);
        mNumberPicker.setValue(0);

        mNumberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                nVal = newVal;
            }
        });

        mAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                capsulesHolder.add(nVal);
                updateList();
            }
        });

        mRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (capsulesHolder.size() != 0)
                    capsulesHolder.remove(capsulesHolder.size() - 1);
                updateList();
            }
        });
    }

    public String getCapsulesHolder() {
        String s = "";
        for (int i : capsulesHolder)
            s += i + " ";
        return s;
    }

    final private void updateList() {
        String text = "";
        for (int i = 0; i < capsulesHolder.size(); i++)
            if (i == 0)
                text = CapsuleHandler.getCapsuleName(capsulesHolder.get(i));
            else
                text += ", " + CapsuleHandler.getCapsuleName(capsulesHolder.get(i));
        mAboutText.setText(text);
    }
}
