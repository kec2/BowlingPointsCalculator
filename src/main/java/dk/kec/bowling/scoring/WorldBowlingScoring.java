package dk.kec.bowling.scoring;

import dk.kec.bowling.model.Result;
import dk.kec.bowling.model.ScoreCard;

/**
 *
 * The new scoring system labelled "current frame scoring" or "world bowling
 * scoring" is being introduced to aid spectator understanding of the sport and
 * aimed at increasing the television viewership.
 * <p>
 * Current frame scoring maintains the traditional 10-frame format but awards 30
 * pins for a strike, 10 pins for a spare plus the pinfall of the first shot in
 * the frame and actual pinfall after two shots in an open frame.
 * <p>
 * The maximum score is still 300 and this figure is based on the player
 * receiving 10 consecutive strikes with no bonus pins being awarded in the 10th
 * frame.
 *
 */
public class WorldBowlingScoring implements BowlingScoring {

    /**
     * This has not been implemented yet. Don't call this you will get an exception.
     */
    @Override
    public Result calculate(ScoreCard points) {
        /*
         * strike: 30 (regardless of ensuing rolls' results)
         *
         * spare: 10 plus pinfall on first roll of the current frame
         *
         * open: total pinfall for current frame
         */

        throw new RuntimeException("Not implemented!");
    }
}
