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
}
