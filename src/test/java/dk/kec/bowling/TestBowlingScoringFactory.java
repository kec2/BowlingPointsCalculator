package dk.kec.bowling;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import dk.kec.bowling.scoring.BowlingScoringFactory;
import dk.kec.bowling.scoring.BowlingScoring;
import dk.kec.bowling.scoring.TraditionalScoring;
import dk.kec.bowling.scoring.WorldBowlingScoring;
import dk.kec.bowling.scoring.BowlingScoringFactory.Scoring;

public class TestBowlingScoringFactory {

    @Test
    void testTraditionalScoring() {
        BowlingScoring calculator = BowlingScoringFactory.create(Scoring.TRADITIONAL);
        assertNotNull(calculator);
        assertEquals(TraditionalScoring.class, calculator.getClass());
    }

    @Test
    void testWorldScoring() {
        BowlingScoring calculator = BowlingScoringFactory.create(Scoring.WORLD_BOWLING);
        assertNotNull(calculator);
        assertEquals(WorldBowlingScoring.class, calculator.getClass());
    }

    @Test
    void testNullScoring() {
        assertThrows(NullPointerException.class, () -> {
            BowlingScoringFactory.create(null);
        }, "A NullPointerException was expected");
    }
}
