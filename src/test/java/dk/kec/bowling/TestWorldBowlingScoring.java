package dk.kec.bowling;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import dk.kec.bowling.model.ScoreCard;
import dk.kec.bowling.scoring.BowlingScoringFactory;
import dk.kec.bowling.scoring.BowlingScoring;
import dk.kec.bowling.scoring.WorldBowlingScoring;
import dk.kec.bowling.scoring.BowlingScoringFactory.Scoring;

/**
 * @author klaus
 *
 */
public class TestWorldBowlingScoring {

    @Test
    void testCase1() {
        int[][] frames = { { 2, 0 }, { 8, 2 } };

        ScoreCard points = new ScoreCard("testCase1", frames);

        BowlingScoring scoring = BowlingScoringFactory.create(Scoring.WORLD_BOWLING);
        assertNotNull(scoring);
        assertEquals(WorldBowlingScoring.class, scoring.getClass());

        assertThrows(RuntimeException.class, () -> {
            scoring.calculate(points);
        }, "World bowling scoring is not expected to be implemented.");
    }
}
