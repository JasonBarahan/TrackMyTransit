package entity;

public interface TrainInterface {
    String getLineNumber();

    String getLineName();

    float getLatitude();

    float getLongitude();

    String getTrainDirection();

    String getScheduledTime();
    String getDepartureTime();
    String getTripNumber();

    String getDelay();
}
