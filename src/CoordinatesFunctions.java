// Java program for the haversine formula
public class CoordinatesFunctions {

    // private final double GOTRAINSPEED = 38.88; // 140(km/hr) = 38.88(m/s)

    /**
     * Takes the Longitude and Latitude of two points and solves for distance.
     *
     * @param lat1
     * @param lon1
     * @param lat2
     * @param lon2
     * @return distance
     */
    static double haversineDistance(double lat1, double lon1,
                                    double lat2, double lon2)
    {
        /**
         * The distance between latitudes and longitudes
         */
        double distanceLatitude = Math.toRadians(lat2 - lat1);
        double distanceLongitude = Math.toRadians(lon2 - lon1);

        /**
         * Convert to Radians
         */
        lat1 = Math.toRadians(lat1);
        lat2 = Math.toRadians(lat2);

        /**
         * Using Haversine to compute distance between the two
         */
        double a = Math.pow(Math.sin(distanceLatitude / 2), 2) +
                Math.pow(Math.sin(distanceLongitude / 2), 2) *
                        Math.cos(lat1) *
                        Math.cos(lat2);
        double rad = 6371;
        double c = 2 * Math.asin(Math.sqrt(a));

        return rad * c; // Distance between the two
    }

    /**
     * Calculates the time using speed and distance.
     *
     * @param distance
     * @return time
     */
    static double time(double distance) { // double speed may be changed
        final double GOTRAINSPEED = 38.88;

        return distance / GOTRAINSPEED;
    }
}
