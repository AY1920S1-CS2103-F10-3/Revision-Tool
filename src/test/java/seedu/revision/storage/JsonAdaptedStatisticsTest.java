package seedu.revision.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.revision.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.revision.model.quiz.Statistics;

/**
 * @@author khiangleon
 */
public class JsonAdaptedStatisticsTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new JsonAdaptedStatistics(new Statistics(null)));
    }

    @Test
    public void toModelType_validStatistics_returnsStatistic() throws Exception {
        Statistics statisticsStub = new Statistics("11/19,6/7,5/5,0/7");
        JsonAdaptedStatistics statistics = new JsonAdaptedStatistics(new Statistics("11/19,6/7,5/5,0/7"));

        assertEquals(statisticsStub, statistics.toModelType());
    }

}

