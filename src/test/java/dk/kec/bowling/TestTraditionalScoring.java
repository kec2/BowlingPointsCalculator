package dk.kec.bowling;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import com.google.gson.Gson;

import dk.kec.bowling.model.Result;
import dk.kec.bowling.model.ScoreCard;
import dk.kec.bowling.scoring.BowlingScoringFactory;
import dk.kec.bowling.scoring.BowlingScoring;
import dk.kec.bowling.scoring.BowlingScoringFactory.Scoring;

public class TestTraditionalScoring {

    private String token = "someValue";
    private static BowlingScoring scoring;

    @BeforeAll
    static void init() {
        scoring = BowlingScoringFactory.create(Scoring.TRADITIONAL);
    }

    @ParameterizedTest(name = "Test case {index}")
    @CsvFileSource(resources = "/test_input_traditional.csv")
    void testGoodInput(String score, String expectedResults) {
        int[][] points = new Gson().fromJson(score, int[][].class);
        ScoreCard scoreCard = new ScoreCard(token, points);
        Result result = scoring.calculate(scoreCard);

        assertEquals(scoreCard.getToken(), result.getToken());

        String finalScore = Arrays.toString(result.getPoints());
        assertEquals(expectedResults, finalScore);
    }

    @Test
    void testNullToken() {
        int[][] points = { { 2, 0 }, { 8, 2 } };
        int[] expectedPoints = { 2, 12 };

        ScoreCard scoreCard = new ScoreCard(null, points);
        Result result = scoring.calculate(scoreCard);
        assertArrayEquals(expectedPoints, result.getPoints());
        assertEquals(scoreCard.getToken(), result.getToken());
    }

    @Test
    void testEmptyPoints() {
        int[][] points = {};
        ScoreCard scoreCard = new ScoreCard(token, points);
        Result result = scoring.calculate(scoreCard);
        assertNotNull(result.getPoints());
        assertEquals(0, result.getPoints().length);
        assertEquals(scoreCard.getToken(), result.getToken());
    }

    @Test
    void testNullScoreCard() {
        Assertions.assertThrows(RuntimeException.class, () -> {
            scoring.calculate(null);
        });
    }

    @Test
    void testNullPoints() {
        int[][] points = null;
        ScoreCard scoreCard = new ScoreCard(token, points);

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            scoring.calculate(scoreCard);
        }, "Null points should be accepted.");
    }

    @Test
    void testToManyPoints() {
        int[][] points = { { 10, 0 }, { 10, 0 }, { 10, 0 }, { 10, 0 }, { 10, 0 }, { 10, 0 }, { 10, 0 }, { 10, 0 },
                { 10, 0 }, { 10, 0 }, { 10, 10 }, { 10, 10 } };
        ScoreCard scoreCard = new ScoreCard(token, points);

        assertThrows(IllegalArgumentException.class, () -> {
            scoring.calculate(scoreCard);
        }, "12 points should not be accepted.");
    }

    @Test
    void testBadFrames() {
        int[][] points = { { 2 }, { 8, 2 } };
        ScoreCard scoreCard = new ScoreCard(null, points);

        assertThrows(IllegalArgumentException.class, () -> {
            scoring.calculate(scoreCard);
        }, "A number is missing in the first frame. That is not legal.");
    }
}
