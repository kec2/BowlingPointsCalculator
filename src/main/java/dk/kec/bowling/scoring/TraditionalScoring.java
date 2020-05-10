package dk.kec.bowling.scoring;

import dk.kec.bowling.model.Result;
import dk.kec.bowling.model.ScoreCard;

/**
 *
 * In an open frame, a bowler simply gets credit for the number of pins knocked
 * down. In the case of a spare, a slash mark is recorded in a small square in
 * the upper right-hand corner of that frame on the score sheet, and no score is
 * entered until the first ball of the next frame is rolled.
 * <p>
 *
 * Then credit is given for 10 plus the number of pins knocked down with that
 * next ball. For example, a player rolls a spare in the first frame; with the
 * first ball of the second frame, the player knocks down seven pins. The first
 * frame, then, gets 17 points. If two of the remaining three pins get knocked
 * down, 9 pins are added, for a total of 26 in the second frame.
 * <p>
 *
 * If a bowler gets a strike, it is recorded with an X in the small square, the
 * score being 10 plus the total number of pins knocked down in the next two
 * rolls. Thus, the bowler who rolls three strikes in a row in the first three
 * frames gets credit for 30 points in the first frame.
 * <p>
 *
 * Bowling's perfect score, a 300 game, represents 12 strikes in a row--a total
 * of 120 pins knocked down. Why 12 strikes, instead of 10? Because, if a bowler
 * gets a strike in the last frame, the score for that frame can't be recorded
 * before rolling twice more. Similarly, if a bowler rolls a spare in the last
 * frame, one more roll is required before the final score can be tallied.
 *
 */
public class TraditionalScoring implements BowlingScoring {

    @Override
    public Result calculate(ScoreCard scoreCard) {
        validateScoreCard(scoreCard);

        int[][] frames = scoreCard.getPoints();
        // No more than 10 results
        int[] results = new int[Math.min(frames.length, 10)];

        int sum = 0;
        for (int i = 0; i < frames.length && i < 10; i++) {
            int[] frame = frames[i];

            sum += frame[0];
            sum += frame[1];
            if (frame[0] == 10) {
                // STRIKE
                // look two ahead if strike (strike = 10)
                // look one ahead if not strike (spare = 10, otherwise just the pins)
                if (i + 1 < frames.length) {
                    sum += frames[i + 1][0];
                    sum += frames[i + 1][1];

                    if (frames[i + 1][0] == 10) {
                        // next frame is a strike
                        // look for one more frame
                        if (i + 2 < frames.length) {
                            sum += frames[i + 2][0];
                        }
                    }
                }

                results[i] = sum;
            } else if (frame[0] + frame[1] == 10) {
                // SPARE
                // look one ahead
                if (i < frames.length - 1) {
                    sum += frames[i + 1][0];
                }

                results[i] = sum;
            } else {
                // MISS
                // just add sum where is no bonus for a "miss".
                results[i] = sum;
            }
        }

        return new Result(scoreCard.getToken(), results);
    }

    private void validateScoreCard(ScoreCard scoreCard) {
        if (scoreCard == null) {
            throw new IllegalArgumentException("Null is not a legal argument.");
        }

        if (scoreCard.getPoints() == null) {
            throw new IllegalArgumentException("Points in the score card can't be null. Its not a legal argument.");
        }

        if (scoreCard.getPoints() != null) {
            int points = scoreCard.getPoints().length;
            if (points > 11) {
                throw new IllegalArgumentException("The number of points must be less than or equal to 11. " + points
                        + " is not a legal argument.");
            }
        }

        for (int[] frame : scoreCard.getPoints()) {
            if (frame.length != 2) {
                throw new IllegalArgumentException(
                        "Each frame must consist of two numbers. " + frame.length + " is not a legal.");
            }
        }
    }
}
