public class OptimizerFactory {
    //create a singleton which gets returned
    private static OptimizerFactory instance = new OptimizerFactory();

    //prevents OptimizerFactory instances from being created
    private OptimizerFactory() {};

    public static OptimizerFactory getInstance() {
        return instance;
    }

    public TourConstructor get(int N) throws Exception {
        switch(N) {
            case 0:
                return new NoOpt();
            case 1:
                return new NN();
            case 2:
                return new TwoOpt();
            case 3:
                return new ThreeOpt();
            default:
                throw new Exception();

        }
    }
}
