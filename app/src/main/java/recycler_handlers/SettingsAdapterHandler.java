package recycler_handlers;

import java.util.ArrayList;

import Utils.CapsuleHandler;
import br.com.createlier.nana.nana.R;

/**
 * Created by Raphael on 24/02/2015.
 */
public class SettingsAdapterHandler {
    private ArrayList<InfoHolder> apl = new ArrayList<InfoHolder>();

    public void add(int resources, String time) {
        apl.add(new InfoHolder(resources, time));
    }

    public ArrayList<InfoHolder> getList() {
        return apl;
    }
}