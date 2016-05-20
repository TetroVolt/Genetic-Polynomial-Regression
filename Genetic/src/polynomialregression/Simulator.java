package polynomialregression;

/**
 * Created by Raymond Sutrisno on 5/5/2016.
 * This class is the actual brains fo the whole program, the class that runs everything.
 */

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;

public class Simulator {

    //Data to be compared to
    static PointD[] data;

    //File Scanner
    static Scanner scn;

    //Debug boolean
    static boolean debug = false;

    //ArrayList Containing population of Polynomials
    static ArrayList<Polynomial> Population;

    //int fields representing minimum/genocide population and max population for genetic drift
    static int populationSize = 20, maxPop = 5000;

    //Random object
    static Random rand = new Random();

    //Main method
    public static void main(String[] args) {
        if (debug) {
            debugLog("Debug mode ON.");
        }


        try {

            scn = new Scanner(new File("values.csv")); //placeholder name
            if (debug) {
                debugLog("Scanner set.");
            }
        } catch (Exception e) {
            //allow for file piping
            scn = new Scanner(System.in);
        }

        //Set PointD data array with values from scanenr
        data = fillData();

        if (debug) {
            debugLog("Printing Points:");

            //prints points
            for (PointD pd : data)
                System.out.println("\t" + pd);

            debugLog("END of Printing Points.");
        }

        //Initiates Population
        initiatePopulation(0, 5, 4);

        //Runs 1000 generations
        for (int x = 0; x < 1000; x++) {
            System.out.println("\nGeneration: " + (1 + x));
            runAGeneration();
            //waitTime(50);
        }


    }

    /**
     * @param coeffMin Minimum value for coefficients inclusive
     * @param coeffMax Maximum value for coefficients inclusive
     * @param maxOrder Max order of equation, for ex: order = 4 -> highest x power is x^4
     * @Precondition coeffMax > coeffMin
     */
    public static void initiatePopulation(int coeffMin, int coeffMax, int maxOrder) {
        Population = new ArrayList();

        if (coeffMax <= coeffMin) {
            throw new IllegalStateException("Error: CoeffMax = " + coeffMax + ", is not greater than CoeffMin = " + coeffMin);
        }

        for (int x = 0; x < populationSize; x++) {
            double[] coeff = new double[maxOrder + 1];

            for (int i = 0; i < coeff.length; i++) {
                double val = (rand.nextDouble() - 0.5) * 2 * (rand.nextInt(coeffMax - coeffMin) + coeffMin);
                coeff[i] = val + 0.0D;
            }
            Population.add(new Polynomial(coeff));
        }

        if (debug) {
            debugLog("Printing out rand Polynomials");
            printPopulation();
        }

        evaluateFitnesses();
    }


    /**
     * This method runs a generation. It breeds then genocides.
     */
    public static void runAGeneration() {
        //Breeds Population until max population size for genetic drift
        breedAll();

        //Genocides Population by fitness
        genocide();

        System.out.println("\nPopulation: ");

        //prints population
        printPopulation();
    }


    /**
     * This method breeds the population of polynomials until it reaches maximum size
     */
    public static void breedAll() {
        int a, b; //Pointers to select parents

        //While loop to ensure full carrying capacity of population
        while (Population.size() <= maxPop) {
            //Sorts by Fitness level first
            sortByFitlvl();

            //Selects Two Random Parents
            a = (int) (Math.abs(Utilities.sharpGauss(4)) * Population.size());
            b = (int) (Math.abs(Utilities.sharpGauss(4)) * Population.size());
            //System.out.println(a+"\t"+b+"\t"+Population.size());

            // Between 1-2 children
            int children = rand.nextInt(2)+1;
            for (int i = 0; i < children; i++) {
                Population.add(Breeder.breed(Population.get(a), Population.get(b), 0.1));
            }

            //sortByFitlvl();
        }

        if (debug)
            printPopulation(true);
    }


    /**
     * Genocides Population by fitness
     */
    public static void genocide() {
        //Assigns fitness levels
        evaluateFitnesses();

        //Sorts by fitness
        sortByFitlvl();

        //Simple genocide, kills off all until population size.
        for (int i = Population.size() - 1; i >= populationSize; i--) {
            Population.remove(i);
        }

        /*
        //Different kind of Genocide, selects organisms based on gaussian distribution

        while(Population.size() > populationSize){
            int randomOrganism = (int)(Population.size() - (Population.size()*Utilities.sharpGauss(2)));
            Population.remove(randomOrganism);
        }
        */
    }

    /**
     * @param p      Polynomial to be tested
     * @param points Set of Data points for polynomial to be compared to
     * @return Returns the sum of the differences at points, the lower the value, the higher the fitness
     */
    public static double getRawFitness(Polynomial p, PointD[] points) {
        double sum = 0;
        for (PointD pd : points) {
            sum += Math.pow((p.compute(pd.getX()) - pd.getY()), 2);
        }
        return sum;
    }

    /**
     * @param rawFit raw Fitness, raw Fitness >= 0
     * @return value between 0 and 1 inclusive, higher number = higher fitness
     */
    public static double getAdjustedFitness(double rawFit) {
        return 1.0 / (1.0 + rawFit);
    }


    //Sets the fitness levels for population
    public static void evaluateFitnesses() {
        for (Polynomial p : Population) {
            p.setRawFitness(getRawFitness(p, data));
            if (debug) {
                printPopulation(true);
            }
        }
    }

    /**
     * Sorts the Population by FitnessLevel
     */
    public static void sortByFitlvl() {
        Collections.sort(Population);
        if (debug) {
            debugLog("\nSorted: ");
            printPopulation();
        }
    }

    /**
     * Prints population without fitness
     */
    public static void printPopulation() {
        printPopulation(false);
    }

    /**
     * Prints Population
     * @param printFitness boolean whether or not to print fitness
     */
    public static void printPopulation(boolean printFitness) {
        for (Polynomial p : Population) {
            if (printFitness)
                System.out.println(p);
            else
                debugLog(p.toString() + "\t\t , Fitness = " + p.getRawFitness() + "\t\t" + getAdjustedFitness(p.getRawFitness()));
        }
    }

    /**
     * Method reading in data points
     * @return
     */
    public static PointD[] fillData() {
        ArrayList<PointD> vals = new ArrayList();
        while (scn.hasNextLine()) {
            String[] xandy = scn.nextLine().split("\\s*,\\s*");
            vals.add(new PointD(Double.parseDouble(xandy[0]), Double.parseDouble(xandy[1])));
        }
        PointD[] vals2 = new PointD[vals.size()];
        for (int x = 0; x < vals.size(); x++) {
            vals2[x] = vals.get(x);
        }
        return vals2;
    }

    //Debug printer
    public static void debugLog(String s) {
        System.out.println("Log: " + s);
    }

    //WaitMethod to slow output
    public static void waitTime(long s) {
        long l = System.currentTimeMillis() + s;
        while (System.currentTimeMillis() < l) {

        }
    }


    public static void waitTime() {
        waitTime(1000L);
    }
}
