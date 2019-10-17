package seedu.revision.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.revision.commons.core.Messages;
import seedu.revision.commons.core.index.Index;
import seedu.revision.logic.commands.exceptions.CommandException;
import seedu.revision.model.Model;
import seedu.revision.model.answerable.Answerable;

/**
 * Deletes a answerable identified using it's displayed index from the revision tool.
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the answerable identified by the index number used in the displayed answerable list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_ANSWERABLE_SUCCESS = "Deleted Answerable: %1$s";

    private final Index targetIndex;

    public DeleteCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Answerable> lastShownList = model.getFilteredAnswerableList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_ANSWERABLE_DISPLAYED_INDEX);
        }

        Answerable answerableToDelete = lastShownList.get(targetIndex.getZeroBased());
        model.deleteAnswerable(answerableToDelete);
        return new CommandResult(String.format(MESSAGE_DELETE_ANSWERABLE_SUCCESS, answerableToDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteCommand // instanceof handles nulls
                && targetIndex.equals(((DeleteCommand) other).targetIndex)); // state check
    }
}
