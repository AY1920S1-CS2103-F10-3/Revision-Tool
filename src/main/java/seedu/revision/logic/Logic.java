package seedu.revision.logic;

import java.nio.file.Path;

import javafx.collections.ObservableList;
import seedu.revision.commons.core.GuiSettings;
import seedu.revision.logic.commands.CommandResult;
import seedu.revision.logic.commands.exceptions.CommandException;
import seedu.revision.logic.parser.exceptions.ParseException;
import seedu.revision.model.ReadOnlyRevisionTool;
import seedu.revision.model.answerable.Answerable;

/**
 * API of the Logic component
 */
public interface Logic {
    /**
     * Executes the command and returns the result.
     * @param commandText The command as entered by the user.
     * @return the result of the command execution.
     * @throws CommandException If an error occurs during command execution.
     * @throws ParseException If an error occurs during parsing.
     */
    CommandResult execute(String commandText) throws CommandException, ParseException;

    /**
     * Returns the RevisionTool.
     *
     * @see seedu.revision.model.Model#getRevisionTool()
     */
    ReadOnlyRevisionTool getRevisionTool();

    /** Returns an unmodifiable view of the filtered list of answerables */
    ObservableList<Answerable> getFilteredAnswerableList();

    /**
     * Returns the user prefs' revision tool file path.
     */
    Path getRevisionToolFilePath();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Set the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);
}
