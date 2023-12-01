package use_case.estimated_time;

public class CoordinatesFunction {

    private final double GOTRAINSPEED = 38.88; // 140(km/hr) = 38.88(m/s)

    static double haversineDistance(double lat1, double lon1,
                                    double lat2, double lon2)
    {
        double distanceLatitude = Math.toRadians(lat2 - lat1);
        double distanceLongitude = Math.toRadians(lon2 - lon1);

        lat1 = Math.toRadians(lat1);
        lat2 = Math.toRadians(lat2);

        double hav = Math.pow(Math.sin(distanceLatitude / 2), 2) + Math.pow(Math.sin(distanceLongitude / 2), 2) *
                Math.cos(lat1) * Math.cos(lat2);
        double radians = 6371;
        double sqrHav2 = 2 * Math.asin(Math.sqrt(hav));

        return radians * sqrHav2; // Distance between the two coordinates
    }

    static double time(double distance, double speed) {
        return distance / speed;
    }
}