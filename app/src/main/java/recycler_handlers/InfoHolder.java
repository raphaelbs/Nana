package recycler_handlers;

import android.view.View;

import java.util.ArrayList;

import Utils.CapsuleHandler;

/**
 * Created by dede on 22/02/2015.
 */
public class InfoHolder {
    public final static int CONTAIN_ONLY_TEXT = 0x0;
    public final static int CONTAIN_ICON_TEXT = 0x1;
    public final static int CONTAIN_ICON_TEXT_ABOUT = 0x2;
    private final static String error = "This InfoHolder is not qualified to store this data-types." +
            "Try to change the CONTENT_VALUE parameter inside the InfoHolder constructor or " +
            "use a different type of InfoHolder.add() method.";
    private ArrayList<String> text;
    private ArrayList<Integer> icon;
    private ArrayList<String> about;
    private ArrayList<Boolean> isEditable;
    private ArrayList<View.OnClickListener> ocl;
    private int current_content_value;

    public InfoHolder(int CONTENT_VALUE) {
        this.current_content_value = CONTENT_VALUE;
        this.isEditable = new ArrayList<Boolean>();
        this.text = new ArrayList<String>();
        this.ocl = new ArrayList<View.OnClickListener>();
        if (CONTENT_VALUE > CONTAIN_ONLY_TEXT)
            this.icon = new ArrayList<Integer>();
        if (CONTENT_VALUE == CONTAIN_ICON_TEXT_ABOUT)
            this.about = new ArrayList<String>();
    }

    public void add(int resource, String text, int[] capsuleChain) {
        if (current_content_value != CONTAIN_ICON_TEXT_ABOUT)
            throw new NullPointerException(error);
        else {
            this.text.add(text);
            this.icon.add(resource);
            String about = "";
            for (int i = 0; i < capsuleChain.length; i++) {
                about += CapsuleHandler.getCapsuleName(capsuleChain[i]);
                if (i != capsuleChain.length - 1)
                    about += ", ";
            }
            this.about.add(about);
            this.isEditable.add(false);
            this.ocl.add(null);
        }
    }

    private void add(int resource, String text, String about, boolean isEditable) {
        if (current_content_value != CONTAIN_ICON_TEXT_ABOUT)
            throw new NullPointerException(error);
        else {
            this.text.add(text);
            this.icon.add(resource);
            this.ocl.add(null);
            this.about.add(about);
            this.isEditable.add(isEditable);
        }
    }


    private void add(int resource, String text, boolean isEditable) {
        if (current_content_value != CONTAIN_ICON_TEXT && current_content_value != CONTAIN_ICON_TEXT_ABOUT)
            throw new NullPointerException(error);
        else {
            this.text.add(text);
            this.icon.add(resource);
            this.ocl.add(null);
            this.isEditable.add(isEditable);
            if(current_content_value == CONTAIN_ICON_TEXT_ABOUT)
                this.about.add("");
        }
    }

    private void add(String text) {
        if (current_content_value != CONTAIN_ICON_TEXT &&
                current_content_value != CONTAIN_ICON_TEXT_ABOUT &&
                current_content_value != CONTAIN_ONLY_TEXT)
            throw new NullPointerException(error);
        else {
            this.text.add(text);
            this.ocl.add(null);
            this.isEditable.add(false);
            if(current_content_value == CONTAIN_ICON_TEXT)
                this.icon.add(0);
            if(current_content_value == CONTAIN_ICON_TEXT_ABOUT ) {
                this.about.add("");
                this.icon.add(0);
            }
        }
    }

    public InfoHolder setOnClick(View.OnClickListener ocl) {
        this.ocl.set(this.text.size() - 1, ocl);
        return this;
    }

    public InfoHolder addTitle(String text) {
        this.add(text);
        return this;
    }

    public InfoHolder addSelectionWithIcon(int iconPath, String text) {
        this.add(iconPath, text, false);
        return this;
    }

    public InfoHolder addSelectionWithComplementAndIcon(int iconPath, String text, String complement) {
        this.add(iconPath, text, complement, false);
        return this;
    }

    public InfoHolder addEditableWithIcon(int iconPath, String hint) {
        this.add(iconPath, "", hint, true);
        return this;
    }

    public InfoHolder addEditableWithLoadedTextAndIcon(int iconPath, String text, String hint) {
        this.add(iconPath, text, hint, true);
        return this;
    }

    public int getCurrent_content_value() {
        return current_content_value;
    }

    public ArrayList<View.OnClickListener> getOcl() {
        return ocl;
    }

    public int getSize(){
        return text.size();
    }

    public ArrayList<String> getTextList() {
        return text;
    }

    public ArrayList<Integer> getIcon() {
        return icon;
    }

    public ArrayList<String> getDescription() {
        return about;
    }

    public ArrayList<Boolean> getIsEditable() {
        return isEditable;
    }
}
