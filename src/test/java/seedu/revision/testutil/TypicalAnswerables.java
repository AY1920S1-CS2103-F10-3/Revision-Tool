package seedu.revision.testutil;

import static seedu.revision.logic.commands.CommandTestUtil.VALID_CATEGORY_GREENFIELD;
import static seedu.revision.logic.commands.CommandTestUtil.VALID_CATEGORY_UML;
import static seedu.revision.logic.commands.CommandTestUtil.VALID_DIFFICULTY_ALPHA;
import static seedu.revision.logic.commands.CommandTestUtil.VALID_DIFFICULTY_BETA;
import static seedu.revision.logic.commands.CommandTestUtil.VALID_QUESTION_ALPHA;
import static seedu.revision.logic.commands.CommandTestUtil.VALID_QUESTION_BETA;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.revision.model.AddressBook;
import seedu.revision.model.answerable.Answer;
import seedu.revision.model.answerable.Answerable;

/**
 * A utility class containing a list of {@code Answerable} objects to be used in tests.
 */
public class TypicalAnswerables {
    public static final Answer MCQ_CORRECT_ANSWER = new Answer("Brownfield");
    public static final Answer MCQ_WRONG_ANSWER_A = new Answer("Greenfield");
    public static final Answer MCQ_WRONG_ANSWER_B = new Answer("Blackfield");
    public static final Answer MCQ_WRONG_ANSWER_C = new Answer("Whitefield");

    public static final ArrayList<Answer> MCQ_VALID_CORRECT_ANSWER_LIST = new ArrayList<>(
            Arrays.asList(MCQ_CORRECT_ANSWER));
    public static final ArrayList<Answer> MCQ_VALID_WRONG_ANSWER_LIST = new ArrayList<>(
            Arrays.asList(MCQ_WRONG_ANSWER_A, MCQ_WRONG_ANSWER_B, MCQ_WRONG_ANSWER_C));

    public static final Answerable MCQ_STUB = new McqBuilder().withQuestion("What type of project is AB3?")
            .withCorrectAnswerList(MCQ_VALID_CORRECT_ANSWER_LIST).withWrongAnswerList(MCQ_VALID_WRONG_ANSWER_LIST)
            .withDifficulty("1").withCategories("LSP", "SOLID", "Week 9").build();
    public static final Answerable B_ANSWERABLE = new McqBuilder().withQuestion("Brownfield or Greenfield?")
            .withCorrectAnswerList(MCQ_VALID_CORRECT_ANSWER_LIST).withWrongAnswerList(MCQ_VALID_WRONG_ANSWER_LIST)
            .withDifficulty("1").withCategories("field", "introduction").build();
    public static final Answerable C_ANSWERABLE = new McqBuilder().withQuestion("Carl Kurz").withDifficulty("1")
            .withCorrectAnswerList(MCQ_VALID_CORRECT_ANSWER_LIST).withWrongAnswerList(MCQ_VALID_WRONG_ANSWER_LIST)
            .build();
    public static final Answerable E_ANSWERABLE = new McqBuilder().withQuestion("Elle Meyer").withDifficulty("1")
            .withCorrectAnswerList(MCQ_VALID_CORRECT_ANSWER_LIST).withWrongAnswerList(MCQ_VALID_WRONG_ANSWER_LIST)
            .build();
    public static final Answerable F_ANSWERABLE = new McqBuilder().withQuestion("Fiona Kunz").withDifficulty("1")
            .withCorrectAnswerList(MCQ_VALID_CORRECT_ANSWER_LIST).withWrongAnswerList(MCQ_VALID_WRONG_ANSWER_LIST)
            .build();

    // Manually added
    public static final Answerable H_ANSWERABLE = new McqBuilder().withQuestion("Hoon Meier")
            .withDifficulty("1").withCorrectAnswerList(MCQ_VALID_CORRECT_ANSWER_LIST)
            .withWrongAnswerList(MCQ_VALID_WRONG_ANSWER_LIST).build();
    public static final Answerable I_ANSWERABLE = new McqBuilder().withQuestion("Ida Mueller")
            .withDifficulty("1").withCorrectAnswerList(MCQ_VALID_CORRECT_ANSWER_LIST)
            .withWrongAnswerList(MCQ_VALID_WRONG_ANSWER_LIST).build();

    // Manually added - Answerable's details found in {@code CommandTestUtil}
    public static final Answerable ALPHA = new McqBuilder().withQuestion(VALID_QUESTION_ALPHA)
            .withDifficulty(VALID_DIFFICULTY_ALPHA).withCorrectAnswerList(MCQ_VALID_CORRECT_ANSWER_LIST)
            .withWrongAnswerList(MCQ_VALID_WRONG_ANSWER_LIST).withCategories(VALID_CATEGORY_UML).build();
    public static final Answerable BETA = new McqBuilder().withQuestion(VALID_QUESTION_BETA)
            .withDifficulty(VALID_DIFFICULTY_BETA).withCorrectAnswerList(MCQ_VALID_CORRECT_ANSWER_LIST)
            .withWrongAnswerList(MCQ_VALID_WRONG_ANSWER_LIST)
            .withCategories(VALID_CATEGORY_GREENFIELD, VALID_CATEGORY_UML).build();

    private TypicalAnswerables() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical answerables.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (Answerable answerable : getTypicalAnswerables()) {
            ab.addAnswerable(answerable);
        }
        return ab;
    }

    public static List<Answerable> getTypicalAnswerables() {
        return new ArrayList<>(Arrays.asList(MCQ_STUB, B_ANSWERABLE, C_ANSWERABLE,
                E_ANSWERABLE, F_ANSWERABLE));
    }
}
