package seedu.revision.logic.commands.main;

import static java.util.Objects.requireNonNull;
import static seedu.revision.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static seedu.revision.logic.parser.CliSyntax.PREFIX_QUESTION;
import static seedu.revision.logic.parser.CliSyntax.PREFIX_DIFFICULTY;
import static seedu.revision.model.Model.PREDICATE_SHOW_ALL_ANSWERABLE;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import seedu.revision.commons.core.Messages;
import seedu.revision.commons.core.index.Index;
import seedu.revision.commons.util.CollectionUtil;
import seedu.revision.logic.commands.Command;
import seedu.revision.logic.commands.exceptions.CommandException;
import seedu.revision.model.Model;
import seedu.revision.model.answerable.Answerable;
import seedu.revision.model.answerable.Difficulty;
import seedu.revision.model.answerable.Mcq;
import seedu.revision.model.answerable.Question;
import seedu.revision.model.answerable.Saq;
import seedu.revision.model.answerable.answer.Answer;
import seedu.revision.model.category.Category;

/**
 * Edits the details of an existing answerable in the test bank.
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the answerable identified "
            + "by the index number used in the displayed answerable list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_QUESTION + "QUESTION] "
            + "[" + PREFIX_DIFFICULTY + "DIFFICULTY] "
            + "[" + PREFIX_CATEGORY + "ADDRESS] "
            + "[" + PREFIX_CATEGORY + "category]...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_DIFFICULTY + "91234567 ";

    public static final String MESSAGE_EDIT_ANSWERABLE_SUCCESS = "Edited Answerable: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_ANSWERABLE = "This question already exists in the test bank.";

    private final Index index;
    private final EditAnswerableDescriptor editAnswerableDescriptor;

    /**
     * @param index of the answerable in the filtered answerable list to edit
     * @param editAnswerableDescriptor details to edit the answerable with
     */
    public EditCommand(Index index, EditAnswerableDescriptor editAnswerableDescriptor) {
        requireNonNull(index);
        requireNonNull(editAnswerableDescriptor);

        this.index = index;
        this.editAnswerableDescriptor = new EditAnswerableDescriptor(editAnswerableDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Answerable> lastShownList = model.getFilteredAnswerableList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_ANSWERABLE_DISPLAYED_INDEX);
        }

        Answerable answerableToEdit = lastShownList.get(index.getZeroBased());
        Answerable editedAnswerable = createEditedAnswerable(answerableToEdit, editAnswerableDescriptor);

        if (!answerableToEdit.isSameAnswerable(editedAnswerable) && model.hasAnswerable(editedAnswerable)) {
            throw new CommandException(MESSAGE_DUPLICATE_ANSWERABLE);
        }

        model.setAnswerable(answerableToEdit, editedAnswerable);
        model.updateFilteredAnswerableList(PREDICATE_SHOW_ALL_ANSWERABLE);
        return new CommandResult(String.format(MESSAGE_EDIT_ANSWERABLE_SUCCESS, editedAnswerable));
    }

    /**
     * Creates and returns a {@code Answerable} with the details of {@code answerableToEdit}
     * edited with {@code editAnswerableDescriptor}.
     */
    private static Answerable createEditedAnswerable(Answerable answerableToEdit, EditAnswerableDescriptor editAnswerableDescriptor) {
        assert answerableToEdit != null;

        Question updatedQuestion = editAnswerableDescriptor.getQuestion().orElse(answerableToEdit.getQuestion());
        ArrayList<Answer> updatedCorrectAnswerList = editAnswerableDescriptor.getCorrectAnswerList()
                .orElse(answerableToEdit.getCorrectAnswerList());
        Difficulty updatedDifficulty = editAnswerableDescriptor.getDifficulty().orElse(answerableToEdit
                .getDifficulty());
        Set<Category> updatedCategories = editAnswerableDescriptor.getCategories().orElse(answerableToEdit
                .getCategories());

        if (answerableToEdit instanceof Mcq) {
            ArrayList<Answer> updatedWrongAnswerList = editAnswerableDescriptor.getWrongAnswerList()
                    .orElse(answerableToEdit.getWrongAnswerList());
            return new Mcq(updatedQuestion, updatedCorrectAnswerList, updatedWrongAnswerList, updatedDifficulty,
                    updatedCategories);
        } else {
            return new Saq(updatedQuestion, updatedCorrectAnswerList, updatedDifficulty, updatedCategories);
        }
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditCommand)) {
            return false;
        }

        // state check
        EditCommand e = (EditCommand) other;
        return index.equals(e.index)
                && editAnswerableDescriptor.equals(e.editAnswerableDescriptor);
    }

    /**
     * Stores the details to edit the answerable with. Each non-empty field value will replace the
     * corresponding field value of the answerable.
     */
    public static class EditAnswerableDescriptor {
        private Question question;
        private ArrayList<Answer> correctAnswerList;
        private ArrayList<Answer> wrongAnswerList;
        private Difficulty difficulty;
        private Set<Category> categories;

        public EditAnswerableDescriptor() {
        }

        /**
         * Copy constructor.
         * A defensive copy of {@code categories} is used internally.
         */
        public EditAnswerableDescriptor(EditAnswerableDescriptor toCopy) {
            setQuestion(toCopy.question);
            setCorrectAnswerList(toCopy.correctAnswerList);
            setWrongAnswerList(toCopy.wrongAnswerList);
            setDifficulty(toCopy.difficulty);
            setCategories(toCopy.categories);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(question, correctAnswerList, wrongAnswerList, difficulty, categories);
        }

        public void setQuestion(Question question) {
            this.question = question;
        }

        public Optional<Question> getQuestion() {
            return Optional.ofNullable(question);
        }

        public void setCorrectAnswerList(ArrayList<Answer> correctAnswerList) {
            this.correctAnswerList = correctAnswerList;
        }

        public Optional<ArrayList<Answer>> getCorrectAnswerList() {
            return Optional.ofNullable(correctAnswerList);
        }

        public void setWrongAnswerList(ArrayList<Answer> wrongAnswerList) {
            this.wrongAnswerList = wrongAnswerList;
        }

        public Optional<ArrayList<Answer>> getWrongAnswerList() {
            return Optional.ofNullable(wrongAnswerList);
        }

        public void setDifficulty(Difficulty difficulty) {
            this.difficulty = difficulty;
        }

        public Optional<Difficulty> getDifficulty() {
            return Optional.ofNullable(difficulty);
        }

        public void setCategories(Set<Category> categories) {
            this.categories = (categories != null) ? new HashSet<>(categories) : null;
        }


        /**
         * Returns an unmodifiable category set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code tags} is null.
         */
        public Optional<Set<Category>> getCategories() {
            return (categories != null) ? Optional.of(Collections.unmodifiableSet(categories)) : Optional.empty();
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditAnswerableDescriptor)) {
                return false;
            }

            // state check
            EditAnswerableDescriptor e = (EditAnswerableDescriptor) other;

            return getQuestion().equals(e.getQuestion())
                    && getCorrectAnswerList().equals(e.getCorrectAnswerList())
                    && getWrongAnswerList().equals(e.getWrongAnswerList())
                    && getDifficulty().equals(e.getDifficulty())
                    && getCategories().equals(e.getCategories());
        }
    }
}
