public class Vincenty {
    public double between(Place coord1, Place coord2, double radius) {
        double coord1Lat = coord1.latRadians();
        double coord1Lon = coord1.lonRadians();
        double coord2Lat = coord2.latRadians();
        double coord2Lon = coord2.lonRadians();

        double numerator1 = Math.pow((Math.cos(coord2Lat) * Math.sin(coord1Lon - coord2Lon)), 2);
        double numerator21 = Math.cos(coord1Lat) * Math.sin(coord2Lat);
        double numerator22 = Math.sin(coord1Lat) * Math.cos(coord2Lat) * Math.cos(coord1Lon - coord2Lon);
        double numerator2 = Math.pow((numerator21 - numerator22), 2);
        double numerator = Math.sqrt(numerator1 + numerator2);

        double denominator1 = Math.sin(coord1Lat) * Math.sin(coord2Lat);
        double denominator2 = Math.cos(coord1Lat) * Math.cos(coord2Lat) * Math.cos(coord1Lon - coord2Lon);
        double denominator = denominator1 + denominator2;

        double angle = Math.atan2(numerator, denominator);

        double distance = radius * angle;

        return distance;
    }
}

    

