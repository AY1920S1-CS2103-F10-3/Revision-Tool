package seedu.revision.ui;

import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Optional;

import javafx.beans.property.ReadOnlyDoubleWrapper;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import seedu.revision.logic.Logic;
import seedu.revision.logic.commands.exceptions.CommandException;
import seedu.revision.logic.commands.main.CommandResult;
import seedu.revision.logic.commands.main.CommandResultBuilder;
import seedu.revision.logic.parser.exceptions.ParseException;
import seedu.revision.model.answerable.Answerable;
import seedu.revision.model.answerable.Mcq;
import seedu.revision.model.answerable.TrueFalse;
import seedu.revision.model.quiz.Mode;
import seedu.revision.model.quiz.Modes;
import seedu.revision.model.quiz.Statistics;
import seedu.revision.ui.answers.AnswersGridPane;
import seedu.revision.ui.answers.McqAnswersGridPane;
import seedu.revision.ui.answers.SaqAnswersGridPane;
import seedu.revision.ui.answers.TfAnswersGridPane;
import seedu.revision.ui.bar.ProgressIndicatorBar;
import seedu.revision.ui.bar.ScoreProgressAndTimerGridPane;
import seedu.revision.ui.bar.Timer;

/**
 * The Quiz Window. Provides the basic application layout containing
 * a menu bar and space where other JavaFX elements can be placed.
 */
public class StartQuizWindow extends ParentWindow {

    protected static final String FXML = "StartQuizWindow.fxml";

    @FXML
    protected StackPane levelPlaceholder;

    private MainWindow mainWindow;
    private ObservableList<Answerable> quizList;
    private Mode mode;

    // Independent Ui parts residing in this Ui container
    private CommandBox commandBox;
    private LevelLabel levelLabel;
    private ResultDisplay questionDisplay;
    private AnswersGridPane answersGridPane;
    private ProgressIndicatorBar progressIndicatorBar;
    private Timer timer;
    private ScoreProgressAndTimerGridPane progressAndTimerGridPane;

    private Answerable previousAnswerable;
    private Answerable currentAnswerable;
    private Iterator<Answerable> answerableIterator;

    // to keep track of scores
    private Dictionary<String, String> results = new Hashtable<>();
    private Dictionary<String, Integer> raw = new Hashtable<>();

    private int totalScore = 0; //accumulated score for completing all questions in entire quiz

    //to keep track of the total number of questions answered so far at every level of the quiz
    private int accumulatedSize = 0;

    private ReadOnlyDoubleWrapper currentProgressIndex = new ReadOnlyDoubleWrapper(
            this, "currentProgressIndex", 0);

    /**
     * Initialises the GUI when Quiz Mode is started.
     * @param primaryStage the stage where scenes can be added to.
     * @param logic the logic that will be used to drive the quiz.
     * @param mode the mode of the quiz
     */
    public StartQuizWindow(Stage primaryStage, Logic logic, Mode mode) {
        super(FXML, primaryStage, logic);
        this.mode = mode;
        raw.put("total", 0);
        raw.put("difficulty 1", 0);
        raw.put("difficulty 2", 0);
        raw.put("difficulty 3", 0);
        results.put("total", "0");
        results.put("difficulty 1", "0");
        results.put("difficulty 2", "0");
        results.put("difficulty 3", "0");
    }

    /** gets the current progress of the user **/
    public final double getCurrentProgressIndex() {
        return currentProgressIndex.get();
    }

    /**
     * Fills up all the placeholders of this window.
     */
    void fillInnerParts() {
        this.quizList = logic.getFilteredSortedAnswerableList();
        answerableIterator = quizList.iterator();
        currentAnswerable = answerableIterator.next();

        setAnswerGridPaneByType(currentAnswerable);

        questionDisplay = new ResultDisplay();
        questionDisplay.setFeedbackToUser(currentAnswerable.getQuestion().toString());
        resultDisplayPlaceholder.getChildren().add(questionDisplay.getRoot());

        StatusBarFooter statusBarFooter = new StatusBarFooter(logic.getAddressBookFilePath());
        statusbarPlaceholder.getChildren().add(statusBarFooter.getRoot());

        commandBox = new CommandBox(this::executeCommand, false);
        commandBoxPlaceholder.getChildren().add(commandBox.getRoot());

        int nextLevel = Integer.parseInt(quizList.get(0).getDifficulty().difficulty);
        this.timer = new Timer(mode.getTime(nextLevel), this::executeCommand);

        levelLabel = new LevelLabel(nextLevel);
        levelPlaceholder.getChildren().add(levelLabel.getRoot());

        int sizeOfFirstLevel = getSizeOfCurrentLevel(quizList.get(0));
        progressIndicatorBar = new ProgressIndicatorBar(currentProgressIndex, sizeOfFirstLevel,
                "%.0f/" + sizeOfFirstLevel);

        progressAndTimerGridPane = new ScoreProgressAndTimerGridPane(progressIndicatorBar, timer);
        scoreProgressAndTimerPlaceholder.getChildren().add(progressAndTimerGridPane.getRoot());
    }

