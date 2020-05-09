package dk.kec.bowling.model;

/**
 *
 * This class hold the calculated result of points in a bowling match. There is
 * also a token that links together with the points.
 *
 */
public class Result {
    private String token;
    private int[] points;

    /**
     * Instantiate a bowling Result object.
     *
     * @param token
     *        An identifier that is linked to the points
     * @param points
     *        An arrays of points. Each element represents a frame in a game
     */
    public Result(String token, int[] points) {
        this.token = token;
        this.points = points;
    }

    /**
     * Get the identifier that is linked to the points.
     *
     * @return the token
     */
    public String getToken() {
        return token;
    }

    /**
     * Get the arrays of points. Each element represents a frame in a game.
     *
     * @return the points
     */
    public int[] getPoints() {
        return points;
    }

    /**
     * Get a pretty print of the resulting points.
     *
     * The format is something like this:
     *
     * <pre>
     * |  20 |  33 |  42 |  62 |  80 | 100 | 120 | 139 | 148 |  178  |
     * '-----'-----'-----'-----'-----'-----'-----'-----'-----'-------'
     * </pre>
     */
    @Override
    public String toString() {
        String sums = "|";
        String buttom = "'";
        for (int i = 0; i < getPoints().length; i++) {
            if (i < 9) {
                sums += String.format("%4d |", getPoints()[i]);
                buttom += "-----'";
            } else {
                // 10'th frames needs a little more space.
                sums += String.format("%5d  |", getPoints()[i]);
                buttom += "-------'";
            }
        }

        return sums + "\n" + buttom;
    }
}
