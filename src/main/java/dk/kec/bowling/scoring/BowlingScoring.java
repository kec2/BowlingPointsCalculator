package dk.kec.bowling.scoring;

import dk.kec.bowling.model.Result;
import dk.kec.bowling.model.ScoreCard;

public interface BowlingScoring {

    /**
     * Calculate the points in a given score card.
     *
     * @param scoreCard the score card to calculate on
     * @return a Result with the same token as the score card and points for each of
     *         the frames in the score card
     */
    Result calculate(ScoreCard scoreCard);
}
