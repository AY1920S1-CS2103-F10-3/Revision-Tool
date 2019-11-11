package seedu.revision.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.revision.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.revision.model.category.Category;

/**
 * @@author khiangleon
 */
public class JsonAdaptedCategoryTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new JsonAdaptedCategory(new Category(null)));
    }

    @Test
    public void toModelType_validCategory_returnsCategory() throws Exception {
        Category categoryStub = new Category("category");
        JsonAdaptedCategory category = new JsonAdaptedCategory(new Category("category"));

        assertEquals(categoryStub, category.toModelType());
    }

}

