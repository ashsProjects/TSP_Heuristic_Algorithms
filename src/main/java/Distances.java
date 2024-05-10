import java.util.LinkedHashMap;

public class Distances extends LinkedHashMap<String, Double> {  
    private double totDis = -1; 
    public double total() {
        if (totDis != -1) return totDis;

        double runningSum = 0L;
        for (Double l: this.values()) {
            runningSum += l;
        }
        return runningSum;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (String key: this.keySet()) {
            sb.append(key);
        }
        sb.append("_______________________________________________________________\n");
        sb.append("Total distance: " + String.format("%.2f", total()));

        return sb.toString();
    }
}
