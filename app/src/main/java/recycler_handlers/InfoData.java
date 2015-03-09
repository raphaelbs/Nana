package recycler_handlers;

import android.view.View;

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

    public InfoData(String mainText) {
        this.mainText = mainText;
        layoutType = InfoHolder.CONTAIN_ONLY_TEXT;
        asAction = false;
        isDeletable = false;
    }

    public InfoData(int iconPath, String mainText, boolean isEditable) {
        this.mainText = mainText;
        this.iconPath = iconPath;
        if (isEditable)
            layoutType = InfoHolder.CONTAIN_ICON_EDIT;
        else
            layoutType = InfoHolder.CONTAIN_ICON_TEXT;
        asAction = false;
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
}
