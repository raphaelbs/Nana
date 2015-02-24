package Utils;

import java.util.ArrayList;

/**
 * Created by dede on 23/02/2015.
 */
public final class CapsuleHandler {
    private static int size = 7;
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

    public static void Fill() {
        capsules.add("Omeprazol");
        capsules.add("Adalat");
        capsules.add("Cymbalta");
        capsules.add("Epaz");
        capsules.add("Oscal");
        capsules.add("Apraz");
        capsules.add("Clinfar");
    }
}