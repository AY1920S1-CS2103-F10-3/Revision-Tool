package seedu.revision.storage;

import static seedu.revision.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.revision.model.History;

/**
 * @@author khiangleon
 */
public class JsonSerializableHistoryTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new JsonSerializableHistory(new History(null)));
    }

}

