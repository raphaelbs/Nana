package recycler_handlers;

import Utils.CapsuleHandler;

/**
 * Created by dede on 22/02/2015.
 */
public class InfoHolder {
    private String time;
    private int res;
    private String about = "";

    public InfoHolder(int resource, String time, int[] capsuleChain) {
        this.time = time;
        this.res = resource;
        for (int i = 0; i < capsuleChain.length; i++) {
            about += CapsuleHandler.getCapsuleName(capsuleChain[i]);
            if (i != capsuleChain.length - 1)
                about += ", ";
        }
    }

    public InfoHolder(int resource, String time) {
        this.time = time;
        this.res = resource;
    }

    public String getTime() {
        return time;
    }

    public int getRes() {
        return res;
    }

    public String getDescription() {
        return about;
    }
}
