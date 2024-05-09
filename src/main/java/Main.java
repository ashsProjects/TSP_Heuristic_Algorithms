import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

public class Main {
    private static Places places = new Places();
    private static double radius = 3959.00;

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        while (true) {
            places.clear();
            System.out.print("\nChoose how you want to read in places: \n"
                + "-Enter 1 to read a list of places with coordinates\n"
                + "-Enter 2 to read in an adjacency matrix with nodes and edges\n"
                + "-Enter q to quit the program\n"
                + ">>> ");
            String readChoice = scan.nextLine();

            if (readChoice.equals("1")) {
                System.out.print("Enter 1 to run from existing file\n"+ "Enter 2 to generate a new file then run\n"+ ">>> ");
                String generateChoice = scan.nextLine();

                if (generateChoice.equals("2")) generatePlaces(scan);
                else if (generateChoice.equals("1")) runPlaces(scan, null);
                else {
                    System.out.println("Invalid option!");
                    continue;
                }
            } else if (readChoice.equals("2")) {
                System.out.print("Enter 1 to run from existing file\n"+ "Enter 2 to generate a new file then run\n"+ ">>> ");
                String generateChoice = scan.nextLine();

                if (generateChoice.equals("2")) generateNodes(scan);
                else if (generateChoice.equals("1")) runNodes(scan, null);
                else {
                    System.out.println("Invalid option!");
                    continue;
                }
            } else if (readChoice.equals("q")) {
                System.out.println("Exiting!");
                break;
            } else {
                System.out.println("Invalid option! Please enter 1, 2, or q");
                continue;
            }
        }

