package seedu.revision.ui;

import java.util.Comparator;
import java.util.Iterator;
import java.util.Optional;
import java.util.logging.Logger;

import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.beans.property.ReadOnlyDoubleWrapper;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import seedu.revision.commons.core.LogsCenter;
import seedu.revision.logic.MainLogic;
import seedu.revision.logic.QuizLogic;
import seedu.revision.logic.commands.exceptions.CommandException;
import seedu.revision.logic.commands.main.CommandResult;
import seedu.revision.logic.parser.exceptions.ParseException;
import seedu.revision.model.answerable.Answerable;
import seedu.revision.model.answerable.Mcq;
import seedu.revision.model.answerable.Saq;
import seedu.revision.model.answerable.TrueFalse;
import seedu.revision.ui.answers.AnswersGridPane;
import seedu.revision.ui.answers.McqAnswersGridPane;
import seedu.revision.ui.answers.TfAnswersGridPane;


/**
 * The Main Window. Provides the basic application layout containing
 * a menu bar and space where other JavaFX elements can be placed.
 */
public class StartQuizWindow extends Window {

    private MainWindow mainWindow;
    private final Logger logger = LogsCenter.getLogger(getClass());

    // Independent Ui parts residing in this Ui container
    private AnswerableListPanel answerableListPanel;
    private ResultDisplay questionDisplay;
    private AnswersGridPane answersGridPane;
    private CommandBox commandBox;
    private ProgressIndicatorBar progressIndicatorBar;

    private Answerable currentAnswerable;
    private Iterator<Answerable> answerableIterator;
    private int score = 0;

    private ReadOnlyDoubleWrapper currentProgressIndex = new ReadOnlyDoubleWrapper(this, "currentProgressIndex",
            0);

    public StartQuizWindow(Stage primaryStage, MainLogic mainLogic, QuizLogic quizLogic) {
        super(primaryStage, mainLogic, quizLogic);
    }
    public final double getCurrentProgressIndex() {
        return currentProgressIndex.get();
    }
    public final ReadOnlyDoubleProperty currentProgressIndexProperty() {
        return currentProgressIndex.getReadOnlyProperty();
    }

    /**
     * Fills up all the placeholders of this window.
     */
    void fillInnerParts() {
        ObservableList<Answerable> filteredAnswerableList = this.mainLogic.getFilteredAnswerableList();

//        Comparator<Answerable> diffComparator = Comparator.comparing(answerable -> answerable.getDifficulty().value);
//        ObservableList<Answerable> sortedList = filteredAnswerableList.sorted(diffComparator);
        Comparator<Answerable> difficultyComparator = Comparator.comparing(
                answerable -> answerable.getDifficulty().value);
        ObservableList<Answerable> sortedList = this.mainLogic.getFilteredSortedAnswerableList(difficultyComparator);
        answerableIterator = sortedList.iterator();
        currentAnswerable = answerableIterator.next();

        if (currentAnswerable instanceof Mcq) {
            answersGridPane = new McqAnswersGridPane(AnswersGridPane.MCQ_GRID_PANE_FXML, currentAnswerable);
        } else if (currentAnswerable instanceof TrueFalse) {
            answersGridPane = new TfAnswersGridPane(AnswersGridPane.TF_GRID_PANE_FXML, currentAnswerable);
        }
//            } else if (currentAnswerable instanceof Saq) {
//                answersGridPane = new SaqAnswersGridPane(AnswersGridPane.SAQ_GRID_PANE_FXML, currentAnswerable);
//            }
        answerableListPanelPlaceholder.getChildren().add(answersGridPane.getRoot());

        questionDisplay = new ResultDisplay();
        questionDisplay.setFeedbackToUser(currentAnswerable.getQuestion().toString());
        resultDisplayPlaceholder.getChildren().add(questionDisplay.getRoot());

        StatusBarFooter statusBarFooter = new StatusBarFooter(mainLogic.getAddressBookFilePath());
        statusbarPlaceholder.getChildren().add(statusBarFooter.getRoot());

        commandBox = new CommandBox(this::executeCommand);
        commandBoxPlaceholder.getChildren().add(commandBox.getRoot());

        progressIndicatorBar = new ProgressIndicatorBar(currentProgressIndex, filteredAnswerableList.size(),
                "%.0f/" + filteredAnswerableList.size());
        scoreProgressBar.getChildren().add(progressIndicatorBar.getRoot());
    }

