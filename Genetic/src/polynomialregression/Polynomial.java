package polynomialregression;

/**
 * Created by Raymond Sutrisno on 5/5/2016.
 *
 * This class represents a polynomial object
 */

public class Polynomial implements Comparable<Polynomial> {

    //Field which stores coefficients
    private double[] coefficients;

    //Raw Fitness = Sum of Least Squares to data points
    private double rawFitness;

    //Basic Constructor
    public Polynomial() {
        coefficients = new double[]{5.0}; //placeholder, never used
    }

    /**
     * This constructor sets this object's coefficients to coeff values
     * @param coeff double array of coefficients to be copied to this.coefficients
     */
    public Polynomial(double[] coeff) {
        coefficients = new double[coeff.length];
        //System.arraycopy(coeff, 0, coefficients, 0, coefficients.length);
        for (int x = 0; x < coefficients.length; x++) {
            coefficients[x] = coeff[x];
        }
    }

    /**
     * Computes and returns a value based on this polynomial's coefficients
     * @param xval x parameter to be used in computing the polynomial's output
     * @return a double calculated from this polynomial
     */
    public double compute(double xval) {
        int pow;
        double ans = 0;
        for (int i = 0; i < coefficients.length; i++) {
            pow = coefficients.length - i - 1;
            ans += coefficients[i] * Math.pow(xval, pow);
        }
        return ans;
    }

    /**
     * Returns order of polynomial
     * @return returns order of polynomial
     */
    public int order() {
        return coefficients.length - 1;
    }

    /**
     * Returns a pointer to this object's pointers, kind of a bad practice
     * @return returns this object's coefficients
     */
    public double[] getCoefficients() {
        return coefficients;
    }

    /**
     * Returns this polynomial in String form
     * NX^3 for exmaple
     * @return returns string form of this polynomial
     */
    @Override
    public String toString() {
        String ret = "";
        int order = order();
        for (int x = 0; x < coefficients.length - 1; x++) {
            //ret = ret + String.format("%.3f",coefficients[x])+"x^"+(order-x)+" + ";
            //String num = coefficients[x]+"";
            String num = String.format("%.3f", coefficients[x]);
            ret = ret + num + "x^" + (order - x) + " + ";
        }
        ret = ret + String.format("%.3f", coefficients[coefficients.length - 1]);
        //ret = ret + coefficients[coefficients.length-1];

        return "y = " + ret;
    }

    /**
     * Settersr for Raw Fitness
     * @param d value for RawFitness to be set to
     */
    public void setRawFitness(double d) {
        this.rawFitness = d;
    }

    /**
     * Getter for Raw Fitness
     * @return returns rawfitness value
     */
    public double getRawFitness() {
        return rawFitness;
    }

    /**
     * Comparable method for Collections.sort
     * @param o Other polynomial to be compared to
     * @return an integer between -1 and 1 inclusive where 0 indicates equal fitness, -1 indicates this object possesses
     * a lower least squares some, and vice versa.
     */
    @Override
    public int compareTo(Polynomial o) {
        //return Integer.compare(this.getRawFitness(), o.getRawFitness());
        if (this.rawFitness == o.getRawFitness()) {
            return 0;
        } else if (this.rawFitness < o.getRawFitness()) {
            return -1;
        } else {
            return 1;
        }
    }
}
