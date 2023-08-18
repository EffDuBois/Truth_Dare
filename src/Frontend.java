import java.awt.*;
import java.awt.event.*;
import javax.swing.JOptionPane;
import java.io.IOException;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Frontend extends Frame {
    private final Label numPlayersLabel;
    private final TextField numPlayersField;
    private final TextArea outputArea;
    private final Button startButton;
    private final Button spinButton;
    private final Button truthButton;
    private final Button dareButton;

    private Players[] players;
    private Random random = new Random();
    private Questions question = new Questions();
    private int currentPlayerIndex = 0;

    public Frontend() {
        setTitle("Truth and Dare Game");
        setSize(600, 400);
        setLayout(new BorderLayout());

        numPlayersLabel = new Label("Enter the number of players:");
        numPlayersField = new TextField();
        outputArea = new TextArea();
        startButton = new Button("Start Game");
        spinButton = new Button("Spin the Bottle");
        truthButton = new Button("Truth");
        dareButton = new Button("Dare");

        final Panel inputPanel = new Panel();
        inputPanel.add(numPlayersLabel);
        inputPanel.add(numPlayersField);
        inputPanel.add(startButton);

        final Panel buttonPanel = new Panel();
        buttonPanel.add(spinButton);
        buttonPanel.add(truthButton);
        buttonPanel.add(dareButton);
        spinButton.setEnabled(false);
        truthButton.setEnabled(false);
        dareButton.setEnabled(false);

        add(inputPanel, BorderLayout.NORTH);
        add(outputArea, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        startButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                startGame();
            }
        });

        spinButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                spinBottle();
            }
        });

        truthButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showQuestion(true);
            }
        });

        dareButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showQuestion(false);
            }
        });

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                dispose();
            }
        });
    }

    private void startGame() {
        int numPlayers = Integer.parseInt(numPlayersField.getText());
        players = new Players[numPlayers];
        for (int i = 0; i < numPlayers; i++) {
            String playerName = JOptionPane.showInputDialog("Enter name for player " + (i + 1));
            players[i] = new Players(playerName);
        }

        numPlayersLabel.setEnabled(false);
        numPlayersField.setEnabled(false);
        startButton.setEnabled(false);
        spinButton.setEnabled(true);
        outputArea.setText("Game started with " + numPlayers + " players.\n");
    }

    private void spinBottle() {
        outputArea.setText("");
        outputArea.append("Spinning the bottle...\n");

        int delay = random.nextInt(3, 6); // Random delay in the range 3 to 5 seconds
        try {
            TimeUnit.SECONDS.sleep(delay);
        } catch (InterruptedException e) {
            handleException(e, "Error during delay");
            return; // No need to proceed further
        }

        int n = players.length;
        currentPlayerIndex = random.nextInt(n);
        outputArea.append("It's " + players[currentPlayerIndex].name + "'s turn!\n");
        spinButton.setEnabled(false);
        truthButton.setEnabled(true);
        dareButton.setEnabled(true);
    }

    private void showQuestion(boolean isTruth) {
        outputArea.append(isTruth ? "Truth: " : "Dare: ");
        String questionText;
        try {
            questionText = question.question(isTruth);
            outputArea.append(questionText + "\n");
        } catch (IOException e) {
            handleException(e, "Error loading question");
            return; // No need to proceed further
        }
        truthButton.setEnabled(false);
        dareButton.setEnabled(false);

        int done = JOptionPane.showOptionDialog(this,
                "Did " + players[currentPlayerIndex].name + " complete the challenge?",
                "Answer", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
                null, new String[]{"Yes", "No"}, "Yes");

        if (done == JOptionPane.YES_OPTION) {
            players[currentPlayerIndex].points++;
        }
        updatePointsDisplay();
        spinButton.setEnabled(true);
    }

    private void updatePointsDisplay() {
        outputArea.append("\nPlayer Points:\n");
        for (Players player : players) {
            outputArea.append(player.name + ": " + player.points + " points\n");
        }
    }

    private void handleException(Exception e, String errorMessage) {
        outputArea.append(errorMessage + ": " + e.getMessage() + "\n");
        e.printStackTrace(); // Print the stack trace for debugging purposes
    }

    public static void main(String[] args) {
        Frontend gui = new Frontend();
        gui.setVisible(true);
    }
}
