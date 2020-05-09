package dk.kec.bowling.model;

/**
 * This class is used to hold the status of the bowling point validation.
 */
public class Status {
    private boolean success;
    private int[] input;

    /**
     * Instantiate status object.
     *
     * @param success
     *        true if the validation successful
     * @param input
     *        The bowling points that was validated
     */
    public Status(boolean success, int[] input) {
        this.success = success;
        this.input = input;
    }

    /**
     * Get the success.
     *
     * @return the success
     */
    public boolean isSuccess() {
        return success;
    }

    /**
     * Get the input that was validated.
     *
     * @return the input that was validated
     */
    public int[] getInput() {
        return input;
    }

}
