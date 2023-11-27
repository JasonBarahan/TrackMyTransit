package entity;
public class Train implements TrainInterface{

    private final String lineName; //parent line name
    private final String lineNumber;  //lineNumber = lineCode
    private final String trainName;
    // train display name
    private final float latitude;
    private final float longitude;
    private final String scheduledTime;
    private final String departureTime;
    private final String tripNumber;
    private final String delay;

    /**
     * Requirement: Train name and trainNumber exist.
     * Note: trainNumber is tripNumber in GOtrainApiClass
     * @param lineName
     * @param lineNumber
     * @param trainName
     * @param latitude
     * @param longitude
     * @param scheduledTime
     * @param departureTime
     * @param tripNumber
     * @param delay
     */
    Train(String lineName, String lineNumber, String trainName, float latitude, float longitude,
          String scheduledTime, String departureTime, String tripNumber, String delay) {
        this.lineName = lineName;
        this.lineNumber = lineNumber;
        this.trainName = trainName;
        this.latitude = latitude;
        this.longitude = longitude;
        this.scheduledTime = scheduledTime;
        this.departureTime = departureTime;
        this.tripNumber = tripNumber;
        this.delay = delay;
    }

    @Override
    public String getLineNumber() {
        return lineNumber;
    }

    @Override
    public String getLineName() {
        return lineName;
    }

    @Override
    public float getLatitude() {
        return latitude;
    }

    @Override
    public float getLongitude() {
        return longitude;
    }

    @Override
    public String getTrainName() {
        return trainName;
    }

    @Override
    public String getScheduledTime() {
        return scheduledTime;
    }

    @Override
    public String getDepartureTime() {
        return departureTime;
    }

    @Override
    public String getTripNumber(){return tripNumber;}

    @Override
    public String getDelay() {
        return delay;
    }
}
