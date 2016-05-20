/**
 * Created by Raymond Sutrisno on 5/5/2016.
 *
 * This is a simple test class to test the functionalities of certain project classes
 */

import polynomialregression.Utilities;

import java.util.*;

public class Test {

    static Random rand = new Random();

    public static void main(String[] args) {
        computeFunct();
        //gaussianTest();
        /*
        for (int i = 0; i<10; i++){
            System.out.println(Utilities.sharpGauss(8));
        }
        */
    }

    /**
     * Tests the ranges of gaussian numbers
     */
    public static void gaussianTest() {
        int[] ranges = new int[10];

        double d = 0;
        for (int x = 0; x < 20; x++) {
            d = Math.abs(Utilities.Gauss() * 100);
            System.out.println(d);
            ranges[(int) (d / 10)]++;
        }

        for (int i = 0; i < 10; i++) {
            System.out.println((i + 1) + "\t" + ranges[i]);
        }
    }

    /**
     * Generates a random clustered set of data points from afuction(double x) by passing in x values
     * and multiplying them by a sharp gaussian number
     */
    public static void computeFunct() {
        double xval = 0;
        for (int x = 1; x < 100; x++) {
            //System.out.println(x);
            xval = x;

            int t = rand.nextInt(10) + 1;
            for (int i = 0; i < t; i++) {
                double d = Math.random() * Math.random();
                xval += rand.nextBoolean() ? d : -d;
                //System.out.printf("%.3f , %.3f\n", xval, afunction(xval));
                System.out.println(xval + "  ,  " + afunction(xval));
            }
        }
    }

    /**
     * returns f(x) where f is some polynomial function
     * @param x function parameter
     * @return
     */
    public static double afunction(double x) {
        //Equation x^3 + x^2
        double ans = (0.12819 * Math.pow(x, 3) - 0.11234 * Math.pow(x, 2) + 4.1245);
        double ran = Utilities.sharpGauss();
        //System.out.println(ran);
        ans += ran;
        return ans;
    }
}