    private int getSizeOfCurrentLevel(Answerable answerable) {
        ObservableList<Answerable> sectionList = quizList.filtered(a ->
                a.getDifficulty().difficulty.equals(answerable.getDifficulty().difficulty));
        return sectionList.size();
    }

    private void setAnswerGridPaneByType(Answerable currentAnswerable) {
        if (currentAnswerable instanceof Mcq) {
            answersGridPane = new McqAnswersGridPane(currentAnswerable);
        } else if (currentAnswerable instanceof TrueFalse) {
            answersGridPane = new TfAnswersGridPane(currentAnswerable);
        } else {
            answersGridPane = new SaqAnswersGridPane(currentAnswerable);
        }
        answerableListPanelPlaceholder.getChildren().add(answersGridPane.getRoot());
    }

    /**
     * Executes the command and returns the result.
     *
     * @see Logic#execute(String, Answerable)
     */
    @Override
    protected CommandResult executeCommand(String commandText) throws CommandException, ParseException {
        try {
            timer.resetTimer();

            CommandResult commandResult = logic.execute(commandText, currentAnswerable);
            if (commandResult.isCorrect()) {
                totalScore++;
                raw.put("total", raw.get("total") + 1);
                switch (currentAnswerable.getDifficulty().difficulty) {
                case "1":
                    raw.put("difficulty 1", raw.get("difficulty 1") + 1);
                    break;
                case "2":
                    raw.put("difficulty 2", raw.get("difficulty 2") + 1);
                    break;
                case "3":
                    raw.put("difficulty 3", raw.get("difficulty 3") + 1);
                    break;
                default:
                    assert false : currentAnswerable.getDifficulty().difficulty;
                }

                for (Enumeration i = raw.keys(); i.hasMoreElements();) {
                    results.put((String) i.nextElement(), String.format((raw.get(i.nextElement())) + "/"
                            + getSizeOfCurrentLevel(currentAnswerable)));
                }
            }

            if (commandResult.isExit()) {
                handleExit();
                return new CommandResultBuilder().build();
            }

            if (!commandResult.isCorrect() && mode.value.equals(Modes.ARCADE.toString())) {
                handleEnd(currentAnswerable);
                return new CommandResultBuilder().build();
            }

            if (!answerableIterator.hasNext()) {
                handleEnd(currentAnswerable);
                return new CommandResultBuilder().build();
            }

            currentProgressIndex.set(getCurrentProgressIndex() + 1);

            previousAnswerable = currentAnswerable;
            currentAnswerable = answerableIterator.next();

            if (previousAnswerable.getDifficulty().compareTo(currentAnswerable.getDifficulty()) < 0) {
                handleNextLevel(previousAnswerable, currentAnswerable);
            }

            answerableListPanelPlaceholder.getChildren().remove(answersGridPane.getRoot());
            setAnswerGridPaneByType(currentAnswerable);
            answersGridPane.updateAnswers(currentAnswerable);

            questionDisplay.setFeedbackToUser(currentAnswerable.getQuestion().toString());

            return commandResult;
        } catch (CommandException | ParseException e) {
            questionDisplay.setFeedbackToUser(currentAnswerable.getQuestion().toString() + "\n\n" + e.getMessage());
            throw e;
        }
    }

