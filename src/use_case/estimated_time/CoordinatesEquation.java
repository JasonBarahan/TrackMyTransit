package use_case.estimated_time;

public class CoordinatesEquation {

    // public final double GOTRAINSPEED = 38.88; // 140(km/hr) = 38.88(m/s)

    /**
     * Takes the Longitude and Latitude of two points and solves for distance.
     *
     * @param lat1
     * @param lon1
     * @param lat2
     * @param lon2
     * @return distance
     */
    static double haversineDistanceFormula(double lat1, double lon1,
                                    double lat2, double lon2)
    {
        // The distance between latitudes and longitudes
        double distanceLatitude = Math.toRadians(lat2 - lat1);
        double distanceLongitude = Math.toRadians(lon2 - lon1);

        // Convert the coordinates into radians
        lat1 = Math.toRadians(lat1);
        lat2 = Math.toRadians(lat2);

        // Using Haversine to compute distance between the two
        double hav = Math.pow(Math.sin(distanceLatitude / 2), 2) + Math.pow(Math.sin(distanceLongitude / 2), 2) *
                Math.cos(lat1) * Math.cos(lat2);
        double radians = 6371;
        double sqrHav2 = 2 * Math.asin(Math.sqrt(hav));

        return radians * sqrHav2; // Distance between the two coordinates
    }

    /**
     * Calculates the time using speed and distance.
     *
     * @param distance
     * @param speed
     * @return time
     */
    static double timeFormula(double distance, double speed) {
        return distance / speed;
    }

    // testing purposes
    public static void main(String[] args) {
        double unionStationGOLat = 43.6454; // N
        double unionStationGOLong = 79.3798; // W
        double danforthStationGOLat = 43.6864; // N
        double danforthStationGOLong = 79.3003; // W

        double hav = haversineDistanceFormula(unionStationGOLat, unionStationGOLong,
                danforthStationGOLat, danforthStationGOLong);

        hav = hav * 1000; // convert to miles

        double time = timeFormula(hav, 38.88);

        System.out.println("Haversine function: " + hav);
        System.out.println("Time function: " + time);
    }
}