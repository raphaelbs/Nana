package Utils;

import android.view.ViewGroup;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

import br.com.createlier.nana.nana.R;

/**
 * Created by dede on 06/03/2015.
 */
public class TimePickerManager {
    private TimePicker mTimePicker;
    private int hourOfDay;
    private int minutesInterval;
    private ViewGroup mParent;

    public TimePickerManager(final ViewGroup parent, final int MINUTES_INTERVAL) {
        mParent = parent;
        mTimePicker = (TimePicker) mParent.findViewById(R.id.timePicker);
        hourOfDay = Calendar.getInstance().get(Calendar.HOUR_OF_DAY); // Get hour now
        minutesInterval = MINUTES_INTERVAL;
    }

    public void build() {
        /* Change our TimePicker to 24h mode and apply current @hourOfDay */
        mTimePicker.setIs24HourView(true);
        mTimePicker.setCurrentHour(hourOfDay);

        final TextView hourShower = (TextView) mParent.findViewById(R.id.timeTextHolder);

        /* Populate the minutes @NumberPicker with intervals of @MINUTES_INTERVAL */
        defineMinutesInterval(minutesInterval);

        hourShower.setText(hourOfDay + ":" + "00");
        mTimePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                hourShower.setText(String.format(hourOfDay + " : %02d", minute * minutesInterval));
            }
        });
    }

    private void defineMinutesInterval(int interval) {
        NumberPicker minutes = (NumberPicker) ((ViewGroup) ((ViewGroup) mTimePicker.getChildAt(0)).getChildAt(0)).getChildAt(2);
        String[] minutesInterval = returnMinutes(interval);
        minutes.setMaxValue(minutesInterval.length - 1);
        minutes.setMinValue(0);
        minutes.setWrapSelectorWheel(true);
        minutes.setDisplayedValues(minutesInterval);
        minutes.setValue(0);
    }

    /**
     * Generate a array of minutes to feed into the TimePicker.
     * If the 60 % interval != 0, then the interval is set to 1.
     *
     * @param interval
     * @return
     */
    private String[] returnMinutes(int interval) {
        String[] array = {};
        if (interval > 0 && interval < 60 && 60 % interval == 0)
            array = new String[60 / interval];
        else {
            array = new String[60];
            interval = 1;
        }
        for (int i = 0; i < array.length; i++)
            array[i] = (i * interval) + "";
        return array;
    }
}
