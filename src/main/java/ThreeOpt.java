
import java.util.Arrays;

public class ThreeOpt extends TourConstructor {
    @Override
    public void improve(int[] route) {
        int[] routePlus = Arrays.copyOf(route, route.length+1);
        routePlus[route.length] = route[0];

        boolean improvement = true;
        while (improvement) {
            improvement = improveHelper(routePlus, route.length);
        }
        System.arraycopy(routePlus, 0, route, 0, route.length);
    }

    private boolean improveHelper(int[] routePlus, int n) {
        boolean improvement = false;
        for (int i = 0; i < n-3; ++i) {
            for (int j = i+1; j < n-2; ++j) {
                for (int k = j+1; k < n-1; ++k) {
                    improvement = checkIfReverseable(routePlus, i, j, k);
                }
            }
        }
        return improvement;
    }

    private boolean checkIfReverseable(int[] routePlus, int i, int j, int k) {
        int reversals = threeOptReverse(routePlus, i, j, k);
        if (threeOptReverseI1J(reversals)) twoOptReverse(routePlus, i+1, j);
        if (threeOptReverseJ1K(reversals)) twoOptReverse(routePlus, j+1, k);
        if (threeOptReverseI1K(reversals)) twoOptReverse(routePlus, i+1, k);
        if (reversals > 0) return true;
        return false;
    }

    private int threeOptReverse(int[] route, int i, int j, int k) {   
        //can be modified so the first improvement returns instead of looking for lowest of all 7 combinations   
        int type = 0;
        double lowest = threeOptReversalHelper(route, i, (i+1), j, (j+1), k, (k+1));// 0
        
        double current = threeOptReversalHelper(route, i, j, (i+1), (j+1), k, (k+1));//1
        if (current < lowest) {lowest = current; type = 1;}
        current = threeOptReversalHelper(route, i, (i+1), j, k, (j+1), (k+1));//2
        if (current < lowest) {lowest = current; type = 2;}
        current = threeOptReversalHelper(route, i, j, (i+1), k, (j+1), (k+1));//3
        if (current < lowest) {lowest = current; type = 3;}
        current = threeOptReversalHelper(route, i, k, (j+1), j, (i+1), (k+1));//4
        if (current < lowest) {lowest = current; type = 4;}
        current = threeOptReversalHelper(route, i, k, (j+1), (i+1), j, (k+1));//5
        if (current < lowest) {lowest = current; type = 5;}
        current = threeOptReversalHelper(route, i, (j+1), k, j, (i+1), (k+1));//6
        if (current < lowest) {lowest = current; type = 6;}
        current = threeOptReversalHelper(route, i, (j+1), k, (i+1), j, (k+1));//7
        if (current < lowest) {lowest = current; type = 7;}

        return type;
    }

    private double threeOptReversalHelper(int[] route, int x1, int x2, int x3, int x4, int x5, int x6) {
        double newDistance = distanceMatrix.getValue(route[x1], route[x2]);
        newDistance += distanceMatrix.getValue(route[x3], route[x4]);
        newDistance += distanceMatrix.getValue(route[x5], route[x6]);
        return newDistance;
    }

    private boolean threeOptReverseI1J(int reversals) {
        return (reversals & 0b001) > 0;
    }

    private boolean threeOptReverseJ1K(int reversals) {
        return (reversals & 0b010) > 0;
    }

    private boolean threeOptReverseI1K(int reversals) {
        return (reversals & 0b100) > 0;
    }

    private void twoOptReverse(int[] route, int i, int j) {
        while (i < j) {
            int temp = route[i];
            route[i] = route[j];
            route[j] = temp;
            i++; j--;
        }
    }
}