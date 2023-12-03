package use_case.delay;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class delay {

    // yyyy-mm-dd hh:mm:ss
    // split by " "
    // no need to use yyyy-mm-dd

    public static String scheduled = "2023-12-02 19:10:00";
    public static String computed = "2023-12-02 19:15:00";
    public static Object delayTime(String scheduled, String computed) throws ParseException {

        String scheduleDate = scheduled.substring(11);
        String computedDate = computed.substring(11);

        DateFormat dateFormat = new SimpleDateFormat("hh:mm:ss");
        Date scheduleTime = dateFormat.parse(scheduleDate);
        Date computedTime = dateFormat.parse(computedDate);

        double difference = (computedTime.getTime() - scheduleTime.getTime()) * 0.001; // in seconds
        System.out.println(difference); // testing


        return "done";
    }
    public static void main(String[] args) throws ParseException {

        delayTime(scheduled, computed);
    }

}
