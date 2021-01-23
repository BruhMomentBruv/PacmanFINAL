/**
 * @author Jessie Baskauf and Ellie Mamantov
 * The Controller handles user input and coordinates the updating of the model and the view with the help of a timer.
 */

package be.inf1.finalPacman;

import be.inf1.finalPacman.model.PacmanModel;
import be.inf1.finalPacman.view.PacmanView;
import javafx.fxml.FXML;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.application.Platform;
import java.util.Timer;
import java.util.TimerTask;

public class PacmanController implements EventHandler<KeyEvent> {
    final private static double FRAMES_PER_SECOND = 5.0;

    @FXML private Label scoreVakje;
    @FXML private Label levelVakje;
    @FXML private Label gameOverVakje;
    @FXML private PacmanView pacManView;
    private PacmanModel pacManModel;
    private static final String[] levelFiles = {"src/levels/level1.txt", "src/levels/level2.txt", "src/levels/level3.txt"};

    private Timer timer;
    private static int ghostEatingModeCounter;
    private boolean paused;

    public PacmanController() {
        this.paused = false;
    }

    /**
     * Initialize and update the model and view from the first txt file and starts the timer.
     */
    public void initialize() {
        String file = this.getLevelFile(0);
        this.pacManModel = new PacmanModel();
        this.update(PacmanModel.Direction.NONE);
        ghostEatingModeCounter = 25;
        this.startTimer();
    }

    /**
     * Schedules the model to update based on the timer.
     */
    private void startTimer() {
        this.timer = new java.util.Timer();
        TimerTask timerTask = new TimerTask() {
            public void run() {
                Platform.runLater(new Runnable() {
                    public void run() {
                        update(pacManModel.getCurrentDirection());
                    }
                });
            }
        };

        long frameTimeInMilliseconds = (long)(1000.0 / FRAMES_PER_SECOND);
        this.timer.schedule(timerTask, 0, frameTimeInMilliseconds);
    }

    /**
     * Steps the PacmanModel, updates the view, updates score and level, displays Game Over/You Won, and instructions of how to play
     * @param direction the most recently inputted direction for PacMan to move in
     */
    private void update(PacmanModel.Direction direction) {
        this.pacManModel.step(direction);
        this.pacManView.update(pacManModel);
        this.scoreVakje.setText(String.format("Score: %d", this.pacManModel.getScore()));
        this.levelVakje.setText(String.format("Level: %d", this.pacManModel.getLevel()));
        if (pacManModel.isGameOver()) {
            this.gameOverVakje.setText(String.format("GAME OVER"));
            pause();
        }
        if (pacManModel.isYouWon()) {
            this.gameOverVakje.setText(String.format("YOU WON!"));
        }
        //when PacMan is in ghostEatingMode, count down the ghostEatingModeCounter to reset ghostEatingMode to false when the counter is 0
        if (pacManModel.isGhostEatingMode()) {
            ghostEatingModeCounter--;
        }
        if (ghostEatingModeCounter == 0 && pacManModel.isGhostEatingMode()) {
            pacManModel.setGhostEatingMode(false);
        }
    }

    /**
     * Takes in user keyboard input to control the movement of PacMan and start new games
     * @param keyEvent user's key click
     */
    @Override
    public void handle(KeyEvent keyEvent) {
        boolean keyRecognized = true;
        KeyCode code = keyEvent.getCode();
        PacmanModel.Direction direction = PacmanModel.Direction.NONE;
        if (code == KeyCode.LEFT) {
            direction = PacmanModel.Direction.LEFT;
        } else if (code == KeyCode.RIGHT) {
            direction = PacmanModel.Direction.RIGHT;
        } else if (code == KeyCode.UP) {
            direction = PacmanModel.Direction.UP;
        } else if (code == KeyCode.DOWN) {
            direction = PacmanModel.Direction.DOWN;
        } else if (code == KeyCode.G) {
            pause();
            this.pacManModel.startNewGame();
            this.gameOverVakje.setText(String.format(""));
            paused = false;
            this.startTimer();
        } else {
            keyRecognized = false;
        }
        if (keyRecognized) {
            keyEvent.consume();
            pacManModel.setCurrentDirection(direction);
        }
    }

    /**
     * Pause the timer
     */
    public void pause() {
            this.timer.cancel();
            this.paused = true;
    }

    public double getBoardWidth() {
        return PacmanView.CELL_WIDTH * this.pacManView.getColumnCount();
    }

    public double getBoardHeight() {
        return PacmanView.CELL_WIDTH * this.pacManView.getRowCount();
    }

    public static void setGhostEatingModeCounter() {
        ghostEatingModeCounter = 25;
    }

    public static int getGhostEatingModeCounter() {
        return ghostEatingModeCounter;
    }

    public static String getLevelFile(int x)
    {
        return levelFiles[x];
    }

    public boolean getPaused() {
        return paused;
    }
}
