package seedu.revision.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxAssert;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.matcher.control.LabeledMatchers;

import javafx.application.Platform;

import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

@ExtendWith(ApplicationExtension.class)
class LevelLabelTest {

    private LevelLabel levelLabel;

    /**
     * Will be called with {@code @Before} semantics, i. e. before each test method.
     *
     * @param stage - Will be injected by the test runner.
     */
    @Start
    private void start(Stage stage) {
        levelLabel = new LevelLabel(1);
        levelLabel.getLevelLabel().setId("myLabel");
        stage.setScene(new Scene(new StackPane(levelLabel.getLevelLabel()), 300, 100)); // arbitrary height
        stage.show();
    }

    /**
     * @param robot - Will be injected by the test runner.
     */
    @Test
    public void defaultLevel1Label_shouldDisplayLevel1(FxRobot robot) {
        FxAssert.verifyThat(levelLabel.getLevelLabel(), LabeledMatchers.hasText("Level 1"));
    }

    /**
     * @param robot - Will be injected by the test runner.
     */
    @Test
    public void updateLevelLabel_level2_shouldShowLevel2(FxRobot robot) {
        Platform.runLater(() -> {
            levelLabel.updateLevelLabel(2);
            assertEquals("Level 2", levelLabel.getLevelLabel().getText());
        });
    }

    /**
     * @param robot - Will be injected by the test runner.
     */
    @Test
    public void udpateLevelLabel_level4_failure(FxRobot robot) {
        levelLabel.updateLevelLabel(4);
        assertFalse(levelLabel.getLevelLabel().getText().equals("Level 4"));

    }
}
