package data_access.API;

public interface TrainApiInterface {
    String API_KEY = System.getenv("API_KEY");

    <T> T retrieveVehicleInfo(String stationID);
}
