package recycler_handlers;

import java.util.ArrayList;

import Utils.CapsuleHandler;
import br.com.createlier.nana.nana.R;


public class PopulateAdapter {
    private AlarmAdapterHandler alarmAdapterHandler;
    private SettingsAdapterHandler settingsAdapterHandler;
    private TitleRowHandler titleRowHandler;

    public void populateAlarmAdapter() {
        alarmAdapterHandler = new AlarmAdapterHandler();
        populateList(alarmAdapterHandler);
    }

    public void populateSettingsAdapter() {
        settingsAdapterHandler = new SettingsAdapterHandler();
        titleRowHandler = new TitleRowHandler();
        populateList(settingsAdapterHandler);
        populateList(titleRowHandler);
    }

    public ArrayList<InfoHolder> getAlarmAdapterList() {
        return alarmAdapterHandler.getList();
    }

    public ArrayList<InfoHolder> getSettingsAdapterList() {
        return settingsAdapterHandler.getList();
    }

    public TitleRowHandler getTitleRowHandler() {
        return titleRowHandler;
    }

    private void populateList(SettingsAdapterHandler sa) {
        for (int i = 0; i < CapsuleHandler.getCapsulesSize(); i++)
            sa.add(R.drawable.ic_launcher, CapsuleHandler.getCapsuleName(i));
    }

    private void populateList(TitleRowHandler tr) {
        tr.add(0,"Nomes:");
        tr.add(8,"Conexão:");
    }

    private void populateList(AlarmAdapterHandler aa) {
        aa.add(7, 30, new int[]{0});
        aa.add(8, 30, new int[]{1, 2, 3});
        aa.add(12, 00, new int[]{4});
        aa.add(20, 30, new int[]{1, 5, 6});
    }
}

