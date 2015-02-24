package recycler_handlers;

import java.util.ArrayList;

import br.com.createlier.nana.nana.R;

/**
 * Created by Raphael on 24/02/2015.
 */

public class AlarmAdapter {
    private ArrayList<InfoHolder> apl = new ArrayList<InfoHolder>();

    public void add(int hour, int minutes, int[] capsulesChain) {
        String time = String.format("%02d", hour) + ":" + String.format("%02d", minutes);
        apl.add(new InfoHolder(chooseResourcesFromTime(hour), time, capsulesChain));
    }

    public ArrayList<InfoHolder> getList() {
        return apl;
    }


    private int chooseResourcesFromTime(int hour){
        if(hour >= 0 && hour < 4)
            return R.mipmap.midnight;
        if(hour >= 4 && hour < 6)
            return R.mipmap.night;
        if(hour >= 6 && hour < 11)
            return R.mipmap.sunrise;
        if(hour >= 10 && hour < 16)
            return R.mipmap.midday;
        if(hour >= 16 && hour < 19)
            return R.mipmap.afternoon;
        if(hour >= 19 && hour < 22)
            return R.mipmap.night;
        if(hour >= 22 && hour < 24)
            return R.mipmap.midnight;
        return 0;
    }
}