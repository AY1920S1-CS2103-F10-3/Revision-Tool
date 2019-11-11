package seedu.revision.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.revision.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.revision.model.answerable.Answer;

/**
 * @@author khiangleon
 */
public class JsonAdaptedAnswerTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new JsonAdaptedAnswer(new Answer(null)));
    }

    @Test
    public void toModelType_validCategory_returnsCategory() throws Exception {
        Answer answerStub = new Answer("answer");
        JsonAdaptedAnswer answer = new JsonAdaptedAnswer(new Answer("answer"));

        assertEquals(answerStub, answer.toModelType());
    }

}

