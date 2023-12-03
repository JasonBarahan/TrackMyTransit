package use_case.delay;

import java.text.ParseException;

public interface Delay {
    String NONE = "No Delay - ";
    String MINIMAL = "Minimal Delay Time - ";
    String DELAY = "Delay - ";
    String MAJOR = "Major Delay - ";

    String SECONDS = " second(s)";
    String MINUTES = " minute(s)";
    String HOURS = " hour(s)";

    String delayTime(String scheduled, String computed) throws ParseException;
    double calculated(double time);
}
