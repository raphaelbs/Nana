package Utils;

import br.com.createlier.nana.nana.R;

/**
 * Created by dede on 01/03/2015.
 */
public class Utils {

    public static int chooseResFromTime(String hour) {
        return chooseResFromTime(Integer.parseInt(hour.charAt(0) + "" + hour.charAt(1)));
    }

    public static int chooseResFromTime(int hour) {
        if (hour >= 0 && hour < 2)
            return R.mipmap.ic_brightness_1;
        if (hour >= 2 && hour < 4)
            return R.mipmap.ic_brightness_4;
        if (hour >= 4 && hour < 6)
            return R.mipmap.ic_brightness_5;
        if (hour >= 6 && hour < 10)
            return R.mipmap.ic_brightness_6;
        if (hour >= 10 && hour < 16)
            return R.mipmap.ic_brightness_7;
        if (hour >= 16 && hour < 19)
            return R.mipmap.ic_brightness_4;
        if (hour >= 19 && hour < 22)
            return R.mipmap.ic_brightness_2;
        if (hour >= 22 && hour < 24)
            return R.mipmap.ic_brightness_1;
        return 0;
    }

    public static String encodeHour(String hour) {
        int ihour = Integer.parseInt(hour.charAt(0) + "" + hour.charAt(1));
        int imin = Integer.parseInt(hour.charAt(3) + "" + hour.charAt(4));
        String r = (ihour * 6 + (int) (imin / 10)) + "";
        return r;
    }

    public static String decodeHour(int hour) {
        int ihour = hour / 6;
        int imin = (hour % 6) * 10;
        String r = String.format("%02d:%02d", ihour, imin);
        return r;
    }

}
