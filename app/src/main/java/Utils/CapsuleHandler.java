package Utils;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.res.Resources;

import java.util.ArrayList;

import br.com.createlier.nana.nana.R;

/**
 * Created by dede on 23/02/2015.
 */
public final class CapsuleHandler {
    private static int size = 7;
    private static Resources mResources;
    private static SharedPreferences mSharedPreferences;
    private static ArrayList<String> capsules = new ArrayList<String>();

    public static void setCapsule(int position, String name) {
        capsules.set(position, name);
    }

    public static String getCapsuleName(int position) {
        return capsules.get(position);
    }

    public static int getCapsulesSize() {
        return size;
    }

    public static ArrayList<String> getCapsules() {
        return capsules;
    }

    public static void Fill(final Resources resources, final SharedPreferences sharedPreferences) {
        capsules = new ArrayList<String>();
        mResources = resources;
        mSharedPreferences = sharedPreferences;

        add(R.string.spk_pill1,R.string.spd_pill1);
        add(R.string.spk_pill2,R.string.spd_pill2);
        add(R.string.spk_pill3,R.string.spd_pill3);
        add(R.string.spk_pill4,R.string.spd_pill4);
        add(R.string.spk_pill5,R.string.spd_pill5);
        add(R.string.spk_pill6,R.string.spd_pill6);
        add(R.string.spk_pill7,R.string.spd_pill7);
    }

    private static void add(final int keyValue, final int defaultValue){
        capsules.add(mSharedPreferences.getString(
                mResources.getString(keyValue), mResources.getString(defaultValue)));
    }
}