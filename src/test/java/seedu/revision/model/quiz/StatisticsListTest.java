package seedu.revision.model.quiz;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * @@author khiangleon
 */
public class StatisticsListTest {

    private final ObservableList<Statistics> internalList = FXCollections.observableArrayList();

    @Test
    public void testSetStatistics() {
        new StatisticsList().setStatistics(internalList);
    }


    @Test
    public void testAsUnmodifiable() {
        new StatisticsList().asUnmodifiableObservableList();
    }

    @Test
    public void testEquals() {
        new StatisticsList().equals("123");
    }

    @Test
    public void testIterator() {
        new StatisticsList().hashCode();
    }


}
