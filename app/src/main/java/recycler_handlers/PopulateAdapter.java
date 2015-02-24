package recycler_handlers;

import java.util.ArrayList;

import Utils.CapsuleHandler;
import br.com.createlier.nana.nana.R;


public class PopulateAdapter {
    private AlarmAdapter alarmAdapter;
    private SettingsAdapter settingsAdapter;

    public void populateAlarmAdapter() {
        alarmAdapter = new AlarmAdapter();
        populateList(alarmAdapter);
    }

    public void populateSettingsAdapter() {
        settingsAdapter = new SettingsAdapter();
        populateList(settingsAdapter);
    }

    public ArrayList<InfoHolder> getAlarmAdapterList() {
        return alarmAdapter.getList();
    }

    public ArrayList<InfoHolder> getSettingsAdapterList() {
        return settingsAdapter.getList();
    }

    private void populateList(SettingsAdapter sa) {
        for (int i = 0; i < CapsuleHandler.getCapsulesSize(); i++)
            sa.add(R.drawable.ic_launcher, CapsuleHandler.getCapsuleName(i));
    }

    private void populateList(AlarmAdapter aa) {
        aa.add(7, 30, new int[]{0});
        aa.add(8, 30, new int[]{1, 2, 3});
        aa.add(12, 00, new int[]{4});
        aa.add(20, 30, new int[]{1, 5, 6});
    }
}

