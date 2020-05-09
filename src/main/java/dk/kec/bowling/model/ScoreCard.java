package dk.kec.bowling.model;

/**
 * This object represents a bowling score card. It has a collection of points
 * and a token that links them together.
 *
 */
public class ScoreCard {
    private String token;
    private int[][] points;

    /**
     * Instantiate a ScoreCard object.
     *
     * @param token
     *        An identifier that is linked to the points.
     * @param points
     *        An array of frames and each frame is an array on points.
     */
    public ScoreCard(String token, int[][] points) {
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
     * Get an array of frames and each frame is an array on points.
     *
     * @return the frames
     */
    public int[][] getPoints() {
        return points;
    }

    /**
     * Get a pretty print of this score card.
     *
     * The format is something like this depending on the number of frames.
     *
     * <pre>
     * |__1__|__2__|__3__|__4__|__5__|__6__|__7__|__8__|__9__|__10___|
     * |   |X|  8|/|  3|6|   |X|  9|/|  8|/|   |X|  9|/|  9|0|  X|X|X|
     * </pre>
     */
    @Override
    public String toString() {
        String frameNo = "|";
        String framePoints = "|";
        for (int i = 0; i < getPoints().length; i++) {
            int[] frame = getPoints()[i];

            // add up to 10 frames.
            if (i < 9) {
                frameNo += String.format("__%d__|", i + 1);
            } else if (i == 9) {
                frameNo += String.format("__%d___|", i + 1);
            }

            if (i < 9) {
                // this only works for frame 1-9
                if (frame[0] == 10) {
                    // strike
                    framePoints += "   |X|";
                } else if (frame[0] + frame[1] == 10) {
                    // spare
                    framePoints += String.format("  %d|/|", frame[0]);
                } else {
                    // miss
                    framePoints += String.format("  %d|%d|", frame[0], frame[1]);
                }
            } else {
                // frame 10 is special because there is a possible extra shot.

                if (i == 9) {
                    framePoints += "  ";
                }

                if (frame[0] == 10) {
                    // strike
                    framePoints += "X|";
                    if (frame[1] == 10) {
                        // strike
                        framePoints += "X|";
                    }
                } else if (frame[0] + frame[1] == 10) {
                    // spare
                    framePoints += String.format("%d|/|", frame[0]);
                } else {
                    // miss
                    framePoints += String.format("%d|%d| |", frame[0], frame[1]);
                }
            }
        }
        return frameNo + "\n" + framePoints;
    }
}
