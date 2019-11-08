package seedu.revision.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;

import seedu.revision.logic.commands.Command;
import seedu.revision.logic.parser.exceptions.ParseException;
import seedu.revision.logic.parser.quiz.QuizCommandParser;
import seedu.revision.model.answerable.Answerable;

/**
 * Contains helper methods for testing command parsers.
 */
public class CommandParserTestUtil {

    /**
     * Asserts that the parsing of {@code userInput} by {@code parser} is successful and the command created
     * equals to {@code expectedCommand}.
     */
    public static void assertParseSuccess(Parser parser, String userInput, Command expectedCommand) {
        try {
            Command command = parser.parse(userInput);
            assertEquals(expectedCommand, command);
        } catch (ParseException pe) {
            throw new IllegalArgumentException("Invalid userInput.", pe);
        }
    }

    /**
     * Asserts that the parsing of {@code userInput} by {@code quizParser} is successful and the command created
     * equals to {@code expectedCommand}.
     */
    public static void assertQuizParseSuccess(QuizCommandParser quizParser, String userInput,
              Answerable answerable, Command expectedCommand) {
        try {
            Command command = quizParser.parseCommand(userInput, answerable);
            assertEquals(expectedCommand, command);
        } catch (ParseException pe) {
            throw new IllegalArgumentException("Invalid userInput.", pe);
        }
    }

    /**
     * Asserts that the parsing of {@code userInput} by {@code parser} is unsuccessful and the error message
     * equals to {@code expectedMessage}.
     */
    public static void assertParseFailure(Parser parser, String userInput, String expectedMessage) {
        try {
            parser.parse(userInput);
            throw new AssertionError("The expected ParseException was not thrown.");
        } catch (ParseException pe) {
            assertEquals(expectedMessage, pe.getMessage());
        }
    }

    /**
     * Asserts that the parsing of {@code userInput} by {@code parser} is unsuccessful and the error message
     * equals to {@code expectedMessage}.
     */
    public static void assertQuizParseFailure(QuizCommandParser parser, String userInput,
              Answerable answerable, String expectedMessage) {
        try {
            parser.parseCommand(userInput, answerable);
            throw new AssertionError("The expected ParseException was not thrown.");
        } catch (ParseException pe) {
            assertEquals(expectedMessage, pe.getMessage());
        }
    }
}
