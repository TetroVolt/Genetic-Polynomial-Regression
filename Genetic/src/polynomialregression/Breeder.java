package polynomialregression;

/**
 * Created by Raymond Sutrisno on 5/5/2016.
 *
 * This class is a static breeder and returns new polynomials
 */

import java.util.Random;

public class Breeder {
    static Random rand = new Random();


    private Breeder() {
        //Privatize so noone can instantiate
    }

    /**
     * @param a            Polynomial Parent a
     * @param b            Polynomial Parent b
     * @param mutationRate
     * @return
     */
    public static Polynomial breed(Polynomial a, Polynomial b, double mutationRate) {
        if (a.order() > b.order()) {
            return breedAB(a, b, mutationRate);
        } else {
            return breedAB(b, a, mutationRate);
        }
    }

    /**
     * @param a            Polynomial a
     * @param b            Polynomial b
     * @param mutationRate Some mutation rate where  0 =< rate <= 1
     * @return
     * @precondition Order of A >= Order of B
     */
    private static Polynomial breedAB(Polynomial a, Polynomial b, double mutationRate) {
        //Breed Polynomials

        //Retrieve Alleles
        double[] allele_A = a.getCoefficients();
        double[] allele_B = b.getCoefficients();
        double[] allele_C = new double[allele_A.length];


        for (int i = 0; i < allele_C.length; i++) {
            double c;


            //Random Allele selection, a or b
            if (rand.nextBoolean()) {
                c = allele_A[i];
            } else {
                c = allele_B[i];
            }


            /*
            //Weighted allele selection
            c = Math.random();
            c = (c * allele_A[i]) + ((1 - c) * allele_B[i]);
            */

            /*
            //average allele selection
            c = (allele_A[i]+allele_B[i])/2.0;
            */

            //Determines if this allele will mutate
            boolean mut = Math.random() <= mutationRate;


            if (mut) {
                double mod = Utilities.sharpGauss(1) + 1;
                c *= (Math.random() < mutationRate) ? mod : -mod;

                mod = Utilities.Gauss();
                c += rand.nextBoolean() ? mod : -mod;

            }

            allele_C[i] = c;
        }

        //System.out.println(Arrays.toString(allele_C));
        return new Polynomial(allele_C);
    }


    /**
     * @param a Polynomial a
     * @param b Polynomial b
     * @return returns a child polynomial using a Math.random() mutation rate% mutation rate
     * @precondition a and b are of equal orders
     */
    public static Polynomial breed(Polynomial a, Polynomial b) {
        return breed(a, b, Math.random());
    }

}
