package polynomialregression;

/**
 * Created by Raymond Sutrisno on 5/10/2016.
 * This class procvides some gaussian utility for the whole project
 */

import java.util.Random;

public class Utilities {

    static Random rand = new Random();

    private Utilities() {
        //Do not let anyone instantiate
    }

    /**
     * Returns a much more clustered gaussian double by powering by 3
     * @return
     */
    public static double sharpGauss() {
        return sharpGauss(3);
    }

    /**
     * Returns a sharper gaussian distribution by powering it by p
     * @param p p is an integer of which the gaussian number will be powered by
     * @return
     */
    public static double sharpGauss(int p) {
        double d = Math.pow(Gauss(), p);
        return d;
        //return rand.nextBoolean() ? d : -d;
    }

    /**
     * Returns a gaussian distributed double eliminating values outside of range -1.0 to 1.0 inclusive
     * @return
     */
    public static double Gauss() {
        double d = rand.nextGaussian();
        while (d < -1.0 || d > 1.0) {
            d = rand.nextGaussian();
        }
        return d;
    }

}