package seedu.revision.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;

/**
 * A ui for the status bar that is displayed at the header of the application.
 */
public class LevelLabel extends UiPart<Region> {

    private static final String FXML = "LevelLabel.fxml";

    @FXML
    private StackPane innerLevelPlaceholder;

    @FXML
    private Label levelLabel;

    /**
     * Initialises a LevelLabel based on the next level in the quiz.
     * @param nextLevel the next level in the quiz.
     */
    public LevelLabel(int nextLevel) {
        super(FXML);
        updateLevelLabel(nextLevel);
    }

    /**
     * Updates the {@code levelLabel}'s text and color according to the next level.
     * @param nextLevel the next level in the quiz.
     */
    private void updateLevelLabel(int nextLevel) {
        switch (nextLevel) {
        case 2:
            levelLabel.setText("Level 2");
            innerLevelPlaceholder.setStyle("-fx-background-color: #ff8264;");
            break;
        case 3:
            levelLabel.setText("Level 3");
            innerLevelPlaceholder.setStyle("-fx-background-color: #f73859;");
            break;
        default:
            levelLabel.setText("Level 1");
            innerLevelPlaceholder.setStyle("-fx-background-color: #5D5D5A;");
            break;
        }

    }

}
