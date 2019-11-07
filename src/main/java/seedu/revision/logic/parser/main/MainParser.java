package seedu.revision.logic.parser.main;

import static seedu.revision.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.revision.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.revision.logic.commands.Command;
import seedu.revision.logic.commands.main.AddCommand;
import seedu.revision.logic.commands.main.ClearCommand;
import seedu.revision.logic.commands.main.DeleteCommand;
import seedu.revision.logic.commands.main.EditCommand;
import seedu.revision.logic.commands.main.ExitCommand;
import seedu.revision.logic.commands.main.FindCommand;
import seedu.revision.logic.commands.main.HelpCommand;
import seedu.revision.logic.commands.main.HistoryCommand;
import seedu.revision.logic.commands.main.ListCommand;
import seedu.revision.logic.commands.main.RestoreCommand;
import seedu.revision.logic.commands.main.StartCommand;
import seedu.revision.logic.commands.main.StatsCommand;
import seedu.revision.logic.parser.exceptions.ParseException;

/**
 * Parses user input.
 */
public class MainParser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public static Command parseCommand(String userInput) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");
        switch (commandWord) {

        case AddCommand.COMMAND_WORD:
            return new AddCommandParser().parse(arguments);

        case EditCommand.COMMAND_WORD:
            return new EditCommandParser().parse(arguments);

        case DeleteCommand.COMMAND_WORD:
            return new DeleteCommandParser().parse(arguments);

        case ClearCommand.COMMAND_WORD:
            return new ClearCommand();

        case FindCommand.COMMAND_WORD:
            return new FindCommandParser().parse(arguments);

        case ListCommand.COMMAND_WORD:
            return new ListCommandParser().parse(arguments);

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        case RestoreCommand.COMMAND_WORD:
            return new RestoreCommand();

        case StartCommand.COMMAND_WORD:
            return new StartCommandParser().parse(arguments);

        case StatsCommand.COMMAND_WORD:
            return new StatsCommand();

        case HistoryCommand.COMMAND_WORD:
            return new HistoryCommand();

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}
