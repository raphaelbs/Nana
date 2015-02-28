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

    public InfoData(String mainText) {
        this.mainText = mainText;
        layoutType = InfoHolder.CONTAIN_ONLY_TEXT;
        asAction = false;
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

    public int getLayoutType() {
        return layoutType;
    }

    public String getMainText() {
        return mainText;
    }

    public String getOptionalText() {
        return optionalText;
    }

    public View.OnClickListener getOnClickListener() {
        return onClickListener;
    }

    public int getIconPath() {
        return iconPath;
    }

    public boolean haveAction() {
        return asAction;
    }
}
