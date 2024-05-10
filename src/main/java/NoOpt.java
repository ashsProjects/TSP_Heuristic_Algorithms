public class NoOpt extends TourConstructor {
    @Override
    public Places construct(Places places, Double radius, boolean allStarting, boolean opt) {
        return places;
    }

    @Override
    public String toString() {
        return "No Optimizations";
    }
}