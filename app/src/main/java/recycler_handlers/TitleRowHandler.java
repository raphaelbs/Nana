package recycler_handlers;

import java.util.ArrayList;

/**
 * Created by Raphael on 24/02/2015.
 */
public class TitleRowHandler {
    private ArrayList<String> apl;
    private ArrayList<Integer> positions;

    public TitleRowHandler(){
        apl = new ArrayList<String>();
        positions = new ArrayList<Integer>();
    }

    public void add(int position, String text) {
        apl.add(text);
        positions.add(position);
    }

    public ArrayList<String> getTextList() {
        return apl;
    }

    public ArrayList<Integer> getPositionList() {
        return positions;
    }
}
