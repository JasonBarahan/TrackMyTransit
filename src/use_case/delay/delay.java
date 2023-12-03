package use_case.delay;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

// TODO: remove static
public class delay {

    // yyyy-mm-dd hh:mm:ss
    // split by " "
    // no need to use yyyy-mm-dd

    // TODO: Change to private
    public static final String NONE = "No delay";
    public static final String MINIMAL = "Minimal Delay Time ";
    public static final String DELAY = "Delay ";
    public static final String MAJOR = "Major Delay ";

    public static final String SECONDS = " second(s)";
    public static final String MINUTES = " minute(s)";
    public static final String HOURS = " hour(s)";

    public static Object delayTime(String scheduled, String computed) throws ParseException {

        String scheduleDate = scheduled.substring(11);
        String computedDate = computed.substring(11);

        DateFormat dateFormat = new SimpleDateFormat("hh:mm:ss");
        Date scheduleTime = dateFormat.parse(scheduleDate);
        Date computedTime = dateFormat.parse(computedDate);

        double difference = (computedTime.getTime() - scheduleTime.getTime()) * 0.001; // in seconds

        //System.out.println(difference); // testing

        // if cases for returning the differences in time
        if (difference >= 3600) {
            return MAJOR + calculated(difference) + HOURS;
        } else if (difference >= 900) {
            return MAJOR + calculated(difference) + MINUTES;
        } else if (difference >= 180) {
            return DELAY + calculated(difference) + MINUTES;
        } else if (difference >= 60) {
            return MINIMAL + calculated(difference) + MINUTES;
        }
        return NONE + " arriving in " + difference + SECONDS;
    }

    public static double calculated(double time){
        if (time >= 3600) {
            return time;
        }
        return time / 60;
    }

}
