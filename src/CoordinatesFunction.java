// Java program for the haversine formula
public class CoordinatesFunctions {

    private double GOTRAINSPEED = 38.88; // 140(km/hr) = 38.88(m/s)

    static double haversineDistance(double lat1, double lon1,
                                    double lat2, double lon2)
    {
        // distance between latitudes and longitudes
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);

        // convert to radians
        lat1 = Math.toRadians(lat1);
        lat2 = Math.toRadians(lat2);

        // apply formulae
        double a = Math.pow(Math.sin(dLat / 2), 2) +
                Math.pow(Math.sin(dLon / 2), 2) *
                        Math.cos(lat1) *
                        Math.cos(lat2);
        double rad = 6371;
        double c = 2 * Math.asin(Math.sqrt(a));
        return rad * c;
    }

    static double time(double distance, double speed) {
        double time = distance / speed;

        return time;
    }
}
