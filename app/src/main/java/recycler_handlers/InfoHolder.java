package recycler_handlers;

import android.content.Context;
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
    public final static int CONTAIN_ICON_EDIT = 0x4;
    public final static int CONTAIN_ICON_EDIT_TEXT = 0x8;
    private final static String error = "This InfoHolder is not qualified to store this data-types." +
            "Try to change the CONTENT_VALUE parameter inside the InfoHolder constructor or " +
            "use a different type of InfoHolder.add() method.";
    private ArrayList<InfoData> infoDatas;
    private Context context;

    public InfoHolder(Context context) {
        this.context = context;
        this.infoDatas = new ArrayList<InfoData>();
    }

    /**
     * Add an item inside this RecyclerView adapter that contains an ImageView(icon), TextView(time) and
     * other TextView(capsule chain name).
     * @param resource
     * @param text
     * @param capsuleChain
     * @return
     */
    public InfoData addAlarmSelector(int resource, String text, int[] capsuleChain) {
        String about = "";
        for (int i = 0; i < capsuleChain.length; i++) {
            about += CapsuleHandler.getCapsuleName(capsuleChain[i]);
            if (i != capsuleChain.length - 1)
                about += ", ";
        }

        infoDatas.add(
                new InfoData(
                        resource,
                        text,
                        about,
                        false)
        );

        return getLastAdded();
    }

    /**
     * Add an item to this RecyclerView adapter with a TextView.
     *
     * @param text
     * @return
     */
    public InfoData addTitle(String text) {
        infoDatas.add(
                new InfoData(
                        text)
        );
        return getLastAdded();
    }

    /**
     * Add an item to this RecyclerView adapter with a TextView.
     * You need to supply the resources path instead of the value.
     *
     * @param textPath
     * @return
     */
    public InfoData addTitle(int textPath) {
        infoDatas.add(
                new InfoData(
                        getFromResources(textPath))
        );
        return getLastAdded();
    }

    /**
     * Add an item to this RecyclerView adapter with a TextView and an ImageView(icon).
     *
     * @param iconPath
     * @param text
     * @return
     */
    public InfoData addSelectionWithIcon(int iconPath, String text) {
        infoDatas.add(
                new InfoData(
                        iconPath,
                        text,
                        false)
        );
        return getLastAdded();
    }

    /**
     * Add an item to this RecyclerView adapter with a TextView and an ImageView(icon).
     * You need to supply the resources path instead of the value.
     *
     * @param iconPath
     * @param textPath
     * @return
     */
    public InfoData addSelectionWithIcon(int iconPath, int textPath) {
        infoDatas.add(
                new InfoData(
                        iconPath,
                        getFromResources(textPath),
                        false)
        );
        return getLastAdded();
    }

    /**
     * Add an item to this RecyclerView adapter with a TextView, a complementary TextView(about) and an ImageView(icon).
     *
     * @param iconPath
     * @param text
     * @param complement
     * @return
     */
    public InfoData addSelectionWithComplementAndIcon(int iconPath, String text, String complement) {
        infoDatas.add(
                new InfoData(
                        iconPath,
                        text,
                        complement,
                        false)
        );
        return getLastAdded();
    }

    /**
     * Add an item to this RecyclerView adapter with a TextView, a complementary TextView(about) and an ImageView(icon).
     * You need to supply the resources path instead of the value.
     *
     * @param iconPath
     * @param textPath
     * @param complementPath
     * @return
     */
    public InfoData addSelectionWithComplementAndIcon(int iconPath, int textPath, int complementPath) {
        infoDatas.add(
                new InfoData(
                        iconPath,
                        getFromResources(textPath),
                        getFromResources(complementPath),
                        false)
        );
        return getLastAdded();
    }


    /**
     * Add an item to this RecyclerView adapter with an EditText(empty), a TextView(hint) and an ImageView(icon).
     *
     * @param iconPath
     * @param hint
     * @return
     */
    public InfoData addEditableWithIcon(int iconPath, String hint) {
        infoDatas.add(
                new InfoData(
                        iconPath,
                        hint,
                        true)
        );
        return getLastAdded();
    }

    /**
     * Add an item to this RecyclerView adapter with an EditText(empty), a TextView(hint) and an ImageView(icon).
     * You need to supply the resources path instead of the value.
     *
     * @param iconPath
     * @param hintPath
     * @return
     */
    public InfoData addEditableWithIcon(int iconPath, int hintPath) {
        infoDatas.add(
                new InfoData(
                        iconPath,
                        getFromResources(hintPath),
                        true)
        );
        return getLastAdded();
    }

    /**
     * Add an item to this RecyclerView adapter with an EditText(with text), a TextView(hint) and an ImageView(icon).
     *
     * @param iconPath
     * @param text
     * @param hint
     * @return
     */
    public InfoData addEditableWithLoadedTextAndIcon(int iconPath, String text, String hint) {
        infoDatas.add(
                new InfoData(
                        iconPath,
                        hint,
                        text,
                        true)
        );
        return getLastAdded();
    }

    /**
     * Add an item to this RecyclerView adapter with an EditText(with text), a TextView(hint) and an ImageView(icon).
     * You need to supply the resources path instead of the value.
     *
     * @param iconPath
     * @param textPath
     * @param hintPath
     * @return
     */
    public InfoData addEditableWithLoadedTextAndIcon(int iconPath, int textPath, int hintPath) {
        infoDatas.add(
                new InfoData(
                        iconPath,
                        getFromResources(hintPath),
                        getFromResources(textPath),
                        true)
        );
        return getLastAdded();
    }

    public ArrayList<InfoData> getInfoDatas() {
        return infoDatas;
    }

    private InfoData getLastAdded() {
        return infoDatas.get(infoDatas.size() - 1);
    }

    private String getFromResources(int path){
        return context.getResources().getString(path);
    }
}
