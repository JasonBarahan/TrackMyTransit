package use_case.delay;

import java.text.ParseException;

public interface DelayInteractor {
    public final String NONE = "No Delay - ";
    public final String MINIMAL = "Minimal Delay Time - ";
    public final String DELAY = "Delay - ";
    public final String MAJOR = "Major Delay - ";

    public final String SECONDS = " second(s)";
    public final String MINUTES = " minute(s)";
    public final String HOURS = " hour(s)";

    String delayTime(String scheduled, String computed) throws ParseException;
    double calculated(double time);
}