    /**
     * Handles progression to the next level and receives response from the user.
     * @param nextAnswerable next answerable that will be displayed.
     */
    private void handleNextLevel(Answerable currentAnswerable, Answerable nextAnswerable) {
        int nextLevel = Integer.parseInt(nextAnswerable.getDifficulty().difficulty);

        accumulatedSize = accumulatedSize + getSizeOfCurrentLevel(currentAnswerable);
        AlertDialog nextLevelDialog = AlertDialog.getNextLevelAlert(nextLevel, totalScore, accumulatedSize);

        Task<Void> task = new Task<>() {
            @Override
            public Void call() {
                timer.stopTimer();
                return null;
            }
        };

        task.setOnSucceeded(e -> {
            Optional<ButtonType> result = nextLevelDialog.showAndWait();
            if (result.get() == nextLevelDialog.getNoButton()) {
                handleExit();
            } else {
                //Reset UI in the window
                levelLabel.updateLevelLabel(nextLevel);
                currentProgressIndex.set(0);
                progressIndicatorBar = new ProgressIndicatorBar(currentProgressIndex,
                        getSizeOfCurrentLevel(nextAnswerable),
                        "%.0f/" + getSizeOfCurrentLevel(nextAnswerable));
                //Start a new timer for the next level
                this.timer = new Timer(mode.getTime(nextLevel), this::executeCommand);
                progressAndTimerGridPane = new ScoreProgressAndTimerGridPane(progressIndicatorBar, timer);
                scoreProgressAndTimerPlaceholder.getChildren().add(progressAndTimerGridPane.getRoot());
            }
        });

        //Start the event on a new thread so that showAndWait event is not conflicted with timer animation.
        new Thread(task).start();
    }

    /**
     * Handles ending of quiz session.
     */
    @FXML
    private void handleEnd(Answerable currentAnswerable) {
        accumulatedSize = accumulatedSize + getSizeOfCurrentLevel(currentAnswerable);
        currentProgressIndex.set(currentProgressIndex.get() + 1);
        boolean isFailure = mode.value.equals(Modes.ARCADE.toString()) && answerableIterator.hasNext();

        AlertDialog endAlert = AlertDialog.getEndAlert(totalScore, accumulatedSize, isFailure);

        Task<Void> task = new Task<>() {
            @Override
            public Void call() {
                timer.stopTimer();
                return null;
            }
        };

        task.setOnSucceeded(e -> {
            Optional<ButtonType> result = endAlert.showAndWait();
            if (result.get() == endAlert.getNoButton()) {
                handleExit();
            } else {
                restartQuiz();
            }
        });

        if (mode.value.equals(Modes.NORMAL.toString())) {
            Statistics newResult = new Statistics(results);
            logic.updateHistory(newResult);
        }

        //Start the event on a new thread so that showAndWait event is not conflicted with timer animation.
        new Thread(task).start();

    }

    /**
     * Restarts the quiz session and resets the progress.
     */
    private void restartQuiz() {
        answerableListPanelPlaceholder.getChildren().remove(answersGridPane.getRoot());
        fillInnerParts();
        raw.put("total", 0);
        raw.put("difficulty 1", 0);
        raw.put("difficulty 2", 0);
        raw.put("difficulty 3", 0);
        results.put("total", "0");
        results.put("difficulty 1", "0");
        results.put("difficulty 2", "0");
        results.put("difficulty 3", "0");
        totalScore = 0;
        accumulatedSize = 0;
        currentProgressIndex.set(0);
        commandBox.getCommandTextField().requestFocus();
    }

    /**
     * Closes the application.
     */
    @FXML
    protected void handleExit() {
        timer.stopTimer();
        logic.removeFiltersFromAnswerableList();
        mainWindow = new MainWindow(getPrimaryStage(), logic);
        mainWindow.show();
        mainWindow.fillInnerParts();
        mainWindow.resultDisplay.setFeedbackToUser("Great attempt! Type 'start mode/MODE' "
                + "(normal / arcade / custom) to try another quiz!");
    }

    public StackPane getLevelPlaceholder() {
        return levelPlaceholder;
    }

    public CommandBox getCommandBox() {
        return commandBox;
    }

    public LevelLabel getLevelLabel() {
        return levelLabel;
    }

    public ResultDisplay getQuestionDisplay() {
        return questionDisplay;
    }

    public AnswersGridPane getAnswersGridPane() {
        return answersGridPane;
    }

    public ProgressIndicatorBar getProgressIndicatorBar() {
        return progressIndicatorBar;
    }

    public Timer getTimer() {
        return timer;
    }
}
