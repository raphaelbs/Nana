package recycler_handlers;

import android.view.View;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Utils.CapsuleHandler;

/**
 * Created by dede on 28/02/2015.
 */
public class InfoData {
    private View.OnClickListener onClickListener;
    private String mainText;
    private String optionalText;
    private int iconPath;
    private int layoutType;
    private boolean asAction;
    private boolean isDeletable;
    private long ID;

    public InfoData(String mainText) {
        this.mainText = mainText;
        layoutType = InfoHolder.CONTAIN_ONLY_TEXT;
        asAction = false;
        isDeletable = false;
        ID = -1;
    }

    public InfoData(int iconPath, String mainText, boolean isEditable) {
        this.mainText = mainText;
        this.iconPath = iconPath;
        if (isEditable)
            layoutType = InfoHolder.CONTAIN_ICON_EDIT;
        else
            layoutType = InfoHolder.CONTAIN_ICON_TEXT;
        asAction = false;
        ID = -1;
    }

    public InfoData(int iconPath, String mainText, String optionalText, boolean isEditable) {
        this.mainText = mainText;
        this.optionalText = optionalText;
        this.iconPath = iconPath;
        if (isEditable)
            layoutType = InfoHolder.CONTAIN_ICON_EDIT_TEXT;
        else
            layoutType = InfoHolder.CONTAIN_ICON_TEXT_ABOUT;
        asAction = false;
        ID = -1;
    }

    public InfoData updateCapsuleNames() {
        ArrayList<Integer> ar = new ArrayList<Integer>();
        Pattern pattern = Pattern.compile("([0-9])");
        Matcher matcher = pattern.matcher(optionalText);
        while (matcher.find()) {
            ar.add(Integer.parseInt(matcher.group()));
        }

        String about = "";
        for (int i = 0; i < ar.size(); i++) {
            about += CapsuleHandler.getCapsuleName(ar.get(i));
            if (i != ar.size() - 1)
                about += ", ";
        }

        optionalText = about;
        return this;
    }

    public int getLayoutType() {
        return layoutType;
    }

    public String getMainText() {
        return mainText;
    }

    public void setMainText(String mainText) {
        this.mainText = mainText;
    }

    public String getOptionalText() {
        return optionalText;
    }

    public View.OnClickListener getOnClickListener() {
        return onClickListener;
    }

    /**
     * Set the @OnClickListener event of the last included item of this RecyclerView adapter.
     *
     * @param onClickListener
     * @return
     */
    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
        this.asAction = true;
    }

    public int getIconPath() {
        return iconPath;
    }

    public boolean haveAction() {
        return asAction;
    }

    public boolean isDeletable() {
        return isDeletable;
    }

    /**
     * If true, the RITAOAdapter will bind a @View.OnLongClickListener to the item.
     * This will delete the item of the list if the listener is trigged.
     *
     * @param isDeletable
     * @return
     */
    public InfoData setDeletable(boolean isDeletable) {
        this.isDeletable = isDeletable;
        return this;
    }

    public long getID() {
        return ID;
    }

    /**
     * Set the ID of this InfoData referent to the data structure SQLite.
     * It can only be set once.
     *
     * @param ID
     */
    public InfoData setID(long ID) {
        if (this.ID == -1)
            this.ID = ID;
        return this;
    }
}