    void show() {
        primaryStage.show();
    }

    /**
     * Handles ending of quiz session.
     */
    @FXML
    private void handleEnd() {
        currentProgressIndex.set(currentProgressIndex.get() + 1);
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(null);
        alert.setHeaderText(null);
        alert.setGraphic(null);
        alert.setContentText("Quiz has ended! Your score is " + score + "\n"
                + "Try again?\n"
                + "Press [ENTER] to try again.\n"
                + "Press [ESC] to return to main screen.");

        ButtonType tryAgainButton = new ButtonType(
                "Yes",
                ButtonBar.ButtonData.OK_DONE
        );
        ButtonType endButton = new ButtonType(
                "No",
                ButtonBar.ButtonData.CANCEL_CLOSE
        );

        alert.getButtonTypes().setAll(tryAgainButton, endButton);

        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == tryAgainButton) {
            restartQuiz();
        } else if (result.get() == endButton) {
            handleExit();
        }
    }

    /**
     * Restarts the quiz session by resetting progress.
     */
    private void restartQuiz() {
        fillInnerParts();
        score = 0;
        currentProgressIndex.set(0);
        commandBox.getCommandTextField().requestFocus();
    }

    /**
     * Closes the application.
     */
    @FXML
    protected void handleExit() {
        mainWindow = new MainWindow(getPrimaryStage(), mainLogic, quizLogic);
        mainWindow.show();
        mainWindow.fillInnerParts();
        mainWindow.resultDisplay.setFeedbackToUser("You attempted these questions."
                + "Type 'list' to view your full list of questions again.");
    }

    public AnswerableListPanel getAnswerableListPanel() {
        return answerableListPanel;
    }

    /**
     * Executes the command and returns the result.
     *
     * @see QuizLogic#execute(String, Answerable)
     */
    @Override
    protected CommandResult executeCommand (String commandText) throws CommandException, ParseException {
        try {

//            if (currentAnswerable instanceof Mcq) {
//                answersGridPane = new McqAnswersGridPane(AnswersGridPane.MCQ_GRID_PANE_FXML, currentAnswerable);
//            } else if (currentAnswerable instanceof TrueFalse) {
//                answersGridPane = new TfAnswersGridPane(AnswersGridPane.TF_GRID_PANE_FXML, currentAnswerable);
//            }
//            } else if (currentAnswerable instanceof Saq) {
//                answersGridPane = new SaqAnswersGridPane(AnswersGridPane.SAQ_GRID_PANE_FXML, currentAnswerable);
//            }

            CommandResult commandResult = quizLogic.execute(commandText, currentAnswerable);
            logger.info("Question result: " + commandResult.getFeedbackToUser());
            if (commandResult.getFeedbackToUser().equalsIgnoreCase("correct")) {
                // TODO: KhiangLeon use the updateStatistics() method here or in McqInputCommand#execute.
                //  Both has access to the answerable.
                score++;
            }

            if (!answerableIterator.hasNext()) {
                handleEnd();
                return new CommandResult("Quiz has ended.");
            }

            if (commandResult.isShowHelp()) {
                handleHelp();
            }

            if (commandResult.isExit()) {
                handleExit();
            }

            currentAnswerable = answerableIterator.next();
            currentProgressIndex.set(getCurrentProgressIndex() + 1);
            questionDisplay.setFeedbackToUser(currentAnswerable.getQuestion().toString());
            answersGridPane.updateAnswers(currentAnswerable);

            return commandResult;
        } catch (CommandException | ParseException e) {
            logger.info("Invalid command: " + commandText);
            questionDisplay.setFeedbackToUser(e.getMessage());
            throw e;
        }
    }
}
