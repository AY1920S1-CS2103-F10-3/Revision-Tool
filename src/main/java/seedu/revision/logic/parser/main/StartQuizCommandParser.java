package seedu.revision.logic.parser.main;

import static seedu.revision.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.revision.logic.parser.CliSyntax.PREFIX_MODE;
import static seedu.revision.model.Model.PREDICATE_SHOW_ALL_ANSWERABLE;

import seedu.revision.logic.commands.main.ListCommand;
import seedu.revision.logic.commands.main.StartQuizCommand;
import seedu.revision.logic.parser.ArgumentMultimap;
import seedu.revision.logic.parser.ArgumentTokenizer;
import seedu.revision.logic.parser.Parser;
import seedu.revision.logic.parser.ParserUtil;
import seedu.revision.logic.parser.exceptions.ParseException;
import seedu.revision.model.quiz.Mode;
import seedu.revision.model.quiz.NormalModePredicate;

/**
 * Parses input arguments and creates a new StartQuizCommand object
 */
public class StartQuizCommandParser implements Parser<StartQuizCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the StartQuizCommand
     * and returns a StartQuizCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public StartQuizCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_MODE);

        Mode mode = null;

        if (!argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, StartQuizCommand.MESSAGE_USAGE));
        }

        if (argMultimap.getValue(PREFIX_MODE).isPresent()) {
            mode = ParserUtil.parseMode(argMultimap.getValue(PREFIX_MODE).get());
            switch (mode.mode.toLowerCase()) {
            case "normal":
                return new StartQuizCommand(new NormalModePredicate());
            default:
                throw new ParseException(Mode.MESSAGE_CONSTRAINTS);
            }
        }

        return new StartQuizCommand(PREDICATE_SHOW_ALL_ANSWERABLE);
    }
}
