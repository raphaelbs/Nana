package Utils;

import java.util.ArrayList;

import br.com.createlier.nana.nana.R;

/**
 * Created by dede on 23/02/2015.
 */
public final class CapsuleHandler {
    private static int size = 7;
    private static ArrayList<String> capsules;

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

    public static void newList() {
        capsules = new ArrayList<String>();
        for(int i=0; i<size; i++)
            capsules.add("");
    }

    public static int getKey(int pos){
        if (pos == 0)
            return R.string.shared_pill_name_1;
        if (pos == 1)
            return R.string.shared_pill_name_2;
        if (pos == 2)
            return R.string.shared_pill_name_3;
        if (pos == 3)
            return R.string.shared_pill_name_4;
        if (pos == 4)
            return R.string.shared_pill_name_5;
        if (pos == 5)
            return R.string.shared_pill_name_6;
        if (pos == 6)
            return R.string.shared_pill_name_7;
        return 0;
    }
}