package entity;
public class Train implements TrainInterface{

    private final String name;
    private final String trainNumber;  //trainNumber = tripNumber
    private final String parentLine;
    // Will have to decide if we need to have parentLine for a certain train
    private final float latitude;
    private final float longitude;
    private final String scheduledTime;
    private final String delay;

    /**
     * Requirement: Train name and trainNumber exist.
     * Note: trainNumber is tripNumber in GOtrainApiClass
     * @param name
     * @param trainNumber
     * @param parentLine
     * @param latitude
     * @param longitude
     * @param scheduledTime
     * @param delay
     */
    Train(String name, String trainNumber, String parentLine, float latitude, float longitude, String scheduledTime, String delay) {
        this.name = name;
        this.trainNumber = trainNumber;
        this.parentLine = parentLine;
        this.latitude = latitude;
        this.longitude = longitude;
        this.scheduledTime = scheduledTime;
        this.delay = delay;
    }

    @Override
    public String getNumber() {
        return trainNumber;
    }

    @Override
    public String getName() {
        return name;
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
    public String getParentLine() {
        return parentLine;
    }
    // We don't really need parentLine for a train?

    @Override
    public String getScheduledTime() {
        return scheduledTime;
    }

    @Override
    public String getDelay() {
        return delay;
    }
}
