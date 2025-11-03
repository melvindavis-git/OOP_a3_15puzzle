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
}