        scan.close();
    }

    private static void runNodes(Scanner scan, String fileName) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'runNodes'");
    }

    private static void generateNodes(Scanner scan) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'generateNodes'");
    }

    private static void runPlaces(Scanner scan, String fileName) {
        if (fileName == null) {
            System.out.print("Enter the name of the file to read from\n>>> ");
            fileName = scan.nextLine();
        }

        try (Scanner fileScan = new Scanner(new File(fileName))) {
            int i = 1;
            while (fileScan.hasNextLine()) {
                String[] data = fileScan.nextLine().split(",");

                Place place = null;
                if (data.length == 2) place = new Place(data[0].trim(), data[1].trim());
                else if (data.length == 3) place = new Place(data[0], data[1].trim(), data[2].trim());

                checkCoords(place, i);
                places.add(place);
                i++;
            }
            approximate(scan);
        } catch (FileNotFoundException fe) {
            System.out.println("File not found. Exiting!");
            System.exit(1);
        } catch (NumberFormatException nfe) {
            System.out.println("Invalid lat/lon found in file! Make sure the lat and lon are valid numbers");
            System.exit(1);
        } catch (ArrayIndexOutOfBoundsException ae) {
            System.out.println("Invalid input found in file! Make sure the entries are in the format 'lat, lon'");
            System.exit(1);
        } catch (Exception e) {
            System.out.println("Coordinates out of bounds on line " + e.getMessage());
        }
    }

    private static void generatePlaces(Scanner scan) {
        int numPlaces = 0;
        long randSeed = -1L;
        
        while (true) {
            try {
                System.out.print("Enter the number of places to generate\n>>> ");
                numPlaces = Integer.parseInt(scan.nextLine().trim());
                break;
            } catch (NumberFormatException nfe) {
                System.out.println("Invalid input! Please enter a valid number");
                continue;
            }
        }

        try {
            System.out.print("Enter the seed to use or press enter for none\n>>> ");
            randSeed = Long.parseLong(scan.nextLine().trim());
        } catch (NumberFormatException nfe) {
            System.out.println("Using no random seed");
        }

        try {
            System.out.print("Enter the radius to use for the calculation or press enter for default of 3959\n>>> ");
            radius = Double.parseDouble(scan.nextLine().trim());
        } catch (NumberFormatException nfe) {
            System.out.println("Using default earth radius in miles");
        }

        Random rand = randSeed == -1 ? new Random(randSeed) : new Random();

        String s = "";
        try (FileWriter writer = new FileWriter(new File("places.txt"), false)) {
            for (int i = 1; i <= numPlaces; i++) {
                double lat = rand.nextInt(-90, 90) + rand.nextDouble();
                double lon = rand.nextInt(-180, 180) + rand.nextDouble();
                s += "place" + i + "," + String.format("%.3f", lat) + "," + String.format("%.3f", lon) + "\n";
            }
            writer.write(s);
        } catch (IOException ioe) {
            System.out.println("There was an error when creating the file! Exiting");
            System.exit(1);
        }

        System.out.println("Successfully created input file with " + numPlaces + " places!");
        runPlaces(scan, "places.txt");
    }

    private static void checkCoords(Place place, int i) throws Exception {
        if (place.latRadians() < (-0.5 * Math.PI) || place.latRadians() > (0.5 * Math.PI))
            throw new Exception(String.valueOf(i));
        if (place.lonRadians() < (-1.0 * Math.PI) || place.lonRadians() > (Math.PI))
            throw new Exception(String.valueOf(i));
    }
    
    private static void approximate(Scanner scan) throws Exception {
        OptimizerFactory factory = OptimizerFactory.getInstance();
        TourConstructor tourConstructor;

        System.out.print("Which algorithm do you want to use?\n"
            + "\t0 for the original optimized list\n"
            + "\t1 for Nearest Neighbor with only the starting point\n"
            + "\t2 for Nearest Neighbor with only all starting points\n"
            + "\t3 for only 2-Opt\n"
            + "\t4 for Nearest Neighbor and 2-Opt\n"
            + "\t5 for only 3-Opt\n"
            + "\t6 for Nearest Neighbor and 3-Opt\n"
            + "\t7 for a bechmark of all\n"
            + "\tq to exit\n");
        
        while (true) {
            System.out.print(">>> ");
            switch (scan.nextLine()) {
                case "0":
                    tourConstructor = factory.get(0);
                    callAlgorithms(tourConstructor, false, false, true);
                    break;
                case "1":
                    tourConstructor = factory.get(1);
                    callAlgorithms(tourConstructor, false, true, true);                
                    break;
                case "2":
                    tourConstructor = factory.get(1);
                    callAlgorithms(tourConstructor, true, true, true);                  
                    break;
                case "3":
                    tourConstructor = factory.get(2);
                    callAlgorithms(tourConstructor, true, false, true);                  
                    break;
                case "4":
                    tourConstructor = factory.get(2);
                    callAlgorithms(tourConstructor, true, true, true);                  
                    break;
                case "5":
                    tourConstructor = factory.get(3);
                    callAlgorithms(tourConstructor, true, false, true);                  
                    break;
                case "6":
                    tourConstructor = factory.get(3);
                    callAlgorithms(tourConstructor, true, true, true);                  
                    break;
                case "7":
                    runAll();
                    break;
                case "q":
                    return;
                default:
                    System.out.println("Invalid option! Try again");
                    break;
            }
        }
    }

    private static String callAlgorithms(TourConstructor tourConstructor, boolean allStarts, boolean yesNN, boolean writeToFile) {
        Vincenty vincenty = new Vincenty();
        Places optPlaces;
        Distances distances = new Distances();
        final double billion = 1_000_000_000.00;
        long startTime = 0L;
        double totalTime = 0L;

        startTime = System.nanoTime();
        optPlaces = tourConstructor.construct(places, radius, allStarts, yesNN);
        totalTime = (System.nanoTime() - startTime) / billion;

        for (int i = 0; i < optPlaces.size(); i++) {
            Place from = optPlaces.get(i);
            Place to = optPlaces.get((i + 1) % optPlaces.size());
            double distance = vincenty.between(from, to, radius);

            StringBuilder sb = new StringBuilder();
            sb.append("Distance from (" + from.toString() + ") to (" + to.toString() + ")");

            distances.put(sb.toString(), distance);
        }

        if (writeToFile) {
            try (FileWriter writer = new FileWriter(new File("output.txt"), false)) {
                writer.write(distances.toString());
            } catch (IOException ioe) {
                System.out.println("There was an error when writing the output! Exiting");
                System.exit(1);
            }

            System.out.println("Time elapsed: " + totalTime + "s");
            System.out.println("Total distance: " + String.format("%.3f", distances.total()));
            System.out.println("List of places written to ./output.txt");
        }

        return String.format("%.4fs:%,.2f", totalTime, distances.total());
    }

    private static void runAll() throws Exception {
        OptimizerFactory factory = OptimizerFactory.getInstance();

        String[][] data = new String[7][3];
        data[0][0] = "No opt";
        data[1][0] = "NN 1 start"; data[2][0] = "NN all start";
        data[3][0] = "2opt no NN"; data[4][0] = "2opt + NN";
        data[5][0] = "3opt no NN"; data[6][0] = "3opt + NN";
        
        String[] metadata = new String[2];
        metadata = callAlgorithms(factory.get(0), false, false, false).split(":");
        data[0][1] = metadata[0]; data[0][2] = metadata[1];
        metadata = callAlgorithms(factory.get(1), false, true, false).split(":");
        data[1][1] = metadata[0]; data[1][2] = metadata[1];
        metadata = callAlgorithms(factory.get(1), true, true, false).split(":");
        data[2][1] = metadata[0]; data[2][2] = metadata[1];
        metadata = callAlgorithms(factory.get(2), true, false, false).split(":");
        data[3][1] = metadata[0]; data[3][2] = metadata[1];
        metadata = callAlgorithms(factory.get(2), true, true, false).split(":");
        data[4][1] = metadata[0]; data[4][2] = metadata[1];
        metadata = callAlgorithms(factory.get(3), true, false, false).split(":");
        data[5][1] = metadata[0]; data[5][2] = metadata[1];
        metadata = callAlgorithms(factory.get(3), true, true, false).split(":");
        data[6][1] = metadata[0]; data[6][2] = metadata[1];

        printTable(data);
    }

    public static void printTable(String[][] array) {
        System.out.printf("+----------------------------------------------------------+%n");
        System.out.printf("|                      Benchmark Results                   |%n");
        System.out.printf("+----------------------------------------------------------+%n");

        System.out.printf("| %-15s | %-15s | %-20s |%n", "Algorithm", "Time", "Total Distance");
        System.out.printf("+----------------------------------------------------------+%n");

        System.out.printf("| %-15s | %-15s | %-20s |%n", array[0][0], array[0][1],  array[0][2]);
        System.out.printf("| %-15s | %-15s | %-20s |%n", array[1][0], array[1][1],  array[1][2]);
        System.out.printf("| %-15s | %-15s | %-20s |%n", array[2][0], array[2][1],  array[2][2]);
        System.out.printf("| %-15s | %-15s | %-20s |%n", array[3][0], array[3][1],  array[3][2]);
        System.out.printf("| %-15s | %-15s | %-20s |%n", array[4][0], array[4][1],  array[4][2]);
        System.out.printf("| %-15s | %-15s | %-20s |%n", array[5][0], array[5][1],  array[5][2]);
        System.out.printf("| %-15s | %-15s | %-20s |%n", array[6][0], array[6][1],  array[6][2]);
        System.out.printf("+----------------------------------------------------------+%n");

    }
}