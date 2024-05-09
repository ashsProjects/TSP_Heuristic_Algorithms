public class NoOpt extends TourConstructor {
    @Override
    public Places construct(Places places, Double radius, boolean allStarting, boolean opt) {
        return places;
    }
}