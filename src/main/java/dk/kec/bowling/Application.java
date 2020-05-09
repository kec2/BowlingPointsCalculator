package dk.kec.bowling;

import java.io.IOException;
import java.util.stream.IntStream;

import dk.kec.bowling.model.Result;
import dk.kec.bowling.model.ScoreCard;
import dk.kec.bowling.model.Status;
import dk.kec.bowling.scoring.BowlingScoring;
import dk.kec.bowling.scoring.BowlingScoringFactory;
import dk.kec.bowling.scoring.BowlingScoringFactory.Scoring;

/**
 *
 * This class is used to start the application.
 *
 * There is a optional System property named GAMES (integer) that runs this
 * application in a loop. The default value is 1 and max is 50.
 *
 * There is another optional System property named DEBUG (boolean) that if true
 * prints extra information. Default value is false.
 *
 */
public class Application {

    public static void main(String[] args) {
        BowlingScoring bowlingScoring = BowlingScoringFactory.create(Scoring.TRADITIONAL);
        int noOfGames = Integer.getInteger("GAMES", 1);
        noOfGames = Math.abs(noOfGames);
        noOfGames = Math.min(noOfGames, 50);

        IntStream.rangeClosed(1, noOfGames).forEach(i -> {
            try {
                ScoreCard scoreCard = RestClient.getPoints();
                System.out.println(scoreCard);

                Result result = bowlingScoring.calculate(scoreCard);
                System.out.println(result);

                Status status = RestClient.validatePoints(result);
                System.out.println("Valid: " + status.isSuccess());

                if (!status.isSuccess()) {
                    System.err.println("FAILED!");
                }
                System.out.println();
            } catch (IOException | InterruptedException e) {
                System.err.println("Error happend while retrieving points.");
                e.printStackTrace();
            }
        });
    }
}
