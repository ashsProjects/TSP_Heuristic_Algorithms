import java.util.LinkedHashMap;

public class Place extends LinkedHashMap<String, String> {
    public Place(String latitude, String longitude) {
        this.put("name", "");
        this.put("latitude", latitude);
        this.put("longitude", longitude);
    }

    public Place(String name, String latitude, String longitude) {
        this.put("name", name);
        this.put("latitude", latitude);
        this.put("longitude", longitude);
    }

    public double latRadians() {
        double latInRad = Double.parseDouble(this.get("latitude"));
        return Math.toRadians(latInRad);
    }
    public double lonRadians() {
        double lonInRad = Double.parseDouble(this.get("longitude"));
        return Math.toRadians(lonInRad);
    }

    @Override
    public String toString() {
        String str = "";
        
        if (this.get("name").equals(""))
            str = this.get("latitude") + "," + this.get("longitude");
        else 
            str = this.get("name") + "," + this.get("latitude") + "," + this.get("longitude");
        
        return str;
    }
}