package recycler_handlers;

import android.content.Context;

import java.util.ArrayList;

import Utils.CapsuleHandler;
import br.com.createlier.nana.nana.R;

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
    private Context mContext;

    public InfoHolder(Context context) {
        this.infoDatas = new ArrayList<InfoData>();
        this.mContext = context;
    }

    /**
     * Add an item inside this RecyclerView adapter that contains an ImageView(icon), TextView(time) and
     * other TextView(capsule chain name).
     *
     * @param hour
     * @param min
     * @param capsules
     * @return
     */
    public InfoData addAlarmSelector(int hour, int min, int[] capsules) {
        String time = String.format("%02d", hour) + ":" + String.format("%02d", min);
        String about = "";
        for (int i = 0; i < capsules.length; i++) {
            about += CapsuleHandler.getCapsuleName(capsules[i]);
            if (i != capsules.length - 1)
                about += ", ";
        }

        infoDatas.add(
                new InfoData(
                        chooseResourcesFromTime(hour),
                        time,
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

    private int chooseResourcesFromTime(int hour) {
        if (hour >= 0 && hour < 2)
            return R.mipmap.ic_brightness_1;
        if (hour >= 2 && hour < 4)
            return R.mipmap.ic_brightness_4;
        if (hour >= 4 && hour < 6)
            return R.mipmap.ic_brightness_5;
        if (hour >= 6 && hour < 10)
            return R.mipmap.ic_brightness_6;
        if (hour >= 10 && hour < 16)
            return R.mipmap.ic_brightness_7;
        if (hour >= 16 && hour < 19)
            return R.mipmap.ic_brightness_4;
        if (hour >= 19 && hour < 22)
            return R.mipmap.ic_brightness_2;
        if (hour >= 22 && hour < 24)
            return R.mipmap.ic_brightness_1;
        return 0;
    }

    public ArrayList<InfoData> getInfoDatas() {
        return infoDatas;
    }

    public void setTextInfoData(int position, String text){
        infoDatas.get(position).setMainText(text);
    }

    private InfoData getLastAdded() {
        return infoDatas.get(infoDatas.size() - 1);
    }

    private String getFromResources(int path) {
        return mContext.getResources().getString(path);
    }
}
