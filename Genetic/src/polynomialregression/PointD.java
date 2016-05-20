package polynomialregression;

/**
 * This class simply holds a double coordinate
 * Created by Raymond Sutrisno on 5/5/2016.
 */
public class PointD {
    //Point fields
    private double x, y;

    /**
     * Constructor initializing this object's x and y positions
     * @param x value for this.x to be set to
     * @param y value for this.y to be set to
     */
    public PointD(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Getter x value
     * @return x value of point
     */
    public double getX() {
        return x;
    }

    /**
     * Getter of Y value
     * @return
     */
    public double getY() {
        return y;
    }

    /**
     * Setter for X and Y Values
     * @param x value for this.x to be set to
     * @param y value for this.y to be set to
     */
    public void setPos(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Simple debugging string
     * @return returns this coordinate's x and y value in string form
     */
    @Override
    public String toString() {
        return x+", "+y;
    }

}
