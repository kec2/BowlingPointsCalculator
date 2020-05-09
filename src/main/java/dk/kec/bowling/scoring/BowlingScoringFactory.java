package dk.kec.bowling.scoring;

public class BowlingScoringFactory {
    public enum Scoring {
        TRADITIONAL, WORLD_BOWLING
    }

    /**
     * Don't let anyone instantiate this class.
     */
    private BowlingScoringFactory() {
    }

    /**
     * Get an implementation of bowling scoring calculator.
     *
     * @param scoring
     *        the type of implementation
     * @return An implementation of bowling scoring calculator. Null is returned if
     *         the scoring does not match an implementation
     */
    public static BowlingScoring create(Scoring scoring) {
        switch (scoring) {
        case TRADITIONAL:
            return new TraditionalScoring();
        case WORLD_BOWLING:
            return new WorldBowlingScoring();
        default:
            System.out.println("Warning: Unknown scoring type: " + scoring);
        }
        return null;
    }
}
