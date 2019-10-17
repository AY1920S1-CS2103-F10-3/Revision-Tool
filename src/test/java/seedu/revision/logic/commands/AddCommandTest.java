package seedu.revision.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.revision.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.revision.commons.core.GuiSettings;
import seedu.revision.logic.commands.exceptions.CommandException;
import seedu.revision.model.Model;
import seedu.revision.model.ReadOnlyRevisionTool;
import seedu.revision.model.ReadOnlyUserPrefs;
import seedu.revision.model.RevisionTool;
import seedu.revision.model.answerable.Answerable;
import seedu.revision.testutil.AnswerableBuilder;

public class AddCommandTest {

    @Test
    public void constructor_nullAnswerable_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddCommand(null));
    }

    @Test
    public void execute_answerableAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingAnswerableAdded modelStub = new ModelStubAcceptingAnswerableAdded();
        Answerable validAnswerable = new AnswerableBuilder().build();

        CommandResult commandResult = new AddCommand(validAnswerable).execute(modelStub);

        assertEquals(String.format(AddCommand.MESSAGE_SUCCESS, validAnswerable), commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validAnswerable), modelStub.answerablesAdded);
    }

    @Test
    public void execute_duplicateAnswerable_throwsCommandException() {
        Answerable validAnswerable = new AnswerableBuilder().build();
        AddCommand addCommand = new AddCommand(validAnswerable);
        ModelStub modelStub = new ModelStubWithAnswerable(validAnswerable);

        assertThrows(CommandException.class, AddCommand.MESSAGE_DUPLICATE_ANSWERABLE, () -> addCommand
                .execute(modelStub));
    }

    @Test
    public void equals() {
        Answerable alice = new AnswerableBuilder().withName("Alice").build();
        Answerable bob = new AnswerableBuilder().withName("Bob").build();
        AddCommand addAliceCommand = new AddCommand(alice);
        AddCommand addBobCommand = new AddCommand(bob);

        // same object -> returns true
        assertTrue(addAliceCommand.equals(addAliceCommand));

        // same values -> returns true
        AddCommand addAliceCommandCopy = new AddCommand(alice);
        assertTrue(addAliceCommand.equals(addAliceCommandCopy));

        // different types -> returns false
        assertFalse(addAliceCommand.equals(1));

        // null -> returns false
        assertFalse(addAliceCommand.equals(null));

        // different answerable -> returns false
        assertFalse(addAliceCommand.equals(addBobCommand));
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public GuiSettings getGuiSettings() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getRevisionToolFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setRevisionToolFilePath(Path revisionToolFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addAnswerable(Answerable answerable) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setRevisionTool(ReadOnlyRevisionTool newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyRevisionTool getRevisionTool() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasAnswerable(Answerable answerable) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteAnswerable(Answerable target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAnswerable(Answerable target, Answerable editedAnswerable) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Answerable> getFilteredAnswerableList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredAnswerableList(Predicate<Answerable> predicate) {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub that contains a single answerable.
     */
    private class ModelStubWithAnswerable extends ModelStub {
        private final Answerable answerable;

        ModelStubWithAnswerable(Answerable answerable) {
            requireNonNull(answerable);
            this.answerable = answerable;
        }

        @Override
        public boolean hasAnswerable(Answerable answerable) {
            requireNonNull(answerable);
            return this.answerable.isSameAnswerable(answerable);
        }
    }

    /**
     * A Model stub that always accept the answerable being added.
     */
    private class ModelStubAcceptingAnswerableAdded extends ModelStub {
        final ArrayList<Answerable> answerablesAdded = new ArrayList<>();

        @Override
        public boolean hasAnswerable(Answerable answerable) {
            requireNonNull(answerable);
            return answerablesAdded.stream().anyMatch(answerable::isSameAnswerable);
        }

        @Override
        public void addAnswerable(Answerable answerable) {
            requireNonNull(answerable);
            answerablesAdded.add(answerable);
        }

        @Override
        public ReadOnlyRevisionTool getRevisionTool() {
            return new RevisionTool();
        }
    }

}
