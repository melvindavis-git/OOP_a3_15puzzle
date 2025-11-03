import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class GUI extends JFrame implements ActionListener {

    JPanel panel = new JPanel();
    JButton[] buttons = new JButton[16];
    ArrayList<String> tileList = new ArrayList<>
            (Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8", "9", "10",
                    "11", "12", "13", "14", "15", ""));
    ArrayList<String> correctOrder = new ArrayList<>(tileList);
    Color emptyColor = Color.LIGHT_GRAY;
    Game game;
    JPanel panel2 = new JPanel();
    JButton restartBtn = new JButton("Restart");
    JButton shuffleBtn = new JButton("Shuffle");
    JButton settingsBtn = new JButton("Settings");
    Color BGcolor;
    Color textColor;

    public GUI() {
        Collections.shuffle(tileList);

        this.add(panel, BorderLayout.CENTER);
        panel.setLayout(new GridLayout(4, 4));

        for (int i = 0; i < 16; i++) {
            buttons[i] = new JButton(tileList.get(i));
            buttons[i].setFocusPainted(false);
            buttons[i].addActionListener(this);
            panel.add(buttons[i]);
            if (buttons[i].getText().isEmpty()) {
                buttons[i].setEnabled(false);
                buttons[i].setBackground(emptyColor);
            }
        }

        this.add(panel2, BorderLayout.SOUTH);
        panel2.add(restartBtn);
        panel2.add(shuffleBtn);
        panel2.add(settingsBtn);
        restartBtn.setFocusPainted(false);
        shuffleBtn.setFocusPainted(false);
        settingsBtn.setFocusPainted(false);
        ActionListener extraBtns = e -> {
            if (e.getSource() == restartBtn) {
                this.dispose();
                new GUI();
            }
            if (e.getSource() == shuffleBtn) {
                game.shuffle();
            }
            if (e.getSource() == settingsBtn) {
                JFrame settingsFrame = new JFrame();

                settingsFrame.pack();
                settingsFrame.setVisible(true);
                settingsFrame.setLocationRelativeTo(this);
                settingsFrame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            }
        };

        restartBtn.addActionListener(extraBtns);
        shuffleBtn.addActionListener(extraBtns);
        settingsBtn.addActionListener(extraBtns);

        game = new Game(buttons, correctOrder, this);

        setSize(600, 600);
        setVisible(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public Color getEmptyColor() {
        return emptyColor;
    }

    public Color getBGcolor() {
        return BGcolor;
    }

    public Color getTextColor() {
        return textColor;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton pressedBtn = (JButton) e.getSource();
        int pressed = game.getBtnPos(pressedBtn);
        int empty = game.getEmptyPos();

        if (game.isNextTo(pressed, empty)) {
            game.btnSwap(pressed, empty);

            if (game.isSolved()) {
                JOptionPane.showMessageDialog(null, "You won!");
            }
        }
    }
}