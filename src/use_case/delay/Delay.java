package use_case.delay;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Delay {

    public static final String NONE = "No Delay";
    public static final String MINIMAL = "Minimal Delay Time ";
    public static final String DELAY = "Delay ";
    public static final String MAJOR = "Major Delay ";

    public static final String SECONDS = " second(s)";
    public static final String MINUTES = " minute(s)";
    public static final String HOURS = " hour(s)";

    /**
     * Calculates Delay time using the Scheduled Time and Computed Time.
     *
     * @param scheduled
     * @param computed
     * @return Message relaying estimated time Delay in string format
     * @throws ParseException
     */
    public static String delayTime(String scheduled, String computed) throws ParseException {

        String scheduleDate = scheduled.substring(11);
        String computedDate = computed.substring(11);

        DateFormat dateFormat = new SimpleDateFormat("hh:mm:ss");
        Date scheduleTime = dateFormat.parse(scheduleDate);
        Date computedTime = dateFormat.parse(computedDate);

        double difference = (computedTime.getTime() - scheduleTime.getTime()) * 0.001; // in seconds

        // if cases for returning the differences in time
        if (difference >= 3600) {
            return MAJOR + calculated(difference) + HOURS;
        } else if (difference >= 900) {
            return MAJOR + calculated(difference) + MINUTES;
        } else if (difference >= 180) {
            return DELAY + calculated(difference) + MINUTES;
        } else if (difference >= 60) {
            return MINIMAL + calculated(difference) + MINUTES;
        } return NONE + " arriving in " + difference + SECONDS;
    }

    /**
     * Converts seconds to minutes, otherwise returns given time.
     *
     * @param time
     * @return time
     */
    public static double calculated(double time){
        if (time >= 3600) {
            return time;
        }
        return time / 60;
    }

}
