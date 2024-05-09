public class DistanceMatrix {
    private double[] adjMatrix;
    private int dimension;

    public DistanceMatrix(int dimension) {
        this.dimension = dimension;
        this.adjMatrix = new double[dimension * dimension];
    }

    public void setValue(int row, int col, double value) {
        int index = (row * dimension) + col;
        adjMatrix[index] = value;
    }

    public double getValue(int row, int col) {
        int index = (row * dimension) + col;
        return adjMatrix[index];
    }

    public int getSize() {
        return this.adjMatrix.length;
    }

    public int getDimension() {
        return this.dimension;
    }

    public void fillMatrix(Places places, double earthRadius) {
        Vincenty distanceCalculator = new Vincenty();

        for (int i = 0; i < places.size(); i++) {
            for (int j = i; j < places.size() - 1; j++) {
                int toIndex = (j + 1) % places.size();
                
                Place from = places.get(i);
                Place to = places.get(toIndex);
                double distance = distanceCalculator.between(from, to, earthRadius);
        
                this.setValue(i, toIndex, distance);
                this.setValue(toIndex, i, distance);
            }
        }
    }

    public int findLowestDistance(int currPosition, int[] tour) {
        double lowest = Double.MAX_VALUE;
        int lowestIndex = -1;

        int currVal = tour[currPosition];
        for (int i = currPosition + 1; i < tour.length; i++) {
            double tourValue = this.getValue(currVal, tour[i]);
            if (tourValue < lowest) {
                lowest = tourValue;
                lowestIndex = tour[i];
            } 
        }

        return lowestIndex;
    }
}