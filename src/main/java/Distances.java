import java.util.Map;
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

        for (Map.Entry<String, Double> entry: this.entrySet()) {
            sb.append(entry.getKey() + ": " + String.format("%.2f", entry.getValue()) + "\n");
        }
        sb.append("__________________________________________________________________________\n");
        sb.append("Total distance: " + String.format("%.2f", total()));

        return sb.toString();
    }
}
