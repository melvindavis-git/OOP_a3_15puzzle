import javax.swing.*;
import java.util.ArrayList;

public class Game {

    private JButton[] buttons;
    private final int gameSize = 4;
    private ArrayList<String> correctOrder;
    GUI gui;

    public Game(JButton[] buttons, ArrayList<String> correctOrder, GUI gui) {
        this.buttons = buttons;
        this.correctOrder = correctOrder;
        this.gui = gui;
    }

    public int getBtnPos(JButton btn) {
        for (int i = 0; i < buttons.length; i++) {
            if (buttons[i] == btn) {
                return i;
            }
        }
        return -1;
    }

    public int getEmptyPos() {
        for (int i = 0; i < buttons.length; i++) {
            if (!buttons[i].isEnabled()) {
                return i;
            }
        }
        return -1;
    }

    public void btnSwap(int pressed, int empty) {
        JButton pressedBtn = buttons[pressed];
        JButton emptyBtn = buttons[empty];

        emptyBtn.setText(pressedBtn.getText());
        emptyBtn.setEnabled(true);
        emptyBtn.setBackground(null);

        pressedBtn.setText("");
        pressedBtn.setEnabled(false);
        pressedBtn.setBackground(gui.getEmptyColor());
    }

    public boolean isNextTo(int pressed, int empty) {
        int pressedX = pressed / gameSize;
        int pressedY = pressed % gameSize;

        int emptyX = empty / gameSize;
        int emptyY = empty % gameSize;

        if ((pressedX == emptyX) && (pressedY == emptyY + 1)) {
            return true;
        }
        if ((pressedX == emptyX) && (pressedY == emptyY - 1)) {
            return true;
        }
        if ((pressedY == emptyY) && (pressedX == emptyX + 1)) {
            return true;
        }
        if ((pressedY == emptyY) && (pressedX == emptyX - 1)) {
            return true;
        }
        return false;
    }

    public boolean isSolved() {
        for (int i = 0; i < buttons.length; i++) {
            if (!buttons[i].getText().equals(correctOrder.get(i))) {
                return false;
            }
        }
        return true;
    }
}
