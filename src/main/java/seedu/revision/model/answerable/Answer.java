package seedu.revision.model.answerable;

import static java.util.Objects.requireNonNull;
import static seedu.revision.commons.util.AppUtil.checkArgument;

/**
 * Represents a Answer in the revision tool.
 * Guarantees: immutable; name is valid as declared in {@link #isValidAnswer(String)}
 */
public class Answer {

    public static final String MESSAGE_CONSTRAINTS = "Answers should not be blank and cannot be duplicates";
    public static final String VALIDATION_REGEX = ".*";

    private String answer;

    /**
     * Default Constructor for Answer.
     */
    public Answer() {}

    /**
     * Constructs a {@code Answer}.
     *
     * @param answer A valid category name.
     */
    public Answer(String answer) {
        requireNonNull(answer);
        checkArgument(isValidAnswer(answer), MESSAGE_CONSTRAINTS);
        this.answer = answer;
    }

    /**
     * Returns true if a given string is a valid category name.
     */
    public static boolean isValidAnswer(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Answer // instanceof handles nulls
                && answer.equals(((Answer) other).answer)); // state check
    }

    @Override
    public int hashCode() {
        return answer.hashCode();
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return answer;
    }

}
