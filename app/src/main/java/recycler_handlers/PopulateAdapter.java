package recycler_handlers;

import java.util.ArrayList;

import Utils.CapsuleHandler;
import br.com.createlier.nana.nana.R;


public class PopulateAdapter {
    public static class AlarmAdapter {
        private static ArrayList<InfoHolder> apl = new ArrayList<InfoHolder>();

        public static void add(int resources, String time, int[] capsulesChain) {
            apl.add(new InfoHolder(resources, time, capsulesChain));
        }

        public static ArrayList<InfoHolder> getPopulatedList() {
            return populateList();
        }


        private static ArrayList<InfoHolder> populateList() {
            for (int i = 0; i < 24; i++) {
                apl.add(new InfoHolder(R.drawable.ic_launcher, i + ":00", new int[]{1, 2, 5, 4}));
                apl.add(new InfoHolder(R.drawable.ic_launcher, i + ":30", new int[]{3, 5, 2, 1}));
            }
            return apl;
        }
    }

    public static class SettingsAdapter {
        private static ArrayList<InfoHolder> apl = new ArrayList<InfoHolder>();

        public static void add(int resources, String time) {
            apl.add(new InfoHolder(resources, time));
        }

        public static ArrayList<InfoHolder> getPopulatedList() {
            return populateList();
        }


        private static ArrayList<InfoHolder> populateList() {
            for (int i = 0; i < CapsuleHandler.getCapsulesSize(); i++)
                apl.add(new InfoHolder(R.drawable.ic_launcher, CapsuleHandler.getCapsuleName(i)));
            return apl;
        }
    }
}

