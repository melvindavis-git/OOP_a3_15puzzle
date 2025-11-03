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

    public GUI() {
        Collections.shuffle(tileList);

        this.add(panel);
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

        setSize(600, 600);
        setVisible(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public Color getEmptyColor(){
        return emptyColor;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}